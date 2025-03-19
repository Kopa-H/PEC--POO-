import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    private void iniciarMenuSistema(TipoUsuario tipoUsuario, Persona personaAccedida) {
        // Se añaden al contenedor de páginas las de cada tipo de usuario
        MenuSistema menuSistema = new MenuSistema(simulacion, ciudad, tipoUsuario, gestorMenus, personaAccedida);
    
        // Al navegar a un nuevo panel, lo agregamos a la pila
        gestorMenus.panelHistory.push(panel);
        gestorMenus.agregarBotonAtras(menuSistema.panel);
        gestorMenus.cardsPanel.add(menuSistema.panel, "Menu" + tipoUsuario.name());
        gestorMenus.navegarA(menuSistema.panel);
    }
    
    private Persona identificarse(Class<?> clasePersona) {
        Menu menu = new Menu();
        JDialog dialog = new JDialog((Frame) null, "Identificación de Usuario", true); // Ventana modal
        dialog.setSize(400, 300);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        
        // Centrar el diálogo en la pantalla
        dialog.setLocationRelativeTo(null);
    
        // Crear el panel para el submenú
        JPanel panel = menu.crearPanel("MenuIdentificacionUsuario");
    
        // Usar un array para hacer mutable la variable de la ID seleccionada
        final String[] idPersona = {null};
        final Persona[] personaSeleccionada = {null};  // Variable para almacenar la entidad seleccionada
    
        // Crear el campo de texto para ingresar la ID
        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(200, 30));
    
        // Crear y añadir un botón para confirmar la ID
        menu.botones.put("Confirmar ID", new Boton("Confirmar ID", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                idPersona[0] = idField.getText();
                if (idPersona[0] != null && !idPersona[0].isEmpty()) {
                    // Intentar convertir el String a int
                    int idIngresada = -1;
                    try {
                        idIngresada = Integer.parseInt(idPersona[0]);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "La ID debe ser un número válido.", "Error de Identificación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
    
                    boolean personaValida = false;
                    // Iterar sobre las entidades para verificar si la ID existe y corresponde a la clase correcta
                    for (Entidad entidad : ciudad.getEntidades()) {
                        if (entidad instanceof Persona && entidad.getId() == idIngresada && clasePersona.isInstance(entidad)) {
                            // Si la ID coincide y es del tipo correcto
                            personaValida = true;
                            personaSeleccionada[0] = (Persona) entidad; // Guardar la entidad seleccionada
                            dialog.dispose(); // Cerrar la ventana modal
                            break;
                        }
                    }
    
                    if (!personaValida) {
                        JOptionPane.showMessageDialog(dialog, "ID no válida o no corresponde a un " + (clasePersona == null ? "Administrador" : clasePersona.getSimpleName()), "Error de Identificación", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Por favor, ingrese una ID válida.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }));
    
        // Añadir el campo de texto al panel
        panel.add(new JLabel("Ingrese su ID de usuario:"));
        panel.add(idField);
    
        // Añadir el botón al panel
        agregarBotones(menu.botones, panel);
    
        // Añadir el panel al JDialog
        dialog.add(agregarScroll(panel));
    
        // Mostrar el JDialog como ventana modal
        dialog.setVisible(true);
    
        // Retorna la entidad seleccionada después de que el diálogo se cierre
        return personaSeleccionada[0];
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
                Persona personaIdentificada = identificarse(Usuario.class);
                
                if (personaIdentificada != null) {
                    System.out.println("Usuario identificado como [" + personaIdentificada.toString() + "]");
                    iniciarMenuSistema(TipoUsuario.USUARIO_NORMAL, personaIdentificada);
                }
            }
        }));
        botones.put("Usuario Premium", new Boton("Usuario Premium", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Persona personaIdentificada = identificarse(Usuario.class);
                
                if (personaIdentificada != null) {
                    System.out.println("Usuario identificado como [" + personaIdentificada.toString() + "]");
                    iniciarMenuSistema(TipoUsuario.USUARIO_PREMIUM, personaIdentificada);
                }
            }
        }));
        botones.put("Técnico de Mantenimiento", new Boton("Técnico de Mantenimiento", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              Persona personaIdentificada = identificarse(TecnicoMantenimiento.class);
                
                if (personaIdentificada != null) {
                    System.out.println("Usuario identificado como [" + personaIdentificada.toString() + "]");
                    iniciarMenuSistema(TipoUsuario.TECNICO_MANTENIMIENTO, personaIdentificada);
                }
            }
        }));
        botones.put("Mecánico", new Boton("Mecánico", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               Persona personaIdentificada = identificarse(Mecanico.class);
                
                if (personaIdentificada != null) {
                    System.out.println("Usuario identificado como [" + personaIdentificada.toString() + "]");
                    iniciarMenuSistema(TipoUsuario.MECANICO, personaIdentificada);
                }
            }
        }));
    }
}
