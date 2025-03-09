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
    // instance variables - replace the example below with your own
    private ArrayList<Vehiculo> vehiculos; // Vehículos almacenados dentro de la base

    /**
     * Constructor for objects of class Base
     */
    public Base(int x, int y)
    {
        super(x, y);
        setColor(Color.RED);

        vehiculos = new ArrayList<>();
        
        id = contadorInstancias;
        contadorInstancias++;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void agregarVehiculo(Vehiculo v) {
        vehiculos.add(v);
    }
    
    @Override
    public String toString() {
        // FALTA COMPLETAR
        return "Base con id " + id + "| Bicis = x | Patinetes = y|";
    }
    
    @Override
    public void actuar(Ciudad ciudad) {
        System.out.println("La base no tiene nada que hacer");
    }
}
