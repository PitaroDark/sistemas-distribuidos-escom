import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

class Hashmap{
    public static void main(String[] args) throws Exception{
        Map<String,Integer> caracteres = new HashMap<>();
        ArrayList<String[]> listcaracteres = new ArrayList<String[]>();
        FileReader fr = new FileReader(
            "/home/skoll/sistemas-distribuidos-escom/clase11/El_viejo_y_el_mar.txt");
 
        int i;
        while ((i = fr.read()) != -1){
            String caracter = "" + (char)i;
            int value = caracteres.getOrDefault(caracter, 0);
            caracteres.put(caracter, value + 1);
        }

        caracteres.forEach((k,v) -> listcaracteres.add(new String[] {k, v + ""}));
        //System.out.println("Map elements: " + caracteres);

        listcaracteres.sort(new Comparator<String[]>() {
            public int compare(String[] str1, String[] str2){
                int value1 = Integer.parseInt(str1[1]); 
                int value2 = Integer.parseInt(str2[1]); 
                return value2 - value1;
            }
        });

        listcaracteres.forEach((s) -> System.out.print(" Key: " + s[0] + ", Value: " + s[1]));
    }

}