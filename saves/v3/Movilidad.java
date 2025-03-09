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
        for (int i=0; i < 8; i++) {
            Ubicacion ubi = randomGenerator.getUbicacionLibreRandom(ciudad);
            System.out.println("Se ha añadido una persona en: [" + ubi.getPosX() + " | " + ubi.getPosY() + "]");
            
            Persona persona = new Usuario(ubi.getPosX(), ubi.getPosY());
            ciudad.addElement(persona);
        }
        
        // Añadimos n motos en ubicaciones random
        for (int i=0; i < 6; i++) {
            Ubicacion ubi = randomGenerator.getUbicacionLibreRandom(ciudad);
            System.out.println("Se ha añadido una moto en: [" + ubi.getPosX() + " | " + ubi.getPosY() + "]");
            
            Moto moto = new Moto(ubi.getPosX(), ubi.getPosY());
            ciudad.addElement(moto);
        }
        
        // Añadimos n bases
        for (int i=0; i < 4; i++) {
            Ubicacion ubi = randomGenerator.getUbicacionLibreRandom(ciudad);
            System.out.println("Se ha añadido una base en: [" + ubi.getPosX() + " | " + ubi.getPosY() + "]");
            
            Base base = new Base(ubi.getPosX(), ubi.getPosY());
            
            // Se añade una bicicleta y un patinete a cada base
            Bicicleta bici = new Bicicleta(ubi.getPosX(), ubi.getPosY());
            base.agregarVehiculo(bici);
            System.out.println("Se ha añadido una bicicleta en la base " + base.toString());
            
            Patinete patinete = new Patinete(ubi.getPosX(), ubi.getPosY());
            base.agregarVehiculo(patinete);
            System.out.println("Se ha añadido un patinete en la base " + base.toString());
            
            ciudad.addElement(base);
        }
        
        ciudad.runSimulacion();

    }
}