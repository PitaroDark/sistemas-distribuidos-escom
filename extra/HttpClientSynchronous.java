import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public class HttpClientSynchronous {
  private static final String STATUS_ENDPOINT = "/status";
  private static final String SEARCHTOKEN_ENDPOINT = "/searchtoken";
  private static final String READ_ENDPOINT = "/read"; 
  private static final String PLAINTEXT_ENDPOINT = "/plaintext";

  private static final HttpClient httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_1_1)
      .connectTimeout(Duration.ofSeconds(10))
      .build();

  public static void main(String[] args) throws IOException, InterruptedException {

    HttpRequest request2 = HttpRequest.newBuilder()
        .POST(HttpRequest.BodyPublishers.ofString("17576000,IPN"))
        .uri(URI.create("http://localhost:8080" + SEARCHTOKEN_ENDPOINT))
        .setHeader("Content-Type", "application/x-www-form-urlencoded")
        .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
        .setHeader("X-Debug", "true")
        .build();

    HttpResponse<String> response = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());

    // print response headers
    HttpHeaders headers = response.headers();
    headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

    // print status code
    System.out.println(response.statusCode());

    // print response body
    System.out.println(response.body());

  }

}
