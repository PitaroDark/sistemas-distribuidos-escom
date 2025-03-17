import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        double velocidad = (args.length > 0) ? Double.parseDouble(args[0]) : 5;
        int perseguidores = (args.length > 1) ? Integer.parseInt(args[1]) : 1;
        Main gui = new Main(velocidad, perseguidores);
        gui.setVisible(true);
    }

    public Main(double velocidad, int perseguidores) {
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel p = new Panel(velocidad, perseguidores);
        this.add(p);
        //p.iniciarMovimiento();
        p.startMoves();
    }
}
