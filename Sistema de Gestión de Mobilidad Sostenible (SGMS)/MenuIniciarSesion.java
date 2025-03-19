import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class MenuIniciarSesion extends Menu {
    
    private GestorMenus gestorMenus;
    private JFrame frame;
    
    protected int WINDOW_WIDTH = 400;
    protected int WINDOW_HEIGHT = 500;
    
    private Simulacion simulacion;
    private Ciudad ciudad;

    public MenuIniciarSesion(Simulacion simulacion, Ciudad ciudad, GestorMenus gestorMenus) {
        this.simulacion = simulacion;
        this.ciudad = ciudad;
        this.gestorMenus = gestorMenus;
        this.frame = gestorMenus.frame;
        
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setTitle("Iniciar Sesión");  // Establece el título de la ventana
        this.panel = crearPanel("IniciarSesion");
        panel.setBackground(Color.GRAY);

        agregarBotonesMenu();
        
        agregarBotones(botones, panel);
    }
    
    // Se le pasa como parámetro el tipo de usuario que ingresa al sistema, dandole unas opciones u otras
    private void iniciarMenuSistema(TipoUsuario tipoUsuario) {
        // Se añaden al contenedor de páginas las de cada tipo de usuario
        MenuSistema menuSistema = new MenuSistema(simulacion, ciudad, tipoUsuario, gestorMenus);
    
        // Al navegar a un nuevo panel, lo agregamos a la pila
        gestorMenus.panelHistory.push(panel);
        gestorMenus.agregarBotonAtras(menuSistema.panel);
        gestorMenus.cardsPanel.add(menuSistema.panel, "Menu" + tipoUsuario.name());
        gestorMenus.navegarA(menuSistema.panel);
    }

    private void agregarBotonesMenu() {
        // Definir roles directamente en el HashMap
        botones.put("Administrador", new Boton("Administrador", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarMenuSistema(TipoUsuario.ADMINISTRADOR);
            }
        }));
        botones.put("Usuario Normal", new Boton("Usuario Normal", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarMenuSistema(TipoUsuario.USUARIO_NORMAL);
            }
        }));
        botones.put("Usuario Premium", new Boton("Usuario Premium", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarMenuSistema(TipoUsuario.USUARIO_PREMIUM);
            }
        }));
        botones.put("Técnico de Mantenimiento", new Boton("Técnico de Mantenimiento", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarMenuSistema(TipoUsuario.TECNICO_MANTENIMIENTO);
            }
        }));
        botones.put("Mecánico", new Boton("Mecánico", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarMenuSistema(TipoUsuario.MECANICO);
            }
        }));
    }
}
