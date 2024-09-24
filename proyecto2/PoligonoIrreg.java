public class PoligonoIrreg {

  private Coordenada[] vertices;
  private int nVertices;

  public PoligonoIrreg(int nVertices) {
    this.vertices = new Coordenada[nVertices];
    this.nVertices = nVertices;
    for (int i = 0; i < nVertices; i++) {
      double x = Math.random();
      double y = Math.random();
      this.vertices[i] = new Coordenada(x, y);
    }
  }

  public void addPoint(int x, int y){
    Coordenada vertice = new Coordenada(x, y);
    
  }

  public void modificaVertice(int nVertice, Coordenada vertice) {
    this.vertices[nVertice] = vertice.clone();
  }

  @Override
  public String toString() {
    String verticesStr = "";
    for (int i = 0; i < this.nVertices; i++) {
      verticesStr += "Vertice #" + i + "; Coordenada: " + this.vertices[i].toString() + "\n";
    }
    return verticesStr;
  }

}
