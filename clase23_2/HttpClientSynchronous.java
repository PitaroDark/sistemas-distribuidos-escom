import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.google.gson.*;

public class HttpClientSynchronous {
    private static final String ENDPOINT = "https://api.breakingbadquotes.xyz/v1/quotes/3";
    private static final String APIKEY = "";
    private static final String TARGET = "es";
    private static final String ENDPOINT_TRANSLATE = "https://translation.googleapis.com/language/translate/v2?key="
            + APIKEY + "&target=" + TARGET;

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String[] args) throws IOException, InterruptedException {

        Gson gson = new Gson();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(ENDPOINT))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .setHeader("Accept", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        System.out.println(jsonArray);
        System.out.println();

        JsonArray translatedData = new JsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject elemento = jsonArray.get(i).getAsJsonObject();
            String text = elemento.get("quote").getAsString();
            String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
            HttpRequest requestTranslate = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(ENDPOINT_TRANSLATE + "&q=" + encodedText))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot")
                    .setHeader("Accept", "application/json")
                    .build();
            HttpResponse<String> responseTranslate = httpClient.send(requestTranslate,
                    HttpResponse.BodyHandlers.ofString());

            // Extraer el texto traducido de la respuesta
            JsonObject responseJson = JsonParser.parseString(responseTranslate.body()).getAsJsonObject();
            JsonObject data = responseJson.getAsJsonObject("data");
            JsonArray translations = data.getAsJsonArray("translations");
            String translatedText = translations.get(0).getAsJsonObject().get("translatedText").getAsString();

            JsonObject toSpanish = new JsonObject();
            toSpanish.addProperty("author", elemento.get("author").getAsString());
            toSpanish.addProperty("quote", elemento.get("quote").getAsString());
            toSpanish.addProperty("translated_quoted", translatedText);
            translatedData.add(toSpanish);
        }

        translatedData.forEach(jsonQuote -> {
            JsonObject jsonObject = jsonQuote.getAsJsonObject();
            String author = jsonObject.get("author").getAsString();
            String quote = jsonObject.get("quote").getAsString();
            String translatedQuote = jsonObject.get("translated_quoted").getAsString();
            System.out.println("Autor: " + author);
            System.out.println("Cita: " + quote);
            System.out.println("Traduccion: " + translatedQuote);
            System.out.println();
        });

    }

}