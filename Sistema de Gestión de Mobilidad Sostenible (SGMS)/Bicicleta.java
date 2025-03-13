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
    private static final int MAX_DISTANCIA_BATERIA = 300;
    
    /**
     * Constructor for objects of class Bicicleta
     */
    public Bicicleta(int posX, int posY)
    {
        // initialise instance variables
        super(posX, posY, MAX_DISTANCIA_BATERIA);
        setColor(Color.GREEN);
        
        setId(contadorInstancias);
        contadorInstancias++;
    }
    
    @Override
    public Patinete clone() {
        // Llamamos a super.clone() para clonar los atributos de Vehiculo, EntidadMovil y Entidad
        Patinete patineteCopia = (Patinete) super.clone();
    
        // Si Patinete tiene atributos específicos, los clonamos aquí.
        // Actualmente, no se mencionan atributos adicionales en este ejemplo, pero si los hubiera, se añadirían aquí.
    
        return patineteCopia;
    }
}
