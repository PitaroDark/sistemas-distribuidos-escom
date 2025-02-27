import java.util.ArrayList;
import java.util.Comparator;

public class PoligonoIrreg implements Comparator<Coordenada> {

  protected ArrayList<Coordenada> vertices;

  public PoligonoIrreg() {
    this.vertices = new ArrayList<Coordenada>();
  }

  public void anadeVertice(Coordenada vertice) {
    this.vertices.add(vertice.clone());
  }

  public int getCurrentLenght() {
    return this.vertices.size();
  }

  // Implementacion del metodo compare de la interfaz Comparator
  public int compare(Coordenada a, Coordenada b) {
    if (a.getMagnitud() < b.getMagnitud())
      return -1;
    if (a.getMagnitud() > b.getMagnitud())
      return 1;
    return 0;
  }

  public void ordenaVertices() {
    this.vertices.sort(this);
  }

  @Override
  public String toString() {
    String verticesStr = "";
    for (Coordenada coordenada : vertices)
      verticesStr += "Vertice #" + vertices.indexOf(coordenada) + "; Coordenada: " + coordenada.toString() + "\n";
    return verticesStr;
  }

}
