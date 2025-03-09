
/**
 * Write a description of class TrabajadorMantenimiento here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TrabajadorMantenimiento extends Trabajador
{
    /**
     * Constructor for objects of class TrabajadorMantenimiento
     */
    public TrabajadorMantenimiento()
    {
        super();

    }

    @Override
    public void actuar(Ciudad ciudad) {    
        // Se itera sobre todos los vehículos y se escoge el primero que se encuentre sin batería
        for (Entidad entidad : ciudad.getEntidades()) {
            if (entidad instanceof Vehiculo) {
                
                Vehiculo vehiculo = (Vehiculo) entidad;
                if (vehiculo.getPorcentajeBateria() < 20) {
                    
                }
            }
        }
    }
        
}
