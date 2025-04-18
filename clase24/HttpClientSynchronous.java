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
  //CAMBIAR POR EL ENDPOINT DE LA IMAGEN DE TU BUCKET
  private static final String ENDPOINT = "https://storage.googleapis.com/storage/v1/b/bucket/o/archivo";
  //CAMBIAR POR TU TOKEN
  private static final String TOKEN = "...";

  private static final HttpClient httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_1_1)
      .connectTimeout(Duration.ofSeconds(10))
      .build();

  public static void main(String[] args) throws IOException, InterruptedException {

    HttpRequest request = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create(ENDPOINT))
        .setHeader("Authorization", "Bearer " + TOKEN)
        .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
        .setHeader("X-Debug", "true")
        .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    // print response headers
    HttpHeaders headers = response.headers();
    headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

    // print status code
    System.out.println(response.statusCode());

    // print response body
    System.out.println(response.body());

  }

}
