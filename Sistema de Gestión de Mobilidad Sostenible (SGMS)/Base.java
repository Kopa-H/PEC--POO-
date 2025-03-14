import java.util.ArrayList;
import java.awt.Color;

/**
 * Write a description of class Base here.
 *
 * @author Kopa
 * @version (a version number or a date)
 */
public class Base extends EntidadFija
{
    private static int contadorInstancias = 0;

    public ArrayList<Vehiculo> vehiculosDisponibles; // Vehículos almacenados dentro de la base disponibles para ser usados
    
    public ArrayList<Vehiculo> vehiculosInhabilitados; // Aquí están los vehículos sin batería (el técnico debe recargarla) y los averiados

    /**
     * Constructor for objects of class Base
     */
    public Base(int x, int y)
    {
        super(x, y);
        setColor(Color.RED);

        vehiculosDisponibles = new ArrayList<>();
        vehiculosInhabilitados = new ArrayList<>();
        
        setId(contadorInstancias);
        contadorInstancias++;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void agregarVehiculoDisponible(Vehiculo v) {
        vehiculosDisponibles.add(v);
    }
    
    // Sirve para sacar el numero de vehiculos disponibles de un tipo que tiene la base
    private int getNumeroVehiculosDisponibles(Class<?> claseVehiculo) {
        int num = 0;
        
        for (Vehiculo vehiculo : vehiculosDisponibles) {
            // Comparamos el tipo de la clase del vehículo con el tipo proporcionado
            if (vehiculo.getClass().equals(claseVehiculo)) {
                num++;
            }
        }
        
        return num; // Recuerda devolver el resultado
    }
    
    // Sirve para sacar el numero de vehiculos inhabilitados de un tipo que tiene la base
    private int getNumeroVehiculosInhabilitados(Class<?> claseVehiculo) {
        int num = 0;
        
        for (Vehiculo vehiculo : vehiculosInhabilitados) {
            // Comparamos el tipo de la clase del vehículo con el tipo proporcionado
            if (vehiculo.getClass().equals(claseVehiculo)) {
                num++;
            }
        }
        
        return num; // Recuerda devolver el resultado
    }
    
    @Override
    public void actuar(Ciudad ciudad) {
        // System.out.println("La base no tiene nada que hacer");
    }
        
    @Override
    public String toString() {
       return String.format("%s  |  vehiculosDisponibles: [motos = %d | bicis = %d | patinetes = %d]  |  vehiculosInhabilitados: [motos = %d | bicis = %d | patinetes = %d]",
            super.toString(), getNumeroVehiculosDisponibles(Moto.class), getNumeroVehiculosDisponibles(Bicicleta.class), getNumeroVehiculosDisponibles(Patinete.class),
            getNumeroVehiculosInhabilitados(Moto.class), getNumeroVehiculosInhabilitados(Bicicleta.class), getNumeroVehiculosInhabilitados(Patinete.class));
    }
}
