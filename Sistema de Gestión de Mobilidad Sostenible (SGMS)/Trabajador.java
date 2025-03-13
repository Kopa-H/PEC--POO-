
/**
 * Write a description of class Trabajador here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
abstract public class Trabajador extends Persona
{
    // Almacena el vehículo que el trabajador tiene asignado
    Vehiculo vehiculoAsignado;
    
    /**
     * Constructor for objects of class Trabajador
     */
    public Trabajador(int posX, int posY)
    {
        super(posX, posY);
    }
    
    public boolean isTrabajando() {
        if (vehiculoAsignado != null) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        String str = super.toString();  // Llamamos al toString de Trabajador
        if (vehiculoAsignado != null) {
            str += "  |  Vehículo asignado: (" + vehiculoAsignado + " )";  // Añadimos el vehículo si está asignado
        }
        return str;
    }
    
    @Override
    public void actuar(Ciudad ciudad) {
        super.actuar(ciudad);
    }
}
