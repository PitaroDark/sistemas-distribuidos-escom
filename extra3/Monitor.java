import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public class Monitor {
  private static final String STATUS_ENDPOINT = "/status";

  private static final HttpClient httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_1_1)
      .connectTimeout(Duration.ofSeconds(2))
      .build();

  public static void main(String[] args) {
    String ports[] = { "8081", "8082", "8083" };

    // Timeout thread to check the status of each port every 2 seconds
    Thread timeoutThread = new Thread(() -> {
      while (true) {
        for (String port : ports) {
          try {
            HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:" + port + STATUS_ENDPOINT))
                .build();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            // Print response body
            System.out.print("Respuesta del servidor " + port + ": " + response.body());
          } catch (IOException | InterruptedException e) {
            System.err.println("Error connecting to port " + port + ": " + e.getMessage());
          }
        }
        try {
          System.out.println("Esperando 2 segundos\n");
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          System.err.println("Timeout interrupted: " + e.getMessage());
        }
      }
    });

    timeoutThread.start();
  }
}

