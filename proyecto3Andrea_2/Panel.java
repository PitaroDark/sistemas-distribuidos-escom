
/*
 * Proyecto 3
 * ANDREA VANEGAS SUSANO
 * 7CM4
 */
import java.awt.*;

import javax.swing.*;

import java.util.ArrayList;

class Panel extends JPanel {
  private ArrayList<Asteroide> asteroides;

  public Panel(int numAsteroides) {
    asteroides = new ArrayList<>();
    this.setSize(900, 700);
    for (int i = 0; i < numAsteroides; i++) {
      Asteroide asteroide = new Asteroide((int) (Math.random() * 10) + 3, this);
      asteroides.add(asteroide);
      new Thread(asteroide).start();
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.BLUE);
    for (Asteroide asteroide : asteroides) {
      Polygon p = new Polygon();
      Coordenada pos = asteroide.getPosicion();
      for (Coordenada v : asteroide.getVertices()) {
        p.addPoint((int) (v.abcisa() + pos.abcisa()), (int) (v.ordenada() + pos.ordenada()));
      }
      g.drawPolygon(p);
    }
  }
}