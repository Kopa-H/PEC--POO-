import java.util.ArrayList;
import java.awt.Color;

/**
 * Write a description of class Vehiculo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
abstract public class Vehiculo extends EntidadMovil
{
    // Nivel de batería del vehículo (de 0 a 100)
    private int nivelBateria;
    
    private ArrayList<FalloMecanico> fallosMecanicos;
    
    public enum TipoVehiculo {
        MOTO, BICI, PATINETE;
    }

    /**
     * Constructor for objects of class Vehiculo
     */
    public Vehiculo(int posX, int posY)
    {
        // initialise instance variables
        super(posX, posY);
        
        fallosMecanicos = new ArrayList<>();
        nivelBateria = 100;
    }
    
 
    
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int getBateria() {
        return nivelBateria;
    }
}
