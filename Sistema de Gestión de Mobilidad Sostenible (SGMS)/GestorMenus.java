import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import javax.swing.JScrollPane;
import java.util.LinkedHashMap;

public class GestorMenus extends Menu {

    protected CardLayout cardLayout;  
    protected JPanel cardsPanel;  // Panel que usará CardLayout
    protected Stack<JPanel> panelHistory = new Stack<>();
    
    protected int WINDOW_WIDTH = 500;
    protected int WINDOW_HEIGHT = 500;
    
    private Simulacion simulacion;
    private Ciudad ciudad;
    
    // Constructor
    public GestorMenus(Simulacion simulacion, Ciudad ciudad) { 
        this.simulacion = simulacion;
        this.ciudad = ciudad;
        
        cardLayout = new CardLayout();
    
        // Se crea un JPanel que usa la organización de CardLayout
        cardsPanel = new JPanel(cardLayout);

        frame = crearNuevaVentana(); // Se crea una nueva ventana para GestorMenus, por la que se navegará a los distintos submenús
        frame.add(cardsPanel); 
        frame.setTitle("Gestor Menús");  // Establece el título de la ventana
        
        panel = crearPanel("GestorMenus");
        
        // Añadir los menús al contenedor CardLayout
        cardsPanel.add(panel, "GestorMenus");  // Añadir el panel principal al CardLayout
        
        LinkedHashMap<String, Boton> botones = new LinkedHashMap<>();
        // Crear y añadir el botón de iniciar sesión al HashMap
        botones.put("IniciarSesion", new Boton("Iniciar Sesión", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarMenuIniciarSesion();
            }
        }));
        
        agregarBotones(botones, panel);
        
        frame.setVisible(true);
    }
    
    private void iniciarMenuIniciarSesion() {
        MenuIniciarSesion menuIniciarSesion = new MenuIniciarSesion(simulacion, ciudad, this);
    
        // Al navegar a un nuevo panel, lo agregamos a la pila
        panelHistory.push(panel);
        agregarBotonAtras(menuIniciarSesion.panel);
        cardsPanel.add(menuIniciarSesion.panel, "IniciarSesion");
        navegarA(menuIniciarSesion.panel);
    }
    
    // Método para agregar un botón "Atrás" con funcionalidad
    protected void agregarBotonAtras(JPanel panel) {
        JButton botonAtras = new JButton("Atrás");
        botonAtras.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!panelHistory.isEmpty()) {
                    // Obtener el último panel de la pila
                    JPanel panelAnterior = panelHistory.pop();
                    // Navegar al panel anterior
                    navegarA(panelAnterior);
                }
            }
        });
        agregarBotonPosicionado(panel, botonAtras, "left");
    }

    // Método para navegar a un nuevo panel y almacenar el panel actual en la pila
    protected void navegarA(JPanel panel) {
        // Mostrar el panel
        cardLayout.show(cardsPanel, panel.getName());
        
        System.out.println("Navegando a " + panel.getName());
    }
}