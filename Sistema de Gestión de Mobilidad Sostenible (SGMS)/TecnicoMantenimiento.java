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
        
        if (entidadAsignada != null && !enTrayecto) {
            
   
        }
    }
    
    public void trabajar() {
        
        // El técnico de mantenimiento sólo se encarga de los vehículos
        if (entidadAsignada instanceof Vehiculo vehiculoAsignado) {
            // Recarga la batería del vehículo
            vehiculoAsignado.sumarBateria();
        
            // Si se ha cargado la batería por completo, el trabajador abandona su labor
            if (vehiculoAsignado.getPorcentajeBateria() >= 100) {
                terminarTrabajo();
            }
        }
    }
    
    @Override
    public boolean intentarAsignarVehiculo(Entidad entidad) {
        
        if (!(entidad instanceof Vehiculo vehiculo)) {
            return false;
        }
        
        if (vehiculo.getPorcentajeBateria() < 20) {
            return true;         
        }
        return false;
    }
}
