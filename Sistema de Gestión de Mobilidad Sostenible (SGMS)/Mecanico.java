import java.awt.Color;

/**
 * Write a description of class TrabajadorMecanico here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Mecanico extends Trabajador
{
    /**
     * Constructor for objects of class TrabajadorMecanico
     */
    public Mecanico(int posX, int posY)
    {
        super(posX, posY);
        setColor(Color.CYAN);
    }
    
    @Override
    public boolean intentarAsignarVehiculo(Entidad entidad) {
        if ((entidad instanceof Vehiculo || entidad instanceof Base) && entidad.tieneAlertaFalloMecanico()) {
            return true;
        }
        
        return false;
    }
    
    public void trabajar() {
        entidadAsignada.restaurarEstadoMecanico();
        
        if (!(entidadAsignada.tieneFalloMecanico())) {
            terminarTrabajo();
        }
    }

    public void emitirFactura() {
        
    }
}
