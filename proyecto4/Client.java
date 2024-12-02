import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class Client {
  private static final String BASE_URL = "http://localhost:8000";

  public static void main(String[] args) {
    login("user1", "password1");
    getMovie();
    updateStatus("user1", "movie1", 5);
    getStatus("user1", "movie1");
  }

  private static void login(String username, String password) {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + "/login"))
        .header("Username", username)
        .header("Password", password)
        .POST(HttpRequest.BodyPublishers.noBody())
        .build();

    CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    response.thenApply(HttpResponse::body).thenAccept(System.out::println).join();
  }

  private static void getMovie() {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + "/movie"))
        .GET()
        .build();

    CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    response.thenApply(HttpResponse::body).thenAccept(System.out::println).join();
  }

  private static void updateStatus(String username, String movieId, int lastSubtitle) {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + "/status"))
        .header("Username", username)
        .header("MovieId", movieId)
        .header("LastSubtitle", String.valueOf(lastSubtitle))
        .POST(HttpRequest.BodyPublishers.noBody())
        .build();

    CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    response.thenApply(HttpResponse::body).thenAccept(System.out::println).join();
  }

  private static void getStatus(String username, String movieId) {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + "/status"))
        .header("Username", username)
        .header("MovieId", movieId)
        .GET()
        .build();

    CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    response.thenApply(HttpResponse::body).thenAccept(System.out::println).join();
  }
}