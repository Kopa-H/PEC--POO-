import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;

public class MenuSistema extends Menu {

    private TipoUsuario tipoUsuario;
    private GestorMenus gestorMenus;
    private JFrame frame;
    
    protected int WINDOW_WIDTH = 600;
    protected int WINDOW_HEIGHT = 700;
    
    Simulacion simulacion;

    /**
     * Constructor para la clase MenuSistema
     * @param tipoUsuario Tipo de usuario que se pasará a la interfaz
     */
    public MenuSistema(Simulacion simulacion, TipoUsuario tipoUsuario, GestorMenus gestorMenus) {
        this.simulacion = simulacion;
        this.tipoUsuario = tipoUsuario;  // Asignamos el tipo de usuario
        this.gestorMenus = gestorMenus;
        this.frame = gestorMenus.frame;

        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setTitle("Menú " + tipoUsuario.name());
        panel = crearPanel("Menu" + tipoUsuario.name());
        panel.setBackground(Color.GREEN);
        
        agregarBotonesMenu();
        
        agregarBotones(botones, panel);
    }
    
    private void agregarBotonesMenu() {
        // Dependiendo del tipo de usuario, los botones serán diferentes
        switch (tipoUsuario) {
            case TipoUsuario.ADMINISTRADOR:
                agregarOpcionesAdministrador();
                break;

            case TipoUsuario.USUARIO_NORMAL:
                agregarOpcionesUsuarioNormal();
                break;

            case TipoUsuario.USUARIO_PREMIUM:
                agregarOpcionesUsuarioNormal();
                botones.put("Reservar", new Boton("RESERVAR (hasta 20' antes)", new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(frame, "Reservar seleccionada");
                    }
                }));
                break;

            case TipoUsuario.TECNICO_MANTENIMIENTO:
                agregarOpcionesComunesTrabajadores();
                botones.put("Trasladar Vehículo", new Boton("TRASLADAR VEHÍCULO", new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(frame, "Trasladar vehículo seleccionada");
                    }
                }));
                break;

            case TipoUsuario.MECANICO:
                agregarOpcionesComunesTrabajadores();
                botones.put("Generar Última Factura", new Boton("GENERAR ÚLTIMA FACTURA", new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(frame, "Generar última factura seleccionada");
                    }
                }));
                break;
        }
    }

    private void agregarOpcionesAdministrador() {
        // Botones existentes
        botones.put("Abrir Gestor de Entidades", new Boton("ABRIR GESTOR DE ENTIDADES", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarGestorEntidades();
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
    
    // APARTADO DE FUNCIONES DE SUBMENUS
    
    public void iniciarGestorEntidades() {
        Menu menu = new Menu();
        JFrame frame = menu.crearNuevaVentana();
        frame.setTitle("Gestor Entidades");
        frame.setSize(600, 400);  // Ajusta el tamaño a tus necesidades
        
        // Crear el panel para el submenú
        JPanel panel = menu.crearPanel("MenuGestorEntidades");
        
        // Crear un HashMap para los botones del submenú
        LinkedHashMap<String, Boton> botones = new LinkedHashMap<>();
        
        // Crear y añadir los botones con sus funcionalidades
        botones.put("AgregarUsuarioNormal", new Boton("Agregar Usuario Normal", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulacion.agregarUsuarioNormal(); // Asegúrate de que este método está bien definido en la clase 'simulacion'
            }
        }));
        botones.put("AgregarUsuarioPremium", new Boton("Agregar Usuario Premium", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulacion.agregarUsuarioPremium(); // Asegúrate de que este método está bien definido en la clase 'simulacion'
            }
        }));
        botones.put("AgregarTecnicoMantenimiento", new Boton("Agregar Técnico Mantenimiento", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulacion.agregarTecnicoMantenimiento(); // Asegúrate de que este método está bien definido en la clase 'simulacion'
            }
        }));
        botones.put("AgregarMecanico", new Boton("Agregar Mecánico", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulacion.agregarMecanico(); // Asegúrate de que este método está bien definido en la clase 'simulacion'
            }
        }));
        
        // Crear y añadir los botones con sus funcionalidades
        botones.put("AgregarMoto", new Boton("Agregar Moto", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulacion.agregarMoto(); // Asegúrate de que este método está bien definido en la clase 'simulacion'
            }
        }));
        botones.put("AgregarBase", new Boton("Agregar Base", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Crear una nueva ventana de diálogo para pedir las cantidades de bicicletas y patinetes
                JDialog dialogo = new JDialog();
                dialogo.setTitle("Agregar Base");
                dialogo.setLayout(new GridLayout(3, 2));  // Para mostrar los campos y los botones de forma ordenada
                
                // Crear los campos de texto para ingresar el número de bicicletas y patinetes
                JTextField bicicletasField = new JTextField();
                JTextField patinetesField = new JTextField();
                
                // Etiquetas para los campos
                dialogo.add(new JLabel("Número de Bicicletas:"));
                dialogo.add(bicicletasField);
                dialogo.add(new JLabel("Número de Patinetes:"));
                dialogo.add(patinetesField);
                
                // Botón para confirmar y cerrar el diálogo
                JButton aceptarButton = new JButton("Aceptar");
                aceptarButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            // Obtener los valores de las bicicletas y patinetes
                            int numBicicletas = Integer.parseInt(bicicletasField.getText());
                            int numPatinetes = Integer.parseInt(patinetesField.getText());
                            
                            // Llamar a simulacion.agregarBase con los valores
                            simulacion.agregarBase(numBicicletas, numPatinetes);
                            
                            // Cerrar el diálogo
                            dialogo.dispose();
                        } catch (NumberFormatException ex) {
                            // Manejar el error si los campos no tienen valores numéricos válidos
                            JOptionPane.showMessageDialog(dialogo, "Por favor ingrese números válidos.");
                        }
                    }
                });
                // Botón de cancelar para cerrar el diálogo sin hacer nada
                JButton cancelarButton = new JButton("Cancelar");
                cancelarButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialogo.dispose();  // Cerrar el diálogo sin hacer nada
                    }
                });
                
                // Añadir los botones al diálogo
                dialogo.add(aceptarButton);
                dialogo.add(cancelarButton);
                
                // Configurar y mostrar el diálogo
                dialogo.setSize(300, 150);
                dialogo.setLocationRelativeTo(null);  // Centrar el diálogo
                dialogo.setModal(true);  // Hacer el diálogo modal (bloquea la ventana principal)
                dialogo.setVisible(true);
            }
        }));
        
        botones.put("AgregarGrupoEntidades", new Boton("Agregar Grupo Entidades", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulacion.agregarGrupoEntidades(); // Asegúrate de que este método está bien definido en la clase 'simulacion'
            }
        }));

        // Agregar los botones al panel
        agregarBotones(botones, panel);
        
        // Añadir el panel al JFrame
        frame.add(panel);
    }
}
