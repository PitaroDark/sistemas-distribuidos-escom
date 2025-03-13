
public class Main {
  public static void main(String[] args) {
    int nVertices = (args.length == 0) ? 7 : Integer.parseInt(args[0]);
    PoligonoIrreg poligono = new PoligonoIrreg();

    for (int i = 0; i < nVertices; i++) {
      // Random number between -100,000 and 100,000
      double x = Math.random() * 200000 - 100000;
      double y = Math.random() * 200000 - 100000;
      Coordenada vertice = new Coordenada(x, y);
      poligono.anadeVertice(vertice);
    }

    System.out.println("Los vertices del poligono son: ");
    System.out.println(poligono.toString());
    System.out.println("Vertices totales: " + poligono.getCurrentLenght() + "\n");

    System.out.println("Agregando 3 vertices mas");
    for (int i = 0; i < 3; i++) {
      // Random number between -100,000 and 100,000
      double x = Math.random() * 200000 - 100000;
      double y = Math.random() * 200000 - 100000;
      Coordenada vertice = new Coordenada(x, y);
      poligono.anadeVertice(vertice);
    }

    System.out.println("Los vertices del poligono son: ");
    System.out.println(poligono.toString());
    System.out.println("Vertices totales: " + poligono.getCurrentLenght() + "\n");

  }
}
