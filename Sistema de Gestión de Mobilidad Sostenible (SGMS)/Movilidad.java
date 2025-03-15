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

    private static Ciudad ciudad = new Ciudad();
    private static Simulacion simulacion = new Simulacion(ciudad);
    
    private static final int VEHICULOS_INICIALES_EN_BASE = 20;
    
    /**
     * Método para agregar n entidades de un tipo específico en ubicaciones aleatorias.
     * 
     * @param cantidad La cantidad de entidades a agregar.
     * @param tipoEntidad El tipo de entidad a agregar (por ejemplo, Usuario, Moto, Base).
     * @param ciudad La ciudad en la que se agregan las entidades.
     */
    private static <T extends Entidad> void agregarEntidad(int cantidad, Class<T> tipoEntidad, Ciudad ciudad) {
        RandomGenerator randomGenerator = new RandomGenerator();
        
        for (int i = 0; i < cantidad; i++) {
            Ubicacion ubi = randomGenerator.getUbicacionLibreRandom(ciudad);
            System.out.println("Se ha añadido una " + tipoEntidad.getSimpleName() + " en:" + ubi.toString());
            
            try {
                // Crear la entidad de acuerdo con el tipo y la ubicación
                T entidad = tipoEntidad.getConstructor(int.class, int.class).newInstance(ubi.getPosX(), ubi.getPosY());
                
                // Si la entidad es una Base, se añaden vehículos
                if (entidad instanceof Base) {
                    Base base = (Base) entidad;
                    
                    for (int j = 0; j < VEHICULOS_INICIALES_EN_BASE; j++) {
                        // Añadir bici
                        Bicicleta bici = new Bicicleta(ubi.getPosX(), ubi.getPosY());
                        base.agregarVehiculoDisponible(bici);
                        ciudad.addElement(bici); 
                        System.out.println("Se ha añadido una bici a la base.");

                        // Añadir patinete
                        Patinete patinete = new Patinete(ubi.getPosX(), ubi.getPosY());
                        base.agregarVehiculoDisponible(patinete);
                        ciudad.addElement(patinete); 
                        System.out.println("Se ha añadido un patinete a la base.");
                    }
                }

                // Añadir la entidad a la ciudad
                ciudad.addElement((Entidad) entidad);  
                Color color = entidad.getColor();
                simulacion.mostrarEntidad(entidad.getUbicacion(), color);
            
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Función principal que ejecuta el sistema.
     * 
     * @param args  Los argumentos de la línea de comandos proporcionados al ejecutar el programa.
     */
    public static void main(String[] args) {
        
        // Añadimos n usuarios
        agregarEntidad(6, Usuario.class, ciudad);
        
        // Añadimos n trabajadores de mantenimiento
        agregarEntidad(1, TecnicoMantenimiento.class, ciudad);
        
        // Añadimos n trabajadores de mecánica
        agregarEntidad(1, Mecanico.class, ciudad);
        
        // Añadimos n motos en ubicaciones aleatorias
        agregarEntidad(5, Moto.class, ciudad);

        // Añadimos n bases con vehículos (en este caso, ya se añadieron en el método anterior)
        agregarEntidad(3, Base.class, ciudad);

        simulacion.runSimulacion(ciudad);
    }
}
