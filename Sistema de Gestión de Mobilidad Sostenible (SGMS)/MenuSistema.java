import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class MenuSistema extends Menu {

    private String tipoUsuario;

    /**
     * Constructor para la clase MenuSistema
     * @param tipoUsuario Tipo de usuario que se pasará a la interfaz
     */
    public MenuSistema(String tipoUsuario) {
        super();  // Llamamos al constructor de la clase base Menu
        this.tipoUsuario = tipoUsuario;  // Asignamos el tipo de usuario
        this.botones = new HashMap<>();  // Inicializamos el HashMap
        
        agregarBotonAtras("MenuIniciarSesion");  // Volver al menú principal
        
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
                agregarOpcionesAdministrador();
                break;

            case "Usuario Normal":
                agregarOpcionesUsuarioNormal();
                break;

            case "Usuario Premium":
                agregarOpcionesUsuarioNormal();
                botones.put("Reservar", new Boton("RESERVAR (hasta 20' antes)", new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(frame, "Reservar seleccionada");
                    }
                }));
                break;

            case "Técnico de Mantenimiento":
                agregarOpcionesComunesTrabajadores();
                botones.put("Trasladar Vehículo", new Boton("TRASLADAR VEHÍCULO", new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(frame, "Trasladar vehículo seleccionada");
                    }
                }));
                break;

            case "Mecánico":
                agregarOpcionesComunesTrabajadores();
                botones.put("Generar Última Factura", new Boton("GENERAR ÚLTIMA FACTURA", new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(frame, "Generar última factura seleccionada");
                    }
                }));
                break;
        }

        // Añadir los botones al panel sin aplicar estética (la clase Boton ya lo hace)
        for (String nombreBoton : botones.keySet()) {
            Boton boton = botones.get(nombreBoton);
            panel.add(boton.getBoton());
        }

        // Mostrar la ventana
        mostrarVentana();
    }

    private void agregarOpcionesAdministrador() {
        botones.put("Abrir Gestor de Personas", new Boton("ABRIR GESTOR DE PERSONAS", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Gestor de personas abierto");
            }
        }));
        botones.put("Abrir Gestor de Vehículos", new Boton("ABRIR GESTOR DE VEHÍCULOS", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Gestor de vehículos abierto");
            }
        }));
        // Agregar más botones para Administrador aquí...
    }

    private void agregarOpcionesUsuarioNormal() {
        botones.put("Consultar Vehículos Disponibles", new Boton("CONSULTAR VEHÍCULOS DISPONIBLES", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Consultando vehículos disponibles");
            }
        }));
        botones.put("Alquilar Vehículo", new Boton("ALQUILAR VEHÍCULO", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Alquilar vehículo");
            }
        }));
        // Agregar más botones para Usuario Normal aquí...
    }

    private void agregarOpcionesComunesTrabajadores() {
        botones.put("Ver Vehículo Asignado", new Boton("VER VEHÍCULO ASIGNADO", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Viendo vehículo asignado");
            }
        }));
        botones.put("Asignar Vehículo", new Boton("ASIGNAR VEHÍCULO", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Asignando vehículo");
            }
        }));
        // Agregar más botones comunes aquí...
    }
}
