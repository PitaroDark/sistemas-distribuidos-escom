import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{
    public static void main(String[] args){
        Main gui = new Main();
        gui.setVisible(true);
    }

    public Main(){
        setTitle("Proyecto 2");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel p = new Panel();
        add(p);
    }

    private class Panel extends JPanel{
        public Panel() {
            setBackground(Color.BLACK); 
        }
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
           
            g.setColor(Color.CYAN);
            g.fillRect(100, 100, 5, 5);

            g.setColor(Color.RED);
            g.fillRect(200, 200, 5, 5);
        }
    }
}