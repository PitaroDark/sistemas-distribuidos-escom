/*
 * Proyecto 2
 * IKARI BRANDON VARGAS OSORNIO
 * 7CM3
 */
import java.util.ArrayList;

public class PoligonoIrreg {
  protected ArrayList<Coordenada> vertices;
  protected int maxVertices;
  protected int[] origin;
  protected int radius;

  public PoligonoIrreg(int maxVertices) {
    this.vertices = new ArrayList<Coordenada>();
    this.origin = new int[] { 0, 0 };
    this.maxVertices = maxVertices;
    this.radius = 1;
  }

  public void addPoint(int x, int y) {
    if (this.vertices.size() == this.maxVertices)
      throw new IllegalArgumentException("No se pueden agregar más vértices al polígono.");
    this.vertices.add(new Coordenada(x, y));
  }

  public void removePoint(int position) {
    this.vertices.remove(position);
  }

  public ArrayList<Coordenada> getVertices() {
    return this.vertices;
  }

  public void setVertices(ArrayList<Coordenada> vertices) {
    this.vertices = vertices;
  }

  public void setVertice(int position, Coordenada vertice) {
    this.vertices.set(position, vertice);
  }

  public Coordenada getVertice(int position) {
    return this.vertices.get(position);
  }

  public void setOrigin(int x, int y) {
    this.origin[0] = x;
    this.origin[1] = y;
  }

  public int[] getOrigin() {
    return this.origin;
  }

  public int setRadius(int ratio) {
    return this.radius = ratio;
  }

  public int getRadius() {
    return this.radius;
  }

  @Override
  public String toString() {
    String verticesStr = "";
    for (Coordenada vertice : this.vertices)
      verticesStr += "Coordenada: " + vertice.toString() + "\n";
    return verticesStr;
  }

}
