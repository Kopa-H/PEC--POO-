import java.awt.Color;

/**
 * Write a description of class Entidad here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Entidad
{
    public Ubicacion ubicacion;
    private Color color; // color mostrado en la visualización
    
    // Variable de instancia para almacenar el ID único de cada usuario
    private int id;
    
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
    
    // Acción que hace a las entidades hacer sus funciones dependiendo de su estado
    public void actuar(Ciudad ciudad) {
        // System.out.println("La entidad no tiene ninguna acción asignada");
    }

    public abstract Entidad clone();  // Método abstracto para obligar a las subclases a implementarlo
    
    // Método para clonar los atributos comunes de Entidad
    protected Entidad cloneCommonAttributes(Entidad entidadCopia) {
        // Clonar la ubicación si existe
        if (this.ubicacion != null) {
            entidadCopia.ubicacion = this.ubicacion.clone();
        }
        
        // Clonar otros atributos comunes
        entidadCopia.id = this.id;
        entidadCopia.color = this.color;

        return entidadCopia;
    }

    @Override
    public String toString() {
        return "Ubi: " + ubicacion.toString() + "  |  Id: " + id + "  |  Tipo: " + getClass().getSimpleName();
    }
}
