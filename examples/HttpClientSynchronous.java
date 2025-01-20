import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientSynchronous {
  // LA RUTA DE TU SERVIDOR Y PUERTO
  private static final String ENDPOINT = "http://localhost:8080/status"; //

  private static final HttpClient httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_1_1)
      .connectTimeout(Duration.ofSeconds(10))
      .build();

  public static void main(String[] args) throws IOException, InterruptedException {

    HttpRequest request = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create(ENDPOINT))
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

    // =================================POST============================================

    // EJEMPLO DE POST CON ENVIO DE DATOS
    HttpRequest postRequest = HttpRequest.newBuilder()
        .POST(HttpRequest.BodyPublishers.ofString("Esto es un mensaje de prueba")) // DE ESTA FORMA SE ENVIA UNA CADENA
                                                                                   // DE TEXTO
        .uri(URI.create(ENDPOINT))
        .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
        .setHeader("X-Debug", "true")
        .build();

    HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

    // print response headers
    HttpHeaders headersPost = response.headers();
    headersPost.map().forEach((k, v) -> System.out.println(k + ":" + v));

    // print status code
    System.out.println(postResponse.statusCode());

    // print response body
    System.out.println(postResponse.body());
  }

}
