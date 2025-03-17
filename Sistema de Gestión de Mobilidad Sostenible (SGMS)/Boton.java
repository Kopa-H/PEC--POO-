import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

public class Boton {
    private JButton boton;
    private String nombre;
    private ActionListener accion;

    public Boton(String nombreBoton, ActionListener accion) {
        this.boton = new JButton();
        this.nombre = nombreBoton;
        this.accion = accion;
        
        this.accion = accion;
        this.boton = new JButton(nombre);
        this.boton.addActionListener(accion);
        aplicarEstetica(); // Aplicamos la estética al botón en el momento de su creación
    }

    public JButton getBoton() {
        return boton;
    }
    
    public String getNombreBoton() {
        return nombre;
    }

    public ActionListener getAccion() {
        return accion;
    }
    
        private void aplicarEstetica() {
        // Estética profesional para los botones
        Font font = new Font("Arial", Font.PLAIN, 16);
        boton.setFont(font);
        boton.setBackground(new Color(0, 123, 255)); // Azul para los botones
        boton.setForeground(Color.WHITE); // Color de texto blanco
        boton.setPreferredSize(new Dimension(250, 40)); // Tamaño de los botones
    }

}

