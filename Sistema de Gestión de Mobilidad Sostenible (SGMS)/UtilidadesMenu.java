import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JScrollPane;
import java.util.ArrayList;

/**
 * Write a description of class UtilidadesMenu here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UtilidadesMenu extends Menu
{   
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
    
    public enum TipoInfoMostrada {
        VEHICULOS, BATERIAS, ESTADO_MECANICO, BASES
    }
    
    public void mostrarInfo(Ciudad ciudad, TipoInfoMostrada tipo) {
        Menu menu = new Menu();
        menu.nombre = "Vehículos Disponibles";
        JFrame frame = menu.crearNuevaVentana();
        
        // Crear el panel para el submenú
        JPanel panel = menu.crearPanel();
        
        // Añadir el scroll
        frame.add(agregarScroll(panel));
        
        frame.setVisible(true);
        
        actualizarInfoMostrada(ciudad, panel, tipo);
        
        // Configurar el Timer para actualizar el panel cada 1 segundo (1000 milisegundos)
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                actualizarInfoMostrada(ciudad, panel, tipo);
            }
        });
        
        // Iniciar el Timer
        timer.start();
    }
            
    private void actualizarInfoMostrada(Ciudad ciudad, JPanel panel, TipoInfoMostrada tipo) {
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

    
}
