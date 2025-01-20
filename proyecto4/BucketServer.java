import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.CompletableFuture;

public class BucketServer {
  private static final int PORT = 8001;
  private static final String ENDPOINT_USER = "/user";
  private static final String ENDPOINT_MOVIE = "/movie";

  public static void main(String[] args) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
    server.createContext(ENDPOINT_USER, new UserHandler());
    server.createContext(ENDPOINT_MOVIE, new MovieHandler());
    server.setExecutor(Executors.newFixedThreadPool(4));
    server.start();
    System.out.println("Bucket Server started on port " + PORT);
  }

}

class BucketClient {
  private static final String BUCKET_NAME = "neflis_bucket";
  private static final String BASE_URL_BUCKET = "https://storage.googleapis.com/" + BUCKET_NAME;

  public BufferedReader get(String fileName) throws IOException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL_BUCKET + "/" + fileName)).GET().build();

    CompletableFuture<HttpResponse<InputStream>> response = client.sendAsync(request,
        HttpResponse.BodyHandlers.ofInputStream());
    return new BufferedReader(new InputStreamReader(response.join().body()));
  }

  public HashMap<String, String> getJson(String fileName) throws IOException {
    BufferedReader reader = get(fileName);
    HashMap<String, String> json = new HashMap<>();
    String line;
    while ((line = reader.readLine()) != null) {
      String[] keyValue = line.split(":");
      json.put(keyValue[0], keyValue[1]);
    }
    return json;
  }

  public boolean createFile(String fileName, String content) throws IOException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL_BUCKET))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(content))
        .build();

    CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    return response.join().statusCode() == 201;
  }

  public boolean putFile(String fileName, String content, String content_type) throws IOException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL_BUCKET + "/" + fileName))
        .header("Content-Type", content_type)
        .PUT(HttpRequest.BodyPublishers.ofString(content))
        .build();

    CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    return response.join().statusCode() == 200;
  }

  public boolean deleteFile(String fileName) throws IOException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL_BUCKET + "/" + fileName))
        .DELETE()
        .build();

    CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    return response.join().statusCode() == 204;
  }

}

class UserHandler extends HttpReqResHandler implements HttpHandler {
  private BucketClient bucketClient = new BucketClient();

  private void post(HttpExchange exchange) throws IOException {
    HashMap<String, String> body = parseRequestBody(exchange);
    String user = body.get("user");
    String file = body.get("file");
    if (file == null) {
      sendResponseJson(exchange, new HashMap<String, String>() {
        {
          put("message", "Invalid request body");
        }
      }, 400);
      return;
    }
    String filename = user + "/" + file;
    // Get file
    BufferedReader reader = bucketClient.get(filename);
    final StringBuilder content = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      content.append(line).append("\n");
    }
    sendResponse(exchange, content.toString(), 200);
  }

  private void patch(HttpExchange exchange) throws IOException {
    HashMap<String, String> body = parseRequestBody(exchange);
    String user = body.get("user");
    String file = body.get("file");
    String content = body.get("content");
    System.out.println(user + " " + file + " " + content);
    if (file == null || content == null) {
      sendResponseJson(exchange, new HashMap<String, String>() {
        {
          put("message", "Invalid request body");
        }
      }, 400);
      return;
    }
    String filename = user + "/" + file;
    // Put file
    String contentType = (file.endsWith(".json")) ? "application/json" : "text/plain";
    boolean success = bucketClient.putFile(filename, content, contentType);
    if (success) {
      sendResponseJson(exchange, new HashMap<String, String>() {
        {
          put("message", "File updated successfully");
        }
      }, 200);
    } else {
      sendResponseJson(exchange, new HashMap<String, String>() {
        {
          put("message", "Error updating file");
        }
      }, 500);
    }
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String methodRequest = exchange.getRequestMethod();
    if ("POST".equals(methodRequest)) {
      post(exchange);
    } else if ("PATCH".equals(methodRequest)) {
      patch(exchange);
    } else {
      sendResponseJson(exchange, new HashMap<String, String>() {
        {
          put("message", "Invalid request method");
        }
      }, 405);
    }
  }
}

class MovieHandler extends HttpReqResHandler implements HttpHandler {
  private BucketClient bucketClient = new BucketClient();

  private void post(HttpExchange exchange) throws IOException {
    HashMap<String, String> body = parseRequestBody(exchange);
    String movie = body.get("movie");
    String numberSubtitle = body.get("numberSubtitle");
    BufferedReader reader = bucketClient.get(movie + ".srt");
    final StringBuilder subtitle = new StringBuilder();
    String line;
    int number = (numberSubtitle != null) ? Integer.parseInt(numberSubtitle) : 1;
    while ((line = reader.readLine()) != null) {
      if (line.equals(String.valueOf(number))) {
        subtitle.append(line).append("\n");
        while ((line = reader.readLine()) != null && !line.isEmpty())
          subtitle.append(line).append("\n");
        break;
      }
    }
    sendResponse(exchange, subtitle.toString(), 200);
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      String methodRequest = exchange.getRequestMethod();
      if ("POST".equals(methodRequest)) {
        post(exchange);
      } else {
        sendResponseJson(exchange, new HashMap<String, String>() {
          {
            put("message", "Invalid request method");
          }
        }, 405);
      }
    } catch (Exception e) {
      sendResponseJson(exchange, new HashMap<String, String>() {
        {
          put("message", "Internal server error");
        }
      }, 500);
      e.printStackTrace();
    }
  }
}