public final class Impresora {
    // Definir códigos de color
    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CIAN = "\u001B[36m";
    public static final String BLANCO = "\u001B[37m";

    // Constructor privado para evitar instanciación
    private Impresora() {
        // No se instancia
    }

    // Método estático para imprimir en color negro (modo normal)
    public static void print(String mensaje) {
        System.out.println(mensaje);
    }
    
    // Método estático para imprimir en color rojo
    public static void printRojo(String mensaje) {
        System.out.println(ROJO + mensaje + RESET);
    }

    // Método estático para imprimir en color verde
    public static void printVerde(String mensaje) {
        System.out.println(VERDE + mensaje + RESET);
    }

    // Método estático para imprimir en color amarillo
    public static void printAmarillo(String mensaje) {
        System.out.println(AMARILLO + mensaje + RESET);
    }

    // Método estático para imprimir en color azul
    public static void printAzul(String mensaje) {
        System.out.println(AZUL + mensaje + RESET);
    }

    // Método estático para imprimir en color magenta
    public static void printMagenta(String mensaje) {
        System.out.println(MAGENTA + mensaje + RESET);
    }

    // Método estático para imprimir en color cian
    public static void printCian(String mensaje) {
        System.out.println(CIAN + mensaje + RESET);
    }

    // Método estático para imprimir en color blanco
    public static void printBlanco(String mensaje) {
        System.out.println(BLANCO + mensaje + RESET);
    }
}