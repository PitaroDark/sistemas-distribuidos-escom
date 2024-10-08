
/**
 * PROYECTO 3
 * IKARI BRANDON VARGAS OSORNIO
 * 7CM3
 */
import java.awt.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import javax.swing.*;

import java.util.Map;
import java.util.HashMap;

class Panel extends JPanel {

  private Map<String, Color> colores;
  private Map<String, JLabel[]> labels;
  private Map<String, Integer> votos;

  public Panel() {
    colores = Map.of(
        "MC", Color.ORANGE,
        "MORENA", Color.GRAY,
        "PAN", Color.BLUE,
        "PRD", Color.YELLOW,
        "PRI", Color.PINK,
        "PT", Color.RED,
        "PVEM", Color.GREEN,
        "Total", Color.BLACK);
    this.labels = new HashMap<String, JLabel[]>();
    this.votos = new HashMap<String, Integer>();
    fillPanel();
    startWatching();
  }

  private Map<String, Integer> getVotos() {
    Map<String, Integer> votos = new HashMap<String, Integer>();
    try {
      File file = new File(Constants.PATH_VOTOS);
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        String partido = parts[1];
        int value = votos.getOrDefault(partido, 0);
        votos.put(partido, value + 1);
      }
      reader.close();
      int total = votos.values().stream().mapToInt(Integer::intValue).sum();
      votos.put("Total", total);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return votos;
  }

  private void startWatching() {
    Thread thread = new Thread(() -> {
      while (true) {
        try {
          Map<String, Integer> votos = getVotos();
          this.votos = votos;
          repaint();
          for (Map.Entry<String, Integer> entry : votos.entrySet()) {
            // clear panel
            String partido = entry.getKey();
            Integer votosPartido = entry.getValue();
            JLabel[] labels = this.labels.get(partido);
            JLabel lblPartido = labels[0];
            lblPartido.setText(partido);
            JLabel lblVotosPartido = labels[1];
            lblVotosPartido.setText(votosPartido.toString());
          }
          Thread.sleep(1000);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    thread.start();
  }

  private void fillPanel() {
    setLayout(null);
    setBackground(Color.WHITE);

    int separacionY = 20;
    int separacionX = 20;

    JLabel lblTitle = new JLabel("Sistema de votos por partido");
    lblTitle.setBounds(separacionX, separacionY, 400, 20);
    add(lblTitle);
    separacionY += 40;

    votos = getVotos();
    for (String partido : votos.keySet()) {
      if (partido.equals("Total")) {
        continue;
      }
      separacionX = 20;
      JLabel lblPartido = new JLabel(partido);
      lblPartido.setBounds(separacionX, separacionY, 80, 20);
      add(lblPartido);
      separacionX += 80;

      JLabel lblVotosPartido = new JLabel("0");
      lblVotosPartido.setBounds(separacionX, separacionY, 50, 20);
      add(lblVotosPartido);
      separacionX += 60;

      this.labels.put(partido, new JLabel[] { lblPartido, lblVotosPartido });
      separacionY += 40;
    }
    // Total
    separacionX = 20;
    JLabel lblPartido = new JLabel("Total");
    lblPartido.setBounds(separacionX, separacionY, 80, 20);
    add(lblPartido);
    separacionX += 80;

    JLabel lblVotosPartido = new JLabel("0");
    lblVotosPartido.setBounds(separacionX, separacionY, 50, 20);
    add(lblVotosPartido);
    separacionX += 60;

    this.labels.put("Total", new JLabel[] { lblPartido, lblVotosPartido });
  }

  @Override
  public void paintComponent(Graphics g) {
    // super.paintComponent(g);
    // clear the panel
    // g.clearRect(0, 0, getWidth(), getHeight());

    int separacionY = 60;
    int separacionX = 160;
    int widht = 500;

    // CLEAR
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, 800, widht);

    for (Map.Entry<String, Integer> entry : votos.entrySet()) {
      String partido = entry.getKey();
      Integer votosPartido = entry.getValue();

      // JLabel[] labels = this.labels.get(partido);
      // System.out.println(labels.length);

      // JLabel lblPartido = labels[0];
      // lblPartido.setText(partido);

      // JLabel lblVotosPartido = labels[1];
      // lblVotosPartido.setText(votosPartido.toString());

      // Grafica de barra proporcional a 600
      if (partido.equals("Total")) {
        continue;
      }
      // CLEAR
      // g.setColor(Color.WHITE);
      // g.fillRect(separacionX, separacionY, widht, 20);

      int anchoBarra = (votosPartido * widht) / votos.get("Total");
      g.setColor(colores.get(partido));
      g.fillRect(separacionX, separacionY, anchoBarra, 20);

      separacionY += 40;
    }

    // CLEAR
    // g.setColor(Color.WHITE);
    // g.fillRect(separacionX, separacionY, widht, 20);
    // Grafica de barra total
    int total = votos.get("Total");
    g.setColor(colores.get("Total"));
    g.fillRect(separacionX, separacionY, (total * widht) / total, 20);

  }

}

public class EstadisticasGUI extends JFrame {

  public EstadisticasGUI() {
    setTitle("Estadisticas Interfaz Grafica");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(new Panel());
  }

  public static void main(String[] args) {
    EstadisticasGUI gui = new EstadisticasGUI();
    gui.setVisible(true);
    System.out.println("Presiona Ctrl + C en terminal para detener el proceso");
    System.out.println("Presiona el boton de cerrar ventana para detener el proceso");
  }

}
