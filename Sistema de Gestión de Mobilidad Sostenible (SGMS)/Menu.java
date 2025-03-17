import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public abstract class Menu {
    protected JFrame frame;
    protected JPanel panel;
    protected JScrollPane scrollPane;  // ScrollPane para el panel
    
    protected HashMap<String, Boton> botones;  // Usar la clase Boton en lugar de JButton
    
    // Default values
    public int WINDOW_WIDTH = 200;
    public int WINDOW_HEIGHT = 400;
    
    // Constructor
    public Menu() {
        botones = new HashMap<>();
        
        frame = new JFrame();
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Apilar verticalmente

        // Añadir márgenes alrededor del panel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Envolver el panel en un JScrollPane con scrollbar vertical
        scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // Scroll siempre visible
        
        // Crear los botones y añadirlos al HashMap
        String[] nombreBotones = {"Usuario Normal", "Usuario Premium", "Técnico de Mantenimiento", "Mecánico", "Administrador"};
        
        for (String nombre : nombreBotones) {
            Boton boton = new Boton(nombre, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    asignarFuncionesBotones(nombre);  // Usar el nombre del botón para asignar la acción
                }
            });
            botones.put(nombre, boton);  // Añadir el Boton al HashMap
            agregarBotonCentrado(boton.getBoton());  // Añadir al panel con el JButton dentro del objeto Boton
        }
    }
    
    // Método abstracto para asignar funciones a los botones
    abstract protected void asignarFuncionesBotones(String nombreBoton);

    // Método para agregar botones centrados con espaciado
    protected void agregarBotonCentrado(JButton boton) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel para centrar el botón
        buttonPanel.add(boton);  // Añadir botón centrado
        panel.add(buttonPanel);  // Añadir el panel con el botón al panel principal
        panel.add(Box.createVerticalStrut(10));  // Añadir espacio entre botones
    }

    // Método para inicializar la interfaz, lo implementan las clases hijas
    public abstract void iniciarInterfaz();

    // Método para mostrar la ventana
    public void mostrarVentana() {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icono.png"));
        frame.setLocationRelativeTo(null); // Centra la ventana
        frame.add(scrollPane);  // Añadir el scrollPane en lugar del panel directamente
        frame.setVisible(true);
    }
}
