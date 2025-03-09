
/**
 * Write a description of class Utilities here.
 *
 * @author Kopa
 * @version (a version number or a date)
 */
public class Utilities {
    public static void gestionarDelay(int simulationSpeed) {
        try {
            // Pausar la ejecución por 500 milisegundos (medio segundo)
            Thread.sleep(simulationSpeed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
