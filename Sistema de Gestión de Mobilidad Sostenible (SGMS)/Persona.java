/**
 * Write a description of class Persona here.
 *
 * @author Kopa
 * @version (a version number or a date)
 */
public abstract class Persona extends EntidadMovil
{
    // instance variables - replace the example below with your own
    private String nombre;
    private RandomGenerator randomGenerator = new RandomGenerator();
    private static int contadorInstancias = 0;

    /**
     * Constructor for objects of class Persona
     */
    public Persona(int posX, int posY)
    {
        // Se inicializa el nombre de la Persona como " "
        super(posX, posY);
        nombre = randomGenerator.getNombreRandom();
    }
    
    /**
     * Constructor for objects of class Persona
     */
    public Persona()
    {
        // Se inicializa el nombre de la Persona como " "
        super();
        nombre = randomGenerator.getNombreRandom();
    }
    
    /**
     * Sirve para otorgarle un nombre a una Persona
     *
     * @param  nombre es el nombre que se le asignará a la Persona
     * @return   the result produced by sampleMethod
     */
    void setNombre(String nombre) {
        this.nombre = nombre;   
    }
    
    /**
     * Sirve para obtener el nombre de una persona
     *
     * @return   the result produced
     */
    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString() {
        return super.toString() + "  |  Nombre: " + nombre;
    }
}
