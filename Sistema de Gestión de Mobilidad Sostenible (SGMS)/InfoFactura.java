import java.io.Serializable;

public class InfoFactura implements Serializable {
    private Class<? extends Entidad> claseEntidadTrabajada;
    private Trabajador trabajador;
    private Tiempo tiempoTrabajado;
    private double price;
    
    private static final double IVA = 0.21;
    
    InfoFactura(Trabajador trabajador, Entidad entidadTrabajada) {
        this.trabajador = trabajador;
        this.tiempoTrabajado = Tiempo.calcularTiempoEntreTiempos(trabajador.tiempoInicioTrabajo, trabajador.tiempoFinalTrabajo);
        this.claseEntidadTrabajada = entidadTrabajada.getClass();
        this.price = calcularPrecio();
    }
    
    public double calcularPrecio() {
        double precio = trabajador.precioBase + (tiempoTrabajado.hora * trabajador.precioPorHora);
        return precio * (1 + IVA); // Incluye el IVA
    }

    @Override
    public String toString() {
        return "===== FACTURA =====\n" +
               "Trabajador: " + trabajador.getNombre() + "\n" +
               "Entidad Trabajada: " + claseEntidadTrabajada.getSimpleName() + "\n" +
               "Tiempo Trabajado: " + tiempoTrabajado.hora + " horas\n" +
               "Precio Base: " + trabajador.precioBase + "€\n" +
               "Precio por Hora: " + trabajador.precioPorHora + "€\n" +
               "IVA: " + (IVA * 100) + "%\n" +
               "Precio Total: " + price + "€\n" +
               "===================";
    }
}
