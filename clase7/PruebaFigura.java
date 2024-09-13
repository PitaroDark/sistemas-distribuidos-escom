public class PruebaFigura {
  public static void main(String[] args) {

    Coordenada left = new Coordenada(2, 3);
    Coordenada right = new Coordenada(5, 1);

    try {
      Rectangulo rect1 = new Rectangulo(left, right);
      float area = rect1.area();

      System.out.println("El área del rectángulo es = " + area);
      System.out.println("El numero de lados es = " + rect1.getNumeroLados());
      
      rect1.imprimePerimetro();
    } catch (IllegalArgumentException e) {
      System.out.println("Error al crear el objeto Rectangulo: " + e.getMessage());
    }

  } 
}