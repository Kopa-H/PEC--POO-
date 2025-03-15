import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuIniciarSesion extends Menu {

    private JButton botonIniciarSesion, botonCancelar;

    public MenuIniciarSesion() {
        super(); // Llamamos al constructor de la clase base Menu
    }

    @Override
    public void iniciarInterfaz() {
        frame.setTitle("Iniciar Sesión");

        // Panel con color de fondo verde claro
        panel.setBackground(Color.GREEN);

        // Botones
        botonIniciarSesion = new JButton("Iniciar Sesión");
        botonCancelar = new JButton("Cancelar");

        // Estética profesional
        Font font = new Font("Arial", Font.PLAIN, 16);
        botonIniciarSesion.setFont(font);
        botonCancelar.setFont(font);

        botonIniciarSesion.setBackground(new Color(0, 123, 255));
        botonCancelar.setBackground(new Color(255, 0, 0));

        botonIniciarSesion.setForeground(Color.WHITE);
        botonCancelar.setForeground(Color.WHITE);

        // Añadir los botones al panel
        panel.add(botonIniciarSesion);
        panel.add(botonCancelar);

        // Funcionalidad de botones
        botonIniciarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelarSesion();
            }
        });

        // Mostrar la ventana
        mostrarVentana();
    }

    private void iniciarSesion() {
        // Lógica para iniciar sesión
        JOptionPane.showMessageDialog(frame, "Sesión iniciada");
    }

    private void cancelarSesion() {
        // Lógica para cancelar sesión
        JOptionPane.showMessageDialog(frame, "Sesión cancelada");
    }
}
