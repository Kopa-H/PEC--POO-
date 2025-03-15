import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GestorMenus {

    private JFrame frame;
    private JPanel panel;
    private JButton button1, button2, button3, button4;

    /**
     * Constructor para objetos de la clase GestorMenus
     */
    public GestorMenus() {
        // Inicializar componentes pero no crear la ventana aún
    }

    /**
     * Inicia la interfaz gráfica.
     */
    public void iniciarInterfaz() {
        // Configuración de la ventana
        frame = new JFrame("Gestor de Menús");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icono.png"));
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Panel con color de fondo azul
        panel = new JPanel();
        panel.setBackground(Color.BLUE);
        panel.setLayout(new FlowLayout());

        // Botones
        button1 = new JButton("Botón 1");
        button2 = new JButton("Botón 2");
        button3 = new JButton("Botón 3");
        button4 = new JButton("Botón 4");

        // Estética profesional
        Font font = new Font("Arial", Font.PLAIN, 16);
        button1.setFont(font);
        button2.setFont(font);
        button3.setFont(font);
        button4.setFont(font);

        button1.setBackground(new Color(0, 123, 255));
        button2.setBackground(new Color(0, 123, 255));
        button3.setBackground(new Color(0, 123, 255));
        button4.setBackground(new Color(0, 123, 255));

        button1.setForeground(Color.WHITE);
        button2.setForeground(Color.WHITE);
        button3.setForeground(Color.WHITE);
        button4.setForeground(Color.WHITE);

        // Añadir los botones al panel
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);

        // Añadir el panel a la ventana
        frame.add(panel);

        // Iniciar la ventana minimizada
        frame.setState(Frame.ICONIFIED);

        // Funcionalidad de botones (puedes agregar la lógica aquí)
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para el botón 1
                System.out.println("Botón 1 presionado");
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para el botón 2
                System.out.println("Botón 2 presionado");
            }
        });

        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para el botón 3
                System.out.println("Botón 3 presionado");
            }
        });

        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para el botón 4
                System.out.println("Botón 4 presionado");
            }
        });

        // Mostrar la ventana
        frame.setVisible(true);
    }
}
