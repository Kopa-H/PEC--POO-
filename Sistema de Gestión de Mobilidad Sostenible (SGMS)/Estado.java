import java.util.ArrayList;

public class Estado {
    private int[][] estadoCuadricula;
    private ArrayList<Entidad> estadoEntidades;

    public Estado(int[][] cuadricula, ArrayList<Entidad> entidades) {
        this.estadoCuadricula = cuadricula;
        this.estadoEntidades = entidades;
    }

    // Métodos para acceder al estado
    public int[][] obtenerEstadoCuadricula() {
        return estadoCuadricula;
    }

    public ArrayList<Entidad> obtenerEstadoEntidades() {
        return estadoEntidades;
    }
}
