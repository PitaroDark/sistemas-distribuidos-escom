/*
 *  MIT License
 *
 *  Copyright (c) 2019 Michael Pogrebinsky - Distributed Systems & Cloud Computing with Java
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class WebServer {
  private static final String PRINT_ENDPOINT = "/print";
  private static final String TASK_ENDPOINT = "/task";
  private static final String STATUS_ENDPOINT = "/status";
  private static final String SEARCHTOKEN_ENDPOINT = "/searchtoken";

  private final int port;
  private HttpServer server;

  public static void main(String[] args) {
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

    HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
    HttpContext taskContext = server.createContext(TASK_ENDPOINT);
    HttpContext searchtokenContext = server.createContext(SEARCHTOKEN_ENDPOINT);
    HttpContext printContext = server.createContext(PRINT_ENDPOINT);

    statusContext.setHandler(this::handleStatusCheckRequest);
    taskContext.setHandler(this::handleTaskRequest);
    searchtokenContext.setHandler(this::handleSearchTokenRequest);
    printContext.setHandler(this::handlePrintRequest);

    server.setExecutor(Executors.newFixedThreadPool(8));
    server.start();
  }

  private void handlePrintRequest(HttpExchange exchange) throws IOException {
    if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
      exchange.close();
      return;
    }

    String queryParams = exchange.getRequestURI().getQuery();
    String textToPrint = queryParams.split("=")[1];

    System.out.println("Texto recibido: " + textToPrint);

    if (textToPrint.equals("EXIT")) {
      System.out.println("El servidor se detendrá");
      String exitResponse = "Servidor deteniéndose...";
      sendResponse(exitResponse.getBytes(), exchange);
      server.stop(0);
      return;
    }

    String successResponse = "Mensaje recibido e impreso correctamente\n";
    sendResponse(successResponse.getBytes(), exchange);
  }

  private void handleTaskRequest(HttpExchange exchange) throws IOException {
    if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
      exchange.close();
      return;
    }

    Headers headers = exchange.getRequestHeaders();

    for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
      String key = entry.getKey();
      List<String> values = entry.getValue();

      System.out.println(key + "=" + values.toString());
    }

    if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {
      String dummyResponse = "123\n";
      sendResponse(dummyResponse.getBytes(), exchange);
      return;
    }

    boolean isDebugMode = false;
    if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
      isDebugMode = true;
    }

    if (headers.containsKey("EXIT") && headers.get("EXIT").get(0).equalsIgnoreCase("true")) {
      System.out.println("El servidor se detendra");
      server.stop(0);
      return;
    }

  }

  private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
    if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
      exchange.close();
      return;
    }

    String responseMessage = "El servidor está vivo\n";
    sendResponse(responseMessage.getBytes(), exchange);
  }

  private void handleSearchTokenRequest(HttpExchange exchange) throws IOException {
    if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
      exchange.close();
      return;
    }

    Headers headers = exchange.getRequestHeaders();

    boolean isDebugMode = false;
    if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
      isDebugMode = true;
    }

  }

  private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
    exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
    exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
    exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
    exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    exchange.sendResponseHeaders(200, responseBytes.length);
    OutputStream outputStream = exchange.getResponseBody();
    outputStream.write(responseBytes);
    outputStream.flush();
    outputStream.close();
    exchange.close();
  }
}
