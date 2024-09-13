public class Rectangulo extends Figura implements Perimetro {

  private Coordenada superiorIzq, inferiorDer;

  public Rectangulo() {
    super(4);
    superiorIzq = new Coordenada(
        0,
        0);
    inferiorDer = new Coordenada(
        0,
        0);
  }

  public Rectangulo(double xSupIzq, double ySupIzq, double xInfDer, double yInfDer) {
    super(4);
    superiorIzq = new Coordenada(xSupIzq, ySupIzq);
    inferiorDer = new Coordenada(xInfDer, yInfDer);
    this.checkCoordenates();
  }

  public Rectangulo(Coordenada left, Coordenada right) {
    super(4);
    this.superiorIzq = left.clone();
    this.inferiorDer = right.clone();
    this.checkCoordenates();
  }

  private void checkCoordenates() {
    if (superiorIzq.abcisa() > inferiorDer.abcisa() && superiorIzq.ordenada() > inferiorDer.ordenada()) {
      throw new IllegalArgumentException("La primer coordenada no se encuentra arriba y a la izquierda de la segunda");
    }
  }

  //Funcion area implementada
  public float area(){
    double alto = this.superiorIzq.ordenada() - this.inferiorDer.ordenada();
    double ancho = this.inferiorDer.abcisa() - this.superiorIzq.abcisa();
    double area = ancho * alto;
    return (float) area;
  }

  //Funcion ImprimePerimetro implementada
  public float imprimePerimetro(){
    //double altura = Math.sqrt(1  + Math.pow(this.inferiorDer.ordenada() - this.superiorIzq.ordenada(), 2));
    //double ancho = Math.sqrt(Math.pow(this.superiorIzq.abcisa() - this.inferiorDer.abcisa(), 2) + 1);
    double ancho = Math.abs(this.superiorIzq.abcisa() - this.inferiorDer.abcisa());
    double altura = Math.abs(this.inferiorDer.ordenada() - this.superiorIzq.ordenada());

    double perimetro = 2 * (ancho + altura);
    System.out.println("El perimetro es = " + perimetro);
    return (float) perimetro;
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