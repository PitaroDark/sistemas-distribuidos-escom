import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.net.InetSocketAddress;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.io.File;

public class Balanceador {

  private static final int PORT = 8000;
  private static final int LIBROS = 45;
  private static final String[] SERVIDORES_PORTS = { "8001", "8002", "8003" };
  private static final HttpClient httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_1_1)
      .connectTimeout(Duration.ofSeconds(30))
      .build();

  public static void main(String[] args) {
    Balanceador balanceador = new Balanceador();
    balanceador.iniciarServidor();
  }

  public void iniciarServidor() {
    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
      server.createContext("/search", this::balanceadorHandler);
      server.setExecutor(Executors.newFixedThreadPool(2));
      server.start();
      System.out.println("Balanceador ejecutandose en " + PORT);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void balanceadorHandler(HttpExchange exchange) throws IOException {
    exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
    exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, OPTIONS");
    exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

    // Manejo de preflight (OPTIONS)
    if ("OPTIONS".equals(exchange.getRequestMethod())) {
      exchange.sendResponseHeaders(204, -1); // No content
      return;
    }

    if (!exchange.getRequestMethod().equals("POST")) {
      byte[] responseBytes = "Método no permitido".getBytes();
      enviarRespuesta(exchange, responseBytes);
      return;
    }

    byte[] requestBytes = exchange.getRequestBody().readAllBytes();
    String bodyString = new String(requestBytes);
    int nPalabras = Integer.parseInt(bodyString);

    // PEDIR PALABRAS A LOS SERVIDORES DE MANERA PARALELA CON HILOS O POOL DE HILOS
    // Y OBTENER LAS RESPUESTAS USANDO COMPLECTABLE FUTURE

    @SuppressWarnings("unchecked")
    CompletableFuture<HttpResponse<String>>[] futures = (CompletableFuture<HttpResponse<String>>[]) new CompletableFuture<?>[LIBROS];
    for (int i = 0; i < LIBROS; i++) {
      int index = i % SERVIDORES_PORTS.length;
      int libro = i;
      futures[i] = CompletableFuture.supplyAsync(() -> pedirPalabras(SERVIDORES_PORTS[index], nPalabras, libro));
    }

    // OBTENER TODAS LAS RESPUESTAS EN UN ARREGLO DE STRINGS
    String[] palabras = new String[SERVIDORES_PORTS.length];
    for (int i = 0; i < SERVIDORES_PORTS.length; i++) {
      palabras[i] = futures[i].join().body();
    }

    // REALIZAR EL FORMATEO DE <Libro>:<Palabra>,<Palabra>,<Palabra>... -
    // <Libro>:<Palabra>,<Palabra>,<Palabra>... ESO A UN STRING MAS DETALLADO
    // ES DECIR LOS LIBROS <Libro> CONTIENEN LAS PALABRAS
    // <Palabra>,<Palabra>,<Palabra>...\n
    // <Libro> CONTIENEN LAS PALABRAS <Palabra>,<Palabra>,<Palabra>...\n...
    // DE TODAS LAS RESPUESTAS
    StringBuilder responseBuilder = new StringBuilder();
    for (String palabra : palabras) {
      String[] libros = palabra.split("-");
      for (String libro : libros) {
        String splited[] = libro.split(":");
        responseBuilder.append("Los libros " + splited[0] + " contienen las palabras:");
        responseBuilder.append(splited[1]);
        responseBuilder.append("\n");
      }
    }
    if (responseBuilder.length() == 0)
      responseBuilder.append("No se encontraron palabras comunes en los libros");

    enviarRespuesta(exchange, responseBuilder.toString().getBytes());
  }

  public HttpResponse<String> pedirPalabras(String port, int nPalabras, int libro) {
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .POST(HttpRequest.BodyPublishers.ofString(nPalabras + ";" + libro))
          .uri(URI.create("http://localhost:" + port + "/text"))
          .build();
      return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void enviarRespuesta(HttpExchange exchange, byte[] responseBytes) throws IOException {
    exchange.sendResponseHeaders(200, responseBytes.length);
    OutputStream outputStream = exchange.getResponseBody();
    outputStream.write(responseBytes);
    outputStream.flush();
    outputStream.close();
    exchange.close();
  }

}
