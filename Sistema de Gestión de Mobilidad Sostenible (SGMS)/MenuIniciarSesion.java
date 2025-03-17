import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class MenuIniciarSesion extends Menu {

    public MenuIniciarSesion() {
        super(); // Llamamos al constructor de la clase base Menu
        
        agregarBotonAtras("GestorMenus");  // Volver al menú principal

        WINDOW_WIDTH = 500;
        WINDOW_HEIGHT = 400;

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
    }

    @Override
    public void iniciarInterfaz() {
        frame.setTitle("Iniciar Sesión");

        // Panel con color de fondo verde claro
        panel.setBackground(Color.GREEN);

        // Añadir los botones al panel
        for (String nombreBoton : botones.keySet()) {
            Boton boton = botones.get(nombreBoton);
            agregarBotonCentrado(boton.getBoton());
        }

        // Mostrar la ventana
        mostrarVentana();
    }

    private void abrirMenuPersonal(String tipoUsuario) {
        // Crear el objeto MenuPersonal correspondiente al tipo de usuario
        MenuSistema menuSistema = new MenuSistema(tipoUsuario);

        // Llamar al método para mostrar la interfaz del menu personal
        menuSistema.iniciarInterfaz();
    }
}
