import java.awt.Color;
import java.io.*;
import java.util.ArrayList;

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
    
    private boolean tieneAlertaFalloMecanico = false;
    
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
    
    // Acción que hace a las entidades hacer sus funciones dependiendo de su estado
    public void actuar(Ciudad ciudad) {
        // System.out.println("La entidad no tiene ninguna acción asignada");
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
