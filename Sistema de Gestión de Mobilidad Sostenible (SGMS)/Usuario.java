import java.awt.Color;

/**
 * Write a description of class Usuario here.
 *
 * @author Kopa
 * @version (a version number or a date)
 */
public class Usuario extends Persona {
    private static int contadorInstancias = 0;
    public static Color colorClase = Color.ORANGE;
    
    // instance variables - replace the example below with your own
    private double saldo;
    public enum TipoMembresia {
        ESTANDAR, PREMIUM
    }
    
    private TipoMembresia tipoMembresia;
    
    /**
     * Constructor for objects of class Usuario
     */
    public Usuario(int posX, int posY) {
        // initialise instance variables
        super(posX, posY);
        setColor(colorClase);
        saldo = 0;
        tipoMembresia = TipoMembresia.ESTANDAR;
        
        setId(contadorInstancias);  // Asignamos el ID único a esta instancia
        contadorInstancias++;
    }
    
    @Override
    public void actuar(Ciudad ciudad) {
        super.actuar(ciudad);
        
        if (!enTrayecto && !isSiguiendoEntidad()) {
            intentarPlanearTrayecto(ciudad, Base.class);
            intentarPlanearTrayecto(ciudad, Moto.class);
        }
    }
    
    public void reservarVehiculo() {
        if (tipoMembresia != TipoMembresia.PREMIUM) {
            return;
        }
    }

    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public void recargarSaldo(double x) {
        if (x > 0) {
            saldo += x;
        }
    }
    
    public TipoMembresia getTipoMembresia() {
        return tipoMembresia;
    }
    
    public void promocionarUsuario() {
        tipoMembresia = TipoMembresia.PREMIUM;
    }
    
    /**
     * Informar de un problema con un vehículo o con una base de bicicletas o patinetes
     */
    public void alertarFalloMecanico(Entidad entidad) {
        if (!getEntidadSeguida().tieneAlertaFalloMecanico() && (entidad instanceof Vehiculo || entidad instanceof Base)) {
            Impresora.printColorClase(this.getClass(), "\n" + this.toSimpleString() + " ha activado la alerta de fallo mecánico de " + getEntidadSeguida().toSimpleString());
            entidad.activarAlertaFalloMecanico();
        }
    }
}
