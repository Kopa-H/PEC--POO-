import javax.swing.*;
import java.awt.event.ActionListener;

public class Boton {
    private JButton boton;
    private ActionListener accion;

    public Boton(JButton boton, ActionListener accion) {
        this.boton = boton;
        this.accion = accion;
    }

    public JButton getBoton() {
        return boton;
    }

    public ActionListener getAccion() {
        return accion;
    }
}
