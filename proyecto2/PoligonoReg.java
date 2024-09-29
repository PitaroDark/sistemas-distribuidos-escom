import java.util.Random;

public class PoligonoReg extends PoligonoIrreg {

  protected double angulo;

  public PoligonoReg(int numVertices) {
    super(numVertices);
    if (numVertices < 3)
      throw new IllegalArgumentException("Un poligono regular debe tener al menos 3 vertices");
    if (numVertices > 15)
      throw new IllegalArgumentException("Un poligono regular no puede tener mas de 15 vertices");
    this.angulo = 360.0 / numVertices;
    Random random = new Random();
    double radio = random.nextDouble() * 100; // Radio del circulo que engloba el poligono
    for (int i = 0; i < numVertices; i++) {
      double x = radio * Math.cos(Math.toRadians(i * angulo));
      double y = radio * Math.sin(Math.toRadians(i * angulo));
      this.vertices.add(new Coordenada(x, y));
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
