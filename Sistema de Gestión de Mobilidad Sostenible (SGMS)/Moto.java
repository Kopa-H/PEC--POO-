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
    public Entidad clone() {
        Moto motoCopia = new Moto(this.getUbicacion().getPosX(), this.getUbicacion().getPosY());    
        motoCopia = (Moto) super.clone(motoCopia);
        
        return motoCopia;
    }

}
