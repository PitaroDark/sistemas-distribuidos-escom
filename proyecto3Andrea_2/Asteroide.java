import java.util.Random;

class Asteroide extends PoligonoIrreg implements Runnable {
  private double velocidad;
  private double dx, dy;
  private Panel panel;
  private Coordenada posicion;
  private double escala;

  public Asteroide(int numVertices, Panel panel) {
    super(numVertices, Math.min(900, 700)); // Restricción de tamaño
    this.panel = panel;
    Random rand = new Random();
    velocidad = 200 / obtieneArea();
    dx = rand.nextDouble() * 4 - 2;
    dy = rand.nextDouble() * 4 - 2;
    posicion = new Coordenada(rand.nextInt(900), rand.nextInt(700));
    escala = 1.0;
  }

  @Override
  public void run() {
    while (true) {
      mover();
      panel.repaint();
      comprobarColisiones();
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

  private void comprobarColisiones() {
    for (Asteroide asteroide : panel.getAsteroides()) {
      if (this != asteroide) {
        double distancia = Math.sqrt(Math.pow(this.posicion.abcisa() - asteroide.posicion.abcisa(), 2) +
            Math.pow(this.posicion.ordenada() - asteroide.posicion.ordenada(), 2));

        if (distancia < (this.obtieneArea() / 10 + asteroide.obtieneArea() / 10)) {
          // Colisión detectada, reducir tamaño y ajustar velocidad
          reducirTamañoYVelocidad();
          asteroide.reducirTamañoYVelocidad();
        }
      }
    }
  }

  private void reducirTamañoYVelocidad() {
    area /= 2; // Reducir el área a la mitad
    velocidad = 200 / obtieneArea(); // Ajustar la velocidad

    // Reducir la escala de los vértices
    escala *= 0.5;
    ajustarVertices();
  }

  private void ajustarVertices() {
    for (Coordenada vertice : getVertices()) {
      // Reducir la posición de los vértices según la escala
      vertice.setAbcisa(vertice.abcisa() * escala);
      vertice.setOrdenada(vertice.ordenada() * escala);
    }
  }

  public Coordenada getPosicion() {
    return posicion;
  }
}