import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JPanel {
    private int x1 = 200, y1 = 200;  
    private int x2 = 600, y2 = 200;  
    private int velocidad;  

    private static final int VELOCIDAD_CONSTANTE = 3;  
    private long startTime; 
    private boolean alcanzado = false;  
    private boolean tiempoTerminado = false;  

    private List<Point> rastroPerseguidor = new ArrayList<>();  
    private List<Point> rastroPerseguido = new ArrayList<>();  

    public Panel(int velocidad) {
        this.velocidad = velocidad;
        setBackground(Color.BLACK); 
    }

    public void iniciarMovimiento() {

        startTime = System.currentTimeMillis();

        Timer timer = new Timer(10, e -> {
            if (x1 < x2) {
                x1 += velocidad;  
            } else if (x1 > x2) {
                x1 -= velocidad;  
            }

            
            if (x2 < getWidth() - 50) {
                x2 += VELOCIDAD_CONSTANTE;  
            } else {
                x2 -= VELOCIDAD_CONSTANTE;  
            }

          
            rastroPerseguidor.add(new Point(x1, y1));
            rastroPerseguido.add(new Point(x2, y2));

           
            if (Math.abs(x1 - x2) < 20 && Math.abs(y1 - y2) < 20) {
                alcanzado = true;
                System.out.println("¡El perseguidor alcanzó al perseguido en las coordenadas: (" + x1 + ", " + y1 + ")");
                ((Timer) e.getSource()).stop();  
            }

            
            if (!tiempoTerminado && (System.currentTimeMillis() - startTime) >= 120000) {
                tiempoTerminado = true;
                System.out.println("¡Han pasado 2 minutos sin que el perseguidor haya alcanzado al perseguido!");
                ((Timer) e.getSource()).stop();  
            }

            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  

        //perseguidor
        g.setColor(Color.GREEN);
        for (Point p : rastroPerseguidor) {
            g.fillRect(p.x, p.y, 2, 2);  
        }

        // perseguido
        g.setColor(Color.BLUE);
        for (Point p : rastroPerseguido) {
            g.fillRect(p.x, p.y, 2, 2); 
        }

       
        g.setColor(Color.GREEN);
        g.fillRect(x1, y1, 5, 5);
        g.setColor(Color.BLUE);
        g.fillRect(x2, y2, 5, 5);  

       
        double distancia = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        g.setColor(Color.WHITE);
        g.drawString("Distancia: " + String.format("%.2f", distancia) + " px", 10, 30);

        
        if (alcanzado) {
            g.setColor(Color.YELLOW);
            g.drawString("¡El perseguidor alcanzó al perseguido!", 10, 50);
        } else if (tiempoTerminado) {
            g.setColor(Color.YELLOW);
            g.drawString("Tiempo agotado: No se alcanzó al perseguido", 10, 50);
        }
    }
}
