import java.io.Serializable;

public class InfoAlquiler implements Serializable {
    private Usuario usuario;
    
    private Vehiculo vehiculoAlquilado;
    
    private int mesAlquiler;
    private Tiempo tiempoAlquiler;
    
    InfoAlquiler(Usuario usuario, Vehiculo vehiculoAlquilado) {
        this.mesAlquiler = usuario.tiempoFinalAlquiler.mes;
        this.tiempoAlquiler = Tiempo.calcularTiempoEntreTiempos(usuario.tiempoInicioAlquiler, usuario.tiempoFinalAlquiler);
        this.vehiculoAlquilado = vehiculoAlquilado;
    }
    
    public int getMesAlquiler() {
        return mesAlquiler;
    }
    
    public Class<? extends Vehiculo> getClaseVehiculoAlquilado() {
        return vehiculoAlquilado.getClass();
    }

    @Override
    public String toString() {
        return "===== ALQUILER =====\n" +
               "Usuario: " + usuario.getNombre() + "\n" +
               "Mes alquiler: " + mesAlquiler + "\n" +
               "Entidad Alquilada: " + vehiculoAlquilado.toSimpleString() + "\n" +
               "=====================";
    }
}
