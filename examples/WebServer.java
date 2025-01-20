import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;

public class WebServer {
  private static final String TASK_ENDPOINT = "/task";
  private static final String STATUS_ENDPOINT = "/status";

  private final int port;
  private HttpServer server;

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

    // CON LOS CONTEXTOS SE AGREGAN LOS ENDPOINTS A LOS QUE SE PUEDE ACCEDER
    HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
    HttpContext taskContext = server.createContext(TASK_ENDPOINT);

    // DE ESTA FORMA SE ASIGNA UNA FUNCION A CADA ENDPOINT CORRESPONDIENTE
    statusContext.setHandler(this::handleStatusCheckRequest);
    taskContext.setHandler(this::handleTaskRequest);

    // AQUI SE DEFINEN LOS HILOS QUE SE VAN A USAR PARA ATENDER LAS PETICIONES, EN ESTE CASO 8
    server.setExecutor(Executors.newFixedThreadPool(8));
    server.start();
  }

  private void handleTaskRequest(HttpExchange exchange) throws IOException {
    // SI NO ES UNA PETICION POST SE CIERRA LA CONEXION
    if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
      exchange.close();
      return;
    }

    // SE OBTIENEN LOS HEADERS DE LA PETICION EN CASO DE USARSE, PUEDE ELIMINARSE
    Headers headers = exchange.getRequestHeaders();
    if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {
      String dummyResponse = "123\n";
      sendResponse(dummyResponse.getBytes(), exchange);
      return;
    }

    boolean isDebugMode = false;
    if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
      isDebugMode = true;
    }

    long startTime = System.nanoTime();

    // SE OBTIENE EL CUERPO DE LA PETICION
    byte[] requestBytes = exchange.getRequestBody().readAllBytes();
    byte[] responseBytes = calculateResponse(requestBytes);

    // AQUI YA SE PUEDE DEFINIR LA LOGICA DEL ENDPOINT
    long finishTime = System.nanoTime();

    if (isDebugMode) {
      String debugMessage = String.format("La operación tomó %d nanosegundos", finishTime - startTime);
      exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
    }

    // SE ENVIA LA RESPUESTA AL CLIENTE
    sendResponse(responseBytes, exchange);
  }

  private byte[] calculateResponse(byte[] requestBytes) {
    String bodyString = new String(requestBytes);
    String[] stringNumbers = bodyString.split(",");

    BigInteger result = BigInteger.ONE;

    for (String number : stringNumbers) {
      BigInteger bigInteger = new BigInteger(number);
      result = result.multiply(bigInteger);
    }

    return String.format("El resultado de la multiplicación es %s\n", result).getBytes();
  }

  private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
    if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
      exchange.close();
      return;
    }

    String responseMessage = "El servidor está vivo\n";
    sendResponse(responseMessage.getBytes(), exchange);
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
