import java.util.Random;
import java.io.Serializable;

/**
 * Clase para generar valores aleatorios, como direcciones y nombres.
 */
public class RandomGenerator implements Serializable {
    // Instancia de Random para generar números aleatorios
    private Random random;

    // Array de nombres aleatorios en español
    private static final String[] nombres = {
        "Juan", "María", "José", "Ana", "Carlos", "Laura", "Miguel", "Pedro", 
        "Lucía", "Luis", "Isabel", "David", "Carmen", "José Luis", "Patricia", 
        "Francisco", "Raquel", "Sergio", "Elena", "Antonio", "Beatriz", "Aina", 
        "Pau"
    };

    /**
     * Constructor para objetos de la clase RandomGenerator
     */
    public RandomGenerator() {
        random = new Random();
    }

    /**
     * Genera una dirección aleatoria.
     *
     * @return Dirección aleatoria (UP, DOWN, LEFT, RIGHT)
     */
    public EntidadMovil.Direcciones getDireccionRandom() {    
        // Generar un número aleatorio entre 0 y 3 usando el método nextInt
        int x = random.nextInt(4); // Esto devuelve 0, 1, 2 o 3
        
        // Realizar el movimiento según el número aleatorio
        switch (x) {
            case 0:
                return EntidadMovil.Direcciones.UP;
            case 1:
                return EntidadMovil.Direcciones.DOWN;
            case 2:
                return EntidadMovil.Direcciones.RIGHT;
            case 3:
                return EntidadMovil.Direcciones.LEFT;
            default:
                throw new IllegalStateException("Dirección aleatoria no válida: " + x);
        }
    }

    /**
     * Genera un nombre aleatorio de un listado de nombres en español.
     *
     * @return Nombre aleatorio en español
     */
    public String getNombreRandom() {
        // Generar un número aleatorio entre 0 y el tamaño del array de nombres
        int index = random.nextInt(nombres.length);
        
        // Devolver el nombre aleatorio
        return nombres[index];
    }
    
    public Ubicacion getUbicacionLibreRandom(Ciudad ciudad) {
        Ubicacion ubi = new Ubicacion();
        int posX, posY;
        
        // Generar posiciones aleatorias hasta que encontremos una libre
        do {
            posX = random.nextInt(Ciudad.ROWS);
            posY = random.nextInt(Ciudad.COLUMNS);
            
            ubi.setUbicacion(posX, posY);
        } while (ciudad.posicionOcupada(ubi));  // Comprobar si la ubicación está ocupada
        
        return ubi;
    }
}
