public class Coordenada {

  private double x, y, magnitud;

  public Coordenada(double x, double y) {
    this.x = x;
    this.y = y;
    this.magnitud = this.calculateMagnitud();
  }

  private double calculateMagnitud(){
    double x_2 = Math.pow(x, 2);
    double y_2 = Math.pow(y, 2);
    return Math.sqrt(x_2 + y_2);
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

  public double getMagnitud() {
    return this.magnitud;
  }

  private double getThreeDecimals(double number) {
    return Math.round(number * 1000) / 1000.0;
  }

  @Override
  public String toString() {
    return "["
        +
        getThreeDecimals(x)
        +
        ","
        +
        getThreeDecimals(y)
        +
        "] | Magnitud | = " + magnitud;
  }
}