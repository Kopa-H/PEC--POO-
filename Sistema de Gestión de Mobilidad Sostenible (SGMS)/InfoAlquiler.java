import java.io.Serializable;

public class InfoAlquiler implements Serializable {
    private int mes;
    private Class<? extends Vehiculo> claseVehiculo;
    
    InfoAlquiler(Tiempo tiempo, Vehiculo vehiculoEscogido) {
        this.mes = tiempo.mes;
        this.claseVehiculo = vehiculoEscogido.getClass();
    }
    
    public int getMes() {
        return mes;
    }
    
    public Class<? extends Vehiculo> getClaseVehiculo() {
        return claseVehiculo;
    }

    @Override
    public String toString() {
        return "===== ALQUILER =====\n" +
               "Mes: " + mes + "\n" +
               "Vehículo Alquilado: " + claseVehiculo.getSimpleName() + "\n" +
               "=====================";
    }
}
