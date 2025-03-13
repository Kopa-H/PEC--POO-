import java.awt.Color;

/**
 * Write a description of class Patinete here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Patinete extends Vehiculo {
    private static int contadorInstancias = 0;
    private static final int MAX_DISTANCIA_BATERIA = 300;
    
    /**
     * Constructor for objects of class Patinete
     */
    public Patinete(int posX, int posY) {
        // initialise instance variables
        super(posX, posY, MAX_DISTANCIA_BATERIA);
        setColor(Color.GREEN);
        
        setId(contadorInstancias);
        contadorInstancias++;
    }
    
    @Override
    public Entidad clone() {
        Patinete patineteCopia = new Patinete(this.getUbicacion().getPosX(), this.getUbicacion().getPosY());    
        patineteCopia = (Patinete) super.clone(patineteCopia);
        
        return patineteCopia;
    }
}
