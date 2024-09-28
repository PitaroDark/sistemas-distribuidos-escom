import javax.swing.*;

public class Main extends JFrame {

  public Main() { }

  public Main(int numPolygons) {
    setTitle("Proyecto 2");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(new Panel(numPolygons));
  }

  public static void main(String[] args) {
    int numPolygons = (args.length > 0) ? Integer.parseInt(args[0]) : 1;
    Main main = new Main(numPolygons);
    main.setVisible(true);
  }

}
