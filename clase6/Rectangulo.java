public class Rectangulo {

  private Coordenada superiorIzq, inferiorDer;

  public Rectangulo() {
    superiorIzq = new Coordenada(
        0,
        0);
    inferiorDer = new Coordenada(
        0,
        0);
  }

  public Rectangulo(double xSupIzq, double ySupIzq, double xInfDer, double yInfDer) {
    superiorIzq = new Coordenada(xSupIzq, ySupIzq);
    inferiorDer = new Coordenada(xInfDer, yInfDer);
    this.checkCoordenates();
  }

  public Rectangulo(Coordenada left, Coordenada right) {
    this.superiorIzq = left.clone();
    this.inferiorDer = right.clone();
    this.checkCoordenates();
  }

  private void checkCoordenates() {
    if (superiorIzq.abcisa() > inferiorDer.abcisa() && superiorIzq.ordenada() > inferiorDer.ordenada()) {
      throw new IllegalArgumentException("La primer coordenada no se encuentra arriba y a la izquierda de la segunda");
    }
  }

  // Metodo getter de la coordenada superior izquierda

  public Coordenada superiorIzquierda() {
    return superiorIzq;
  }

  // Metodo getter de la coordenada inferior derecha

  public Coordenada inferiorDerecha() {
    return inferiorDer;
  }

  // Sobreescritura del método de la superclase objeto para imprimir con
  // System.out.println( )

  @Override
  public String toString() {
    return "Esquina superior izquierda: "
        +
        superiorIzq
        +
        "\tEsquina inferior derecha:"
        +
        inferiorDer
        +
        "\n";
  }

}