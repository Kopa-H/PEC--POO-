
/**
 * Write a description of class Alquiler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class InfoAlquiler {
    private int mes;
    private Class<? extends Vehiculo> claseVehiculo;
    
    InfoAlquiler(Tiempo tiempo, Vehiculo vehiculoEscogido) {
        this.mes = tiempo.mes;
        this.claseVehiculo = vehiculoEscogido.getClass();
    }
}
