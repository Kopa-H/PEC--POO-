import java.util.ArrayList;
import javax.swing.JButton;

public class Estado {
    private JButton[][] estadoCuadricula;
    private ArrayList<Entidad> estadoEntidades;

    public Estado(JButton[][] cuadricula, ArrayList<Entidad> entidades) {
        this.estadoCuadricula = cuadricula;
        this.estadoEntidades = entidades;
    }

    // Métodos para acceder al estado
    public JButton[][] obtenerEstadoCuadricula() {
        return estadoCuadricula;
    }

    public ArrayList<Entidad> obtenerEstadoEntidades() {
        return estadoEntidades;
    }
}
