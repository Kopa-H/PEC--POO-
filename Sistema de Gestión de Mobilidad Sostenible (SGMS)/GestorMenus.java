import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GestorMenus extends Menu {
    private JButton botonMenuIniciarSesion;
    private Simulacion simulacion;

    public GestorMenus(Simulacion simulacion) {
        super(); // Llamamos al constructor de la clase base Menu
        this.simulacion = simulacion;
        
        WINDOW_WIDTH = 500;
        WINDOW_HEIGHT = 400;
    }

    @Override
    public void iniciarInterfaz() {
        
        // Configuración de la ventana
        frame.setTitle("Gestor de Menús");

        // Panel con color de fondo azul
        panel.setBackground(Color.BLUE);

        // Botones
        botonMenuIniciarSesion = new JButton(" - INICIAR SESIÓN - ");

        // Estética profesional
        Font font = new Font("Arial", Font.PLAIN, 16);
        botonMenuIniciarSesion.setFont(font);

        botonMenuIniciarSesion.setBackground(new Color(0, 123, 255));

        botonMenuIniciarSesion.setForeground(Color.WHITE);
        // Añadir los botones al panel
        panel.add(botonMenuIniciarSesion);
        
        botonesGestorMenu.add(botonMenuIniciarSesion);

        botonMenuIniciarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abre otra interfaz de inicio de sesión
                iniciarMenuIniciarSesion();
            }
        });

        // Mostrar la ventana
        mostrarVentana();
    }
    
    @Override
    protected void asignarFuncionesBotones() {
        
    }

    private void iniciarMenuIniciarSesion() {
        // Aquí puedes iniciar otra interfaz de inicio de sesión
        MenuIniciarSesion menuIniciarSesion = new MenuIniciarSesion();
        menuIniciarSesion.iniciarInterfaz();
    }
}
