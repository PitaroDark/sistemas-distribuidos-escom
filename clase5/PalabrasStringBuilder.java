import java.util.Random;
 
public class PalabrasStringBuilder {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        StringBuilder cadenota = new StringBuilder(n * 4);
 
        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
 
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                cadenota.append(alphabet.charAt(random.nextInt(26)));
            }
            if (i < n - 1) {
                cadenota.append(' ');
            }
        }
 
        String subcadena = "IPN";
        int count = 0;
        int position = 0;
 
        while ((position = cadenota.indexOf(subcadena, position)) != -1) {
            count++;
            position += subcadena.length();
        }

        long endTime = System.currentTimeMillis() - startTime; 
 
        System.out.println(count);
        System.out.println("Tiempo de ejecución: " + endTime);
    }
}