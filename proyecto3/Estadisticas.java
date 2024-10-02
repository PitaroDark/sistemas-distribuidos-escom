
/**
 * PROYECTO 3
 * IKARI BRANDON VARGAS OSORNIO
 * 7CM3
 */
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class Estadisticas {

  private static Scanner scanner = new Scanner(System.in);
  private static Curp curp = new Curp();

  private static int menu() {
    System.out.println("1.- Votos por sexo");
    System.out.println("2.- Votos por estado");
    System.out.println("3.- Votos por edades");
    System.out.println("4.- Votos por partido");
    System.out.println("5.- Salir");
    System.out.print("Opción: ");
    return scanner.nextInt();
  }

  private static void votosPorSexo() {
    System.out.println("VOTOS POR SEXO\n");
    try {
      File file = new File(Constants.PATH_VOTOS);
      BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
      String line;
      int hombres = 0;
      int mujeres = 0;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (curp.getGender(parts[0]).equals("Mujer"))
          mujeres++;
        else
          hombres++;
      }
      reader.close();
      System.out.println("Votos de Mujeres: " + mujeres);
      System.out.println("Votos de Hombres: " + hombres);
      System.out.println("\nVotos Totales: " + (hombres + mujeres) + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static void votosPorEstado() {
    System.out.println("VOTOS POR ESTADO\n");
    Map<String, Integer> estados = new HashMap<String, Integer>();
    try {
      File file = new File(Constants.PATH_VOTOS);
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        String estado = curp.getState(parts[0]);
        if (estados.containsKey(estado))
          estados.put(estado, estados.get(estado) + 1);
        else
          estados.put(estado, 1);
      }
      reader.close();
      for (Map.Entry<String, Integer> entry : estados.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
      }
      System.out.println("\nVotos Totales: " + estados.values().stream().mapToInt(Integer::intValue).sum() + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void votosPorEdades() {
    System.out.println("VOTOS POR EDADES\n");
    Map<String, Integer> edades = new HashMap<String, Integer>();
    try {
      File file = new File(Constants.PATH_VOTOS);
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        String edad = curp.getAge(parts[0]) + " años";
        if (edades.containsKey(edad))
          edades.put(edad, edades.get(edad) + 1);
        else
          edades.put(edad, 1);
      }
      reader.close();
      for (Map.Entry<String, Integer> entry : edades.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
      }
      System.out.println("\nVotos Totales: " + edades.values().stream().mapToInt(Integer::intValue).sum() + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void votosPorPartido() {
    System.out.println("VOTOS POR PARTIDO\n");
    Map<String, Integer> partidos = new HashMap<String, Integer>();
    try {
      File file = new File(Constants.PATH_VOTOS);
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (partidos.containsKey(parts[1]))
          partidos.put(parts[1], partidos.get(parts[1]) + 1);
        else
          partidos.put(parts[1], 1);
      }
      reader.close();
      for (Map.Entry<String, Integer> entry : partidos.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
      }
      System.out.println("\nVotos Totales: " + partidos.values().stream().mapToInt(Integer::intValue).sum() + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void salir() {
    System.exit(0);
  }

  private static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  private static void pause() {
    System.out.println("Presiona enter para continuar...");
    try {
      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    while (true) {
      clearScreen();
      int option = menu();
      clearScreen();
      switch (option) {
        case 1:
          votosPorSexo();
          break;
        case 2:
          votosPorEstado();
          break;
        case 3:
          votosPorEdades();
          break;
        case 4:
          votosPorPartido();
          break;
        case 5:
          salir();
          break;
        default:
          System.out.println("Opción no válida");
          break;
      }
      pause();
    }
  }
}
