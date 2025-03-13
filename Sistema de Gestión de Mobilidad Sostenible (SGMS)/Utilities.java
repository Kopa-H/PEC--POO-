
/**
 * Write a description of class Utilities here.
 *
 * @author Kopa
 * @version (a version number or a date)
 */
public class Utilities {
    public static void gestionarDelay(int simulationSpeed) {
        try {
            // Invertir la velocidad: mayor valor = menor delay y viceversa
            int adjustedSpeed = Math.max(1, Simulacion.MAX_SIMULATION_SPEED - simulationSpeed);
            
            // Pausar la ejecución según la velocidad ajustada
            Thread.sleep(adjustedSpeed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}