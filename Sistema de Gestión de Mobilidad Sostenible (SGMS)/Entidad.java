import java.awt.Color;

/**
 * Write a description of class Entidad here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
abstract public class Entidad
{
    Ubicacion ubicacion;
    private Color color; // color mostrado en la visualización
        // Variable de instancia para almacenar el ID único de cada usuario
    public int id;
    
    public Entidad() {       
        ubicacion = new Ubicacion(0, 0); // Ubicación inicial en (0,0)
    }
    
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

    /**
     * Método para establecer manualmente la ubicación de la entidad.
     * 
     * @param x Nueva coordenada X
     * @param y Nueva coordenada Y
     */
    public void setUbicacion(int x, int y) {
        this.ubicacion.setPosX(x);
        this.ubicacion.setPosY(y);
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
    
    /**
     * Método para representar la entidad móvil en forma de cadena de texto.
     * 
     * @return Información de la entidad y su ubicación.
     */
    public String getTipoEntidad() {
        return toString();
    }
    
    // Acción que hace a las entidades hacer sus funciones dependiendo de su estado
    abstract public void actuar(Ciudad ciudad);
    
    @Override
    public String toString() {
        return "  Id: " + id + "  |  Tipo: " + getClass().getSimpleName();
    }
}
