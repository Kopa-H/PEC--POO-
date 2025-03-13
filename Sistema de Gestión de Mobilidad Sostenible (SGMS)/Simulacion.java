import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    
    public static final int MIN_SIMULATION_SPEED = 10;
    public static final int MAX_SIMULATION_SPEED = 500;
    public int simulationSpeed = (MAX_SIMULATION_SPEED + MIN_SIMULATION_SPEED) / 2;
    
    // Tamaño fijo de la ventana donde se muestra el estado de gridButtons
    private static final int HORIZONTAL_WINDOW_SIZE = 1500;
    private static final int VERTICAL_WINDOW_SIZE = 1000;
    
    public static final int ROWS = Ciudad.ROWS;
    public static final int COLUMNS = Ciudad.COLUMNS;
    
    private boolean simulationRunning = true;
    
    public Simulacion(Ciudad ciudad) {

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
                                statusLabel.setText("Objeto en (" + row + "," + col + "): " + entidad.getTipoEntidad());
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
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, MIN_SIMULATION_SPEED, MAX_SIMULATION_SPEED, simulationSpeed);
        speedSlider.setMajorTickSpacing(500);
        speedSlider.setMinorTickSpacing(100);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        // Etiquetas para el slider
        JLabel sliderLabel = new JLabel("Ajustar velocidad (milisegundos): " + simulationSpeed, JLabel.CENTER);
        // Añadir un ChangeListener al slider para cambiar la velocidad
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                simulationSpeed = speedSlider.getValue();
                sliderLabel.setText("Ajustar velocidad (milisegundos): " + simulationSpeed);
            }
        });
        // Panel para el slider y su etiqueta
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BorderLayout());
        sliderPanel.add(sliderLabel, BorderLayout.NORTH);
        sliderPanel.add(speedSlider, BorderLayout.CENTER);
    
        // Crear un botón para detener o reanudar la simulación
        JButton toggleButton = new JButton("Detener Simulación");
        toggleButton.addActionListener(e -> toggleSimulation(toggleButton, ciudad));  // Alternar el estado de la simulación al hacer clic en el botón
        
        JButton backButton = new JButton("Retroceder");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retrocederEstado();
            }
        });
    
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(stepPanel);
        controlPanel.add(backButton);
        
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
        southPanel.add(toggleButton);
    
        // Añadir el statusPanel al norte
        add(statusPanel, BorderLayout.NORTH);
    
        // Añadir el panel southPanel al SOUTH de la ventana
        add(southPanel, BorderLayout.SOUTH);
    
        // Configurar pantalla completa
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Pantalla completa
    
        setVisible(true);
    }
    
    // Método para alternar entre detener y reanudar la simulación
    public void toggleSimulation(JButton toggleButton, Ciudad ciudad) {
        if (simulationRunning) {
            simulationRunning = false;  // Detener la simulación
            toggleButton.setText("Reanudar Simulación");  // Cambiar el texto del botón
        } else {
            simulationRunning = true;  // Reanudar la simulación
            toggleButton.setText("Detener Simulación");  // Cambiar el texto del botón
            new Thread(() -> runSimulacion(ciudad)).start();  // Iniciar la simulación en un hilo separado para evitar bloquear la interfaz gráfica
        }
    }
    
    /**
     * Captura el estado actual de la simulación y lo guarda en el historial de estados.
     */
    public void guardarEstado(Ciudad ciudad) {
        // Crear una copia del estado actual (grid y entidades)
        Estado estadoActual = new Estado(ciudad.getGrid(), ciudad.getEntidades());
        
        // Guardar el estado actual en la lista de historial de estados
        this.historialEstados.add(estadoActual);
    }

    /**
     * Devuelve el historial de estados guardados.
     * @return ArrayList con los estados guardados
     */
    public ArrayList<Estado> getHistorialEstados() {
        return this.historialEstados;
    }
    
    // Método para retroceder al estado anterior
    private void retrocederEstado() {
        if (!historialEstados.isEmpty()) {
            Estado estadoAnterior = historialEstados.remove(historialEstados.size() - 1); // Recupera el último estado
            ciudad.setGrid(estadoAnterior.obtenerEstadoCuadricula()); // Restaura la cuadrícula
            ciudad.setEntidades(estadoAnterior.obtenerEstadoEntidades()); // Restaura las entidades
            actualizarEstadoVisual();
            step--;
            stepLabel.setText("Paso: " + step); // Actualiza la etiqueta de paso
        } else {
            System.out.println("No hay más estados anteriores.");
        }
    }
    
    public void runSimulacion(Ciudad ciudad) {
        while (simulationRunning) {
            // Se hace que todas las entidades actúen según su estado (moverse)
            for (Entidad entidad : ciudad.getEntidades()) {
                entidad.actuar(ciudad);
            }
            
            actualizarEstadoVisual(ciudad);
            // Se almacena el estado actual de la simulación para poder retroceder
            guardarEstado();
            
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
    
        // Incrementar el contador de pasos
        step++;
    
        // Actualizar el JLabel con el nuevo número de pasos
        stepLabel.setText("Paso: " + step);
    
        // Redibujar el JFrame (si es necesario)
        repaint();
    }
    
    // Método que actualiza visualmente la posición de una entidad en la cuadrícula
    public void mostrarEntidad(Ubicacion ubi, Color color) {
        gridButtons[ubi.getPosX()][ubi.getPosY()].setBackground(color); // Actualiza la celda correspondiente
    }
}
