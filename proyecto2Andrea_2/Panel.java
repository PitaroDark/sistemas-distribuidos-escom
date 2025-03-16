import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private int x = 200;  
    private int y = 200;  
    private int velocidad; 

    public Panel(int velocidad) {
        this.velocidad = velocidad;
        setBackground(Color.BLACK); 
    }

    public void iniciarMovimiento() {
        // Timer para mover el rectángulo a intervalos regulares
        Timer timer = new Timer(10, e -> {
            x += velocidad;  
            if (x > getWidth()) {
                x = 0;  
            }
            repaint();  
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  

        g.setColor(Color.blue);
        g.fillRect(x, y, 5, 5);  
    }
}
