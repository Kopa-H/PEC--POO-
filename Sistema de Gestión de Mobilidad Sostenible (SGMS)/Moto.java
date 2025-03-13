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
    private static final int MAX_DISTANCIA_BATERIA = 100;
    
    /**
     * Constructor for objects of class Moto
     */
    public Moto(int posX, int posY) {
        // initialise instance variables
        super(posX, posY, MAX_DISTANCIA_BATERIA);
        setColor(Color.GREEN);
        
        setId(contadorInstancias);  // Asignamos el ID único a esta instancia
        contadorInstancias++;
    }
    
    @Override
    public Moto clone() {
        // Llamamos a super.clone() para clonar los atributos de Vehiculo y superiores
        Moto motoCopia = (Moto) super.clone();
    
        // Aquí, si Moto tiene atributos específicos adicionales, se clonan. 
        // Actualmente, no hay atributos específicos en este ejemplo, pero si los hubiera, los manejaríamos aquí.
    
        return motoCopia;
    }
}
