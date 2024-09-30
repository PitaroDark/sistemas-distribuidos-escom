/**
 * Proyecto 2
 * Andrea Vanegas Susano
 * 7CM3
 */
import javax.swing.*;

public class Main extends JFrame{
    public static void main(String[] args){
        int poligonos = Integer.parseInt(args[0]);
        Main gui = new Main(poligonos);
        gui.setVisible(true);
    }
    
    public Main(int poligonos){
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Ventana p = new Ventana(poligonos);
        add(p);
    }
}
