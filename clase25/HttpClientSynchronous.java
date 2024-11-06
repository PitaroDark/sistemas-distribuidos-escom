import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import com.google.gson.Gson;

public class HttpClientSynchronous {
  private static final String ENDPOINT = "https://www.breakingbadapi.com/api/";

  private static final HttpClient httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_1_1)
      .connectTimeout(Duration.ofSeconds(10))
      .build();

  public static void main(String[] args) throws IOException, InterruptedException {

    // Creamos una instancia de Gson
    Gson gson = new Gson();

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

  }

}

// Clase de ejemplo
class Person {
  private String cita;
  private String autor;
  private String translated_text;

  // Constructor, getters y setters
  public Person(String name, int age, String email) {
      this.name = name;
      this.age = age;
      this.email = email;
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public int getAge() {
      return age;
  }

  public void setAge(int age) {
      this.age = age;
  }

  public String getEmail() {
      return email;
  }

  public void setEmail(String email) {
      this.email = email;
  }
}
