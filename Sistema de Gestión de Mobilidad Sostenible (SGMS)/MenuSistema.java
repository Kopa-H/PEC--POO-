
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JScrollPane;
import java.util.ArrayList;

public class MenuSistema extends Menu {

    private UtilidadesMenu utilidadesMenu = new UtilidadesMenu();
    
    private TipoUsuario tipoUsuario;
    private GestorMenus gestorMenus;
    private JFrame frame;
    
    protected String nombreMenuPrincipal;
    
    protected int WINDOW_WIDTH = 600;
    protected int WINDOW_HEIGHT = 700;
    
    Simulacion simulacion;
    Ciudad ciudad;
    Persona personaAccedida;

    /**
     * Constructor para la clase MenuSistema
     * @param tipoUsuario Tipo de usuario que se pasará a la interfaz
     */
    public MenuSistema(Simulacion simulacion, Ciudad ciudad, TipoUsuario tipoUsuario, GestorMenus gestorMenus, Persona personaAccedida) {
        this.simulacion = simulacion;
        this.ciudad = ciudad;
        this.personaAccedida = personaAccedida;

        this.tipoUsuario = tipoUsuario;  // Asignamos el tipo de usuario
        this.gestorMenus = gestorMenus;
        this.frame = gestorMenus.frame;

        Menu menu = new Menu();
        menu.nombre = "Menú " + tipoUsuario.name();
        this.nombreMenuPrincipal = menu.nombre; 
        
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setTitle(menu.nombre);
        
        panel = menu.crearPanel();
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
                utilidadesMenu.mostrarInfo(ciudad, UtilidadesMenu.TipoInfoMostrada.BATERIAS);
            }
        }));
        
        botones.put("Visualizar Estados Mecánicos", new Boton("VISUALIZAR ESTADOS MECÁNICOS", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                utilidadesMenu.mostrarInfo(ciudad, UtilidadesMenu.TipoInfoMostrada.ESTADO_MECANICO);
            }
        }));
        botones.put("Visualizar Estado de Bases", new Boton("VISUALIZAR ESTADO DE BASES", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                utilidadesMenu.mostrarInfo(ciudad, UtilidadesMenu.TipoInfoMostrada.BASES);
            }
        }));
        botones.put("Visualizar Estado Promociones", new Boton("VISUALIZAR ESTADO PROMOCIONES", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                visualizarEstadoPromociones();
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
    
    public void visualizarEstadoPromociones() {
        Menu menu = new Menu();
        menu.nombre = "Estado Promociones";
        JDialog dialogo = menu.crearNuevoDialogo();
        
        // Crear el panel para el submenú
        JPanel panel = menu.crearPanel();
                
        // Se itera sobre todos los usuarios
        ArrayList<Entidad> usuarios = ciudad.obtenerEntidadesPorClase(Usuario.class);
        
        for (Usuario usuario : usuarios) {
            // SE añade un JPanel del usuario con su nombre e id
            
            for (InfoAlquiler infoAlquiler : registroInfoAlquileres) {
                
            }
            
            if (15 vehiculos usados en en ultimo mes)
            
            if (10 vehiculos al mes durante 3 meses seguidos)
            
            if (todos los tipos de vehiculo usados en los ultimos 6 meses)
            
            // SE añade un boton si es que puede promocionar
            eventlistener{
                usuario.promocionarUsuario();
            }
        }
        
        // Añadir el panel al JFrame
        dialogo.add(agregarScroll(panel));
        
        dialogo.setVisible(true);
    }
    
    public void iniciarGestorEntidades() {
        Menu menu = new Menu();
        menu.nombre = "Gestor Entidades";
        JFrame frame = menu.crearNuevaVentana();
        
        // Crear el panel para el submenú
        JPanel panel = menu.crearPanel();
        
        // Crear y añadir los botones con sus funcionalidades
        menu.botones.put("AgregarUsuarioNormal", new Boton("Agregar Usuario Normal", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulacion.agregarUsuarioNormal(); // Asegúrate de que este método está bien definido en la clase 'simulacion'
            }
        }));
        menu.botones.put("AgregarUsuarioPremium", new Boton("Agregar Usuario Premium", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulacion.agregarUsuarioPremium(); // Asegúrate de que este método está bien definido en la clase 'simulacion'
            }
        }));
        menu.botones.put("AgregarTecnicoMantenimiento", new Boton("Agregar Técnico Mantenimiento", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulacion.agregarTecnicoMantenimiento(); // Asegúrate de que este método está bien definido en la clase 'simulacion'
            }
        }));
        menu.botones.put("AgregarMecanico", new Boton("Agregar Mecánico", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulacion.agregarMecanico(); // Asegúrate de que este método está bien definido en la clase 'simulacion'
            }
        }));
        
        // Crear y añadir los botones con sus funcionalidades
        menu.botones.put("AgregarMoto", new Boton("Agregar Moto", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulacion.agregarMoto(); // Asegúrate de que este método está bien definido en la clase 'simulacion'
            }
        }));
        menu.botones.put("AgregarBase", new Boton("Agregar Base", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Crear una nueva ventana de diálogo para pedir las cantidades de bicicletas y patinetes
                Menu menu = new Menu();
                menu.nombre = "Agregar Base con Bicicletas y Patinetes";
                JDialog dialogo = menu.crearNuevoDialogo();
                
                // Crear el panel para el submenú
                JPanel panel = menu.crearPanel();
                
                // Crear los campos de texto para ingresar el número de bicicletas y patinetes
                JTextField bicicletasField = new JTextField();
                JTextField patinetesField = new JTextField();
                
                // Etiquetas para los campos
                panel.add(new JLabel("Número de Bicicletas:"));
                panel.add(bicicletasField);
                panel.add(new JLabel("Número de Patinetes:"));
                panel.add(patinetesField);
                
                // Botón para confirmar y cerrar el diálogo
                JButton aceptarButton = new JButton("Aceptar");
                aceptarButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            // Obtener los valores de las bicicletas y patinetes
                            int numBicicletas = Integer.parseInt(bicicletasField.getText());
                            int numPatinetes = Integer.parseInt(patinetesField.getText());
                            
                            // Llamar a simulacion.agregarBase con los valores
                            ciudad.agregarBase(simulacion, numBicicletas, numPatinetes);
                            
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
                panel.add(aceptarButton);
                panel.add(cancelarButton);
                
                dialogo.add(agregarScroll(panel));
                
                dialogo.setVisible(true);
            }
        }));
        
        menu.botones.put("AgregarGrupoEntidades", new Boton("Agregar Grupo Entidades", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulacion.agregarGrupoEntidades(); // Asegúrate de que este método está bien definido en la clase 'simulacion'
            }
        }));

        // Agregar los botones al panel
        agregarBotones(menu.botones, panel);
        
        // Añadir el panel al JFrame
        frame.add(agregarScroll(panel));
        
        frame.setVisible(true);
    }
    
    private void agregarOpcionesUsuarioNormal() {
        String nombreBoton;
        
        // CONSULTAR VEHÍCULOS DISPONIBLES
        nombreBoton = "Consultar Vehículos Disponibles";
        botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                utilidadesMenu.mostrarInfo(ciudad, UtilidadesMenu.TipoInfoMostrada.VEHICULOS);
            }
        }));

        // ALQUILAR VEHÍCULOS
        nombreBoton = "Alquilar Vehículo";
        botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Class<?> claseVehiculo = utilidadesMenu.seleccionarClase("vehiculo");
                
                if (claseVehiculo != null) {
                
                    Entidad entidadPorAlquilar = ciudad.encontrarEntidadUsableMasCercana(personaAccedida, claseVehiculo);
                    
                    // Comprobar si se ha encontrado una entidad
                    if (entidadPorAlquilar == null) {
                        JOptionPane.showMessageDialog(frame, "No se ha podido encontrar ninguna moto disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    personaAccedida.planearTrayecto(entidadPorAlquilar.getUbicacion(), entidadPorAlquilar);
                }
            }
        }));
        
        nombreBoton = "Alertar Fallo Mecánico";
        botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Class<?> claseEntidad = utilidadesMenu.seleccionarClase("entidad");
                
                if (claseEntidad != null) {
                    int indice = utilidadesMenu.seleccionarIndice();
                    
                    Entidad entidad = ciudad.encontrarEntidad(claseEntidad, indice);
                    
                    if (entidad == null) {
                        JOptionPane.showMessageDialog(frame, "No se ha encontrado la entidad indicada.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    ((Usuario)personaAccedida).alertarFalloMecanico(entidad);
                }
                
            }
        }));
        
        nombreBoton = "Visualización Historial de Viajes";
        botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Visualizando historial de viajes");
            }
        }));
        
        nombreBoton = "Consultar / Recargar Saldo";
        botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu();
                menu.nombre = "Gestor Entidades";
                JFrame frame = menu.crearNuevaVentana();
                
                // Crear el panel para el submenú
                JPanel panel = menu.crearPanel();
                
                // Crear un JLabel para mostrar el saldo
                JLabel saldoLabel = new JLabel("Saldo disponible: " + ((Usuario)personaAccedida).getSaldo() + "€");
                panel.add(saldoLabel);
                
                String nombreBoton = "Recargar saldo";
                menu.botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Menu menu = new Menu();
                        menu.nombre = "Recargar saldo";
                        
                        // Crear un nuevo diálogo para la recarga de saldo
                        JDialog dialogo = menu.crearNuevoDialogo();
                        dialogo.setTitle("Recargar Saldo");
                        
                        // Crear un panel para añadir los elementos
                        JPanel panel = new JPanel();
                        dialogo.add(panel);
                        
                        // Crear el input para la cantidad a recargar
                        JLabel label = new JLabel("Introduce la cantidad a recargar:");
                        JTextField inputCantidad = new JTextField(10);  // Campo de texto para introducir la cantidad
                        
                        panel.add(label);
                        panel.add(inputCantidad);
                        
                        // Botón de aceptar
                        JButton aceptarButton = new JButton("Aceptar");
                        aceptarButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    // Obtener la cantidad introducida
                                    double cantidad = Double.parseDouble(inputCantidad.getText());
                                    
                                    // Verificar que la cantidad sea mayor que 0
                                    if (cantidad <= 0) {
                                        JOptionPane.showMessageDialog(dialogo, "Introduce una cantidad mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        // Lógica para recargar el saldo
                                        ((Usuario)personaAccedida).recargarSaldo(cantidad);
                                        
                                        // Actualizar el JLabel del saldo
                                        saldoLabel.setText("Saldo disponible: " + ((Usuario)personaAccedida).getSaldo() + "€");
                                        
                                        dialogo.dispose();  // Cerrar el diálogo después de la recarga
                                    }
                                } catch (NumberFormatException ex) {
                                    // Manejar el error si no se introduce un número válido
                                    JOptionPane.showMessageDialog(dialogo, "Introduce una cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        });
                        
                        // Botón de cancelar
                        JButton cancelarButton = new JButton("Cancelar");
                        cancelarButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                dialogo.dispose();  // Cerrar el diálogo si se cancela la operación
                            }
                        });
                        
                        // Añadir los botones al panel
                        panel.add(aceptarButton);
                        panel.add(cancelarButton);
                        
                        // Ajustar el tamaño del diálogo y hacerlo visible
                        dialogo.pack();
                        dialogo.setVisible(true);
                    }
                }));
        
                // Agregar los botones al panel
                agregarBotones(menu.botones, panel);
                
                // Añadir el panel al JFrame
                frame.add(agregarScroll(panel));
                
                frame.setVisible(true);
            }
        }));
        
        nombreBoton = "Consultar Moto Cercana";
        botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu();
                menu.nombre = "Consultar Moto Cercana";
                JFrame frame = menu.crearNuevaVentana();
        
                // Crear el panel para el submenú
                JPanel panel = menu.crearPanel();
        
                // Buscar la moto más cercana disponible
                Moto moto = (Moto) ciudad.encontrarEntidadUsableMasCercana(personaAccedida, Moto.class);
                if (moto == null) {
                    // Mostrar mensaje de error si no se encuentra ninguna moto disponible
                    JLabel errorLabel = new JLabel("No se ha encontrado ninguna moto disponible.");
                    panel.add(errorLabel);
                } else {
                    // Mostrar la información de la moto
                    JLabel motoLabel = new JLabel("Moto cercana disponible: " + moto.toString());
                    panel.add(motoLabel);
        
                    // Botón para alquilar la moto
                    JButton alquilarButton = new JButton("Alquilar moto");
                    alquilarButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            personaAccedida.planearTrayecto(moto.getUbicacion(), moto);
                            frame.dispose();
                        }
                    });
        
                    panel.add(alquilarButton);
                }
        
                // Agregar los botones al panel
                agregarBotones(menu.botones, panel);
        
                // Añadir el panel al JFrame
                frame.add(agregarScroll(panel));
                frame.setVisible(true);
            }
        }));
    }


    private void agregarOpcionesComunesTrabajadores() {
        String nombreBoton;
    
        nombreBoton = "Consultar Entidad Asignada";
        botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu();
                JLabel entidadAsignadaLabel;
                menu.nombre = "Consultar Entidad Asignada";
                JFrame frame = menu.crearNuevaVentana();
    
                // Crear el panel para el submenú
                JPanel panel = menu.crearPanel();
    
                // Crear un JLabel para mostrar la entidad asignada
                Entidad entidadAsignada = ((Trabajador) personaAccedida).getEntidadAsignada();
                
                if (entidadAsignada == null) {
                    entidadAsignadaLabel = new JLabel("El trabajador no tiene ninguna entidad asignada");
                } else {
                    entidadAsignadaLabel = new JLabel("Entidad asignada: " + entidadAsignada.toString());
                }
                panel.add(entidadAsignadaLabel);
    
                // Añadir el panel al JFrame
                frame.add(agregarScroll(panel));
                frame.setVisible(true);
    
                // Configurar el Timer para actualizar el texto del JLabel cada 1 segundo (1000 milisegundos)
                Timer timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        // Obtener la entidad asignada actualizada
                        Entidad entidadActualizada = ((Trabajador) personaAccedida).getEntidadAsignada();
                        
                        if (entidadActualizada == null) {
                            entidadAsignadaLabel.setText("El trabajador no tiene ninguna entidad asignada");
                        } else {
                            entidadAsignadaLabel.setText("Entidad asignada: " + entidadActualizada.toString());
                        }
                    }
                });
    
                // Iniciar el Timer
                timer.start();
            }
        }));
        
        nombreBoton = "Asignar Vehículo";
        botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Trabajador trabajador = (Trabajador) personaAccedida;

                // Comprobar si el trabajador ya tiene una entidad asignada
                if (trabajador.getEntidadAsignada() != null) {
                    trabajador.terminarTrabajo();
                }

                // Seleccionamos la clase de vehículo
                Class<?> claseVehiculo = utilidadesMenu.seleccionarClase("vehiculo");

                if (claseVehiculo == null) {
                    // Mostrar mensaje de error si no se seleccionó ninguna clase de vehículo
                    JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna clase de vehículo.",
                                                  "Error de selección", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Intentar encontrar una entidad con fallo mecánico de la clase seleccionada
                Entidad entidadPorAsignar = ciudad.encontrarEntidadConFalloMecanico(claseVehiculo);

                if (entidadPorAsignar == null) {
                    // Mostrar mensaje si no se encontró ninguna entidad con fallo mecánico
                    JOptionPane.showMessageDialog(null, "No se encontró ninguna entidad con fallo mecánico del tipo seleccionado.",
                                                  "Fallo Mecánico No Encontrado", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Intentar asignar el vehículo al trabajador
                    trabajador.intentarAsignarEntidad(ciudad, entidadPorAsignar);
                    JOptionPane.showMessageDialog(null, "Vehículo asignado correctamente.",
                                                  "Asignación exitosa", JOptionPane.INFORMATION_MESSAGE);
                }
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
