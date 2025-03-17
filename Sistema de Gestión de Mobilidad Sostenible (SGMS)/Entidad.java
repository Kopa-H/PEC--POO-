import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Write a description of class Entidad here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Entidad implements Serializable
{
    public Ubicacion ubicacion;
    private Color color; // color mostrado en la visualización
    
    // Variable de instancia para almacenar el ID único de cada usuario
    private int id;
    
    // Variable que va aumentando la probabilidad de que los usuarios reporten fallos mecanicos cuanto más baja está
    private int estadoMecanico; // [0-100]
    
    private int edad; // El número de steps que ha vivido la entidads
    
    // Variable registra si la entidad sufre un daño mecánico. En ese caso, el próximo usuario en interactuar con ella lo notificará y el mecánico lo arreglará.
    private boolean tieneFalloMecanico = false;
    private boolean tieneAlertaFalloMecanico = false;
    
    public static final int ESTADO_MECANICO_PERFECTO = 500;
    
    /**
     * Constructor que permite inicializar la EntidadMovil en una ubicación específica.
     * 
     * @param x Coordenada X inicial
     * @param y Coordenada Y inicial
     */
    public Entidad(int x, int y) {
        ubicacion = new Ubicacion(x, y); // Ubicación inicial en las coordenadas (x, y)
    }
    
    /**
     * Método para obtener la ubicación actual de la entidad.
     * 
     * @return La ubicación actual (coordenadas X e Y).
     */
    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void activarAlertaFalloMecanico() {
        tieneAlertaFalloMecanico = true;
    }
    
    public void desactivarAlertaFalloMecanico() {
        tieneAlertaFalloMecanico = false;
    }
    
    public boolean tieneAlertaFalloMecanico() {
        return tieneAlertaFalloMecanico;
    }
    
    public boolean tieneFalloMecanico() {
        return tieneFalloMecanico;
    }
    
    public void deteriorarEstadoMecanico() {        
        if (estadoMecanico >= 0) {
            estadoMecanico--;
        }
    }
    
    public void restaurarEstadoMecanico() {
        estadoMecanico++;
        
        if (estadoMecanico >= 70) {
            desactivarFalloMecanico();
            desactivarAlertaFalloMecanico();
        }
    }
    
    public void sumarEdad() {
        edad++;
    }
    
    public double getProbabilidadFalloMecanico() {
        // Regla de tres inversa: si estadoMecanico es 0, fallo 100%; si es estadoMaximo, fallo 0%.
        double probabilidad = 1 - (double) estadoMecanico / ESTADO_MECANICO_PERFECTO;
        return probabilidad * 100; // Retorna la probabilidad como un porcentaje
    }
    
    // Función que se llama si ocurre un fallo mecánico
    public void activarFalloMecanico() {
        tieneFalloMecanico = true;
        System.out.println("¡Fallo mecánico activado!");
    }
    
    public void desactivarFalloMecanico() {
        tieneFalloMecanico = false;
    }
    
    // Acción que ejecuta el deterioro y posibilidad de fallo
    public void actuar(Ciudad ciudad) {
        Random random = new Random();
        
        this.deteriorarEstadoMecanico();
        
        this.sumarEdad();
        
        // Obtiene la probabilidad de fallo mecánico
        double probabilidadFallo = getProbabilidadFalloMecanico();
        
        // Genera un número aleatorio entre 0 y 100
        double numeroAleatorio = random.nextDouble() * 100;
        
        // Si el número aleatorio es menor que la probabilidad de fallo, produce un fallo
        if (numeroAleatorio < probabilidadFallo) {
            activarFalloMecanico();
        }
    }
    
    // Método para hacer una copia profunda de una entidad
    public static Object deepCopy(Object object) throws IOException, ClassNotFoundException {
        // Serializa el objeto a un flujo de bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);

        // Deserializa el objeto para obtener una copia profunda
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }

    @Override
    public String toString() {
        return "Ubi: " + ubicacion.toString() + "  |  Id: " + id + "  |  Tipo: " + getClass().getSimpleName();
    }
    
    public String toSimpleString() {
        return "[" + getClass().getSimpleName() + "  |  Id: " + this.getId() + "]";
    }
}
