import java.io.Serializable;

/**
 * Write a description of class Alquiler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class InfoFactura implements Serializable {
    private Class<? extends Vehiculo> claseVehiculo;
    Trabajador trabajador;
    public Tiempo tiempoTrabajado;
    
    InfoFactura(Trabajador trabajador, Entidad entidadTrabajada) {
        this.trabajador = trabajador;

        this.tiempoTrabajado = Tiempo.calcularTiempoEntreTiempos(trabajador.tiempoInicioTrabajo, trabajador.tiempoFinalTrabajo);

    }
    
    public double calcularPrecio() {
        double precio = 0;
        
        precio += trabajador.precioBase;
        precio += tiempoTrabajado.hora * trabajador.precioPorHora;
        
        return precio;
    }
}
