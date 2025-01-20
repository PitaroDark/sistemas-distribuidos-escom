import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.util.Scanner;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import java.io.File;

public class WebServer {
  private static final String READER_ENDPOINT = "/reader";
  private static final String READER_FILE = "lines.txt";

  private final int port;
  private HttpServer server;

  private Scanner readerScanner;

  public static void main(String[] args) {
    // EL MAIN SOLO SIRVE PARA INICIALIZAR EL SERVIDOR
    int serverPort = 8080;
    if (args.length == 1) {
      serverPort = Integer.parseInt(args[0]);
    }

    WebServer webServer = new WebServer(serverPort);
    webServer.startServer();

    System.out.println("Servidor escuchando en el puerto " + serverPort);
  }

  public WebServer(int port) {
    this.port = port;
  }

  public void startServer() {
    try {
      this.server = HttpServer.create(new InetSocketAddress(port), 0);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    HttpContext readerContext = server.createContext(READER_ENDPOINT);

    readerContext.setHandler(this::handleReaderRequest);

    // LEEMOS EL ARCHIVO Y LO GUARDAMOS EN EL SCANNER
    try {
      File file = new File(READER_FILE);
      this.readerScanner = new Scanner(file);
    } catch (Exception e) {
      e.printStackTrace();
    }

    server.setExecutor(Executors.newFixedThreadPool(8));
    server.start();
  }

  private void handleReaderRequest(HttpExchange exchange) throws IOException {
    // SI NO ES UNA PETICION GET SE CIERRA LA CONEXION
    if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
      exchange.close();
      return;
    }

    // SE OBTIENE LA LINEA CORRESPONDIENTE DEL ARCHIVO
    if (this.readerScanner.hasNextLine()) {
      String responseMessage = this.readerScanner.nextLine() + "\n";
      sendResponse(responseMessage.getBytes(), exchange);
    } else {
      sendResponse("No hay más líneas en el archivo\n".getBytes(), exchange);
    }

  }

  private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
    exchange.sendResponseHeaders(200, responseBytes.length);
    OutputStream outputStream = exchange.getResponseBody();
    outputStream.write(responseBytes);
    outputStream.flush();
    outputStream.close();
    exchange.close();
  }
}
