/*
 * PROYECTO 3
 * ANDREA VANEGAS SUSANO
 * 7CM3
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuTerminal {
    public static Scanner scanner = new Scanner(System.in);
    public static File file = new File("VOTOS.dat");
    public static CURP Curp = new CURP();

    public static void main(String[] args) throws IOException {
        while (true) {

            System.out.println("1.- Votos por sexo");
            System.out.println("2.- Votos por estado");
            System.out.println("3.- Votos por edades");
            System.out.println("4.- Votos por partido");
            System.out.println("5.- Salir");
            System.out.print("Opcion: ");
            int opcion = scanner.nextInt();

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            switch (opcion) {
                case 1:
                    int hombres = 0;
                    int mujeres = 0;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (Curp.isWoman(parts[0]))
                            mujeres++;
                        else
                            hombres++;
                    }
                    System.out.println("Votos de Mujeres: " + mujeres);
                    System.out.println("Votos de Hombres: " + hombres);
                    break;
                case 2:
                    Map<String, Integer> votosEstados = new HashMap<String, Integer>();
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        String estado = Curp.obtenerEstado(parts[0].substring(11,13));
                        if (votosEstados.containsKey(estado))
                            votosEstados.put(estado, votosEstados.get(estado) + 1);
                        else
                            votosEstados.put(estado, 1);
                    }
                    for (Map.Entry<String, Integer> entry : votosEstados.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    break;
                case 3:
                    Map<String, Integer> edades = new HashMap<String, Integer>();
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        String edad = Curp.obtenerEdad(parts[0]) + " años";
                        if (edades.containsKey(edad))
                            edades.put(edad, edades.get(edad) + 1);
                        else
                            edades.put(edad, 1);
                    }
                    for (Map.Entry<String, Integer> entry : edades.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    break;
                case 4:
                    Map<String, Integer> partidos = new HashMap<String, Integer>();
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (partidos.containsKey(parts[1]))
                            partidos.put(parts[1], partidos.get(parts[1]) + 1);
                        else
                            partidos.put(parts[1], 1);
                    }
                    for (Map.Entry<String, Integer> entry : partidos.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    System.out.println(
                            "\nVotos Totales: " + partidos.values().stream().mapToInt(Integer::intValue).sum() + "\n");
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    break;

            }
            reader.close();
        }

    }

}
