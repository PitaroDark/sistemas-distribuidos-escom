import javax.swing.*;

public class Main extends JFrame {
    private int velocidad; 

    public static void main(String[] args) {
        int velocidad = 5;  
        if (args.length > 0) {
            try {
                velocidad = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Error: Argumento de velocidad no válido. Usando velocidad por defecto.");
            }
        }

        Main gui = new Main(velocidad);
        gui.setVisible(true);
    }

    public Main(int velocidad) {
        this.velocidad = velocidad;
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel p = new Panel(velocidad);  
        add(p);

        p.iniciarMovimiento();
    }
}
