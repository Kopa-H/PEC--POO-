import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**
 * Se trata de la clase principal del programa, encargada de poner a funcionar el sistema.
 *
 * @author KOPA
 * @version (a version number or a date)
 */
public class Movilidad {

    private static Simulacion simulacion = new Simulacion();
    private static GestorMenus gestorMenus = new GestorMenus();
    
    /**
     * Función principal que ejecuta el sistema.
     * 
     * @param args  Los argumentos de la línea de comandos proporcionados al ejecutar el programa.
     */
    public static void main(String[] args) {

        simulacion.runSimulacion();
    }
}
