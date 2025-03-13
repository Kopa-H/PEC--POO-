import java.util.ArrayList;

public class Estado {
    private int[][] estadoCuadricula;
    private ArrayList<Entidad> estadoEntidades;
    private ArrayList<int[][]> historialCuadricula = new ArrayList<>();
    private ArrayList<ArrayList<Entidad>> historialEntidades = new ArrayList<>();

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
    
    // Método que guarda el estado actual del grid y las entidades
    public void guardarEstado() {
        // Clonar la cuadrícula
        int[][] cuadriculaClonada = new int[estadoCuadricula.length][estadoCuadricula[0].length];
        for (int i = 0; i < estadoCuadricula.length; i++) {
            cuadriculaClonada[i] = estadoCuadricula[i].clone(); // Clonar cada fila de la cuadrícula
        }
        historialCuadricula.add(cuadriculaClonada); // Guardar el clon de la cuadrícula

        // Clonar la lista de entidades
        ArrayList<Entidad> entidadesClonadas = new ArrayList<>(estadoEntidades); // Clonar la lista de entidades
        historialEntidades.add(entidadesClonadas); // Guardar el clon de la lista de entidades
    }

    // Método que restaura el estado anterior
    public void restaurarEstado() {
        if (!historialCuadricula.isEmpty() && !historialEntidades.isEmpty()) {
            // Restaurar el último estado de la cuadrícula
            estadoCuadricula = historialCuadricula.remove(historialCuadricula.size() - 1);

            // Restaurar la última lista de entidades
            estadoEntidades = historialEntidades.remove(historialEntidades.size() - 1);
        }
    }
}
