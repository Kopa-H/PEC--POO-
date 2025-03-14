import javax.swing.*;
import javax.swing.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.*;

/**
 * Interfaz gráfica de la simulación de ciudad.
 */
public class Interfaz extends JFrame {
    private JLabel statusLabel = new JLabel("Estado inicial");
    private JPanel statusPanel = new JPanel(new BorderLayout());
    JTextArea descripcionArea = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(descripcionArea);
    private JLabel stepLabel;
    JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, -Simulacion.MAX_SIMULATION_SPEED, Simulacion.MAX_SIMULATION_SPEED, 0);
    JLabel sliderLabel = new JLabel("Ajustar velocidad: " + 0, JLabel.CENTER);
    JPanel sliderPanel = new JPanel();
    private boolean isLocked = false;  // Variable para controlar si el contenido está bloqueado
    private static final int HORIZONTAL_WINDOW_SIZE = 1500;
    private static final int VERTICAL_WINDOW_SIZE = 1000;

    /**
     * Constructor para la interfaz de simulación.
     */
    public Interfaz(Simulacion simulacion, Ciudad ciudad, JButton[][] gridButtons, int step) {
        setTitle("Simulación de Ciudad");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gridPanel = new JPanel(new GridLayout(Simulacion.ROWS, Simulacion.COLUMNS));
        statusPanel.add(scrollPane, BorderLayout.CENTER);
        descripcionArea.setEditable(false);
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        descripcionArea.setRows(5);

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
                        if (!isLocked) { // Solo actualizar si no está bloqueado
                            actualizarEstadoPanel(ciudad, row, col);
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (!isLocked) { // Solo actualizar si no está bloqueado
                            statusLabel.setText("Estado inicial");
                            statusPanel.revalidate();
                            statusPanel.repaint();
                        }
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!isLocked) {
                            isLocked = true; // Bloquear la actualización
                        } else {
                            isLocked = false; // Desbloquear la actualización
                            statusLabel.setText("Estado inicial"); // Restablecer el estado si se desbloquea
                            statusPanel.revalidate();
                            statusPanel.repaint();
                        }
                    }
                });

                gridPanel.add(button);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        JPanel stepPanel = new JPanel();
        stepLabel = new JLabel("Paso: " + step);
        stepPanel.add(stepLabel);

        speedSlider.setMajorTickSpacing(500);
        speedSlider.setMinorTickSpacing(100);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                actualizarEstadoSlider(simulacion);
            }
        });
        
        sliderPanel.setLayout(new BorderLayout());
        sliderPanel.add(sliderLabel, BorderLayout.NORTH);
        sliderPanel.add(speedSlider, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(stepPanel);
        southPanel.add(Box.createVerticalStrut(10));
        southPanel.add(sliderPanel);
        southPanel.add(Box.createVerticalStrut(10));

        add(statusPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void actualizarEstadoPanel(Ciudad ciudad, int row, int col) {
        descripcionArea.setText("");
        for (Entidad entidad : ciudad.getEntidades()) {
            if (entidad.getUbicacion().getPosX() == row && entidad.getUbicacion().getPosY() == col) {
                descripcionArea.append(entidad.toString() + "\n");
            }
        }

        if (descripcionArea.getText().length() > 0) {
            statusPanel.revalidate();
            statusPanel.repaint();
        } else {
            statusLabel.setText("Vacío en (" + row + "," + col + ")");
        }
    }

    public void actualizarEstadoSlider(Simulacion simulacion) {
        int sliderValue = speedSlider.getValue();
        int deadZone = 5;

        if (Math.abs(sliderValue) <= deadZone) {
            simulacion.simulationSpeed = 0;
            simulacion.runningForward = false;
            simulacion.runningBackward = false;
            speedSlider.setValue(0);
        } else if (sliderValue > deadZone) {
            simulacion.simulationSpeed = sliderValue;
            simulacion.runningForward = true;
            simulacion.runningBackward = false;
        } else if (sliderValue < -deadZone) {
            simulacion.simulationSpeed = -sliderValue;
            simulacion.runningForward = false;
            simulacion.runningBackward = true;
        }

        sliderLabel.setText("Velocidad: " + Math.abs(simulacion.simulationSpeed) + (sliderValue < 0 ? " (retroceso)" : " (avance)"));
    }

    public void actualizarStepLabel(int step) {
        stepLabel.setText("Paso: " + step);
    }
}
