import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GestorMenus extends Menu {

    // Constructor
    public GestorMenus() {    
        crearVentana(); // Se crea una nueva ventana para GestorMenus, por la que se navegará a los distintos submenús
        
        frame.setTitle("Gestor Menús");  // Establece el título de la ventana
        
        crearPanel();
        
        // Añadir los menús al contenedor CardLayout
        container.add(panel, "GestorMenus");  // Añadir el panel principal al CardLayout
        
        // Crear y añadir el botón de iniciar sesión al HashMap
        botones.put("IniciarSesion", new Boton("Iniciar Sesión", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuIniciarSesion menuIniciarSesion = new MenuIniciarSesion(frame);
                panels.put(MenuIniciarSesion.class, menuIniciarSesion.panel);  // Guardamos la instancia en el HashMap
                navegarA(MenuIniciarSesion.class);  // Navegar usando la clase
            }
        }));
        
        iniciarMenu();
    }

    @Override
    public void iniciarMenu() {
        super.iniciarMenu();
    }
}