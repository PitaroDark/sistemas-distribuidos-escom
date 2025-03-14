import java.io.*;
import java.util.*;
import java.util.Comparator;

class HashmapOrden {
    public static void main(String[] args) throws Exception {
        Map<String, Integer> caracteres = new HashMap<>();
        ArrayList<String[]> listCaracteres = new ArrayList<>();
        FileReader fr = new FileReader("El_viejo_y_el_mar.txt");
        int i;
        
        while ((i = fr.read()) != -1) {
            String caracter = String.valueOf((char) i);
            caracteres.put(caracter, caracteres.getOrDefault(caracter, 0) + 1);
        }
        
        fr.close();
        
        caracteres.forEach((k, v) -> listCaracteres.add(new String[]{k, v.toString()}));
        
        // Ordenar de mayor a menor ocurrencia usando Comparator
        listCaracteres.sort(new Comparator<String[]>() {
            @Override
            public int compare(String[] str1, String[] str2) {
                return Integer.compare(Integer.parseInt(str2[1]), Integer.parseInt(str1[1]));
            }
        });
        
        System.out.println("Número de caracteres distintos: " + caracteres.size());
        
        System.out.println("Caracteres y sus ocurrencias ordenados de mayor a menor:");
        for (String[] s : listCaracteres) {
            System.out.println("Carácter: '" + s[0] + "', Ocurrencias: " + s[1]);
        }
    }
}
