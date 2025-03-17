import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class GestorMenus extends Menu {

    protected CardLayout cardLayout;  
    protected JPanel panelsContainer;
    protected Stack<JPanel> panelHistory = new Stack<>();
    
    protected int WINDOW_WIDTH = 500;
    protected int WINDOW_HEIGHT = 500;
    
    // Constructor
    public GestorMenus() {            
        cardLayout = new CardLayout();  
        // Se crea un panel que usa la organización del cardLayout y que contendrá otros paneles
        panelsContainer = new JPanel(cardLayout);  
        
        crearNuevaVentana(); // Se crea una nueva ventana para GestorMenus, por la que se navegará a los distintos submenús
        frame.setTitle("Gestor Menús");  // Establece el título de la ventana
        
        crearPanel("GestorMenus");
        
        // Añadir los menús al contenedor CardLayout
        panelsContainer.add(panel, "GestorMenus");  // Añadir el panel principal al CardLayout
        
        // Crear y añadir el botón de iniciar sesión al HashMap
        botones.put("IniciarSesion", new Boton("Iniciar Sesión", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarMenuIniciarSesion();
            }
        }));
        
        agregarBotones();
    }
    
    private void iniciarMenuIniciarSesion() {
        MenuIniciarSesion menuIniciarSesion = new MenuIniciarSesion(this);
    
        // Al navegar a un nuevo panel, lo agregamos a la pila
        panelHistory.push(panel);
        agregarBotonAtras(menuIniciarSesion.panel);
        panelsContainer.add(menuIniciarSesion.panel, "IniciarSesion");
        navegarA(menuIniciarSesion.panel);
    }
    
    
    // Método para crear la ventana
    protected void crearNuevaVentana() {
        // Crear el JFrame para la ventana principal
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        // Centrar la ventana en la pantalla
        frame.setLocationRelativeTo(null);
        
        frame.add(panelsContainer);  
        frame.setVisible(true);
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
        cardLayout.show(panelsContainer, panel.getName());
        
        System.out.println("Navegando a " + panel.getName());
    }
}