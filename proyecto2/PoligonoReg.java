/*
 * Proyecto 2
 * IKARI BRANDON VARGAS OSORNIO
 * 7CM3
 */
import java.util.Random;

public class PoligonoReg extends PoligonoIrreg {

  protected double angulo;

  public PoligonoReg(int numVertices) {
    super(numVertices);
    if (numVertices < 3)
      throw new IllegalArgumentException("Un poligono regular debe tener al menos 3 vertices");
    if (numVertices > 15)
      throw new IllegalArgumentException("Un poligono regular no puede tener mas de 15 vertices");
    Random random = new Random();
    int maxRadius = 600 / 8; // radio maximo del poligono
    this.angulo = 360.0 / numVertices; // angulo entre cada vertice
    this.radius = random.nextInt(maxRadius) + 5; // radio del poligono
    int originX = random.nextInt(800 - (2 * maxRadius)) + maxRadius; // Posicion x dentro de los limites de la ventana
    int originY = random.nextInt(600 - (2 * maxRadius)) + maxRadius; // Posicion y dentro de los limites de la ventana
    this.setOrigin(originX, originY);
    for (int i = 0; i < numVertices; i++) {
      double x = this.radius * Math.cos(Math.toRadians(this.angulo * i)) + this.origin[0];
      double y = this.radius * Math.sin(Math.toRadians(this.angulo * i)) + this.origin[1];
      this.addPoint((int) x, (int) y);
    }
  }

  public double obtieneArea() {
    double area = 0;
    for (int i = 0; i < this.vertices.size(); i++) {
      Coordenada verticeActual = this.vertices.get(i);
      Coordenada verticeSiguiente = this.vertices.get((i + 1) % this.vertices.size());
      area += verticeActual.abcisa() * verticeSiguiente.ordenada()
          - verticeActual.ordenada() * verticeSiguiente.abcisa();
    }
    return Math.abs(area) / 2;
  }

}
