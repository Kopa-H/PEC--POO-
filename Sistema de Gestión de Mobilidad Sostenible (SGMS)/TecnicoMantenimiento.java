import java.awt.Color;

/**
 * Write a description of class TrabajadorMantenimiento here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TecnicoMantenimiento extends Trabajador
{
    
    /**
     * Constructor for objects of class TrabajadorMantenimiento
     */
    public TecnicoMantenimiento(int posX, int posY)
    {
        super(posX, posY);
        setColor(Color.MAGENTA);
    }

    @Override
    public void actuar(Ciudad ciudad) {        
         // Asciende en la jerarquía de clases y hereda las funciones de actuación de EntidadMovil
        super.actuar(ciudad);
        
        // Si no tiene ningún vehículo asignado, busca uno que necesite atención
        if (vehiculoAsignado == null) {
            // Se itera sobre todos los vehículos y se escoge el primero que se encuentre sin batería
            for (Entidad entidad : ciudad.getEntidades()) {
                if (entidad instanceof Vehiculo) {
                    
                    Vehiculo vehiculo = (Vehiculo) entidad;
                    if (vehiculo.getPorcentajeBateria() < 20) {
                        vehiculoAsignado = vehiculo;
                        System.out.println("[" + toSimpleString() + "] se ha asignado [" + vehiculoAsignado.toSimpleString() + "] para cargar su batería");
                    }
                }
            }
        } else {
            // Si hay un vehículo asignado
            
            // Si el técnico NO está yendo a ningún sitio
            if (!enTrayecto) {
                // Si el vehículo está en una base
                if (ciudad.posicionOcupadaPor(vehiculoAsignado.getUbicacion(), Base.class)) {
                    
                    // Recarga la batería del vehículo
                    vehiculoAsignado.sumarBateria();
                    
                    // Si se ha cargado la batería por completo, el trabajador abandona su labor
                    if (((Vehiculo) vehiculoAsignado).getPorcentajeBateria() >= 100) {
                        terminarTrabajo();
                        
                        // Si el vehículo recargado es una moto
                        if (vehiculoAsignado instanceof Moto) {
                            // El vehículo que ha sido cargado se mueve afuera de la base para que sea visible
                            vehiculoAsignado.moverRandom(ciudad);
                        }
                    }

                } else if (ciudad.posicionOcupadaPor(vehiculoAsignado.getUbicacion(), Vehiculo.class)) {                   
                    // El vehículo sigue a la persona
                    vehiculoAsignado.empezarSeguimiento(this);
                    
                    // La persona planea un trayecto hacia la base más cercana
                    Base base = (Base) ciudad.encontrarEntidadMasCercana(this, Base.class);
                    planearTrayecto(base.getUbicacion(), base);
                    
                } else {
                    // Si el vehículo NO está en una base y aun no se ha comenzado el trayecto planea un trayecto hacia el vehículo
                    planearTrayecto(vehiculoAsignado.getUbicacion(), vehiculoAsignado);
                }
            }            
        }
    }
    
    public void terminarTrabajo() {
        vehiculoAsignado = null;   
    }
}
