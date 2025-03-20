
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JScrollPane;
import java.util.ArrayList;

public class MenuSistema extends Menu {

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
    
    public enum TipoInfoMostrada {
        VEHICULOS, BATERIAS, ESTADO_MECANICO, BASES
    }
    
    public void mostrarInfo(TipoInfoMostrada tipo) {
        Menu menu = new Menu();
        menu.nombre = "Vehículos Disponibles";
        JFrame frame = menu.crearNuevaVentana();
        
        // Crear el panel para el submenú
        JPanel panel = menu.crearPanel();
        
        // Añadir el scroll
        frame.add(agregarScroll(panel));
        
        frame.setVisible(true);
        
        actualizarInfoMostrada(panel, tipo);
        
        // Configurar el Timer para actualizar el panel cada 1 segundo (1000 milisegundos)
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                actualizarInfoMostrada(panel, tipo);
            }
        });
        
        // Iniciar el Timer
        timer.start();
    }
            
    public void actualizarInfoMostrada(JPanel panel, TipoInfoMostrada tipo) {
        // Limpiar el panel para actualizarlo con las nuevas entidades
        panel.removeAll();
        
        // Verificar si hay entidades en la lista
        if (ciudad.getEntidades().isEmpty()) {
            JLabel noEntidadesLabel = new JLabel("No existen entidades todavía.");
            panel.add(noEntidadesLabel);
        } else {
            boolean entidadEncontrada = false; // Variable para verificar si se encuentra alguna entidad del tipo necesario
            
            switch(tipo) {
                case VEHICULOS:
                    for (Entidad entidad : ciudad.getEntidades()) {
                        if (entidad instanceof Vehiculo) {
                            JLabel label = new JLabel(entidad.toString());
                            panel.add(label);
                            entidadEncontrada = true; // Se encontró al menos un vehículo
                        }
                    }
                    break;
                    
                case BATERIAS:
                    for (Entidad entidad : ciudad.getEntidades()) {
                        if (entidad instanceof Vehiculo vehiculo) {
                            JLabel label = new JLabel(entidad.toSimpleString() + " con nivel de batería " + vehiculo.getPorcentajeBateria() + "%");
                            panel.add(label);
                            entidadEncontrada = true; // Se encontró al menos un vehículo con batería
                        }
                    }
                    break;
                    
                case ESTADO_MECANICO:
                    for (Entidad entidad : ciudad.getEntidades()) {
                        if (entidad instanceof Vehiculo) {
                            JLabel label = new JLabel(entidad.toSimpleString() + " con nivel de batería " + entidad.getPorcentajeEstadoMecanico() + "%");
                            panel.add(label);
                            entidadEncontrada = true; // Se encontró al menos un vehículo con estado mecánico
                        }
                    }
                    break;
                    
                case BASES:
                    for (Entidad entidad : ciudad.getEntidades()) {
                        if (entidad instanceof Base) {
                            JLabel label = new JLabel(entidad.toString());
                            panel.add(label);
                            entidadEncontrada = true; // Se encontró al menos una base
                        }
                    }
                    break;
            }
            
            // Si no se encontraron entidades del tipo seleccionado
            if (!entidadEncontrada) {
                JLabel noEntidadesLabel = new JLabel("No existen " + tipo.toString().toLowerCase() + " todavía.");
                panel.add(noEntidadesLabel);
            }
        }
        
        // Refrescar el panel después de actualizar
        panel.revalidate();
        panel.repaint();
    }
    
        public Class<?> seleccionarClase(String tipo) {
        final Class<?>[] claseSeleccionada = {null};

        Menu menu = new Menu();
        menu.nombre = "Seleccionar Clase";
        JDialog dialogo = menu.crearNuevoDialogo();

        // Crear el panel para el submenú
        JPanel panel = menu.crearPanel();

        String nombreBoton;

        if (tipo.equalsIgnoreCase("vehiculo")) {
            // Vehículos
            nombreBoton = "Seleccionar Moto";
            menu.botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    claseSeleccionada[0] = Moto.class;
                    dialogo.dispose(); // Cerrar el diálogo tras la selección
                }
            }));

            nombreBoton = "Seleccionar Bicicleta";
            menu.botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    claseSeleccionada[0] = Bicicleta.class;
                    dialogo.dispose(); // Cerrar el diálogo tras la selección
                }
            }));

            nombreBoton = "Seleccionar Patinete";
            menu.botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    claseSeleccionada[0] = Patinete.class;
                    dialogo.dispose(); // Cerrar el diálogo tras la selección
                }
            }));
        } else if (tipo.equalsIgnoreCase("entidad")) {
            // Entidades
            nombreBoton = "Seleccionar Base";
            menu.botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    claseSeleccionada[0] = Base.class;
                    dialogo.dispose(); // Cerrar el diálogo tras la selección
                }
            }));

            nombreBoton = "Seleccionar Moto";
            menu.botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    claseSeleccionada[0] = Moto.class;
                    dialogo.dispose(); // Cerrar el diálogo tras la selección
                }
            }));

            nombreBoton = "Seleccionar Bicicleta";
            menu.botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    claseSeleccionada[0] = Bicicleta.class;
                    dialogo.dispose(); // Cerrar el diálogo tras la selección
                }
            }));

            nombreBoton = "Seleccionar Patinete";
            menu.botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    claseSeleccionada[0] = Patinete.class;
                    dialogo.dispose(); // Cerrar el diálogo tras la selección
                }
            }));
        }

        // Agregar los botones al panel
        agregarBotones(menu.botones, panel);

        // Añadir el panel al JFrame
        dialogo.add(agregarScroll(panel));

        dialogo.setVisible(true);

        return claseSeleccionada[0];  // Devolver la clase seleccionada
    }
    
    public static int seleccionarIndice() {
        final int[] indiceSeleccionado = {-1}; // -1 por defecto si no se selecciona un índice

        // Crear el diálogo
        Menu menu = new Menu();
        menu.nombre = "Seleccionar Índice";
        JDialog dialogo = menu.crearNuevoDialogo();

        // Crear el panel para el submenú
        JPanel panel = menu.crearPanel();

        // Etiqueta para el campo de texto
        JLabel etiqueta = new JLabel("Introduce un índice válido (0 o mayor):");
        panel.add(etiqueta, BorderLayout.NORTH);

        // Campo de texto para el índice
        JTextField campoIndice = new JTextField(10);
        panel.add(campoIndice, BorderLayout.CENTER);

        // Botón para aceptar el índice
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int indice = Integer.parseInt(campoIndice.getText());
                    if (indice >= 0) {
                        indiceSeleccionado[0] = indice;
                    } else {
                        JOptionPane.showMessageDialog(dialogo, "El índice debe ser mayor o igual a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialogo, "Por favor, introduce un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                dialogo.dispose(); // Cerrar el diálogo tras la selección
            }
        });

        // Botón para cancelar
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialogo.dispose(); // Cerrar el diálogo sin selección
            }
        });

        // Agregar los botones al panel
        JPanel botonesPanel = new JPanel();
        botonesPanel.add(botonAceptar);
        botonesPanel.add(botonCancelar);
        panel.add(botonesPanel, BorderLayout.SOUTH);

        // Añadir el panel al JFrame
        dialogo.add(panel);

        // Mostrar el diálogo
        dialogo.pack();
        dialogo.setVisible(true);

        return indiceSeleccionado[0]; // Devolver el índice seleccionado
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
                mostrarInfo(TipoInfoMostrada.BATERIAS);
            }
        }));
        
        botones.put("Visualizar Estados Mecánicos", new Boton("VISUALIZAR ESTADOS MECÁNICOS", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarInfo(TipoInfoMostrada.ESTADO_MECANICO);
            }
        }));
        botones.put("Visualizar Estado de Bases", new Boton("VISUALIZAR ESTADO DE BASES", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarInfo(TipoInfoMostrada.BASES);
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
        
        // CONSULTAR VEHÍCULOS DISPONIBLES
        botones.put("Consultar Vehículos Disponibles", new Boton("CONSULTAR VEHÍCULOS DISPONIBLES", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarInfo(TipoInfoMostrada.VEHICULOS);
            }
        }));

        // ALQUILAR VEHÍCULOS
        botones.put("Alquilar Vehículo", new Boton("ALQUILAR VEHÍCULO", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Class<?> claseVehiculo = seleccionarClase("vehiculo");
                
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
        
        botones.put("Alertar Fallo Mecánico", new Boton("ALERTAR FALLO MECÁNICO", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Class<?> claseEntidad = seleccionarClase("entidad");
                
                if (claseEntidad != null) {
                    int indice = seleccionarIndice();
                    
                    Entidad entidad = ciudad.encontrarEntidad(claseEntidad, indice);
                    
                    if (entidad == null) {
                        JOptionPane.showMessageDialog(frame, "No se ha encontrado la entidad indicada.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    ((Usuario)personaAccedida).alertarFalloMecanico(entidad);
                }
                
            }
        }));
        botones.put("Visualización Historial de Viajes", new Boton("VISUALIZACIÓN HISTORIAL DE VIAJES", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Visualizando historial de viajes");
            }
        }));
        
        String nombreBoton = "Consultar / Recargar Saldo";
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
