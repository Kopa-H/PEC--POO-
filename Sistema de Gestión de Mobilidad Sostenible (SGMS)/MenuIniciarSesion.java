import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class MenuIniciarSesion extends Menu {

    public MenuIniciarSesion() {
        WINDOW_WIDTH = 500;
        WINDOW_HEIGHT = 400;
        
        frame.setTitle("Iniciar Sesión");

        // Panel con color de fondo verde claro
        panel.setBackground(Color.GREEN);

        // Definir roles directamente en el HashMap
        botones.put("Usuario Normal", new Boton("Usuario Normal", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuPersonal("Usuario Normal");
            }
        }));
        botones.put("Usuario Premium", new Boton("Usuario Premium", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuPersonal("Usuario Premium");
            }
        }));
        botones.put("Técnico de Mantenimiento", new Boton("Técnico de Mantenimiento", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuPersonal("Técnico de Mantenimiento");
            }
        }));
        botones.put("Mecánico", new Boton("Mecánico", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuPersonal("Mecánico");
            }
        }));
        botones.put("Administrador", new Boton("Administrador", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuPersonal("Administrador");
            }
        }));
        
        agregarBotonAtras("GestorMenus");  // Volver al menú principal
        
        iniciarMenu();
    }

    @Override
    public void iniciarMenu() {
        super.iniciarMenu();
    }

    private void abrirMenuPersonal(String tipoUsuario) {
        // Crear el objeto MenuPersonal correspondiente al tipo de usuario
        // MenuSistema menuSistema = new MenuSistema(tipoUsuario);

        // Llamar al método para mostrar la interfaz del menu personal
        // menuSistema.iniciarMenu();
    }
}
