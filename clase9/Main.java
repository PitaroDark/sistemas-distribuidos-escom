import java.util.ArrayList;
import java.util.Iterator;

public class Main {
  protected static int num = 5;
  private static ArrayList<String> curps = new ArrayList<String>();

  public static void main(String[] args) {
    if (args.length > 0)
      num = Integer.parseInt(args[0]);
    for (int i = 0; i < num; i++){
      String curp = getCURP();
      if(i != 0){
        System.out.println("Nueva CURP generada: " + curp + "\n");
        System.out.println("La lista de CURPs es: ");
        addByAscendingOrder(curp);
        System.out.println(curps.toString() + "\n");
      }
      else{
        System.out.println("CURP inicial: " + curp + "\n");
        curps.add(curp);
      }
    }
  }

  public static void addByAscendingOrder(String curp) {
    Iterator<String> curpIterator = curps.iterator();
    int index = 0;
    while (curpIterator.hasNext()) {
      if (curp.substring(0,4).compareTo(curpIterator.next().substring(0,4)) < 0) {
        curps.add(index, curp);
        return;
      }
      index++;
    }
    curps.add(curp);
  }

  public static boolean isWoman(String curp) {
    return curp.charAt(10) == 'M';
  }

  public static String getCURP() {
    String Letra = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String Numero = "0123456789";
    String Sexo = "HM";
    String Entidad[] = { "AS", "BC", "BS", "CC", "CS", "CH", "CL", "CM", "DF", "DG", "GT", "GR", "HG", "JC", "MC", "MN",
        "MS", "NT", "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR", "TC", "TL", "TS", "VZ", "YN", "ZS" };
    int indice;

    StringBuilder sb = new StringBuilder(18);

    for (int i = 1; i < 5; i++) {
      indice = (int) (Letra.length() * Math.random());
      sb.append(Letra.charAt(indice));
    }

    for (int i = 5; i < 11; i++) {
      indice = (int) (Numero.length() * Math.random());
      sb.append(Numero.charAt(indice));
    }
    indice = (int) (Sexo.length() * Math.random());
    sb.append(Sexo.charAt(indice));

    sb.append(Entidad[(int) (Math.random() * 32)]);
    for (int i = 14; i < 17; i++) {
      indice = (int) (Letra.length() * Math.random());
      sb.append(Letra.charAt(indice));
    }
    for (int i = 17; i < 19; i++) {
      indice = (int) (Numero.length() * Math.random());
      sb.append(Numero.charAt(indice));
    }

    return sb.toString();
  }
}