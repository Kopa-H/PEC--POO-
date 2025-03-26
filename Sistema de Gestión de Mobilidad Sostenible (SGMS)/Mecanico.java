import java.awt.Color;

/**
 * Write a description of class TrabajadorMecanico here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Mecanico extends Trabajador {
    
    private static int contadorInstancias = 0;
    public static Color colorClase = Color.CYAN;
    
    /**
     * Constructor for objects of class TrabajadorMecanico
     */
    public Mecanico(int posX, int posY)
    {
        super(posX, posY);
        setColor(colorClase);
        
        setId(contadorInstancias);
        contadorInstancias++;
    }
    
    @Override
    public boolean intentarAsignarEntidad(Ciudad ciudad, Entidad entidad) {
        if ((entidad instanceof Vehiculo || entidad instanceof Base) && entidad.tieneAlertaFalloMecanico()) {
            
            if (!ciudad.existeTrabajadorConEntidadAsignada(entidad)) {
                return true;
            }
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
