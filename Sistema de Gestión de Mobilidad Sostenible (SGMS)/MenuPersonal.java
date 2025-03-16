import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MenuPersonal extends Menu {

    private ArrayList<JButton> botones;
    private String tipoUsuario;

    /**
     * Constructor para la clase MenuPersonal
     * @param tipoUsuario Tipo de usuario que se pasará a la interfaz
     */
    public MenuPersonal(String tipoUsuario) {
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
                botones.add(new JButton(" VISUALIZAR ESTADOS BATERÍAS "));
                botones.add(new JButton(" VISUALIZAR ESTADOS MECÁNICOS "));
                break;
            
            case "Usuario Normal":
                agregarOpcionesUsuarioNormal(botones);
                break;
        
            case "Usuario Premium":
                agregarOpcionesUsuarioNormal(botones);  // Hereda las opciones de Usuario Normal
                botones.add(new JButton("Reservar (hasta 20' antes)"));  // Opción específica de Usuario Premium
                break;
                
            case "Técnico de Mantenimiento":
                botones.add(new JButton(" VER VEHICULO ASIGNADO ")); // aBSURDO, ESTO NO SERÁ UN MÉTODO, SE VERÁ EN LA INTEFAZ
                botones.add(new JButton(" ASIGNAR VEHICULO ")); // SE TERMINA EL TRABAJO ANTERIOR Y SE ASIGNA EL TRABAJO
                botones.add(new JButton(" TRABAJAR  "));
                botones.add(new JButton(" DEFINIR PERIODO INACTIVIDAD DEL VEHICULO ")); // EL TIEMPO TRANSCURRIDO ANTES DE QUE SE DESACTIVE LA ALERTA DE BATERIA
                botones.add(new JButton(" TRASLADAR VEHÍCULO  "));
                break;
                
            case "Mecánico":
                botones.add(new JButton(" VER VEHICULO ASIGNADO ")); // aBSURDO, ESTO NO SERÁ UN MÉTODO, SE VERÁ EN LA INTEFAZ
                botones.add(new JButton(" ASIGNAR VEHICULO ")); // SE TERMINA EL TRABAJO ANTERIOR Y SE ASIGNA EL TRABAJO
                botones.add(new JButton(" TRABAJAR  "));
                botones.add(new JButton(" --- "));
                botones.add(new JButton(" GENERAR ÚLTIMA FACTURA "));
                botones.add(new JButton(" DEFINIR PERIODO INACTIVIDAD DEL VEHICULO ")); // EL TIEMPO TRANSCURRIDO ANTES DE QUE SE DESACTIVE LA ALERTA DE FALLO
                break;
                
            case "Administrador":
                botones.add(new JButton(" --- "));
                botones.add(new JButton(" --- "));
                botones.add(new JButton(" --- "));
                botones.add(new JButton(" --- "));
                break;
        }

        // Aplicar la estética y añadir los botones al panel
        for (JButton boton : botones) {
            aplicarEsteticaBoton(boton);
            panel.add(boton);
        }

        // Funcionalidad de los botones
        for (int i = 0; i < botones.size(); i++) {
            int index = i; // Crear una variable local para almacenar el valor de i 
            // Esto es necesario porque el valor de la variable i podría cambiar antes de que se ejecute el ActionListener
            
            JButton boton = botones.get(index); // Acceder al botón correspondiente en la lista
        
            boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Aquí puedes agregar la lógica para cada botón
                    JOptionPane.showMessageDialog(frame, "Opción " + (index + 1) + " seleccionada");
                }
            });
        }

        // Mostrar la ventana
        mostrarVentana();
    }

    private void aplicarEsteticaBoton(JButton boton) {
        // Estética profesional para los botones
        Font font = new Font("Arial", Font.PLAIN, 16);
        boton.setFont(font);
        boton.setBackground(new Color(0, 123, 255)); // Azul para los botones
        boton.setForeground(Color.WHITE); // Color de texto blanco
        boton.setPreferredSize(new Dimension(250, 40)); // Tamaño de los botones
    }
    
    private void agregarOpcionesUsuarioNormal(ArrayList<JButton> botones) {
        botones.add(new JButton("Consultar Vehículos Disponibles"));
        botones.add(new JButton("Alquilar Vehículo"));
        botones.add(new JButton("Alertar Fallo Mecánico"));
        botones.add(new JButton("Visualización Historial de Viajes"));
        botones.add(new JButton("Consultar Vehículos Disponibles"));
        botones.add(new JButton("Visualización Historial de Viajes"));
        botones.add(new JButton("Consultar Vehículos Disponibles"));
        botones.add(new JButton("Consultar Saldo Disponible"));
        botones.add(new JButton("Recargar Saldo"));
        botones.add(new JButton("Consultar Moto Cercana"));
    }
}
