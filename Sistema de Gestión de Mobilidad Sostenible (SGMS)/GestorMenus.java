import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GestorMenus extends Menu {
    private JButton botonIniciarDemoSimulacion, botonIniciarSesion, button3, button4;
    private Simulacion simulacion;

    public GestorMenus(Simulacion simulacion) {
        super(); // Llamamos al constructor de la clase base Menu
        this.simulacion = simulacion;
    }

    @Override
    public void iniciarInterfaz() {
        // Configuración de la ventana
        frame.setTitle("Gestor de Menús");

        // Panel con color de fondo azul
        panel.setBackground(Color.BLUE);

        // Botones
        botonIniciarDemoSimulacion = new JButton("Iniciar Simulación");
        botonIniciarSesion = new JButton("Iniciar Sesión");
        button3 = new JButton("Botón 3");
        button4 = new JButton("Botón 4");

        // Estética profesional
        Font font = new Font("Arial", Font.PLAIN, 16);
        botonIniciarDemoSimulacion.setFont(font);
        botonIniciarSesion.setFont(font);
        button3.setFont(font);
        button4.setFont(font);

        botonIniciarDemoSimulacion.setBackground(new Color(0, 123, 255));
        botonIniciarSesion.setBackground(new Color(0, 123, 255));
        button3.setBackground(new Color(0, 123, 255));
        button4.setBackground(new Color(0, 123, 255));

        botonIniciarDemoSimulacion.setForeground(Color.WHITE);
        botonIniciarSesion.setForeground(Color.WHITE);
        button3.setForeground(Color.WHITE);
        button4.setForeground(Color.WHITE);

        // Añadir los botones al panel
        panel.add(botonIniciarDemoSimulacion);
        panel.add(botonIniciarSesion);
        panel.add(button3);
        panel.add(button4);

        // Funcionalidad de botones
        botonIniciarDemoSimulacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarDemoSimulacion();
            }
        });

        botonIniciarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abre otra interfaz de inicio de sesión
                iniciarSesion();
            }
        });

        // Mostrar la ventana
        mostrarVentana();
    }

    private void iniciarDemoSimulacion() {
        simulacion.iniciarDemo();
    }

    private void iniciarSesion() {
        // Aquí puedes iniciar otra interfaz de inicio de sesión
        JOptionPane.showMessageDialog(frame, "Iniciando sesión...");
    }
}
