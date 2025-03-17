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
    protected JPanel container;       
    protected JFrame frame; 
    
    protected int WINDOW_WIDTH = 500;
    protected int WINDOW_HEIGHT = 500;

    // Constructor
    public Menu() {
        botones = new HashMap<>();
        
        // Crear y configurar el CardLayout para el contenedor
        cardLayout = new CardLayout();  
        container = new JPanel(cardLayout);  
        
        // Crear el JFrame para la ventana principal dentro del constructor
        frame = new JFrame("Gestor de Menús");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  
        
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        frame.add(container);  
    }

    // Método abstracto que las subclases implementarán para crear el menú específico
    public void iniciarMenu() {
        // Añadir los botones al menú
        for (String nombreBoton : botones.keySet()) {
            Boton boton = botones.get(nombreBoton);
            agregarBotonPosicionado(boton.getBoton(), "middle");
            
            System.out.println("Se ha agregado el botón: " + nombreBoton);
        }

        // Hacer visible la ventana
        frame.setVisible(true);
        
    }

    // Método para agregar botones con posición controlada
    protected void agregarBotonPosicionado(JButton boton, String posicion) {
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
    protected void agregarBotonAtras(String nombreMenuAnterior) {
        JButton botonAtras = new JButton("Atrás");
        botonAtras.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                navegarA(nombreMenuAnterior);
            }
        });
        agregarBotonPosicionado(botonAtras, "left");
    }

    // Método para navegar entre las tarjetas del CardLayout
    protected void navegarA(String nombreMenu) {
        System.out.println("Se navega hacia " + nombreMenu);
        cardLayout.show(container, nombreMenu);  
    }
}