import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class Server {
  private static Map<String, String> users = new HashMap<>();
  private static Map<String, Integer> userMovieStatus = new HashMap<>();

  public static void main(String[] args) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
    server.createContext("/login", new LoginHandler());
    server.createContext("/movie", new MovieHandler());
    server.createContext("/status", new StatusHandler());
    server.setExecutor(Executors.newFixedThreadPool(10));
    server.start();
    System.out.println("Server started on port 8000");
  }

  static class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      if ("POST".equals(exchange.getRequestMethod())) {
        Headers headers = exchange.getRequestHeaders();
        String username = headers.getFirst("Username");
        String password = headers.getFirst("Password");

        if (username != null && password != null) {
          users.put(username, password);
          String response = "Login successful";
          exchange.sendResponseHeaders(200, response.length());
          OutputStream os = exchange.getResponseBody();
          os.write(response.getBytes());
          os.close();
        } else {
          String response = "Invalid login";
          exchange.sendResponseHeaders(400, response.length());
          OutputStream os = exchange.getResponseBody();
          os.write(response.getBytes());
          os.close();
        }
      }
    }
  }

  static class MovieHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      if ("GET".equals(exchange.getRequestMethod())) {
        String response = "Movie content with subtitles";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
      }
    }
  }

  static class StatusHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      if ("POST".equals(exchange.getRequestMethod())) {
        Headers headers = exchange.getRequestHeaders();
        String username = headers.getFirst("Username");
        String movieId = headers.getFirst("MovieId");
        String lastSubtitle = headers.getFirst("LastSubtitle");

        if (username != null && movieId != null && lastSubtitle != null) {
          userMovieStatus.put(username + "_" + movieId, Integer.parseInt(lastSubtitle));
          String response = "Status saved";
          exchange.sendResponseHeaders(200, response.length());
          OutputStream os = exchange.getResponseBody();
          os.write(response.getBytes());
          os.close();
        } else {
          String response = "Invalid status update";
          exchange.sendResponseHeaders(400, response.length());
          OutputStream os = exchange.getResponseBody();
          os.write(response.getBytes());
          os.close();
        }
      } else if ("GET".equals(exchange.getRequestMethod())) {
        Headers headers = exchange.getRequestHeaders();
        String username = headers.getFirst("Username");
        String movieId = headers.getFirst("MovieId");

        if (username != null && movieId != null) {
          Integer lastSubtitle = userMovieStatus.get(username + "_" + movieId);
          String response = lastSubtitle != null ? lastSubtitle.toString() : "No status found";
          exchange.sendResponseHeaders(200, response.length());
          OutputStream os = exchange.getResponseBody();
          os.write(response.getBytes());
          os.close();
        } else {
          String response = "Invalid status request";
          exchange.sendResponseHeaders(400, response.length());
          OutputStream os = exchange.getResponseBody();
          os.write(response.getBytes());
          os.close();
        }
      }
    }
  }
}



class MovieHandler {

}