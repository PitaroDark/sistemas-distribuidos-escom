import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.*;

public class Panel extends JPanel {
  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.blue);

    Polygon polygon = new Polygon();
    polygon.addPoint(0, 0);
    polygon.addPoint(100, 0);
    polygon.addPoint(50, 100);
    g.drawPolygon(polygon);
  }
}
