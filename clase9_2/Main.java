import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
  protected static int num = 5;
  protected static String genderToDelete;
  private static ArrayList<String> curps = new ArrayList<String>();

  public static void main(String[] args) {
    if (args.length > 0){
      num = Integer.parseInt(args[0]);
      genderToDelete = args[1];
      // If gender to Delete not matches 'H' or 'M', return
      if(genderToDelete.matches("[^HM]")){
        System.out.println("El segundo argumento debe ser 'H' o 'M'");
        return;
      }
    }
    for (int i = 0; i < num; i++){
      String curp = getCURP();
      if(i != 0){
        //System.out.println("Nueva CURP generada: " + curp + "\n");
        //System.out.println("La lista de CURPs es: ");
        addByAscendingOrder(curp);
        //System.out.println(curps.toString() + "\n");
      }
      else{
        //System.out.println("CURP inicial: " + curp + "\n");
        curps.add(curp);
      }
    }

    System.out.println(num + " CURPs generadas");
    System.out.println("Eliminando el genero " + genderToDelete);

    Iterator<String> curpIterator = curps.iterator();
    ArrayList<String> filteredCurps = new ArrayList<String>();
    while (curpIterator.hasNext()) {
      String curp = curpIterator.next();
      System.out.println("CURP: " + curp);
      if(isWoman(curp) && genderToDelete.equals("H")){
        filteredCurps.add(curp);
      }
      else if(!isWoman(curp) && genderToDelete.equals("M")){
        filteredCurps.add(curp);
      }
    }
    System.out.println("CURPs filtradas: ");
    System.out.println(filteredCurps.toString());
    
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