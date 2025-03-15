import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class Menu {
    protected JFrame frame;
    protected JPanel panel;
    
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
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icono.png"));
        frame.setLocationRelativeTo(null); // Centra la ventana
        frame.add(panel);
        frame.setVisible(true);
    }
}
