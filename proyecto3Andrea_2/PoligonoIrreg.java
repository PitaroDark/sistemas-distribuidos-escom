/*
 * Proyecto 3
 * ANDREA VANEGAS SUSANO
 * 7CM4
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PoligonoIrreg {
  protected ArrayList<Coordenada> vertices;
  protected double area;

  public PoligonoIrreg(int numVertices, int maxSize) {
    this.vertices = new ArrayList<>();
    Random rand = new Random();
    int size = rand.nextInt(maxSize / 8) + 10; // Tamaño aleatorio que no supere 1/8 del área
    System.out.println("Tamaño: " + size);
    for (int i = 0; i < numVertices; i++) {
        vertices.add(new Coordenada(rand.nextInt(size) - size / 2, rand.nextInt(size) - size / 2));
    }
    calcularArea();
  }

  private void calcularArea() {
    this.area = vertices.size() * 10; // Estimación simple
  }

  public double obtieneArea() {
    return area;
  }

  public List<Coordenada> getVertices() {
    return vertices;
  }

  @Override
  public String toString() {
    String verticesStr = "";
    for (Coordenada vertice : this.vertices)
      verticesStr += "Coordenada: " + vertice.toString() + "\n";
    return verticesStr;
  }

}
