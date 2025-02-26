public class Main {
  public static void main(String[] args) {
    Figura triangulo = new TrianguloEq(5);
    Figura rectangulo = new Rectangulo(5, 10);

    System.out.println("Area del triangulo: " + triangulo.area());
    System.out.println("Area del rectangulo: " + rectangulo.area());

    System.out.println("Centro del triangulo: " + triangulo.getCentro());
    System.out.println("Centro del rectangulo: " + rectangulo.getCentro());

    System.out.println("Desplazando el triangulo");
    triangulo.desplazar(5, 5);
    System.out.println("Desplazando el rectangulo");
    rectangulo.desplazar(5, 5);

    System.out.println("Centro del triangulo despues del desplazo: " + triangulo.getCentro());
    System.out.println("Centro del rectangulo despues del desplazo: " + rectangulo.getCentro());
  }
}
