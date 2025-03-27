import javax.swing.*;
import javax.swing.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.text.*;
import java.awt.Dimension;
import java.util.ArrayList;

public class InterfazSimulacion extends JFrame {   
    private static final Color DESCRIPTION_PANE_COLOR = Color.CYAN;
    
    private JTextPane panelTextoInfo;
    private JLabel tiempoLabel;

    JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, -Tiempo.MAX_VEL_TRANSCURSO_INSTANTES, Tiempo.MAX_VEL_TRANSCURSO_INSTANTES, 0);
    JLabel sliderLabel = new JLabel("Ajustar velocidad: " + 0, JLabel.CENTER);
    JPanel sliderPanel = new JPanel();

    private boolean isLocked = false;

    private static final int HORIZONTAL_WINDOW_SIZE = 1500;
    private static final int VERTICAL_WINDOW_SIZE = 1000;
    
    private int rowSelected = 0;
    private int colSelected = 0;

    public InterfazSimulacion(Simulacion simulacion, Ciudad ciudad, JButton[][] gridButtons, Tiempo tiempo) {
        setTitle("Simulación de Ciudad");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gridPanel = new JPanel(new GridLayout(Simulacion.ROWS, Simulacion.COLUMNS));
        
        // Se crea la ventana independiente para el estatus panel
        Menu menu = new Menu();
        menu.nombre = "InfoUbiSeleccionada";
        JFrame frame = menu.crearNuevaVentana();
        frame.setAlwaysOnTop(true);
        panelTextoInfo = new JTextPane();  // Cambiado a JTextPane

        for (int i = 0; i < Ciudad.ROWS; i++) {
            for (int j = 0; j < Ciudad.COLUMNS; j++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                gridButtons[i][j] = button;

                final int row = i;
                final int col = j;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (!isLocked) {
                            rowSelected = row;
                            colSelected = col;
                            actualizarEstadoPanel(ciudad, row, col);
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (!isLocked) {
                            panelTextoInfo.revalidate();
                            panelTextoInfo.repaint();
                        }
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        isLocked = !isLocked;
                        
                        if (!isLocked) {
                            panelTextoInfo.setText("");
                        }
                        
                        panelTextoInfo.repaint();
                    }
                });

                gridPanel.add(button);
            }
        }

        agregarEsteticaPanelInfo();
        // Añadir el panel al JFrame
        frame.add(panelTextoInfo);
        frame.setVisible(true);
        
        add(gridPanel, BorderLayout.CENTER);

        JPanel tiempoPanel = new JPanel();
        tiempoLabel = new JLabel();
        tiempoPanel.add(tiempoLabel);

        // Determinar el espaciado de las marcas principales dinámicamente, ajustándolo al rango
        int majorSpacing = tiempo.velocidadTranscursoInstantes / 5;  // Dividir el máximo en 5 partes para las marcas principales
        
        // Determinar el espaciado de las marcas menores como una fracción de las principales
        int minorSpacing = majorSpacing / 5;  // Por ejemplo, dividir el espaciado mayor en 5 para las marcas menores
        
        // Configurar el espaciado en el slider
        speedSlider.setMajorTickSpacing(majorSpacing);
        speedSlider.setMinorTickSpacing(minorSpacing);
        
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                actualizarSliderSpeedSimulation(simulacion, tiempo);
            }
        });

        sliderPanel.setLayout(new BorderLayout());
        sliderPanel.add(sliderLabel, BorderLayout.NORTH);
        sliderPanel.add(speedSlider, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(tiempoPanel);
        southPanel.add(Box.createVerticalStrut(10));
        southPanel.add(sliderPanel);
        southPanel.add(Box.createVerticalStrut(10));

        add(southPanel, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void agregarEsteticaPanelInfo() {
         // Configuración de JTextPane
        panelTextoInfo.setEditable(false);
        panelTextoInfo.setBackground(DESCRIPTION_PANE_COLOR);  // Color de fondo cian
        StyledDocument doc = panelTextoInfo.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        panelTextoInfo.setPreferredSize(new java.awt.Dimension(200, 100));  // Tamaño fijo para el JTextPane
        panelTextoInfo.setSize(new java.awt.Dimension(200, 100));  // Fijar el tamaño
        panelTextoInfo.setMinimumSize(new java.awt.Dimension(200, 100));  // Establecer el tamaño mínimo
        panelTextoInfo.setMaximumSize(new java.awt.Dimension(200, 100));  // Establecer el tamaño máximo
        panelTextoInfo.setCaretColor(DESCRIPTION_PANE_COLOR); 
    }
    
    public void actualizarEstadoPanel(Ciudad ciudad, int row, int col) {       
        panelTextoInfo.setText("");
        
        // Separar las entidades de tipo Base de las demás
        ArrayList<Entidad> entidadesBase = new ArrayList<>();
        ArrayList<Entidad> otrasEntidades = new ArrayList<>();
        
        for (Entidad entidad : ciudad.getEntidades()) {
            if (entidad instanceof Base) {
                entidadesBase.add(entidad);  // Añadir las entidades Base a una lista separada
            } else {
                otrasEntidades.add(entidad);  // Añadir el resto a otra lista
            }
        }
    
        // Mostrar las entidades Base primero
        for (Entidad entidad : entidadesBase) {
            if (entidad.getUbicacion().getPosX() == row && entidad.getUbicacion().getPosY() == col) {
                panelTextoInfo.setText(panelTextoInfo.getText() + entidad.toString() + "\n\n");
            }
        }
    
        // Luego mostrar las otras entidades
        for (Entidad entidad : otrasEntidades) {
            if (entidad.getUbicacion().getPosX() == row && entidad.getUbicacion().getPosY() == col) {
                panelTextoInfo.setText(panelTextoInfo.getText() + entidad.toString() + "\n\n");
            }
        }
        
        // Asegúrate de mover el caret al inicio para que se vea el contenido superior
        panelTextoInfo.setCaretPosition(0); // Mover el caret al principio
    
        if (panelTextoInfo.getText().length() > 0) {
            panelTextoInfo.revalidate();
            panelTextoInfo.repaint();
        } else {
            panelTextoInfo.setText("Vacío en (" + row + "," + col + ")");
        }
    }

    public void actualizarSliderSpeedSimulation(Simulacion simulacion, Tiempo tiempo) {
        int sliderValue = speedSlider.getValue();
        int deadZone = 5;

        if (Math.abs(sliderValue) <= deadZone) {
            tiempo.setVelocidadTranscursoInstantes(0);
            simulacion.runningForward = false;
            simulacion.runningBackward = false;
            speedSlider.setValue(0);
        } else if (sliderValue > deadZone) {
            tiempo.setVelocidadTranscursoInstantes(sliderValue);
            simulacion.runningForward = true;
            simulacion.runningBackward = false;
        } else if (sliderValue < -deadZone) {
            tiempo.setVelocidadTranscursoInstantes(sliderValue);
            simulacion.runningForward = false;
            simulacion.runningBackward = true;
        }

        sliderLabel.setText("Velocidad: " + Math.abs(tiempo.velocidadTranscursoInstantes) + (simulacion.runningForward ? " (avance)" : " (retroceso)"));
    }

    public void actualizarTiempoLabel(Tiempo tiempo) {
        // Formatear el tiempo en una cadena
        String tiempoFormateado = String.format(
            "Hora (%02d/%02d/%02d) - Mes (%02d/%d)",
            tiempo.hora, tiempo.minuto, tiempo.segundo, tiempo.mes, tiempo.año
        );    
        // Actualizar el texto del label con la cadena formateada
        tiempoLabel.setText("[ " + tiempoFormateado + " ]");
    }
    
    public void actualizarInfoCasillaSeleccionada(Ciudad ciudad) {
        actualizarEstadoPanel(ciudad, rowSelected, colSelected);
    }
}
