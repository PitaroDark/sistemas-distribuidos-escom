import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Task implements Runnable {

  private static final int NUM_LINES = 7037;
  private int piece;

  public Task(int piece) {
    this.piece = piece;
  }

  @Override
  public void run() {
    try {
      Map<String, Integer> caracteres = new HashMap<>();
      ArrayList<String[]> listcaracteres = new ArrayList<String[]>();
      FileReader fr = new FileReader(
          "/mnt/c/Users/dark2/Documents/java/distribuidos/clase13/BIBLIA_COMPLETA.txt");
      int linesReaded = 0, startToRead = piece * NUM_LINES, endToRead = startToRead + NUM_LINES;

      // READ FILE BY LINE AND THEN COUNT CHARACTERS
      long startTime = System.nanoTime();
      int i;
      while ((i = fr.read()) != -1) {
        if (linesReaded < startToRead) {
          if (i == '\n') {
            linesReaded++;
          }
          continue;
        }
        String caracter = "" + (char) i;
        int value = caracteres.getOrDefault(caracter, 0);
        caracteres.put(caracter, value + 1);
      }

      fr.close();

      caracteres.forEach((k, v) -> listcaracteres.add(new String[] { k, v + "" }));
      // System.out.println("Map elements: " + caracteres);

      listcaracteres.sort(new Comparator<String[]>() {
        public int compare(String[] str1, String[] str2) {
          int value1 = Integer.parseInt(str1[1]);
          int value2 = Integer.parseInt(str2[1]);
          return value2 - value1;
        }
      });

      long endTime = System.nanoTime();

      System.out.println("Hilo: " + this.piece);
      listcaracteres.forEach((s) -> System.out.println(" Key: " + s[0] + ", Value:" + s[1]));
      System.out.println("Hilo: " + this.piece + " La operacion tomo " + (endTime - startTime) + " nanosegundos");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
