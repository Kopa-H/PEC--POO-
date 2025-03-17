import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MenuSistema extends Menu {

    private ArrayList<JButton> botones;
    private String tipoUsuario;

    /**
     * Constructor para la clase MenuPersonal
     * @param tipoUsuario Tipo de usuario que se pasará a la interfaz
     */
    public MenuSistema(String tipoUsuario) {
        super();  // Llamamos al constructor de la clase base Menu
        this.tipoUsuario = tipoUsuario;  // Asignamos el tipo de usuario
        botones = new ArrayList<>();  // Inicializamos el arreglo de botones
        
        WINDOW_WIDTH = 500;
        WINDOW_HEIGHT = 400;
    }

    @Override
    public void iniciarInterfaz() {
        frame.setTitle("Menú de " + tipoUsuario);

        // Panel con color de fondo verde claro
        panel.setBackground(Color.GREEN);

        // Dependiendo del tipo de usuario, los botones serán diferentes
        switch (tipoUsuario) {
            case "Administrador":
                botones.add(new JButton(" ABRIR GESTOR DE PERSONAS "));
                botones.add(new JButton(" ABRIR GESTOR DE VEHÍCULOS "));
                botones.add(new JButton(" VISUALIZAR ESTADOS BATERÍAS "));
                botones.add(new JButton(" VISUALIZAR ESTADOS MECÁNICOS "));
                botones.add(new JButton(" VISUALIZAR ESTADO DE BASES "));
                botones.add(new JButton(" VISUALIZAR ESTADO PROMOCIONES "));
                botones.add(new JButton(" ASIGNAR TRABAJOS "));
                botones.add(new JButton(" VISUALIZAR INTERACCIONES USUARIOS E IMPORTES "));
                botones.add(new JButton(" VISUALIZAR INTERACCIONES VEHÍCULOS "));
                botones.add(new JButton(" MODIFICAR TARIFAS "));
                botones.add(new JButton(" VISUALIZAR ESTADÍSTICAS "));
                break;

            case "Usuario Normal":
                agregarOpcionesUsuarioNormal(botones);
                break;

            case "Usuario Premium":
                agregarOpcionesUsuarioNormal(botones);
                botones.add(new JButton("RESERVAR (hasta 20' antes)"));
                break;

            case "Técnico de Mantenimiento":
                agregarOpcionesComunesTrabajadores(botones);
                botones.add(new JButton("TRASLADAR VEHÍCULO"));
                break;

            case "Mecánico":
                agregarOpcionesComunesTrabajadores(botones);
                botones.add(new JButton("GENERAR ÚLTIMA FACTURA"));
                break;
        }

        // Aplicar la estética y añadir los botones al panel
        for (JButton boton : botones) {
            aplicarEsteticaBoton(boton);
            panel.add(boton);
        }

        // Funcionalidad de los botones
        for (int i = 0; i < botones.size(); i++) {
            int index = i;
            JButton boton = botones.get(index);

            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(frame, "Opción " + (index + 1) + " seleccionada");
                }
            });
        }

        // Mostrar la ventana
        mostrarVentana();
    }

    private void aplicarEsteticaBoton(JButton boton) {
        Font font = new Font("Arial", Font.PLAIN, 16);
        boton.setFont(font);
        boton.setBackground(new Color(0, 123, 255));
        boton.setForeground(Color.WHITE);
        boton.setPreferredSize(new Dimension(250, 40));
    }
    
    private void agregarOpcionesUsuarioNormal(ArrayList<JButton> botones) {
        botones.add(new JButton("CONSULTAR VEHÍCULOS DISPONIBLES"));
        botones.add(new JButton("ALQUILAR VEHÍCULO"));
        botones.add(new JButton("ALERTAR FALLO MECÁNICO"));
        botones.add(new JButton("VISUALIZACIÓN HISTORIAL DE VIAJES"));
        botones.add(new JButton("CONSULTAR SALDO DISPONIBLE"));
        botones.add(new JButton("RECARGAR SALDO"));
        botones.add(new JButton("CONSULTAR MOTO CERCANA"));
    }

    private void agregarOpcionesComunesTrabajadores(ArrayList<JButton> botones) {
        botones.add(new JButton("VER VEHÍCULO ASIGNADO"));
        botones.add(new JButton("ASIGNAR VEHÍCULO"));
        botones.add(new JButton("TRABAJAR"));
        botones.add(new JButton("DEFINIR PERIODO INACTIVIDAD DEL VEHÍCULO"));
    }
}
