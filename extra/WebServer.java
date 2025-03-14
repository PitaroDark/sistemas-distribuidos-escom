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
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import java.io.File;

public class WebServer {
  // Se definen las rutas de los endpoints

  private static final String READ_ENDPOINT = "/read";
  private static final String READS = "Texto.txt";

  // Se define el puerto en el que se va a correr el servidor
  private final int port;
  private HttpServer server;

  // Metodo main que se encarga de correr el servidor
  public static void main(String[] args) {
    int serverPort = 8080;
    if (args.length == 1) {
      serverPort = Integer.parseInt(args[0]);
    }

    // Se crea una instancia de la clase WebServer y se inicia el servidor
    WebServer webServer = new WebServer(serverPort);
    webServer.startServer();

    // Se imprime un mensaje en consola para indicar que el servidor esta corriendo
    System.out.println("Servidor escuchando en el puerto " + serverPort);
  }

  // Constructor de la clase WebServer que recibe el puerto en el que se va a
  // correr el servidor
  public WebServer(int port) {
    this.port = port;
  }

  // Metodo que se encarga de iniciar el servidor
  public void startServer() {
    try {
      // Se crea una instancia de HttpServer en el puerto especificado
      this.server = HttpServer.create(new InetSocketAddress(port), 0);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    // Se crean dos contextos para los endpoints de status y task

    HttpContext readContext = server.createContext(READ_ENDPOINT);

    // Se asignan los manejadores para cada contexto los cuales son metodos de la
    // clase WebServer

    readContext.setHandler(this::handleReadRequest);

    // Se asigna un pool de 8 hilos para manejar las solicitudes
    server.setExecutor(Executors.newFixedThreadPool(8));
    // Se inicia el servidor
    server.start();
  }

  try{
    File archivo = 
    
  }catch(IOException e){
    e.printStackTrace();
    return;
  }


  //Este es el mio para editar
  private void handleReadRequest(HttpExchange exchange) throws IOException {
    // Si la solicitud no es de tipo GET se cierra la conexion
    if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
      exchange.close();
      return;
    }

  }

  
  // Metodo que se encarga de enviar la respuesta al cliente
  private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
    // Se envia la respuesta con el codigo 200 y el tamaño de la respuesta
    exchange.sendResponseHeaders(200, responseBytes.length);
    // Se obtiene el output stream de la respuesta
    OutputStream outputStream = exchange.getResponseBody();
    // Se escriben los bytes de la respuesta en el output stream
    outputStream.write(responseBytes);
    // Se limpia el output stream y se cierra la conexion
    outputStream.flush();
    outputStream.close();
    exchange.close();
  }
}