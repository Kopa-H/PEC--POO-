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
        
        // Si no tiene ningún vehículo asignado, busca uno que necesite atención
        if (vehiculoAsignado == null) {
            // Se itera sobre todos los vehículos y se escoge el primero que se encuentre sin batería
            for (Entidad entidad : ciudad.getEntidades()) {
                if (entidad instanceof Vehiculo) {
                    
                    Vehiculo vehiculo = (Vehiculo) entidad;
                    if (vehiculo.getPorcentajeBateria() < 20) {
                        vehiculoAsignado = vehiculo;
                        System.out.println("El técnico de mantenimiento " + toString() + " se ha asignado el vehículo para cargar su batería " + vehiculoAsignado.toString());
                    }
                }
            }
        } else {
            
            // Si el técnico está yendo hacia un vehículo o base, sigue su trayecto
            if (enTrayecto) {
                // seguirTrayecto(ciudad);   
            } else {
                
                // Si el vehículo está en una base le recarga la batería
                if (ciudad.posicionOcupadaPor(vehiculoAsignado.getUbicacion(), Base.class)) {
                    vehiculoAsignado.sumarBateria();
                    System.out.println("El trabajador de mantenimiento " + toString() + " recarga la batería de " + vehiculoAsignado.toString());
                } else {
                    // Si el vehículo NO está en una base y aun no se ha comenzado el trayecto, se lleva a una base
                    
                    // El vehículo sigue a la persona
                    vehiculoAsignado.empezarSeguimiento(this);
                    
                    // La persona planea un trayecto hacia la base más cercana
                    Base base = (Base) ciudad.encontrarEntidadMasCercana(this, Base.class);
                    planearTrayecto(base.getUbicacion());
                }
            }            
        }
    }
}
