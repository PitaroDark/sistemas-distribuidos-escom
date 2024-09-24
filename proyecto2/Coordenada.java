public class Coordenada {

  private double x, y;

  public Coordenada(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double abcisa() {
    return x;
  }

  public double ordenada() {
    return y;
  }

  public Coordenada clone() {
    return new Coordenada(this.x, this.y);
  }

  // @Override
  // public String toString() {
  // return "[" + x+ "," + y + "]";
  // }

  @Override
  public String toString() {
    return String.format("[%d, %d]", x, y);
  }
}
