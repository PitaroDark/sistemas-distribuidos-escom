public abstract class Figura implements Desplazable{

  protected Coordenada centro;

  public Figura() {
    this.centro = new Coordenada(0, 0);
  }

  public Figura(Coordenada centro) {
    this.centro = centro;
  }

  public abstract float area();

  public void desplazar(double dx, double dy) {
    this.centro = new Coordenada(this.centro.abcisa() + dx, this.centro.ordenada() + dy);
  }

  public Coordenada getCentro() {
    return this.centro.clone();
  }

  public void setCentro(Coordenada centro) {
    this.centro = centro.clone();
  }
}