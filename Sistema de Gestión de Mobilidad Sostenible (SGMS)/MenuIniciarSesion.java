import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuIniciarSesion extends Menu {

    public MenuIniciarSesion() {
        super(); // Llamamos al constructor de la clase base Menu
        
        WINDOW_WIDTH = 500;
        WINDOW_HEIGHT = 400;
        
        roles.add("Usuario Normal");
        roles.add("Usuario Premium");
        roles.add("Técnico de Mantenimiento");
        roles.add("Mecánico");
        roles.add("Administrador");
    }

    @Override
    public void iniciarInterfaz() {
        frame.setTitle("Iniciar Sesión");

        // Panel con color de fondo verde claro
        panel.setBackground(Color.GREEN);

        // Aplicar la estética y añadir los botones al panel
        for (JButton boton : botones) {
            aplicarEsteticaBoton(boton);
            panel.add(boton);
        }
        
        // Mostrar la ventana
        mostrarVentana();
    }

    private void aplicarEsteticaBoton(JButton boton) {
        // Estética profesional para los botones
        Font font = new Font("Arial", Font.PLAIN, 16);
        boton.setFont(font);
        boton.setBackground(new Color(0, 123, 255)); // Azul para los botones
        boton.setForeground(Color.WHITE); // Color de texto blanco
        boton.setPreferredSize(new Dimension(250, 40)); // Tamaño de los botones
    }

    private void abrirMenuPersonal(String tipoUsuario) {
        // Crear el objeto MenuPersonal correspondiente al tipo de usuario
        MenuSistema menuSistema = new MenuSistema(tipoUsuario);

        // Llamar al método para mostrar la interfaz del menu personal
        menuSistema.iniciarInterfaz();
    }
}
