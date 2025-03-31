
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JFrame;

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
            case TipoUsuario.MECANICO:
                agregarOpcionesComunesTrabajadores();
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
        // Nuevos botones añadidos
        botones.put("Modificar Tarifas", new Boton("MODIFICAR TARIFAS", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarMenuTarifas();
            }
        }));
        botones.put("Visualizar Estadísticas", new Boton("VISUALIZAR ESTADÍSTICAS", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Visualizando estadísticas");
            }
        }));
    }

    public void iniciarMenuTarifas() {
        Menu menu = new Menu();
        menu.nombre = "Menú Tarifas";
    
        JDialog dialogo = menu.crearNuevoDialogo();
        JPanel panel = menu.crearPanel();
    
        JLabel labelDias = new JLabel("Introduce el número de días que transcurren entre cobros:");
        JTextField campoDias = new JTextField(10);
    
        JLabel labelTasas = new JLabel("Introduce el precio de las tasas en euros:");
        JTextField campoTasas = new JTextField(10);
    
        JButton botonConfirmar = new JButton("Confirmar");
    
        panel.add(labelDias);
        panel.add(campoDias);
        panel.add(Box.createVerticalStrut(10)); // Espaciado
        panel.add(labelTasas);
        panel.add(campoTasas);
        panel.add(Box.createVerticalStrut(10));
        panel.add(botonConfirmar);
    
        dialogo.add(panel);
        dialogo.setVisible(true);
    
        // Acción del botón
        botonConfirmar.addActionListener(e -> {
            try {
                int dias = Integer.parseInt(campoDias.getText().trim());
                double tasas = Double.parseDouble(campoTasas.getText().trim());
    
                if (dias <= 0 || tasas < 0) {
                    JOptionPane.showMessageDialog(dialogo, "Los valores deben ser positivos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (dias > 365) {
                    JOptionPane.showMessageDialog(dialogo, "Los días entre cobros no pueden superar el año (365).", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (tasas > 1000) {
                    JOptionPane.showMessageDialog(dialogo, "El precio de las tasas no puede superar los 1000€.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                ciudad.tiempo.diasEntrePagos = dias;
                ciudad.dinero.precioTasasEnEuros = tasas;
                JOptionPane.showMessageDialog(dialogo, "Tarifas actualizadas correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dialogo.dispose(); // Cierra el diálogo después de confirmar
    
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, "Introduce valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    public void iniciarMenuTrasladoVehiculo() {
        Menu menu = new Menu();
        menu.nombre = "Menú de traslado de vehículo";
        
        JDialog dialogo = menu.crearNuevoDialogo();
        JPanel panel = menu.crearPanel();
    
        // Se selecciona el tipo de vehículo
        Class<?> claseVehiculo = utilidadesMenu.seleccionarClase("vehiculo");
        
        // Verificar si el valor es nulo
        if (claseVehiculo == null) {
            dialogo.dispose(); // Cerrar el diálogo si el valor es nulo
            return; // Salir del método
        }
    
        // Se selecciona su índice
        int indice = utilidadesMenu.seleccionarIndice();
        
        // Verificar si el índice no es válido (-1 indica un valor no seleccionado en el ejemplo previo)
        if (indice == -1) {
            dialogo.dispose(); // Cerrar el diálogo si no se seleccionó un índice válido
            return; // Salir del método
        }
    
        Vehiculo vehiculoSeleccionado = (Vehiculo) ciudad.encontrarEntidad(claseVehiculo, indice);
        
        // Verificar si el vehículo es nulo
        if (vehiculoSeleccionado == null) {
            dialogo.dispose(); // Cerrar el diálogo si no se encontró un vehículo
            return; // Salir del método
        }
    
        // Se selecciona la ubicación de destino
        Ubicacion ubicacionDestino = utilidadesMenu.seleccionarUbicacion();
        
        // Verificar si la ubicación es nula
        if (ubicacionDestino == null) {
            dialogo.dispose(); // Cerrar el diálogo si no se seleccionó una ubicación válida
            return; // Salir del método
        }
    
        // Si todos los valores son válidos, se procede con el traslado del vehículo
        Trabajador trabajadorAccedido = (Trabajador) personaAccedida; 
        trabajadorAccedido.activarModoTraslado();
        trabajadorAccedido.setUbicacionTraslado(ubicacionDestino);
        trabajadorAccedido.setEntidadAsignada(ciudad, vehiculoSeleccionado);
    }
    
    public void mostrarFacturasTrabajador() {
        Menu menu = new Menu();
        menu.nombre = "Facturas Trabajador " + personaAccedida.toSimpleString();
    
        JFrame frame = menu.crearNuevaVentana();
        JPanel panel = menu.crearPanel();

        frame.add(agregarScroll(panel));
        frame.setVisible(true);
    
        Trabajador trabajadorAccedido = (Trabajador) personaAccedida;
    
        // Etiqueta de "No hay facturas"
        JLabel mensajeVacio = new JLabel("Todavía no hay ninguna factura.");
        mensajeVacio.setHorizontalAlignment(SwingConstants.CENTER);
        mensajeVacio.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
    
        actualizarFacturas(panel, trabajadorAccedido, mensajeVacio);
    
        // Timer que actualiza cada segundo
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarFacturas(panel, trabajadorAccedido, mensajeVacio);
            }
        });
    
        timer.start();
    
        // Cierra el timer cuando se cierre el diálogo
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                timer.stop();
            }
        });
    }
    
    // Método que actualiza la lista de facturas
    private void actualizarFacturas(JPanel panel, Trabajador trabajadorAccedido, JLabel mensajeVacio) {
        panel.removeAll(); // Limpia el panel antes de actualizar
    
        if (trabajadorAccedido.registroInfoFacturas.isEmpty()) {
            panel.add(mensajeVacio);
        } else {
            for (InfoFactura factura : trabajadorAccedido.registroInfoFacturas) {
                JLabel facturaLabel = new JLabel("<html>" + factura.toString().replace("\n", "<br>") + "</html>");
                facturaLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                panel.add(facturaLabel);
                panel.add(Box.createVerticalStrut(10)); // Espaciado entre facturas
            }
        }
    
        panel.revalidate();
        panel.repaint();
    }
    public void visualizarEstadoPromociones() {
        Menu menu = new Menu();
        menu.nombre = "Estado Promociones";
        frame.setTitle(menu.nombre);
        JFrame frame = menu.crearNuevaVentana();
    
        // Crear el panel para el submenú
        JPanel panel = menu.crearPanel();
    
        // Método que actualiza el contenido del panel
        Runnable actualizarEstado = () -> {
            // Limpiar el panel antes de agregar la nueva información
            panel.removeAll();
    
            // Se itera sobre todos los usuarios
            List<Entidad> entidades = ciudad.obtenerEntidadesPorClase(Usuario.class);
    
            // Si no hay usuarios, mostrar un mensaje
            if (entidades.isEmpty()) {
                panel.add(new JLabel("De momento no hay ningún usuario."));
            } else {
                for (Entidad entidad : entidades) {
                    Usuario usuario = (Usuario) entidad;
    
                    // Crear un JPanel para el usuario con su nombre e ID
                    JPanel panelUsuario = new JPanel();
                    panelUsuario.setLayout(new BoxLayout(panelUsuario, BoxLayout.Y_AXIS));
                    panelUsuario.add(new JLabel("Usuario: " + usuario.getNombre() + " (ID: " + usuario.getId() + ")"));
    
                    // Variables para las condiciones de promoción
                    int vehiculosUltimoMes = 0;
                    int vehiculosTresMesesSeguidos = 0;
                    HashSet<Class<? extends Vehiculo>> tiposVehiculosUsados = new HashSet<>();
    
                    // Se itera sobre su registro de alquileres
                    for (InfoAlquiler infoAlquiler : usuario.registroInfoAlquileres) {
                        // Verificar si el alquiler es del último mes
                        if (simulacion.tiempo.esUltimoMes(infoAlquiler.getMes())) {
                            vehiculosUltimoMes++;
                        }
    
                        // Verificar si alquiló 10 vehículos en 3 meses consecutivos
                        if (simulacion.tiempo.esMesReciente(infoAlquiler.getMes(), 3)) {
                            vehiculosTresMesesSeguidos++;
                        }
    
                        // Guardar el tipo de vehículo usado en los últimos 6 meses
                        if (simulacion.tiempo.esUltimosSeisMeses(infoAlquiler.getMes())) {
                            tiposVehiculosUsados.add(infoAlquiler.getClaseVehiculo());
                        }
                    }
    
                    // Condiciones para la promoción
                    boolean puedePromocionar = false;
    
                    // Crear los JLabels
                    JLabel lblVehiculosUltimoMes = new JLabel();
                    JLabel lblVehiculosTresMesesSeguidos = new JLabel();
                    JLabel lblTiposVehiculosUsados = new JLabel();
    
                    // Si ha usado 15 vehículos en el último mes
                    if (vehiculosUltimoMes >= 15) {
                        lblVehiculosUltimoMes.setText("    Cumple con la promoción: " + vehiculosUltimoMes + "/15 vehículos alquilados en el último mes");
                        puedePromocionar = true;
                    } else {
                        lblVehiculosUltimoMes.setText("    Vehículos alquilados en el último mes: " + vehiculosUltimoMes + "/15");
                    }
    
                    // Si ha usado 10 vehículos por mes durante 3 meses seguidos
                    if (vehiculosTresMesesSeguidos >= 30) { // 10 vehículos por mes * 3 meses
                        lblVehiculosTresMesesSeguidos.setText("    Cumple con la promoción: " + vehiculosTresMesesSeguidos + "/30 vehículos en 3 meses (10 por mes)");
                        puedePromocionar = true;
                    } else {
                        int vehiculosPromedioPorMes = vehiculosTresMesesSeguidos / 3;
                        lblVehiculosTresMesesSeguidos.setText("    Vehículos por mes en los últimos 3 meses: " + vehiculosPromedioPorMes + "/10 (Promedio por mes)");
                    }
    
                    // Si ha usado todos los tipos de vehículos en los últimos 6 meses
                    int totalTiposVehiculo = Vehiculo.obtenerTotalTiposVehiculo();
                    if (tiposVehiculosUsados.size() == totalTiposVehiculo) {
                        lblTiposVehiculosUsados.setText("    Cumple con la promoción: " + tiposVehiculosUsados.size() + "/" + totalTiposVehiculo + " tipos de vehículos usados en los últimos 6 meses");
                        puedePromocionar = true;
                    } else {
                        lblTiposVehiculosUsados.setText("    Tipos de vehículos usados en los últimos 6 meses: " + tiposVehiculosUsados.size() + "/" + totalTiposVehiculo);
                    }
    
                    // Añadir los JLabels al panel del usuario
                    panelUsuario.add(lblVehiculosUltimoMes);
                    panelUsuario.add(lblVehiculosTresMesesSeguidos);
                    panelUsuario.add(lblTiposVehiculosUsados);
    
                    // Si el usuario cumple con alguna promoción, añadir botón para promocionar
                    if (puedePromocionar) {
                        JButton botonPromocionar = new JButton("Promocionar Usuario");
                        botonPromocionar.addActionListener(e -> {
                            usuario.promocionarUsuario();
                            JOptionPane.showMessageDialog(null, "Usuario promocionado con éxito!");
                        });
                        panelUsuario.add(botonPromocionar);
                    } else {
                        panelUsuario.add(new JLabel("  Este usuario no es promocionable."));
                        panelUsuario.add(new JLabel(" "));
                    }
    
                    // Añadir el panel del usuario al panel general
                    panel.add(panelUsuario);
                }
            }
    
            // Actualizar la interfaz
            panel.revalidate();
            panel.repaint();
        };
    
        // Ejecutar la actualización inicial
        actualizarEstado.run();
    
        // Añadir el panel al JFrame
        frame.add(agregarScroll(panel));
        frame.setVisible(true);
    
        // Actualización cada segundo
        Timer timer = new Timer(1000, e -> actualizarEstado.run());
        timer.start();
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
        
        nombreBoton = "Asignar Entidad";
        botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Trabajador trabajador = (Trabajador) personaAccedida;

                // Comprobar si el trabajador ya tiene una entidad asignada
                if (trabajador.getEntidadAsignada() != null) {
                    trabajador.terminarTrabajo();
                }

                // Seleccionamos la clase de vehículo
                Class<?> claseEntidad = utilidadesMenu.seleccionarClase("entidad");

                if (claseEntidad == null) {
                    // Mostrar mensaje de error si no se seleccionó ninguna clase de vehículo
                    JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna clase de entidad.",
                                                  "Error de selección", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Intentar encontrar una entidad con fallo mecánico de la clase seleccionada
                Entidad entidadPorAsignar = ciudad.encontrarEntidadConFalloMecanico(claseEntidad);

                if (entidadPorAsignar == null) {
                    // Mostrar mensaje si no se encontró ninguna entidad con fallo mecánico
                    JOptionPane.showMessageDialog(null, "No se encontró ninguna entidad con fallo mecánico del tipo seleccionado.",
                                                  "Fallo Mecánico No Encontrado", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Intentar asignar el vehículo al trabajador
                    trabajador.setEntidadAsignada(ciudad, entidadPorAsignar);
                    JOptionPane.showMessageDialog(null, "Entidad asignada correctamente.",
                                                  "Asignación exitosa", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }));
        
        nombreBoton = "Generar Facturas";
        botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarFacturasTrabajador();
            }
        }));
        
        nombreBoton = "Trasladar Vehículo";
        botones.put(nombreBoton, new Boton(nombreBoton, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                iniciarMenuTrasladoVehiculo();
            }
        }));
    }
}
