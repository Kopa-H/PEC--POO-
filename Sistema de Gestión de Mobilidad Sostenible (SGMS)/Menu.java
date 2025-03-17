import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

// Clase abstracta para manejar los menús
public abstract class Menu {
    protected HashMap<String, Boton> botones;  
    protected JPanel panel;                    
    protected JScrollPane scrollPane;
    
    protected CardLayout cardLayout;  
    protected JPanel panelsContainer;       
    protected JFrame frame; 
    
    protected int WINDOW_WIDTH = 500;
    protected int WINDOW_HEIGHT = 500;

    // Constructor
    public Menu() {
        botones = new HashMap<>();
        // Crear y configurar el CardLayout para el contenedor
        cardLayout = new CardLayout();  
        // Se crea un panel que usa la organización del cardLayout y que contendrá otros paneles
        panelsContainer = new JPanel(cardLayout);  
    }
    
    // Método para crear la ventana
    protected void crearNuevaVentana() {
        // Crear el JFrame para la ventana principal
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        frame.add(panelsContainer);  
        frame.setVisible(true);
    }
    
    // Método para crear un panel
    protected void crearPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    // Método abstracto que las subclases implementarán para crear el menú específico
    public void iniciarMenu() {
        // Añadir los botones al menú con sus funcionalidades en un HashMap
        for (String nombreBoton : botones.keySet()) {
            Boton boton = botones.get(nombreBoton);
            agregarBotonPosicionado(panel, boton.getBoton(), "middle");
        }       
    }

    // Método para agregar botones con posición controlada
    protected void agregarBotonPosicionado(JPanel panel, JButton boton, String posicion) {
        FlowLayout layout;
        switch (posicion.toLowerCase()) {
            case "left": layout = new FlowLayout(FlowLayout.LEFT); break;
            case "right": layout = new FlowLayout(FlowLayout.RIGHT); break;
            case "middle": default: layout = new FlowLayout(FlowLayout.CENTER); break;
        }

        JPanel buttonPanel = new JPanel(layout);
        buttonPanel.add(boton);
        panel.add(buttonPanel);
        panel.add(Box.createVerticalStrut(10));  
    }

    // Método para agregar un botón "Atrás" con funcionalidad
    protected void agregarBotonAtras(JPanel panel, String nombreMenuAnterior) {
        JButton botonAtras = new JButton("Atrás");
        botonAtras.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                navegarA(nombreMenuAnterior);
            }
        });
        agregarBotonPosicionado(panel, botonAtras, "left");
    }

    // Método para navegar entre las tarjetas del CardLayout
    protected void navegarA(String nombreMenu) {
        System.out.println("Se navega hacia " + nombreMenu);
        cardLayout.show(container, nombreMenu);
    }
}