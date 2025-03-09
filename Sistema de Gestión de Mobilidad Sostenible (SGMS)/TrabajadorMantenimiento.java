
/**
 * Write a description of class TrabajadorMantenimiento here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TrabajadorMantenimiento extends Trabajador
{
    // Almacena el vehículo que el trabajador de mantenimiento tiene asignado
    Vehiculo vehiculoAsignado;
    
    /**
     * Constructor for objects of class TrabajadorMantenimiento
     */
    public TrabajadorMantenimiento()
    {
        super();

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
                    }
                }
            }
        } else {
            
            // Si el vehículo está en una base le recarga la batería
            if (ciudad.posicionOcupadaPor(vehiculoAsignado.getUbicacion(), Base.class)) {
                vehiculoAsignado.sumarBateria();
                System.out.println("El trabajador de mantenimiento " + toString() + " recarga la batería de " + vehiculoAsignado.toString());
            } else {
                // Si el vehículo NO está en una base, se lleva a una base
                
                // El vehículo sigue a la persona
                vehiculoAsignado.empezarSeguimiento(this);
                
                // La persona planea un trayecto hacia la base más cercana
                Base base = (Base) ciudad.encontrarEntidadMasCercana(this, Base.class);
                planearTrayecto(base.getUbicacion());
            }
            
            

        }
    }
        
}
