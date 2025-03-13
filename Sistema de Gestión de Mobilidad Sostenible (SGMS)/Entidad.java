import java.awt.Color;

/**
 * Write a description of class Entidad here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Entidad
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
    
    /**
     * Método para representar la entidad móvil en forma de cadena de texto.
     * 
     * @return Información de la entidad y su ubicación.
     */
    public String getTipoEntidad() {
        return toString();
    }
    
    // Acción que hace a las entidades hacer sus funciones dependiendo de su estado
    public void actuar(Ciudad ciudad) {
        System.out.println("La entidad no tiene ninguna acción asignada");
    }
       
    // Implementación del método clone()
    public Entidad clone() {
        Entidad entidadCopia = new Entidad();
        
        // Clonamos la ubicación de la entidad
        Ubicacion copiaUbicacion = this.ubicacion.clone();
        
        entidadCopia.ubicacion.setUbicacion(copiaUbicacion);
  
        entidadCopia.setId(this.id);  // Copiamos el id (si es necesario copiar otros atributos, hacerlo aquí)
 
        entidadCopia.setColor(this.color);  // Copiar color también si es necesario

        return entidadCopia;
    }
    
    @Override
    public String toString() {
        return "Ubi: " + ubicacion.toString() + "  |  Id: " + id + "  |  Tipo: " + getClass().getSimpleName();
    }
}
