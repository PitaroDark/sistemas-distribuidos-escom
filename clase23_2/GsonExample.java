import com.google.gson.*;

public class GsonExample {
    public static void main(String[] args) {
        // JSON en forma de String que representa un arreglo de objetos
        String jsonString = "[{\"nombre\":\"Juan\",\"edad\":25},{\"nombre\":\"Ana\",\"edad\":30}]";

        // Convertir String a JsonArray usando JsonParser
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        // Mostrar el resultado
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.size(); i++)
        {
            JsonObject elemento = jsonArray.get(i).getAsJsonObject();
            String text = elemento.get("nombre").getAsString();
            System.out.println("Nombre: " + text);

        }
/*
        //Envía solicitud a la API de traducción de Google
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Extraer el texto traducido de la respuesta
        JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject data = responseJson.getAsJsonObject("data");
        JsonArray translations = data.getAsJsonArray("translations");
        String translatedText = translations.get(0).getAsJsonObject().get("translatedText").getAsString();
*/
    }
}
