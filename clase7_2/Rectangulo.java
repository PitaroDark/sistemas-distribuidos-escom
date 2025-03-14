public class Rectangulo extends Figura {

  private float base, altura;

  public Rectangulo(float base, float altura) {
    super();
    this.base = base;
    this.altura = altura;
  }

  public float area() {
    return base * altura;
  }
}
