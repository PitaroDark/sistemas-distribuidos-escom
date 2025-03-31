
/*
 * Proyecto 3
 * ANDREA VANEGAS SUSANO
 * 7CM4
 */
import java.util.Random;

class Asteroide extends PoligonoIrreg implements Runnable {
  private double velocidad;
  private double dx, dy;
  private Panel panel;
  private Coordenada posicion;

  public Asteroide(int numVertices, Panel panel) {
    super(numVertices, 900 * 700); // Restricción de tamaño
    this.panel = panel;
    Random rand = new Random();
    velocidad = 200 / obtieneArea();
    dx = rand.nextDouble() * 4 - 2;
    dy = rand.nextDouble() * 4 - 2;
    posicion = new Coordenada(rand.nextInt(900), rand.nextInt(700));
  }

  @Override
  public void run() {
    while (true) {
      mover();
      panel.repaint();
      try {
        Thread.sleep((long) (50 / velocidad));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void mover() {
    posicion.setAbcisa((posicion.abcisa() + dx + 900) % 900);
    posicion.setOrdenada((posicion.ordenada() + dy + 700) % 700);
  }

  public Coordenada getPosicion() {
    return posicion;
  }
}
