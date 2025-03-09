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

    public void repararVehiculo() {
        
    }
    
    public void emitirFactura() {
        
    }
}
