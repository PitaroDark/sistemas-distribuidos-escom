import java.awt.*;
import javax.swing.*;

public class Panel extends JPanel {

  private int numPolygons;

  public Panel(int numPolygons) {
    this.numPolygons = numPolygons;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int i = 0; i < this.numPolygons; i++) {
      int numVertices = (int) (Math.random() * 13) + 3;
      PoligonoReg poligono = new PoligonoReg(numVertices);
      g.setColor(Color.BLUE);
      Polygon polygon = new Polygon();
      int posX = (int) (Math.random() * 800);
      int posY = (int) (Math.random() * 600);
      for (int j = 0; j < numVertices; j++) {
        Coordenada vertice = poligono.vertices().get(j);
        polygon.addPoint((int) vertice.abcisa() + posX, (int) vertice.ordenada() + posY);
      }
      g.drawPolygon(polygon);
    }
  }

}
