import java.awt.Color;

/**
 * Write a description of class Moto here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Moto extends Vehiculo
{
    private static int contadorInstancias = 0;
    
    /**
     * Constructor for objects of class Moto
     */
    public Moto(int posX, int posY) {
        // initialise instance variables
        super(posX, posY);
        setColor(Color.GREEN);
        
        id = contadorInstancias;  // Asignamos el ID único a esta instancia
        contadorInstancias++;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    @Override
    public String toString() {
        return "Moto con id " + id;
    }
}
