/*
 * Proyecto 2
 * Andrea Vanegas Susano
 * 7CM3
 */
import java.awt.*;

import javax.swing.*;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;

public class Ventana extends JPanel {

    private int numeroPoligonos;
    private List<PoligonoReg> poligonos;

    public Ventana(int numeroPoligonos) {
        this.numeroPoligonos = numeroPoligonos;
        this.poligonos = new ArrayList<PoligonoReg>();
        for (int i = 0; i < numeroPoligonos; i++) {
            int vertices = (int) (Math.random() * 13) + 3;
            PoligonoReg poligonoReg = new PoligonoReg(vertices);
            this.poligonos.add(poligonoReg);
        }
        poligonos.sort(new Comparator<PoligonoReg>() {
            @Override
            public int compare(PoligonoReg poligono1, PoligonoReg poligono2) {
                return Double.compare(poligono2.obtieneArea(), poligono1.obtieneArea());
            }
        });
        Thread hilo1 = new Thread(() -> {
            try {
                Thread.sleep(3 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Random random = new Random();
            for (int i = 0; i < numeroPoligonos; i++) {
                PoligonoReg poligono = this.poligonos.get(i);
                Thread thread = new Thread(() -> {
                    Polygon polygonActual = poligono.getPolygon();
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
                        repaint();
                        int area = (int) poligono.obtieneArea() / 100;
                        try {
                            Thread.sleep(area);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        hilo1.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.blue);
        for (int i = 0; i < this.numeroPoligonos; i++) {
            PoligonoReg poligonoReg = this.poligonos.get(i);
            g.drawPolygon(poligonoReg.getPolygon());
        }
    }
}