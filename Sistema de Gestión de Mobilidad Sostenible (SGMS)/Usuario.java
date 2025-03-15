import java.awt.Color;

/**
 * Write a description of class Usuario here.
 *
 * @author Kopa
 * @version (a version number or a date)
 */
public class Usuario extends Persona {
    private static int contadorInstancias = 0;
    
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
        setColor(Color.ORANGE);
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
        if (saldo > 0) {
            saldo += x;
        }
    }
    
    public TipoMembresia getTipoMembresia() {
        return tipoMembresia;
    }
    
    /**
     * Informar de un problema con un vehículo o con una base de bicicletas o patinetes
     */
    public void informarFalloMecanico(Entidad entidad) {
        if (entidad instanceof Vehiculo || entidad instanceof Base) {
            entidad.activarAlertaFalloMecanico();
        }
    }
}
