import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GestorMenus extends Menu {
    private JButton botonMenuSimulacion, botonMenuIniciarSesion;
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
        botonMenuSimulacion = new JButton(" - MENÚ SIMULACIÓN - ");
        botonMenuIniciarSesion = new JButton(" - MENU SISTEMA - ");

        // Estética profesional
        Font font = new Font("Arial", Font.PLAIN, 16);
        botonMenuSimulacion.setFont(font);
        botonMenuIniciarSesion.setFont(font);

        botonMenuSimulacion.setBackground(new Color(0, 123, 255));
        botonMenuIniciarSesion.setBackground(new Color(0, 123, 255));

        botonMenuSimulacion.setForeground(Color.WHITE);
        botonMenuIniciarSesion.setForeground(Color.WHITE);
        // Añadir los botones al panel
        panel.add(botonMenuSimulacion);
        panel.add(botonMenuIniciarSesion);

        // Funcionalidad de botones
        botonMenuSimulacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarMenuSimulacion();
            }
        });

        botonMenuIniciarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abre otra interfaz de inicio de sesión
                iniciarMenuIniciarSesion();
            }
        });

        // Mostrar la ventana
        mostrarVentana();
    }

    private void iniciarMenuSimulacion() {
        simulacion.iniciarDemo();
    }

    private void iniciarMenuIniciarSesion() {
        // Aquí puedes iniciar otra interfaz de inicio de sesión
        MenuIniciarSesion menuIniciarSesion = new MenuIniciarSesion();
        menuIniciarSesion.iniciarInterfaz();
    }
}
