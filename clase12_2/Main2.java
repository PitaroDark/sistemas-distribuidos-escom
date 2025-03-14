import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main2 {
    static final int NUM_THREADS = 3;
    protected static int num = 15;
    private static ArrayList<String> aleatorios = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length > 0) {
            num = Integer.parseInt(args[0]);
        }

        if (num % 5 != 0) {
            System.out.println("Error: El número de CURPs debe ser múltiplo de 5.");
            return;
        }

        for (int i = 0; i < num; i++) {
            aleatorios.add(getCURP());
        }

        //System.out.println("Lista sin ordenar: " + aleatorios + "\n");
       
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);

        int Size = num / NUM_THREADS;
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * Size;
            int end = start + Size;
            pool.execute(new Task("Tarea " + (i + 1), aleatorios, start, end));
        }

        pool.shutdown();
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
