/*
 * Proyecto 3
 * ANDREA VANEGAS SUSANO
 * 7CM4
 */
import java.awt.*;

import javax.swing.*;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;

public class Panel extends JPanel implements Comparator<PoligonoReg> {

  private int numPolygons;
  private List<Polygon> polygons;
  private List<PoligonoReg> poligonos;

  public Panel(int numPolygons) {
    this.numPolygons = numPolygons;
    this.poligonos = new ArrayList<PoligonoReg>();
    for (int i = 0; i < this.numPolygons; i++) {
      int numVertices = (int) (Math.random() * 13) + 3;
      PoligonoReg poligono = new PoligonoReg(numVertices);
      this.poligonos.add(poligono);
    }
    poligonos.sort(this);
    this.polygons = new ArrayList<Polygon>();
    for (PoligonoReg poligono : this.poligonos) {
      Polygon polygon = new Polygon();
      for (Coordenada vertice : poligono.getVertices())
        polygon.addPoint((int) vertice.abcisa(), (int) vertice.ordenada());
      this.polygons.add(polygon);
    }
    new Thread(() -> {
      this.wait(3000);
      this.movePolygons();
    }).start();
  }

  public void movePolygons() {
    Random random = new Random();
    for (int i = 0; i < poligonos.size(); i++) {
      Polygon polygon = this.polygons.get(i);
      PoligonoReg poligono = this.poligonos.get(i);
      Thread thread = new Thread(() -> {
        Polygon polygonActual = polygon;
        int maxRadius = 600 / 8;
        int x = random.nextInt(800 - (2 * maxRadius)) + maxRadius;
        int y = random.nextInt(600 - (2 * maxRadius)) + maxRadius;
        // MOVE 1 BY 1 TO THE NEW POSITION USING WHILE EACH 50MS AND REPAINT
        while (polygonActual.getBounds().x != x || polygonActual.getBounds().y != y) {
          if (polygonActual.getBounds().x < x)
            polygonActual.translate(1, 0);
          if (polygonActual.getBounds().x > x)
            polygonActual.translate(-1, 0);
          if (polygonActual.getBounds().y < y)
            polygonActual.translate(0, 1);
          if (polygonActual.getBounds().y > y)
            polygonActual.translate(0, -1);
          this.repaint();
          //EL TIEMPO DE ESPERA ES DIRECTAMENTE PROPORCINAL AL AREA
          int area = (int) poligono.obtieneArea()/100;
          this.wait(area);
          //this.wait(50);
        }
      });
      thread.start();
      this.wait(1000);
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    // Clear the screen
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, 900, 700);
    // Draw the polygons
    g.setColor(Color.BLUE);
    for (Polygon polygon : this.polygons) {
      g.drawPolygon(polygon);
    }
  }

  public void wait(int ms) {
    try {
      Thread.sleep(ms);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public int compare(PoligonoReg p1, PoligonoReg p2) {
    return Double.compare(p2.obtieneArea(), p1.obtieneArea());
  }

}
