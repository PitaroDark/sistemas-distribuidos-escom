import java.util.Arrays;
import java.util.Comparator;

public class Prueba{    

    static int x = 3;
    static int y = 10;
    static String[] names = {"Ikari", "Brandon", "Andrea", "Hati", "Amy", "Tom", "Jago", "Emily"};

    public static void main(String[] args){
        Operacion suma = (a, b) -> a + b;
        //printCosa(suma);
        Verificador verificador = (a) -> a % 2 == 0;
        //printVerificador(verificador);
        Comparator<String> comparador= (String s1, String s2) -> s1.compareTo(s2);
        Arrays.sort(names, comparador);
        for (String nombre : names) 
            System.out.println(nombre);
        
    }

    public static void printCosa(Operacion oper){
        System.out.println("La suma de " + x + " + " + y + " es: " + oper.realizarOperacion(x, y));   
    }

    public static void printVerificador(Verificador veri){
        System.out.println("El numero " + x + " es: " + (veri.verificar(x)?"par":"impar"));   
    }

}
