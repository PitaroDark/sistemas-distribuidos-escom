import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private final int VELOCIDAD_CONSTANTE = 3;
    private final Color BACKGROUND_COLOR = Color.BLACK;
    private final Color COLOR_PERSEGUIDOR = Color.GREEN;
    private final Color COLOR_PERSEGUIDO = Color.BLUE;
    private final int posicionInicialPerseguido[] = { 200, 200 };
    private final int posicionInicialPerseguidores[][] = {
            { 200, HEIGHT - 200 }, { 300, HEIGHT - 200 }, { 400, HEIGHT - 200 }, { 500, HEIGHT - 200 },
            { 800, HEIGHT - 200 }, { 900, HEIGHT - 200 }, { 1000, HEIGHT - 200 }
    };
    private Airplane perseguido;
    private Airplane perseguidores[];
    private boolean alcanzado;
    private boolean tiempoTerminado;

    private double velocidad;

    private long startTime;

    public Panel(double velocidad, int nPerseguidores) {
        this.velocidad = velocidad;
        this.perseguidores = new Airplane[nPerseguidores];
        this.alcanzado = false;
        this.tiempoTerminado = false;
        this.setBackground(BACKGROUND_COLOR);
        this.setSize(WIDTH, HEIGHT);

        this.perseguido = new Airplane(posicionInicialPerseguido[0], posicionInicialPerseguido[1],
                VELOCIDAD_CONSTANTE, 0.05, 5, 5);
        this.perseguido.setMaxWindow(WIDTH, HEIGHT);
        for (int i = 0; i < nPerseguidores; i++) {
            this.perseguidores[i] = new Airplane(posicionInicialPerseguidores[i][0], posicionInicialPerseguidores[i][1],
                    VELOCIDAD_CONSTANTE * this.velocidad, 0.05, 5, 5);
            this.perseguidores[i].setMaxWindow(WIDTH, HEIGHT);
        }
    }

    public void startMoves() {
        this.startTime = System.currentTimeMillis();
        new Timer(10, e -> {
            this.perseguido.stayWithinBounds();
    
            // El perseguido evalúa la posición de los perseguidores y ajusta su dirección
            this.perseguido.evadePursuers(perseguidores);
            this.perseguido.move();
    
            boolean isCloseToPerseguido = false;
            for (Airplane perseguidor : perseguidores) {
                double anglePerseguido = Math.atan2(this.perseguido.getY() - perseguidor.getY(),
                        this.perseguido.getX() - perseguidor.getX());
                perseguidor.setAngle(anglePerseguido);
                perseguidor.move();
                perseguidor.stayWithinBounds();
    
                isCloseToPerseguido |= perseguido.isCloseTo(perseguido, 40);
    
                if (perseguidor.isCloseTo(perseguido, 20)) {
                    alcanzado = true;
                    System.out.println("¡El perseguidor alcanzó al perseguido en las coordenadas: ("
                            + perseguidor.getX() + ", " + perseguidor.getY() + ")");
                    ((Timer) e.getSource()).stop();
                }
            }
    
            // Verificar si el tiempo se ha agotado (2 minutos sin alcanzar)
            if (!tiempoTerminado && (System.currentTimeMillis() - startTime) >= 1000 * 60 * 2) {
                tiempoTerminado = true;
                System.out.println("¡Han pasado 2 minutos sin que el perseguidor haya alcanzado al perseguido!");
                ((Timer) e.getSource()).stop();
            }
    
            repaint();
        }).start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(COLOR_PERSEGUIDOR);
        for (Airplane perseguidor : perseguidores) {
            for (Point p : perseguidor.getRastro()) {
                g.fillRect(p.x, p.y, 2, 2);
            }
        }

        g.setColor(COLOR_PERSEGUIDO);
        for (Point p : this.perseguido.getRastro()) {
            g.fillRect(p.x, p.y, 2, 2);
        }

        g.setColor(COLOR_PERSEGUIDOR);
        for (Airplane perseguidor : perseguidores) {
            g.fillRect((int) perseguidor.getX(), (int) perseguidor.getY(), 5, 5);
        }

        g.setColor(COLOR_PERSEGUIDO);
        g.fillRect((int) this.perseguido.getX(), (int) this.perseguido.getY(), 5, 5);

        g.setColor(Color.WHITE);
        int y = 10;
        for (Airplane perseguidor : perseguidores) {
            double distancia = Math.sqrt(Math.pow(perseguidor.getX() - this.perseguido.getX(), 2)
                    + Math.pow(perseguidor.getY() - this.perseguido.getY(), 2));
            g.drawString("Distancia: " + String.format("%.2f", distancia) + " px", 10, y);
            y += 10;
        }

        if (this.alcanzado) {
            g.setColor(Color.YELLOW);
            g.drawString("¡El perseguidor alcanzó al perseguido!", 10, y);
        } else if (tiempoTerminado) {
            g.setColor(Color.YELLOW);
            g.drawString("Tiempo agotado: No se alcanzó al perseguido", 10, y);
        }
    }
}