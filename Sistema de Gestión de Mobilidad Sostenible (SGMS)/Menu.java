import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

// Clase abstracta para manejar los menús
public abstract class Menu {
    protected HashMap<String, Boton> botones;
    protected JPanel panel;                    
    protected JScrollPane scrollPane;
        
    protected JFrame frame; 
    
    protected int WINDOW_WIDTH = 500;
    protected int WINDOW_HEIGHT = 500;

    // Constructor
    public Menu() {
        botones = new HashMap<>();
    }

    // Método abstracto que las subclases implementarán para crear el menú específico
    public void agregarBotones() {
        // Añadir los botones al menú con sus funcionalidades en un HashMap
        for (String nombreBoton : botones.keySet()) {
            Boton boton = botones.get(nombreBoton);
            agregarBotonPosicionado(panel, boton.getBoton(), "middle");
        }       
    }
    
    // Método para crear un panel
    public void crearPanel(String nombrePanel) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setName(nombrePanel);  // Asignamos un nombre único al panel
        
        scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
}