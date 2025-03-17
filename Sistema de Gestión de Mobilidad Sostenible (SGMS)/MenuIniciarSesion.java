import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class MenuIniciarSesion extends Menu {
    
    private GestorMenus gestorMenus;

    public MenuIniciarSesion(GestorMenus gestorMenus) {
        this.gestorMenus = gestorMenus;
        
        gestorMenus.frame.setTitle("Iniciar Sesión");  // Establece el título de la ventana
        crearPanel("IniciarSesion");
        panel.setBackground(Color.GRAY);

        agregarBotonesMenu();
        
        agregarBotones();
    }


    private void agregarBotonesMenu() {
        // Definir roles directamente en el HashMap
        botones.put("Usuario Normal", new Boton("Usuario Normal", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuSistema("UsuarioNormal");
            }
        }));
        botones.put("Usuario Premium", new Boton("Usuario Premium", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuSistema("UsuarioPremium");
            }
        }));
        botones.put("Técnico de Mantenimiento", new Boton("Técnico de Mantenimiento", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuSistema("TecnicoMantenimiento");
            }
        }));
        botones.put("Mecánico", new Boton("Mecánico", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuSistema("Mecanico");
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
        MenuSistema menuSistema = new MenuSistema(tipoUsuario, gestorMenus);
    
        // Al navegar a un nuevo panel, lo agregamos a la pila
        gestorMenus.panelHistory.push(panel);
        gestorMenus.agregarBotonAtras(menuSistema.panel);
        gestorMenus.panelsContainer.add(menuSistema.panel, tipoUsuario);
        gestorMenus.navegarA(menuSistema.panel);
    }
}
