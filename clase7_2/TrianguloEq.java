public class TrianguloEq extends Figura {

  private float lado;

  public TrianguloEq(float lado) {
    super();
    this.lado = lado;
  }

  public float area() {
    return (float) (Math.sqrt(3) / 4 * lado * lado);
  }
}
