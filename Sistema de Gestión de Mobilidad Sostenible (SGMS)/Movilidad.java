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

    private static Ciudad ciudad;
    private static Simulacion simulacion;
    private static GestorMenus gestorMenus;
    
    /**
     * Función principal que ejecuta el sistema.
     * 
     * @param args  Los argumentos de la línea de comandos proporcionados al ejecutar el programa.
     */
    public static void main(String[] args) {
        
        ciudad = new Ciudad();
        
        simulacion = new Simulacion(ciudad);
        
        gestorMenus = new GestorMenus(simulacion, ciudad);

        simulacion.runSimulacion();
    }
}
