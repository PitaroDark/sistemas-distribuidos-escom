import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ParallelTextSearch {

    private static final int[] PORTS = {8001, 8002, 8003};
    private static final String BOOK_PATHS[][] = {
        {
            "Adler,Elizabeth1991.La_esmeralda_de_los_Ivanoff[10057].txt",
            "Adler_Olsen,Jussi1997.La_casa_del_alfabeto[7745].txt",
            "Aguilera,Juan_Miguel1998.La_locura_de_Dios[5644].txt",
            "Alameddine,Rabih2008.El_contador_de_historias[5735].txt",
            "Albom,Mitch2002.Martes_con_mi_viejo_profesor[382].txt",
            "Alcott,Louisa_May1868.Mujercitas[11086].txt",
            "Alcott,Louisa_May1871.Hombrecitos[15392].txt",
            "Alders,Hanny1987.El_tesoro_de_los_templarios[13014].txt",
            "Alexander,Caroline1998.Atrapados_en_el_hielo[15727].txt",
            "Allende,Isabel1982.La_casa_de_los_espíritus[563].txt",
            "Allende,Isabel1984.De_amor_y_de_sombra[6283].txt",
            "Alten,Steve2001._Trilogía_maya_01El_testamento_maya[8901].txt",
            "Alten,Steve2008.Al_borde_del_infierno[12141].txt",
            "Amis,Martin1990.Los_monstruos_de_Einstein[8080].txt",
            "Anderson,Sienna2008.No_me_olvides[15047].txt",
            "Anónimo_1554.Lazarillo_de_Tormes[11043].txt"
        },
        {
            "Anónimo_2004.Robin_Hood[11853].txt",
            "Archer,Jeffrey1979.Kane_y_Abel[1965].txt",
            "Asimov,Isaac1950.Yo,_robot[10874].txt",
            "Asimov,Isaac1967.Guía_de_la_BibliaAntiguo_Testamento_[6134].txt",
            "Asimov,Isaac1985.El_monstruo_subatómico[167].txt",
            "Bach,Richard1970.Juan_Salvador_Gaviota[15399].txt",
            "Baum,Lyman_Frank1900.El_Mago_de_Oz[15715].txt",
            "Beevor,Antony1998.Stalingrado[10491].txt",
            "Benítez,J._J.1984._Caballo_de_Troya_01Jerusalén[4826].txt",
            "Dickens,Charles1843.Cuento_de_Navidad[3285].txt",
            "Dostoievski,Fiódor1865.Crimen_y_castigo[13400].txt",
            "Ende,Michael1973.Momo[1894].txt",
            "Esquivel,Laura1989.Como_agua_para_chocolate[7750].txt",
            "Flaubert,Gustave1857.Madame_Bovary[3067].txt",
            "Fromm,Erich1947.El_miedo_a_la_libertad[11619].txt",
            "Gaarder,Jostein1991.El_mundo_de_Sofía[6571].txt"
        },
        {
            "Gaiman,Neil2002.Coraline[1976].txt",
            "García_Márquez,Gabriel1967.Cien_años_de_soledad[8376].txt",
            "García_Márquez,Gabriel1985.El_amor_en_los_tiempos_del_cólera[874].txt",
            "García_Márquez,Gabriel1989.El_general_en_su_laberinto[875].txt",
            "Golding,William1954.El_señor_de_las_moscas[6260].txt",
            "Goleman,Daniel1995.Inteligencia_emocional[4998].txt",
            "Gorki,Máximo1907.La_madre[1592].txt",
            "Harris,Thomas1988.El_silencio_de_los_inocentes[11274].txt",
            "Hawking,Stephen1988.Historia_del_tiempo[8536].txt",
            "Hemingway,Ernest1952.El_viejo_y_el_mar[1519].txt",
            "Hesse,Herman1919.Demian[2612].txt",
            "Hitler,Adolf1935.Mi_lucha[11690].txt",
            "Hobbes,Thomas1651.Leviatán[2938].txt",
            "Huxley,Aldous1932.Un_mundo_feliz[293].txt"
        }
    };

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < PORTS.length; i++) {
            startServer(PORTS[i], BOOK_PATHS[i]);
        }
    }

    private static void startServer(int port, String[] filePaths) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/text", new TextHandler(filePaths));
        server.setExecutor(Executors.newFixedThreadPool(2));
        server.start();
        System.out.println("Server started on port " + port);
    }

    static class TextHandler implements HttpHandler {
        private final String[] filePaths;

        public TextHandler(String[] filePaths) {
            this.filePaths = filePaths;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            List<String> texts = new ArrayList<>();
            for (String filePath : filePaths) {
                String text = new String(Files.readAllBytes(Paths.get("LIBROS_TXT/" + filePath))).toLowerCase().replaceAll("[^a-zA-Z ]", "");
                texts.add(text);
            }
            String response = String.join("\n", texts);
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    public static List<String> getPhrases(String text, int n) {
        String[] words = text.split(" ");
        List<String> phrases = new ArrayList<>();
        for (int i = 0; i <= words.length - n; i++) {
            phrases.add(String.join(" ", Arrays.copyOfRange(words, i, i + n)));
        }
        return phrases;
    }

    public static Map<String, Set<String>> findCommonPhrases(int n) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<String>> responses = new ArrayList<>();

        for (int port : PORTS) {
            responses.add(executor.submit(() -> fetchTextFromServer(port)));
        }

        List<String> texts = new ArrayList<>();
        for (Future<String> response : responses) {
            texts.add(response.get());
        }

        executor.shutdown();

        Map<String, Set<String>> result = new HashMap<>();
        for (int i = 0; i < texts.size(); i++) {
            for (int j = i + 1; j < texts.size(); j++) {
                Set<String> phrases1 = new HashSet<>(getPhrases(texts.get(i), n));
                Set<String> phrases2 = new HashSet<>(getPhrases(texts.get(j), n));
                phrases1.retainAll(phrases2);
                if (!phrases1.isEmpty()) {
                    result.put("Server " + (i + 1) + " & Server " + (j + 1), phrases1);
                }
            }
        }
        return result;
    }

    private static String fetchTextFromServer(int port) throws IOException {
        URL url = new URL("http://localhost:" + port + "/text");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8);
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        return text;
    }
}
