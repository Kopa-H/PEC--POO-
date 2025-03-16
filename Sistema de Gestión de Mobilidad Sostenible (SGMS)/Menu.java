import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class Menu {
    protected JFrame frame;
    protected JPanel panel;
    
    // Default values
    public int WINDOW_WIDTH = 200;
    public int WINDOW_HEIGHT = 400;
    
    // Constructor
    public Menu() {
        frame = new JFrame();
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
    }

    // Método para inicializar la interfaz, lo implementan las clases hijas
    public abstract void iniciarInterfaz();

    // Método para mostrar la ventana
    public void mostrarVentana() {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icono.png"));
        frame.setLocationRelativeTo(null); // Centra la ventana
        frame.add(panel);
        frame.setVisible(true);
    }
}
