import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class MenuIniciarSesion extends Menu {

    public MenuIniciarSesion(JFrame frame) {
        frame.setTitle("Iniciar Sesión");  // Establece el título de la ventana
        
        crearPanel();

        agregarBotonesMenu();
        
        agregarBotonAtras(panel);  // Volver al menú principal
        
        iniciarMenu();
    }

    @Override
    public void iniciarMenu() {
        super.iniciarMenu();
    }

    private void agregarBotonesMenu() {
        // Definir roles directamente en el HashMap
        botones.put("Usuario Normal", new Boton("Usuario Normal", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuSistema("Usuario Normal");
            }
        }));
        botones.put("Usuario Premium", new Boton("Usuario Premium", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuSistema("Usuario Premium");
            }
        }));
        botones.put("Técnico de Mantenimiento", new Boton("Técnico de Mantenimiento", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuSistema("Técnico de Mantenimiento");
            }
        }));
        botones.put("Mecánico", new Boton("Mecánico", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuSistema("Mecánico");
            }
        }));
        botones.put("Administrador", new Boton("Administrador", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuSistema("Administrador");
            }
        }));
    }
    
    // Se le pasa como parámetro el tipo de usuario que ingresa al sistema, dandole unas opciones u otras
    private void abrirMenuSistema(String tipoUsuario) {
       // Se añaden al contenedor de páginas las de cada tipo de usuario
        MenuSistema menuSistema = new MenuSistema(tipoUsuario);
        
        // FALTAA LOGICA
    }
}
