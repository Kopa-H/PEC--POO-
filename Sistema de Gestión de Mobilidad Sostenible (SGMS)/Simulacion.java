import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;
import java.awt.Color;


/**
 * Write a description of class Simulacion here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Simulacion {
        
    private JButton[][] gridButtons; // Matriz para almacenar los botones de la cuadrícula
    
    private int step; // Recuento de pasos
    
    public static final int MAX_SIMULATION_SPEED = 300;
    public int simulationSpeed = 0;
    
    public static final int ROWS = Ciudad.ROWS;
    public static final int COLUMNS = Ciudad.COLUMNS;
    
    public static final int MAX_ESTADOS_GUARDADOS = 1000000;
    public static final int ESTADOS_ELIMINADOS_SOBRECARGA = 100;
    
    public boolean simulationRunning = true;
    public boolean runningForward = false;
    public boolean runningBackward = false;
    
    private ArrayList<Estado> historialEstados;
    
    Interfaz interfaz;
    
    public Simulacion(Ciudad ciudad) {
        historialEstados = new ArrayList<>();
        gridButtons = new JButton[ROWS][COLUMNS];
        step = 0;
        
        // Se crea la interfaz
        interfaz = new Interfaz(this, ciudad, gridButtons, step);
    }
    
    public JButton[][] getGridButtons() {
        return gridButtons;
    }
    
    public void setGridButtons(JButton[][] newGridButtons) {
        gridButtons = newGridButtons;
    }
    
    /**
     * Captura el estado actual de la simulación y lo guarda en el historial de estados.
     */
    public void guardarEstado(Ciudad ciudad) {
        // Crear una copia profunda del estado actual del grid de botones (interfaz)
        JButton[][] copiaCuadricula = new JButton[Ciudad.ROWS][Ciudad.COLUMNS];
        for (int i = 0; i < Ciudad.ROWS; i++) {
            for (int j = 0; j < Ciudad.COLUMNS; j++) {
                JButton button = getGridButtons()[i][j];
                copiaCuadricula[i][j] = button;  // Copiar cada valor de la cuadrícula
            }
        }
    
        // Crear una copia profunda de las entidades usando el método deepCopy de Entidad
        ArrayList<Entidad> copiaEntidades = new ArrayList<>();
        for (Entidad entidad : ciudad.getEntidades()) {
            try {
                // Usamos deepCopy para obtener una copia profunda de la entidad
                Entidad entidadCopia = (Entidad) Entidad.deepCopy(entidad);
                copiaEntidades.add(entidadCopia);  // Agregar la entidad copiada a la lista
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    
        // Crear un nuevo estado con las copias
        Estado estadoActual = new Estado(copiaCuadricula, copiaEntidades);
        
        if (historialEstados.size() > MAX_ESTADOS_GUARDADOS) {
            // Eliminar los primeros 100 estados
            for (int i = 0; i < ESTADOS_ELIMINADOS_SOBRECARGA; i++) {
                historialEstados.remove(0);
            }
        }
        
        // Guardar el estado actual en la lista de historial de estados
        historialEstados.add(estadoActual);
    }
    
    // Método para retroceder al estado anterior
    private void retrocederEstado(Ciudad ciudad) {
        if (historialEstados.size() != step) {
            throw new IllegalStateException("El sistema de retroceso de estados se ha desincronizado!");
        }
        
        if (!historialEstados.isEmpty()) {
            Estado estadoAnterior = historialEstados.remove(historialEstados.size() - 1); // Recupera el último estado
            
            setGridButtons(estadoAnterior.obtenerEstadoCuadricula()); // Restaura la cuadrícula
            
            ciudad.setEntidades(estadoAnterior.obtenerEstadoEntidades()); // Restaura las entidades
        }
    }
    
    public void runSimulacion(Ciudad ciudad) {        
        while (simulationRunning) {
            
            if (runningForward) {
        
                // Se hace que todas las entidades actúen según su estado
                for (Entidad entidad : ciudad.getEntidades()) {
                    entidad.actuar(ciudad);
                }
        
                // Se almacena el estado actual de la simulación para poder retroceder
                guardarEstado(ciudad);
                
                // Incrementar el contador de pasos
                step++;

            } else if (runningBackward && step > 0) {
                
                // Se tira para atrás
                retrocederEstado(ciudad);
                
                step--;
            }
            
            actualizarEstadoVisual(ciudad);
            Utilities.gestionarDelay(simulationSpeed);
        }
    }
    
    // Método que actualiza el estado de la ciudad
    public void actualizarEstadoVisual(Ciudad ciudad) {
        // Limpiar la cuadrícula visual (poner todos los botones en blanco)
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                gridButtons[i][j].setBackground(Color.WHITE);
            }
        }
        
        // Mover todas las personas en la ciudad y actualizar su posición
        Ubicacion ubi = new Ubicacion();
        Color color;
        int x, y;
        for (Entidad entidad : ciudad.getEntidades()) {
            x = entidad.getUbicacion().getPosX();
            y = entidad.getUbicacion().getPosY();
    
            ubi.setUbicacion(x, y);
            color = entidad.getColor();
    
            mostrarEntidad(ubi, color); // Actualiza la posición en la interfaz gráfica
        }
        
        interfaz.actualizarStepLabel(step);
    }
    
    // Método que actualiza visualmente la posición de una entidad en la cuadrícula
    public void mostrarEntidad(Ubicacion ubi, Color color) {
        gridButtons[ubi.getPosX()][ubi.getPosY()].setBackground(color); // Actualiza la celda correspondiente
    }
}
