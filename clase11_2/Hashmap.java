import java.io.*;
import java.util.*;

class Hashmap {
    public static void main(String[] args) throws Exception {
        Map<Character, Integer> caracteres = new HashMap<>();
        List<Character> ordenAparicion = new ArrayList<>();
        FileReader fr = new FileReader("/home/skoll/sistemas-distribuidos-escom/clase11_2/El_viejo_y_el_mar.txt");
        int i;
        
        while ((i = fr.read()) != -1) {
            char caracter = (char) i;
            if (!caracteres.containsKey(caracter)) {
                ordenAparicion.add(caracter);
            }
            caracteres.put(caracter, caracteres.getOrDefault(caracter, 0) + 1);
        }
        
        fr.close();
        
        // Imprimir el número de caracteres distintos
        System.out.println("Número de caracteres distintos: " + caracteres.size());
        
        // Imprimir el mapa en orden de aparición
        System.out.println("Caracteres y sus ocurrencias:");
        for (char c : ordenAparicion) {
            System.out.println("Carácter: '" + c + "', Ocurrencias: " + caracteres.get(c));
        }
    }
}