import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuIniciarSesion extends Menu {

    private JButton botonCancelar;

    public MenuIniciarSesion() {
        super(); // Llamamos al constructor de la clase base Menu
        
        WINDOW_WIDTH = 200;
        WINDOW_HEIGHT = 400;
    }

    @Override
    public void iniciarInterfaz() {
        frame.setTitle("Iniciar Sesión");

        // Panel con color de fondo verde claro
        panel.setBackground(Color.GREEN);

        // Crear un arreglo de botones
        JButton[] botones = {
            new JButton("USUARIO NORMAL"),
            new JButton("USUARIO PREMIUM"),
            new JButton("TÉCNICO DE MANTENIMIENTO"),
            new JButton("MECÁNICO"),
            new JButton("ADMINISTRADOR")
        };

        // Aplicar la estética y añadir los botones al panel
        for (JButton boton : botones) {
            aplicarEsteticaBoton(boton);
            panel.add(boton);
        }

        // Funcionalidad de los botones
        botones[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuPersonal("Usuario Normal");
            }
        });

        botones[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuPersonal("Usuario Premium");
            }
        });

        botones[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuPersonal("Técnico de Mantenimiento");
            }
        });

        botones[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuPersonal("Mecánico");
            }
        });

        botones[4].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMenuPersonal("Administrador");
            }
        });
        
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
