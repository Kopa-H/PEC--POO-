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
        
        setId(contadorInstancias);
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
    
    // Sirve para sacar el numero de vehiculos de un tipo que tiene la base
    private int getNumeroVehiculos(Class<?> claseVehiculo) {
        int num = 0;
        
        for (Vehiculo vehiculo : vehiculos) {
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
    public Entidad clone() {
        Base baseCopia = new Base(this.getUbicacion().getPosX(), this.getUbicacion().getPosY());
        baseCopia = (Base) super.clone(baseCopia);
        
        // Clonamos la lista de vehículos
        baseCopia.vehiculos = new ArrayList<>();
        for (Vehiculo v : this.vehiculos) {
            baseCopia.agregarVehiculo(v.clone());
        }

        return baseCopia;
    }

    
    @Override
    public String toString() {
       return String.format("%s  |  Vehiculos: [motos = %d | bicis = %d | patinetes = %d]",
            super.toString(), getNumeroVehiculos(Moto.class), getNumeroVehiculos(Bicicleta.class), getNumeroVehiculos(Patinete.class));
    }
}
