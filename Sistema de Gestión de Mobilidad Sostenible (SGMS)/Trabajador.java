
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
        
        // Si no tiene ningún vehículo asignado, busca uno que necesite atención
        if (vehiculoAsignado == null) {
            // Se itera sobre todos los vehículos y se escoge el primero que se encuentre sin batería
            for (Entidad entidad : ciudad.getEntidades()) {
                if (entidad instanceof Vehiculo) {
                    
                    Vehiculo vehiculo = (Vehiculo) entidad;
                    if (this.intentarAsignarVehiculo()) {
                        vehiculoAsignado = vehiculo;
                        System.out.println("[" + toSimpleString() + "] se ha asignado [" + vehiculoAsignado.toSimpleString() + "] para trabajar en él");
                    }                   
                }
            }
        }
    }
    
    abstract public boolean intentarAsignarVehiculo();
}
