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
  private static final String STRAY_IMAGE_ENDPOINT = "https://storage.googleapis.com/storage/v1/b/first_bucket_brandon/o/stray.jpg";
  //CAMBIAR POR TU TOKEN
  private static final String TOKEN = "ya29.c.c0ASRK0GbiEa05QyPKNWLM37VvQ5ybgQYH8JEvaMTbJ99b5I521PWQdrOvDjti9k9wVsdaKhZjCHaP1w-dbclajG5IuuGpNaG3xod9_B9d26pHFJYEl4L7GOmbEUXo7ZUcxfqYWOt1qfOaAxywFClvjdvjOG9iz7hPy-Xr7JkoPn4iXLAYDuE91ywIWqCgiSU2FhuysH-rWoqO22mNIYUhPBbyVoGzo9uxTa_rMyQOOOE-adWVo700-le0La-D9M5FJ2IvV8GrXA6e5O2YmD9kKI1lj424aJwd0S2iWiQOCIFbCDjlm0RGbu4imYqddfk3fP4pNrOOZduGke3GQQm4UQWshRq_9dYXC3YwIxB_T9Jv93P5iElsH13lk7sMOfTHhEz6OQL399KJYr1rqM9IpFeOfgiigOIffng9bOv8px8Zm30xrmyOZ88vzozejJnxtRUQt5-l4zwsJea3oJ-h8SouXxwz77JcFzr4skbaxy0bQ6dtS_SmyF9h9zydJvRlV2mw2vk9ZifWrkn4QUJvbF6VXh6-MyaWb5Z0_u1kX9VBRzjukrQB2cajgM99j3j6-obygfbrewnzjbSRm56WgIwg1Rt3BXR5pR1Ms69lYx9avRk1m06-uFescs2cauMjQ608Ilec-ZVd6Zfdrm4lUuiFMWvVZ27I6kpe8Rqfstx97OwuM57v8Fomcujuub-uq3cBlOStSntFFVd8wM89uk19iOXm4g-Q_u255e3UivcIhIm91a7Ozpbb_i4Sh2YXajX_4o69mVpWjSqB49VuJSZxbl1BQfnJ0qk0YjVJ9-lY8RX09M-I64yxd0zy-6WzdQ1mUyt__reRnk8QX2JthVSV4vS2eia2b3127g78nc0yiizbMjf5a7jkcmrOMYfwOjR4wISeW17xBizlvzS7XvuYif5sS5M89SiluFnQ1vUJ-B5du5Y8kahZegc977ar2sIvz2Xki3i6UO7Ze2Oy0k8eZjhOVb3XcsiV3JjhyQyQIxvczi9z18J";

  private static final HttpClient httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_1_1)
      .connectTimeout(Duration.ofSeconds(10))
      .build();

  public static void main(String[] args) throws IOException, InterruptedException {

    HttpRequest request = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create(STRAY_IMAGE_ENDPOINT))
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
