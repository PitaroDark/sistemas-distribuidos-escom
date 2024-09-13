import java.util.Random;

public class PalabrasCharArray {
    public static int indexOf(char[] text, char[] pattern, int fromIndex) {
        int textLen = text.length;
        int patternLen = pattern.length;

        if (fromIndex >= textLen || patternLen == 0) {
            return -1; // Si el índice de inicio está fuera de rango o el patrón está vacío, no se
                       // encuentra
        }

        outer: for (int i = fromIndex; i <= textLen - patternLen; i++) {
            for (int j = 0; j < patternLen; j++) {
                if (text[i + j] != pattern[j]) {
                    continue outer; // Si los caracteres no coinciden, pasamos al siguiente índice
                }
            }
            return i; // Si se encontró una coincidencia, retornamos el índice de inicio
        }

        return -1; // Si no se encontró ninguna coincidencia, retornamos -1
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        char[] cadenota = new char[n * 4];

        Random random = new Random();
        char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        int index = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                cadenota[index++] = alphabet[random.nextInt(26)];
            }
            if (i < n - 1) {
                cadenota[index++] = ' ';
            }
        }

        char[] subcadena = {'I', 'P', 'N'};
        int count = 0;
        int position = 0;

        while ((position = indexOf(cadenota, subcadena, position)) != -1) {
            count++;
            position += 4;
        };

        System.out.println(count);
    }
}