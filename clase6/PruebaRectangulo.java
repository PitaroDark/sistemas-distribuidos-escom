public class PruebaRectangulo {

  static void main(String[] args) {

    Coordenada left = new Coordenada(6, 2);
    Coordenada right = new Coordenada(5, 1);

    // Rectangulo rect1 = new Rectangulo(
    // 2,
    // 3,
    // 5,
    // 1);
    try {
      Rectangulo rect1 = new Rectangulo(left, right);

      double ancho, alto;

      System.out.println("Calculando el área de un rectángulo dadas sus coordenadas en un plano cartesiano:");

      System.out.println(rect1);

      alto = rect1.superiorIzquierda().ordenada() - rect1.inferiorDerecha().ordenada();

      ancho = rect1.inferiorDerecha().abcisa() - rect1.superiorIzquierda().abcisa();

      System.out.println("El área del rectángulo es = " + ancho * alto);
    } catch (IllegalArgumentException e) {
      System.out.println("Error al crear el objeto Rectangulo: " + e.getMessage());
    }

  }

}