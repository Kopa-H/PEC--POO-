import java.awt.Color;

/**
 * Write a description of class Bicicleta here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bicicleta extends Vehiculo
{
    private static int contadorInstancias = 0;
    
    /**
     * Constructor for objects of class Bicicleta
     */
    public Bicicleta(int posX, int posY)
    {
        // initialise instance variables
        super(posX, posY);
        setColor(Color.GREEN);
        
        contadorInstancias++;
        id = contadorInstancias;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    
    @Override
    public String toString() {
        return "Bici con id " + id;
    }
}
