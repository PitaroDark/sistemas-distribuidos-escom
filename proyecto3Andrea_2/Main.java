/*
 * Proyecto 3
 * ANDREA VANEGAS SUSANO
 * 7CM4
 */
import javax.swing.*;

public class Main extends JFrame {
  public Main(int numAsteroides) {
    setTitle("Asteroides");
    setSize(900, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(new Panel(numAsteroides));
  }

  public static void main(String[] args) {
    int numAsteroides = (args.length > 0) ? Integer.parseInt(args[0]) : 5;
    Main main = new Main(numAsteroides);
    main.setVisible(true);
  }
}
