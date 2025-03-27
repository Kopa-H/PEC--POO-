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
        
        // Si al aumentarle la edad en la superclase coincide con un día de pago, paga
        if (edad % DIAS_ENTRE_PAGOS == 0) {
            pagarTasas();
        }
        
        if (!enTrayecto && !isSiguiendoEntidad()) {
            intentarPlanearTrayecto(ciudad, Base.class);
            intentarPlanearTrayecto(ciudad, Moto.class);
        }
    }
    
    private void pagarTasas() {
        if (saldo - PRECIO_EUROS_TASAS < 0) {
            Impresora.printRojo("\nEl usuario " + this.toSimpleString() + " no ha podido pagar el importe de sus tasas");
    
            // Calcular la cantidad necesaria para recargar el saldo
            double cantidadNecesaria = PRECIO_EUROS_TASAS - saldo;
            
            // Generar una cantidad aleatoria entre [cantidadNecesaria, 2 * cantidadNecesaria]
            double cantidadRecargar = cantidadNecesaria + (Math.random() * cantidadNecesaria); // Se añade algo más para la próxima con algo de aleatoriedads
            
            // Recargar el saldo con la cantidad generada
            recargarSaldo(cantidadRecargar);
            
            Impresora.printVerde("\n" + this.toSimpleString() + " ha recargado " + cantidadRecargar + "€");
            
            // Se llama a la propia función para proceder con el pago de las tasas
            pagarTasas();
        
        } else {
            // Si tiene suficiente saldo, paga las tasas
            saldo -= PRECIO_EUROS_TASAS;
            Impresora.printVerde("\nEl usuario " + toSimpleString() + " ha pagado las tasas de " + PRECIO_EUROS_TASAS + "€.");
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
