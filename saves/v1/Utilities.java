
/**
 * Write a description of class Utilities here.
 *
 * @author Kopa
 * @version (a version number or a date)
 */
public class Utilities {
    public static int millisBetweenUpdates = 1000;
    
    public static void gestionarDelay() {
        try {
            // Pausar la ejecución por 500 milisegundos (medio segundo)
            Thread.sleep(millisBetweenUpdates);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
