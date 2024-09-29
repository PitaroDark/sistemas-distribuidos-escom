import javax.swing.*;

public class Main extends JFrame {

  public static int numPolygons;

  public Main() {
    setTitle("Proyecto 2");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(new Panel(numPolygons));
  }

  public static void main(String[] args) {
    numPolygons = (args.length > 0) ? Integer.parseInt(args[0]) : 1;
    Main main = new Main();
    main.setVisible(true);
  }

}
