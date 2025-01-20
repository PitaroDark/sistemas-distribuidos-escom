import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientSynchronous {
  private static final String ENDPOINT = "http://localhost:8080/reader";

  private static final HttpClient httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_1_1)
      .connectTimeout(Duration.ofSeconds(10))
      .build();

  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Cliente HTTP sincrono");

    while (true) {

      HttpRequest request = HttpRequest.newBuilder()
          .GET()
          .uri(URI.create(ENDPOINT))
          .setHeader("User-Agent", "Java 11 HttpClient Bot")
          .build();

      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      // print response body
      System.out.println(response.body());

      try {
        System.out.println("Esperando 4 segundos para la siguiente petición...");
        Thread.sleep(4 * 1000);
      } catch (Exception e) {
      }

    }
  }

}
