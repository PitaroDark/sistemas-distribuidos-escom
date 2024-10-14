/**
 * PROYECTO 3
 * ANDREA VANEGAS SUSANO
 * 7CM3
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class VotosSegundo {
    public static final String[] PARTIDOS = {
            "MC", "MORENA", "PAN", "PRD", "PRI", "PT", "PVEM",
    };

    public static void main(String args[]) throws IOException {
        int n = Integer.parseInt(args[0]);
        CURP Curp = new CURP();
        int contador = 0;
        File file = new File("VOTOS.dat");
        FileWriter fileWriter = new FileWriter(file.getAbsolutePath(), true);

        while (true) {
            if (contador >= n) {
                contador = 0;
                esperar(1);
                System.out.println(n + " votos generados por segundo");
            }
            int random = (int) (Math.random() * PARTIDOS.length);
            String partidoAleatorio = PARTIDOS[random];
            String curpGenerado = Curp.generaCurp();
            fileWriter.write(curpGenerado + "," + partidoAleatorio + "\n");
            fileWriter.flush();
            contador++;
        }

    }

    public static void esperar(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (Exception e) {
            return;
        }
    }
}
