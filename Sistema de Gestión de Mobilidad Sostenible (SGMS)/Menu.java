import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public abstract class Menu {
    protected JFrame frame;
    protected JPanel panel;
    protected JScrollPane scrollPane;  // ScrollPane para el panel
    protected CardLayout cardLayout;  // CardLayout para gestionar la navegación entre menús
    protected JPanel container;  // Contenedor para los menús (Panel con CardLayout)
    
    protected HashMap<String, Boton> botones;  // Usar la clase Boton en lugar de JButton
    
    // Default values
    public int WINDOW_WIDTH = 200;
    public int WINDOW_HEIGHT = 400;
    
    // Constructor
    public Menu() {
        botones = new HashMap<>();
        
        // Crear el CardLayout y el contenedor de los menús
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // Crear el JFrame y panel para los botones
        frame = new JFrame();
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Apilar verticalmente

        // Añadir márgenes alrededor del panel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Envolver el panel en un JScrollPane con scrollbar vertical
        scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // Scroll siempre visible
    }

    // Método para agregar botones centrados con espaciado
    protected void agregarBotonCentrado(JButton boton) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel para centrar el botón
        buttonPanel.add(boton);  // Añadir botón centrado
        panel.add(buttonPanel);  // Añadir el panel con el botón al panel principal
        panel.add(Box.createVerticalStrut(10));  // Añadir espacio entre botones
    }

    // Método para agregar el botón de "Atrás"
    protected void agregarBotonAtras(String nombreMenuAnterior) {
        JButton botonAtras = new JButton("Atrás");
        botonAtras.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Volver al menú anterior usando CardLayout
                cardLayout.show(container, nombreMenuAnterior);
            }
        });
        agregarBotonCentrado(botonAtras);  // Añadimos el botón "Atrás" al menú
    }

    // Método para inicializar la interfaz, lo implementan las clases hijas
    public abstract void iniciarInterfaz();

    // Método para mostrar la ventana
    public void mostrarVentana() {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Cambiado para no cerrar todo el programa
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icono.png"));
        frame.setLocationRelativeTo(null); // Centra la ventana
        frame.add(container);  // Añadir el contenedor con CardLayout en lugar del panel directamente
        frame.setVisible(true);
    }

    // Método para navegar a un nuevo menú, agregándolo al CardLayout
    public void navegarA(Menu nuevoMenu, String nombreMenu) {
        container.add(nuevoMenu.panel, nombreMenu);  // Añadimos el nuevo panel al contenedor
        cardLayout.show(container, nombreMenu);  // Mostramos el nuevo menú
    }
}
