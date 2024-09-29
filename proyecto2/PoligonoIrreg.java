import java.util.ArrayList;

public class PoligonoIrreg {
  protected ArrayList<Coordenada> vertices;
  protected int maxVertices;

  public PoligonoIrreg(int maxVertices) {
    this.vertices = new ArrayList<Coordenada>();
    this.maxVertices = maxVertices;
  }

  public void addPoint(int x, int y) {
    if (this.vertices.size() == this.maxVertices) {
      System.out.println("No se pueden agregar más vértices al polígono.");
      return;
    }
    this.vertices.add(new Coordenada(x, y));
  }

  public void setVertice(int position, Coordenada vertice) {
    this.vertices.set(position, vertice);
  }

  public ArrayList<Coordenada> vertices() {
    return this.vertices;
  }

  @Override
  public String toString() {
    String verticesStr = "";
    for (Coordenada vertice : this.vertices)
      verticesStr += "Coordenada: " + vertice.toString() + "\n";
    // for (int i = 0; i < this.nVertices; i++)
    //   verticesStr += "Vertice #" + i + "; Coordenada: " + this.vertices[i].toString() + "\n";
    return verticesStr;
  }

}
