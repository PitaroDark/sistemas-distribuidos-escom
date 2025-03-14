import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Servidor {

  private static final int PORTS[] = { 8001, 8002, 8003 };
  private static final String PATH_BOOKS = "books";
  private static final String BOOKS[][] = {
      {
          "Adler,_Elizabeth__1991_._La_esmeralda_de_los_Ivanoff_[10057].txt",
          "Adler_Olsen,_Jussi__1997_._La_casa_del_alfabeto_[7745].txt",
          "Aguilera,_Juan_Miguel__1998_._La_locura_de_Dios_[5644].txt",
          "Alameddine,_Rabih__2008_._El_contador_de_historias_[5735].txt",
          "Albom,_Mitch__2002_._Martes_con_mi_viejo_profesor_[382].txt",
          "Alcott,_Louisa_May__1868_._Mujercitas_[11086].txt",
          "Alcott,_Louisa_May__1871_._Hombrecitos_[15392].txt",
          "Alders,_Hanny__1987_._El_tesoro_de_los_templarios_[13014].txt",
          "Alexander,_Caroline__1998_._Atrapados_en_el_hielo_[15727].txt",
          "Allende,_Isabel__1982_._La_casa_de_los_espiritus_[563].txt",
          "Allende,_Isabel__1984_._De_amor_y_de_sombra_[6283].txt",
          "Alten,_Steve__2001_.__Trilogia_maya_01__El_testamento_maya_[8901].txt",
          "Alten,_Steve__2008_._Al_borde_del_infierno_[12141].txt",
          "Amis,_Martin__1990_._Los_monstruos_de_Einstein_[8080].txt",
          "Anderson,_Sienna__2008_._No_me_olvides_[15047].txt",
          "Anonimo__1554_._Lazarillo_de_Tormes_[11043].txt"
      },
      {
          "Anonimo__2004_._Robin_Hood_[11853].txt",
          "Archer,_Jeffrey__1979_._Kane_y_Abel_[1965].txt",
          "Asimov,_Isaac__1950_._Yo,_robot_[10874].txt",
          "Asimov,_Isaac__1967_._Guia_de_la_Biblia__Antiguo_Testamento__[6134].txt",
          "Asimov,_Isaac__1985_._El_monstruo_subatomico_[167].txt",
          "Bach,_Richard__1970_._Juan_Salvador_Gaviota_[15399].txt",
          "Baum,_Lyman_Frank__1900_._El_Mago_de_Oz_[15715].txt",
          "Beevor,_Antony__1998_._Stalingrado_[10491].txt",
          "Benitez,_J._J.__1984_.__Caballo_de_Troya_01__Jerusalen_[4826].txt",
          "Dickens,_Charles__1843_._Cuento_de_Navidad_[3285].txt",
          "Dostoievski,_Fiodor__1865_._Crimen_y_castigo_[13400].txt",
          "Ende,_Michael__1973_._Momo_[1894].txt",
          "Esquivel,_Laura__1989_._Como_agua_para_chocolate_[7750].txt",
          "Flaubert,_Gustave__1857_._Madame_Bovary_[3067].txt",
          "Fromm,_Erich__1947_._El_miedo_a_la_libertad_[11619].txt",
          "Gaarder,_Jostein__1991_._El_mundo_de_Sofia_[6571].txt"
      },
      {
          "Gaiman,_Neil__2002_._Coraline_[1976].txt",
          "Garcia_Marquez,_Gabriel__1967_._Cien_anios_de_soledad_[8376].txt",
          "Garcia_Marquez,_Gabriel__1985_._El_amor_en_los_tiempos_del_colera_[874].txt",
          "Garcia_Marquez,_Gabriel__1989_._El_general_en_su_laberinto_[875].txt",
          "Golding,_William__1954_._El_senor_de_las_moscas_[6260].txt",
          "Goleman,_Daniel__1995_._Inteligencia_emocional_[4998].txt",
          "Gorki,_Maximo__1907_._La_madre_[1592].txt",
          "Harris,_Thomas__1988_._El_silencio_de_los_inocentes_[11274].txt",
          "Hawking,_Stephen__1988_._Historia_del_tiempo_[8536].txt",
          "Hemingway,_Ernest__1952_._El_viejo_y_el_mar_[1519].txt",
          "Hesse,_Herman__1919_._Demian_[2612].txt",
          "Hitler,_Adolf__1935_._Mi_lucha_[11690].txt",
          "Hobbes,_Thomas__1651_._Leviatan_[2938].txt",
          "Huxley,_Aldous__1932_._Un_mundo_feliz_[293].txt"
      }
  };
  private int INDEX;

  public Servidor(int index) {
    this.INDEX = index;
  }

  public static void main(String[] args) {
    int index = (args.length > 0) ? Integer.parseInt(args[0]) - 1 : 0;
    if (index < 0 || index > PORTS.length) {
      System.out.println("El servidor no existe");
      return;
    }
    Servidor servidor = new Servidor(index);
    servidor.iniciarServidor();
  }

  public void iniciarServidor() {
    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(PORTS[INDEX]), 0);
      server.createContext("/text", this::servidorHandler);
      server.setExecutor(Executors.newFixedThreadPool(2));
      server.start();
      System.out.println("Servidor ejecutandose en " + PORTS[INDEX]);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void servidorHandler(HttpExchange exchange) throws IOException {
    if (!exchange.getRequestMethod().equals("POST")) {
      byte[] responseBytes = "Método no permitido".getBytes();
      enviarRespuesta(exchange, responseBytes);
      return;
    }

    byte[] requestBytes = exchange.getRequestBody().readAllBytes();
    String bodyString = new String(requestBytes);
    int nPalabras = Integer.parseInt(bodyString);

    System.out.println("Servidor " + (INDEX + 1) + " recibió " + nPalabras + "numero palabras a buscar");

    HashMap<String, Set<String>> palabrasMap = new HashMap<>();
    for (String book : BOOKS[INDEX]) {
      Set<String> palabrasLibro = leerLibro(book, nPalabras);
      if (palabrasLibro.isEmpty())
        continue;
      palabrasMap.put(book, palabrasLibro);
    }

    HashMap<String, Set<String>> comparacionPareja = new HashMap<>();
    // REALIZAR LA INTERSECCION DE LAS PALABRAS DE LOS LIBROS EN PAREJAS, EJEMPLO
    // (LIBRO1,LIBRO2) (LIBRO1,LIBRO3) (LIBRO2,LIBRO3), ETC
    for (int i = 0; i < palabrasMap.size(); i += 2) {
      for (int j = i + 1; j < palabrasMap.size(); j++) {
        String book1 = BOOKS[INDEX][i];
        String book2 = BOOKS[INDEX][j];
        Set<String> palabras1 = palabrasMap.get(book1);
        Set<String> palabras2 = palabrasMap.get(book2);
        Set<String> palabrasComunes = new HashSet<>(palabras1);
        palabrasComunes.retainAll(palabras2);
        if (palabrasComunes.isEmpty())
          continue;
        comparacionPareja.put(book1 + " & " + book2, palabrasComunes);
      }
    }

    // Separar <Libro>:<Palabra>,<Palabra>,<Palabra>... -
    // <Libro>:<Palabra>,<Palabra>,<Palabra>...

    String responseString = comparacionPareja.entrySet().stream().map(entry -> {
      String books = entry.getKey();
      Set<String> palabras = entry.getValue();
      String palabrasString = palabras.stream().collect(Collectors.joining(","));
      books = books.replace(",", "_").replace(":", "_");
      return books + ":" + palabrasString;
    }).collect(Collectors.joining("-"));

    
    System.out.println("Servidor " + (INDEX + 1) + " terminó de buscar y separar palabras");
    System.out.println("Servidor " + (INDEX + 1) + " enviando respuesta\n");
    if (responseString.isEmpty())
      responseString = "No se encontraron palabras comunes";
    enviarRespuesta(exchange, responseString.getBytes());
  }

  public Set<String> leerLibro(String book, int nPalabras) {
    // NO DEBE IMPORTAR SI SON MAYUSCULAS O MINUSCULAS NI LOS PUNTOS, COMAS NI SALTO
    // DE LINEA
    try {
      System.out.println("Servidor " + (INDEX + 1) + " leyendo " + book);
      String content = Files.readString(Paths.get(PATH_BOOKS, book));
      String[] words = content.split("\\s+");
      Set<String> wordsSet = new HashSet<>();
      int count = 0;
      String wordsToJoin[] = new String[nPalabras];
      System.out.println("Servidor " + (INDEX + 1) + " separando palabras de " + book);
      for (String word : words) {
        if (!word.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+"))
          continue;
        wordsToJoin[count] = word.toLowerCase();
        count++;
        if (count == nPalabras) {
          wordsSet.add(String.join(" ", wordsToJoin));
          count = 0;
        }
      }
      System.out.println("Servidor " + (INDEX + 1) + " terminó de leer " + book + "\n");
      return wordsSet;
    } catch (IOException e) {
      e.printStackTrace();
      return new HashSet<>();
    }
  }

  public void enviarRespuesta(HttpExchange exchange, byte[] responseBytes) throws IOException {
    exchange.sendResponseHeaders(200, responseBytes.length);
    OutputStream outputStream = exchange.getResponseBody();
    outputStream.write(responseBytes);
    outputStream.flush();
    outputStream.close();
    exchange.close();
  }

}
