import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

class PruebaBiblia {
  public static void main(String[] args) throws Exception {
    Map<String, Integer> caracteres = new HashMap<>();
    ArrayList<String[]> listcaracteres = new ArrayList<String[]>();
    FileReader fr = new FileReader(
        "/mnt/c/Users/dark2/Documents/java/distribuidos/clase13/BIBLIA_COMPLETA.txt");

    long startTime = System.nanoTime();
    int i;
    while ((i = fr.read()) != -1) {
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

    listcaracteres.forEach((s) -> System.out.println(" Key: " + s[0] + ", Value: " + s[1]));
    System.out.println("La operacion tomo " + (endTime - startTime) + " nanosegundos");
  }

}