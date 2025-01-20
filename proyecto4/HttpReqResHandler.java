import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

class HttpReqResHandler {

  public void sendResponse(HttpExchange exchange, String response, int httpStatus) throws IOException {
    try {
      exchange.sendResponseHeaders(httpStatus, response.getBytes().length);
      OutputStream os = exchange.getResponseBody();
      os.write(response.toString().getBytes());
      os.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void sendResponseJson(HttpExchange exchange, HashMap<String, String> responseMap, int httpStatus)
      throws IOException {
    try {
      StringBuilder response = new StringBuilder("{");
      for (Map.Entry<String, String> entry : responseMap.entrySet())
        response.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");

      if (response.length() > 1)
        response.setLength(response.length() - 1); // Remove trailing comma

      response.append("}");
      exchange.sendResponseHeaders(httpStatus, response.toString().getBytes().length);
      OutputStream os = exchange.getResponseBody();
      os.write(response.toString().getBytes());
      os.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static HashMap<String, String> parseRequestBody(HttpExchange exchange) throws IOException {
    HashMap<String, String> body = new HashMap<>();
    String requestBody = new String(exchange.getRequestBody().readAllBytes());
    String[] pairs = requestBody.split("&");
    for (String pair : pairs) {
      String[] keyValue = pair.split("=");
      body.put(keyValue[0], keyValue[1]);
    }
    return body;
  }

}