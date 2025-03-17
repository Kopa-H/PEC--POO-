import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class GestorMenus extends Menu {

    public GestorMenus(Simulacion simulacion) {
        super(); // Llamamos al constructor de la clase base Menu

        WINDOW_WIDTH = 500;
        WINDOW_HEIGHT = 400;
    }

    @Override
    public void iniciarInterfaz() {
        // Configuración de la ventana
        frame.setTitle("Gestor de Menús");

        // Panel con color de fondo azul
        panel.setBackground(Color.BLUE);

        // Crear y añadir el botón de iniciar sesión al HashMap
        Boton botonMenuIniciarSesion = new Boton("Iniciar Sesión", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarMenuIniciarSesion();
            }
        });
        botones.put("Iniciar Sesión", botonMenuIniciarSesion);  // Añadir el botón al HashMap
        agregarBotonCentrado(botonMenuIniciarSesion.getBoton());  // Añadir al panel con el JButton dentro del objeto Boton

        // Mostrar la ventana
        mostrarVentana();
    }

    private void iniciarMenuIniciarSesion() {
        // Aquí puedes iniciar otra interfaz de inicio de sesión
        MenuIniciarSesion menuIniciarSesion = new MenuIniciarSesion();
        menuIniciarSesion.iniciarInterfaz();
    }
}
