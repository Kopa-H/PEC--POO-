import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Ciudad extends JFrame {
    // Variables de instancia
    private int[][] grid;
    private JButton[][] gridButtons; // Matriz para almacenar los botones de la cuadrícula
    private ArrayList<Entidad> entidades; // Lista de entidades móviles en la ciudad
    private int step; // Recuento de pasos
    private JLabel statusLabel; // JLabel para mostrar el nombre del objeto
    private JLabel stepLabel;
    
    // Tamaño fijo de la ventana donde se muestra el estado de gridButtons
    private static final int HORIZONTAL_WINDOW_SIZE = 1500;
    private static final int VERTICAL_WINDOW_SIZE = 1000;
    
    public static final int ROWS = 50;
    public static final int COLUMNS = 50;
    
    public static final int MIN_SIMULATION_SPEED = 10;
    public static final int MAX_SIMULATION_SPEED = 500;
    public int simulationSpeed = (MAX_SIMULATION_SPEED + MIN_SIMULATION_SPEED) / 2;
    
    /**
     * Constructor para objetos de la clase Ciudad.
     */ 
    public Ciudad() {
        // Inicializamos el grid y otros componentes
        grid = new int[ROWS][COLUMNS];
        gridButtons = new JButton[ROWS][COLUMNS];
        entidades = new ArrayList<>(); // Inicializamos la lista de personas
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
                        for (Entidad entidad : entidades) {
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
    
        // Crear el panel contenedor para stepPanel y statusPanel con BoxLayout
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
    
        // Añadir los paneles stepPanel y statusPanel al northPanel
        northPanel.add(statusPanel);
    
        // Añadir el panel northPanel al NORTH de la ventana
        add(northPanel, BorderLayout.NORTH);
    
        // Añadir el panel stepPanel al SUR (SOUTH) de la ventana
        add(stepPanel, BorderLayout.SOUTH);
    
        // Añadir el slider al pie de la ventana
        add(sliderPanel, BorderLayout.SOUTH);
    
        // Configurar pantalla completa
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Pantalla completa
    
        setVisible(true);
    }

    public ArrayList<Entidad> getEntidades() {
        return entidades;
    }
    
    public void runSimulacion() {
        boolean appActive = true;
        
        while (appActive) {
            
            // Se hace que todas las entidades actúen según su estado (moverse)
            for (Entidad entidad : entidades) {
                entidad.actuar(this);
            }
            
            actualizarEstadoVisual();
            
            Utilities.gestionarDelay(simulationSpeed);
        }
    }
    
    // Método para añadir una entidad a la ciudad
    public void addElement(Entidad entidad) {
        entidades.add(entidad);
        
        Color color = entidad.getColor();
        mostrarEntidad(entidad.getUbicacion(), color);
    }
    
    // Método que actualiza visualmente la posición de una entidad en la cuadrícula
    private void mostrarEntidad(Ubicacion ubi, Color color) {
        gridButtons[ubi.getPosX()][ubi.getPosY()].setBackground(color); // Actualiza la celda correspondiente
    }
    
    public Entidad encontrarEntidadMasCercana(EntidadMovil entidadBuscando, Ubicacion ubi, Class<?> tipoEntidad) {
        Entidad entidadMasCercana = null;
        int distanciaMinima = Integer.MAX_VALUE; // Inicializamos con el valor más alto posible
    
        // Iteramos sobre las entidades
        for (Entidad entidad : entidades) {
            // Comprobamos si la entidad es una instancia del tipo proporcionado
            if (tipoEntidad.isInstance(entidad)) {
                // Si la entidad es una moto y está en trayecto, la ignoramos
                if (tipoEntidad == Moto.class && ((Moto) entidad).enTrayecto) {
                    continue;
                }
    
                // Calculamos la distancia Manhattan entre la ubicación actual y la entidad
                int distancia = Math.abs(entidad.getUbicacion().getPosX() - ubi.getPosX()) 
                              + Math.abs(entidad.getUbicacion().getPosY() - ubi.getPosY());
                              
                // Si la entidad es un Vehículo y quien busca es una Persona
                if (entidad instanceof Vehiculo && entidadBuscando instanceof Usuario) {
                    Vehiculo vehiculo = (Vehiculo) entidad;
                    Usuario usuario = (Usuario) entidadBuscando;
    
                    // Si el vehículo tiene menos del 10% de batería, lo descartamos
                    if (vehiculo.getPorcentajeBateria() < 10) {
                        continue;
                    }
    
                    // Si el vehículo tiene menos del 20% de batería y la persona no es premium, lo descartamos
                    if (vehiculo.getPorcentajeBateria() < 20 && !usuario.getTipoMembresia().equals(Usuario.TipoMembresia.PREMIUM)) {
                        continue;
                    }
    
                    // También debería añadir la lógica para descartar bicis o patinetes si no hay batería suficiente para alcanzar una base
                }
    
                // Si la distancia calculada es menor que la distancia mínima, actualizamos la entidad más cercana
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    entidadMasCercana = entidad;
                }
            }
        }
    
        if (entidadMasCercana == null) {
            System.out.println("No se ha encontrado ninguna entidad");
        }
    
        // Devolvemos la entidad más cercana, o null si no se encontró ninguna
        return entidadMasCercana;
    }

    // Método que verifica si una posición está ocupada
    public boolean posicionOcupada(int posX, int posY) {
        // Recorrer las entidades existentes y verificar si alguna está en la misma posición
        for (Entidad entidad : entidades) {
            if (entidad.getUbicacion().getPosX() == posX && entidad.getUbicacion().getPosY() == posY) {
                return true; // La posición está ocupada
            }
        }
        return false; // La posición no está ocupada
    }
    
    // Método que actualiza el estado de la ciudad
    public void actualizarEstadoVisual() {
        // Limpiar la cuadrícula visual (poner todos los botones en blanco)
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                gridButtons[i][j].setBackground(Color.WHITE);
            }
        }
        
        // Mover todas las personas en la ciudad y actualizar su posición
        Ubicacion ubi = new Ubicacion();
        Color color;
        int x, y;
        for (Entidad entidad : entidades) {
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
}
