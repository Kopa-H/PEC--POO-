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
        this.tipoUsuario = tipoUsuario;  // Asignamos el tipo de usuario
        this.botones = new HashMap<>();  // Inicializamos el HashMap

        frame.setTitle("Menú de " + tipoUsuario);

        // Panel con color de fondo verde claro
        panel.setBackground(Color.GREEN);
        
        WINDOW_WIDTH = 500;
        WINDOW_HEIGHT = 400;
        
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

        // Mostrar la ventana
        agregarBotonAtras("MenuIniciarSesion");  // Volver al menú principal
    }

    @Override
    public void iniciarMenu() {
        super.iniciarMenu();
    }

    private void agregarOpcionesAdministrador() {
        // Botones existentes
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
    
        // Nuevos botones con sus respectivas acciones
        botones.put("Visualizar Estados Baterías", new Boton("VISUALIZAR ESTADOS BATERÍAS", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Visualizando estados de baterías");
            }
        }));
        botones.put("Visualizar Estados Mecánicos", new Boton("VISUALIZAR ESTADOS MECÁNICOS", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Visualizando estados mecánicos");
            }
        }));
        botones.put("Visualizar Estado de Bases", new Boton("VISUALIZAR ESTADO DE BASES", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Visualizando estado de bases");
            }
        }));
        botones.put("Visualizar Estado Promociones", new Boton("VISUALIZAR ESTADO PROMOCIONES", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Visualizando estado de promociones");
            }
        }));
        botones.put("Asignar Trabajos", new Boton("ASIGNAR TRABAJOS", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Asignando trabajos");
            }
        }));
        botones.put("Visualizar Interacciones Usuarios e Importes", new Boton("VISUALIZAR INTERACCIONES USUARIOS E IMPORTES", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Visualizando interacciones de usuarios e importes");
            }
        }));
        botones.put("Visualizar Interacciones Vehículos", new Boton("VISUALIZAR INTERACCIONES VEHÍCULOS", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Visualizando interacciones de vehículos");
            }
        }));
    
        // Nuevos botones añadidos
        botones.put("Modificar Tarifas", new Boton("MODIFICAR TARIFAS", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Modificando tarifas");
            }
        }));
        botones.put("Visualizar Estadísticas", new Boton("VISUALIZAR ESTADÍSTICAS", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Visualizando estadísticas");
            }
        }));
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
        botones.put("Alertar Fallo Mecánico", new Boton("ALERTAR FALLO MECÁNICO", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Alertando de fallo mecánico");
            }
        }));
        botones.put("Visualización Historial de Viajes", new Boton("VISUALIZACIÓN HISTORIAL DE VIAJES", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Visualizando historial de viajes");
            }
        }));
        botones.put("Consultar Saldo Disponible", new Boton("CONSULTAR SALDO DISPONIBLE", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Consultando saldo disponible");
            }
        }));
        botones.put("Recargar Saldo", new Boton("RECARGAR SALDO", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Recargando saldo");
            }
        }));
        botones.put("Consultar Moto Cercana", new Boton("CONSULTAR MOTO CERCANA", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Consultando moto cercana");
            }
        }));
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
        botones.put("Trabajar", new Boton("TRABAJAR", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Iniciando trabajo");
            }
        }));
        botones.put("Definir Periodo Inactividad del Vehículo", new Boton("DEFINIR PERIODO INACTIVIDAD DEL VEHÍCULO", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Definiendo periodo de inactividad del vehículo");
            }
        }));
    }
}
