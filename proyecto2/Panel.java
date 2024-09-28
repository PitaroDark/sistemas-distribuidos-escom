import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

  private int numPolygons;

  public Panel(int numPolygons) {
    this.numPolygons = numPolygons;
  }

  @Override
  public void paintComponent(Graphics g) {
    for (int i = 0; i < this.numPolygons; i++) {
      int x = (i == 0) ? 0 : (int) (Math.random() * 800);
      Polygon polygon = new Polygon();
      polygon.addPoint(0 + x, 0 + x);
      polygon.addPoint(100 + x, 0 + x);
      polygon.addPoint(50 + x, 100 + x);
      g.setColor(Color.BLUE);
      g.drawPolygon(polygon);
    }

    //g.fill3DRect(0, 0, 100, 100, false);
    //g.fillPolygon(new int[] { 0, 100, 50 }, new int[] { 0, 0, 100 }, 3);
    //Grafic a circle where englobes the polygon and set color to red
    g.setColor(Color.RED);
    g.drawOval(0, 0, 100, 100);
    g.setColor(Color.BLACK); //DEFAULT COLOR

    //Hacer que un poligono rote 45 grados cada segundo
    
  }

}
