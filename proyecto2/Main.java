import javax.swing.*;

public class Main extends JFrame {

  public Main() {
    setTitle("Proyecto 2");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Panel panel = new Panel();
    add(panel);
  }
  
  public static void main(String[] args) {
    Main main = new Main();
    main.setVisible(true);
  }

}
