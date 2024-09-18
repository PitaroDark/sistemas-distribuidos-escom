public class PruebaArrayList {
  public static void main(String[] args) {
    int nVertices = (args.length == 0) ? 7 : Integer.parseInt(args[0]);
    PoligonoIrreg poligono = new PoligonoIrreg();

    for (int i = 0; i < nVertices; i++) {
      double x = Math.random() * 200 - 100;
      double y = Math.random() * 200 - 100;
      Coordenada vertice = new Coordenada(x, y);
      poligono.anadeVertice(vertice);
    }

    System.out.println("Los vertices del poligono son: ");
    System.out.println(poligono.toString());
    System.out.println("Vertices totales: " + poligono.getCurrentLenght() + "\n");

    poligono.ordenaVertices();

    System.out.println(poligono.toString());
    System.out.println("Vertices totales: " + poligono.getCurrentLenght());
  }
}
