/**
 * PROYECTO 3
 * IKARI BRANDON VARGAS OSORNIO
 * 7CM3
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VotosPorSegundo {

  public static void main(String[] args) {
    int n = (args.length == 0) ? 10 : Integer.parseInt(args[0]);
    int counterVotes = 0;
    Curp curp = new Curp();
    File file = null;
    FileWriter writer = null;
    try {
      file = new File(Constants.PATH_VOTOS);
      writer = new FileWriter(file.getAbsolutePath(), true);

      while (true) {
        if (counterVotes >= n) {
          counterVotes = 0;
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        int random = (int) (Math.random() * Constants.PARTIDOS.length);
        String curpGenerated = curp.generateRandomCURP();
        String partido = Constants.PARTIDOS[random];
        String lineToWrite = curpGenerated + "," + partido;
        writer.write(lineToWrite + "\n");
        writer.flush();
        counterVotes++;
        System.out.println("Voto registrado #" + counterVotes + " de " + curpGenerated);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
