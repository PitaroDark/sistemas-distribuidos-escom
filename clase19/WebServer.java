/*
 *  MIT License
 *
 *  Copyright (c) 2019 Michael Pogrebinsky - Distributed Systems & Cloud Computing with Java
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

//Se importan las librerias httpserver para poder crear un servidor http
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

//Se importan las librerias necesarias para manejar excepciones, entrada y salida de datos, operaciones matematicas y manejo de arreglos
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;

public class WebServer {
  //Se definen las rutas de los endpoints
  private static final String TASK_ENDPOINT = "/task";
  private static final String STATUS_ENDPOINT = "/status";

  //Se define el puerto en el que se va a correr el servidor
  private final int port;
  private HttpServer server;

  //Metodo main que se encarga de correr el servidor
  public static void main(String[] args) {
    int serverPort = 8080;
    if (args.length == 1) {
      serverPort = Integer.parseInt(args[0]);
    }

    //Se crea una instancia de la clase WebServer y se inicia el servidor
    WebServer webServer = new WebServer(serverPort);
    webServer.startServer();

    //Se imprime un mensaje en consola para indicar que el servidor esta corriendo
    System.out.println("Servidor escuchando en el puerto " + serverPort);
  }

  //Constructor de la clase WebServer que recibe el puerto en el que se va a correr el servidor
  public WebServer(int port) {
    this.port = port;
  }

  //Metodo que se encarga de iniciar el servidor
  public void startServer() {
    try {
      //Se crea una instancia de HttpServer en el puerto especificado
      this.server = HttpServer.create(new InetSocketAddress(port), 0);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    //Se crean dos contextos para los endpoints de status y task 
    HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
    HttpContext taskContext = server.createContext(TASK_ENDPOINT);

    //Se asignan los manejadores para cada contexto los cuales son metodos de la clase WebServer
    statusContext.setHandler(this::handleStatusCheckRequest);
    taskContext.setHandler(this::handleTaskRequest);

    //Se asigna un pool de 8 hilos para manejar las solicitudes
    server.setExecutor(Executors.newFixedThreadPool(8));
    //Se inicia el servidor
    server.start();
  }

  //Metodo(Handler) que se encarga de manejar las solicitudes al endpoint /task (multiplicacion de numeros)
  private void handleTaskRequest(HttpExchange exchange) throws IOException {
    //Si la solicitud no es de tipo POST se cierra la conexion
    if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
      exchange.close();
      return;
    }

    //Se obtienen los headers de la solicitud
    Headers headers = exchange.getRequestHeaders();
    //Si el header X-Test es true se envia una respuesta dummy
    if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {
      String dummyResponse = "123\n";
      sendResponse(dummyResponse.getBytes(), exchange);
      return;
    }

    //Si el header X-Debug es true se activa el modo debug
    boolean isDebugMode = false;
    if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
      isDebugMode = true;
    }

    //Se toma el tiempo de inicio de la operacion
    long startTime = System.nanoTime();

    //Se leen los bytes de la solicitud y se calcula la respuesta
    byte[] requestBytes = exchange.getRequestBody().readAllBytes();
    byte[] responseBytes = calculateResponse(requestBytes);

    //Se toma el tiempo de finalizacion de la operacion
    long finishTime = System.nanoTime();

    //Si el modo debug esta activado se envia un header con el tiempo que tomo la operacion
    if (isDebugMode) {
      String debugMessage = String.format("La operación tomó %d nanosegundos", finishTime - startTime);
      exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
    }

    //Se envia la respuesta
    sendResponse(responseBytes, exchange);
  }

  //Metodo que se encarga de calcular la respuesta a la solicitud
  private byte[] calculateResponse(byte[] requestBytes) {
    //Se convierten los bytes de la solicitud a un string
    String bodyString = new String(requestBytes);
    //Se separan los numeros por comas
    String[] stringNumbers = bodyString.split(",");

    //Se inicializa el resultado en 1
    BigInteger result = BigInteger.ONE;

    //Se multiplican los numeros y se guarda el resultado
    for (String number : stringNumbers) {
      BigInteger bigInteger = new BigInteger(number);
      result = result.multiply(bigInteger);
    }

    //Se retorna el resultado en un string
    return String.format("El resultado de la multiplicación es %s\n", result).getBytes();
  }

  //Metodo(Handler) que se encarga de manejar las solicitudes al endpoint /status
  private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
    //Si la solicitud no es de tipo GET se cierra la conexion
    if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
      exchange.close();
      return;
    }

    //Se envia una respuesta indicando que el servidor esta vivo
    String responseMessage = "El servidor está vivo\n";
    sendResponse(responseMessage.getBytes(), exchange);
  }

  //Metodo que se encarga de enviar la respuesta al cliente
  private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
    //Se envia la respuesta con el codigo 200 y el tamaño de la respuesta
    exchange.sendResponseHeaders(200, responseBytes.length);
    //Se obtiene el output stream de la respuesta
    OutputStream outputStream = exchange.getResponseBody();
    //Se escriben los bytes de la respuesta en el output stream
    outputStream.write(responseBytes);
    //Se limpia el output stream y se cierra la conexion
    outputStream.flush();
    outputStream.close();
    exchange.close();
  }
}
