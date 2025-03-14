import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Write a description of class Simulacion here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Simulacion extends JFrame {
        
    private JButton[][] gridButtons; // Matriz para almacenar los botones de la cuadrícula
    
    private int step; // Recuento de pasos
    private JLabel statusLabel; // JLabel para mostrar el nombre del objeto
    private JLabel stepLabel;
    
    public static final int MAX_SIMULATION_SPEED = 1000;
    public int simulationSpeed = 0;
    
    // Tamaño fijo de la ventana donde se muestra el estado de gridButtons
    private static final int HORIZONTAL_WINDOW_SIZE = 1500;
    private static final int VERTICAL_WINDOW_SIZE = 1000;
    
    public static final int ROWS = Ciudad.ROWS;
    public static final int COLUMNS = Ciudad.COLUMNS;
    
    public static final int MAX_ESTADOS_GUARDADOS = 1000000;
    public static final int ESTADOS_ELIMINADOS_SOBRECARGA = 100;
    
    private boolean simulationRunning = true;
    private boolean runningForward = false;
    private boolean runningBackward = false;
    
    private ArrayList<Estado> historialEstados;
    
    public Simulacion(Ciudad ciudad) {

        historialEstados = new ArrayList<>();
        
        gridButtons = new JButton[ROWS][COLUMNS];
        step = 0; // Inicializamos el contador de pasos
        
        setTitle("Simulación de Ciudad");
        setSize(600, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        // Configuramos el panel con GridLayout
        JPanel gridPanel = new JPanel(new GridLayout(ROWS, COLUMNS));
    
        // Crear los botones y agregarlos al panel
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE); // Color inicial de las celdas
                gridButtons[i][j] = button;
    
                // Agregar MouseListener al botón
                final int row = i;
                final int col = j;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // Mostrar el nombre de la entidad que está en la celda si existe
                        for (Entidad entidad : ciudad.getEntidades()) {
                            if (entidad.getUbicacion().getPosX() == row && entidad.getUbicacion().getPosY() == col) {
                                statusLabel.setText(entidad.toString());
                                return;
                            }
                        }
                        statusLabel.setText("Vacío en (" + row + "," + col + ")");
                    }
    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        // Limpiar el JLabel cuando el ratón salga del botón
                        statusLabel.setText(" ");
                    }
                });
    
                gridPanel.add(button); 
            }
        }
    
        // Añadir el panel de la cuadrícula a la ventana
        add(gridPanel, BorderLayout.CENTER);
    
        // Crear un JPanel para el recuento de pasos
        JPanel stepPanel = new JPanel();
        stepLabel = new JLabel("Paso: " + step);
        stepPanel.add(stepLabel);
    
        // Crear un JPanel para el statusLabel
        JPanel statusPanel = new JPanel();
        statusLabel = new JLabel(" ");
        statusPanel.add(statusLabel);
    
        // Crear el slider para controlar la velocidad
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, -MAX_SIMULATION_SPEED, MAX_SIMULATION_SPEED, simulationSpeed);
        speedSlider.setMajorTickSpacing(500);
        speedSlider.setMinorTickSpacing(100);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        // Etiquetas para el slider
        JLabel sliderLabel = new JLabel("Ajustar velocidad: " + simulationSpeed, JLabel.CENTER);
        
        // Añadir un ChangeListener al slider para cambiar la velocidad y dirección
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int sliderValue = speedSlider.getValue();
                
                // Definir un rango alrededor de 0 como la "zona muerta"
                int deadZone = 5;
                
                // Si el valor del slider está dentro de la zona muerta, se pausa la simulación
                if (Math.abs(sliderValue) <= deadZone) {
                    simulationSpeed = 0;
                    runningForward = false;
                    runningBackward = false;
                    
                    // Mover el slider al centro (0)
                    speedSlider.setValue(0);
                } 
                // Si el slider está en valores positivos, la simulación avanza
                else if (sliderValue > deadZone) {
                    simulationSpeed = sliderValue;
 
                    runningForward = true;
                    runningBackward = false;
                } 
                // Si el slider está en valores negativos, la simulación retrocede
                else if (sliderValue < -deadZone) {
                    simulationSpeed = -sliderValue; // La velocidad de retroceso será la magnitud del valor

                    runningForward = false;
                    runningBackward = true;
                }
            
                sliderLabel.setText("Velocidad: " + Math.abs(simulationSpeed) + (sliderValue < 0 ? " (retroceso)" : " (avance)"));
            }
        });
        // Panel para el slider y su etiqueta
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BorderLayout());
        sliderPanel.add(sliderLabel, BorderLayout.NORTH);
        sliderPanel.add(speedSlider, BorderLayout.CENTER);
        
        // Panel para el slider y su etiqueta
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        
        // Agregar stepPanel encima
        southPanel.add(stepPanel);
        
        // Agregar un pequeño espacio entre stepPanel y sliderPanel
        southPanel.add(Box.createVerticalStrut(10));  // Ajusta el tamaño según lo que necesites
    
        // Agregar el sliderPanel debajo
        southPanel.add(sliderPanel);
        
        // Agregar el botón de detener simulación
        southPanel.add(Box.createVerticalStrut(10));
    
        // Añadir el statusPanel al norte
        add(statusPanel, BorderLayout.NORTH);
    
        // Añadir el panel southPanel al SOUTH de la ventana
        add(southPanel, BorderLayout.SOUTH);
    
        // Configurar pantalla completa
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Pantalla completa
    
        setVisible(true);
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
            stepLabel.setText("Paso: " + step);
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
    
        // Redibujar el JFrame (si es necesario)
        repaint();
    }
    
    // Método que actualiza visualmente la posición de una entidad en la cuadrícula
    public void mostrarEntidad(Ubicacion ubi, Color color) {
        gridButtons[ubi.getPosX()][ubi.getPosY()].setBackground(color); // Actualiza la celda correspondiente
    }
}
