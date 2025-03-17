import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GestorMenus extends Menu {

    private MenuIniciarSesion menuIniciarSesion;  // Menú de iniciar sesión

    // Constructor
    public GestorMenus() {
        super();  // Llamamos al constructor de la clase base Menu
        
        // Inicializamos el menú de iniciar sesión
        menuIniciarSesion = new MenuIniciarSesion();
        
        // Añadir los menús al contenedor CardLayout
        container.add(panel, "GestorMenus");  // Añadir el panel principal al CardLayout
        container.add(menuIniciarSesion.panel, "IniciarSesion");  // Añadir el panel de iniciar sesión
        
         // Crear y añadir el botón de iniciar sesión al HashMap
        botones.put("IniciarSesion", new Boton("Iniciar Sesión", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                navegarA("IniciarSesion");
            }
        }));
    }

    @Override
    public void iniciarMenu() {
        super.iniciarMenu();
    }
}