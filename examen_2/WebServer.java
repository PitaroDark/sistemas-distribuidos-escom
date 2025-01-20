import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class WebServer {
  private static final String ENPOINT = "/line";

  private HttpServer server;
  private ArrayList<String> lines;
  private Iterator<String> linesIterator;

  public static void main(String[] args) {
    WebServer webServer = new WebServer();
    webServer.startServer();
    System.out.println("Servidor ejecutandose en " + 8080);
  }

  public WebServer() {
    lines = new ArrayList<>();
    llenarArrayList();
  }

  public void startServer() {
    try {
      this.server = HttpServer.create(new InetSocketAddress(8080), 0);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    HttpContext endpointContext = server.createContext(ENPOINT);
    endpointContext.setHandler(this::handleReq);
    server.setExecutor(Executors.newFixedThreadPool(8));
    server.start();
  }

  private void llenarArrayList() {
    try {
      File file = new File("archivo.txt");
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
      reader.close();
      linesIterator = lines.iterator();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void handleReq(HttpExchange exchange) throws IOException {
    String response = (linesIterator.hasNext()) ? linesIterator.next() : "-";
    sendResponse(response.getBytes(), exchange);
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
