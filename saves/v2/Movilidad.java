/**
 * Se trata de la clase principal del programa, encargada de poner a funcionar el sistema.
 *
 * @author KOPA
 * @version (a version number or a date)
 */
public class Movilidad {
    
    /**
     * Función principal que ejecuta el sistema.
     * @param args  Los argumentos de la línea de comandos proporcionados al ejecutar el programa.
     */
    public static void main(String[] args) {
        RandomGenerator randomGenerator = new RandomGenerator();
        
        // Crear una instancia de la clase Ciudad y mostrar su interfaz gráfica.
        Ciudad ciudad = new Ciudad();
        
        // Añadimos n personas en ubicaciones random
        for (int i=0; i < 3; i++) {
            Ubicacion ubi = randomGenerator.getUbicacionRandom();
            System.out.println("Ubicacion para persona: " + ubi.getPosX() + " | " + ubi.getPosY());
            
            Persona persona = new Usuario(ubi.getPosX(), ubi.getPosY());
            ciudad.addElement(persona);
        }
        
        // Añadimos n motos en ubicaciones random
        for (int i=0; i < 1; i++) {
            Ubicacion ubi = randomGenerator.getUbicacionRandom();
            System.out.println("Ubicacion para moto: " + ubi.getPosX() + " | " + ubi.getPosY());
            
            Moto moto = new Moto(ubi.getPosX(), ubi.getPosY());
            ciudad.addElement(moto);
        }
        
        // Añadimos n bases
        for (int i=0; i < 1; i++) {
            Ubicacion ubi = randomGenerator.getUbicacionRandom();
            System.out.println("Ubicacion para base: " + ubi.getPosX() + " | " + ubi.getPosY());
            
            Base base = new Base(ubi.getPosX(), ubi.getPosY());
            ciudad.addElement(base);
        }
        
        ciudad.runSimulacion();

    }
}