/*
 * Proyecto 2
 * Andrea Vanegas Susano
 * 7CM3
 */
import java.awt.Polygon;

public class PoligonoIrreg {

  protected Coordenada[] vertices;
  protected Polygon polygon;
  protected int nVertices;
  protected int indice;

  public PoligonoIrreg(int nVertices) {
    this.vertices = new Coordenada[nVertices];
    this.polygon = new Polygon();
    this.nVertices = nVertices;
    this.indice = 0;
  }

  public void agregarVertice(Coordenada vertice) {
    this.vertices[this.indice] = vertice.clone();
    this.polygon.addPoint((int) vertice.abcisa(), (int) vertice.ordenada());
    this.indice++;
  }

  public Polygon getPolygon(){
    return this.polygon;
  }

  @Override
  public String toString() {
    String verticesStr = "";
    for (int i = 0; i < this.nVertices; i++)
      verticesStr += "Vertice #" + i + "; Coordenada: " + this.vertices[i].toString() + "\n";
    return verticesStr;
  }

}
