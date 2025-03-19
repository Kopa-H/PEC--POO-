import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MenuIniciarSesion extends Menu {
    
    private GestorMenus gestorMenus;
    private JFrame frame;
    
    protected int WINDOW_WIDTH = 400;
    protected int WINDOW_HEIGHT = 500;
    
    private Simulacion simulacion;
    private Ciudad ciudad;

    public MenuIniciarSesion(Simulacion simulacion, Ciudad ciudad, GestorMenus gestorMenus) {
        this.simulacion = simulacion;
        this.ciudad = ciudad;
        this.gestorMenus = gestorMenus;
        this.frame = gestorMenus.frame;
        
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setTitle("Iniciar Sesión");  // Establece el título de la ventana
        this.panel = crearPanel("IniciarSesion");
        panel.setBackground(Color.GRAY);

        agregarBotonesMenu();
        
        agregarBotones(botones, panel);
    }
    
    // Se le pasa como parámetro el tipo de usuario que ingresa al sistema, dandole unas opciones u otras
    private void iniciarMenuSistema(TipoUsuario tipoUsuario, Entidad entidadAccedida) {
        // Se añaden al contenedor de páginas las de cada tipo de usuario
        MenuSistema menuSistema = new MenuSistema(simulacion, ciudad, tipoUsuario, gestorMenus, entidadAccedida);
    
        // Al navegar a un nuevo panel, lo agregamos a la pila
        gestorMenus.panelHistory.push(panel);
        gestorMenus.agregarBotonAtras(menuSistema.panel);
        gestorMenus.cardsPanel.add(menuSistema.panel, "Menu" + tipoUsuario.name());
        gestorMenus.navegarA(menuSistema.panel);
    }
    
    private Entidad identificarse(Class<?> claseEntidad) {
        // Crear un nuevo menú para pedir la ID del usuario
        Menu menu = new Menu();
        JFrame frame = menu.crearNuevaVentana();
        
        // Configurar la ventana
        frame.setTitle("Identificación de Usuario");
        frame.setSize(400, 300);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
        // Crear el panel para el submenú
        JPanel panel = menu.crearPanel("MenuIdentificacionUsuario");
        
        // Crear un HashMap para los botones del submenú
        LinkedHashMap<String, Boton> botones = new LinkedHashMap<>();
        
        // Usar un array para hacer mutable la variable de la ID seleccionada
        final String[] idUsuario = {null};
        final Entidad[] entidadSeleccionada = {null};  // Variable para almacenar la entidad seleccionada
        
        // Crear el campo de texto para ingresar la ID
        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(200, 30));
        
        // Crear y añadir un botón para confirmar la ID
        botones.put("Confirmar ID", new Boton("Confirmar ID", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                idUsuario[0] = idField.getText();
                if (idUsuario[0] != null && !idUsuario[0].isEmpty()) {
                    // Intentar convertir el String a int
                    int idIngresada = -1;
                    try {
                        idIngresada = Integer.parseInt(idUsuario[0]);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "La ID debe ser un número válido.", "Error de Identificación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    boolean usuarioValido = false;
                    // Iterar sobre las entidades para verificar si la ID existe y corresponde a la clase correcta
                    for (Entidad entidad : ciudad.getEntidades()) {
                        if (entidad.getId() == idIngresada && claseEntidad.isInstance(entidad)) {
                            // Si la ID coincide y es del tipo correcto
                            usuarioValido = true;
                            entidadSeleccionada[0] = entidad; // Guardar la entidad seleccionada
                            frame.dispose(); // Cerrar la ventana
                            break;
                        }
                    }
                    
                    if (!usuarioValido) {
                        JOptionPane.showMessageDialog(frame, "ID no válida o no corresponde a un " + (claseEntidad == null ? "Administrador" : claseEntidad.getSimpleName()), "Error de Identificación", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingrese una ID válida.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }));
        
        // Añadir el campo de texto al panel
        panel.add(new JLabel("Ingrese su ID de usuario:"));
        panel.add(idField);
        
        // Añadir el botón al panel
        agregarBotones(botones, panel);
        
        // Añadir el panel al JFrame
        frame.add(agregarScroll(panel));

        // Retorna la entidad seleccionada
        return entidadSeleccionada[0];
    }


    private void agregarBotonesMenu() {
        // Definir roles directamente en el HashMap
        botones.put("Administrador", new Boton("Administrador", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarMenuSistema(TipoUsuario.ADMINISTRADOR, null);
            }
        }));
        botones.put("Usuario Normal", new Boton("Usuario Normal", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Entidad entidadIdentificada = identificarse(Usuario.class);
                System.out.println("Usuario identificado " + entidadIdentificada.toString());
                
                if (entidadIdentificada != null) {
                    iniciarMenuSistema(TipoUsuario.USUARIO_NORMAL, entidadIdentificada);
                }
            }
        }));
        botones.put("Usuario Premium", new Boton("Usuario Premium", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Entidad entidadIdentificada = identificarse(Usuario.class);
                
                if (entidadIdentificada != null) {
                    iniciarMenuSistema(TipoUsuario.USUARIO_PREMIUM, entidadIdentificada);
                }
            }
        }));
        botones.put("Técnico de Mantenimiento", new Boton("Técnico de Mantenimiento", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              Entidad entidadIdentificada = identificarse(Usuario.class);
                
                if (entidadIdentificada != null) {
                    iniciarMenuSistema(TipoUsuario.TECNICO_MANTENIMIENTO, entidadIdentificada);
                }
            }
        }));
        botones.put("Mecánico", new Boton("Mecánico", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               Entidad entidadIdentificada = identificarse(Usuario.class);
                
                if (entidadIdentificada != null) {
                    iniciarMenuSistema(TipoUsuario.MECANICO, entidadIdentificada);
                }
            }
        }));
    }
}
