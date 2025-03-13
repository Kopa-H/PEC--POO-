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

    /**
     * Muestra en la interfaz los vehículos disponibles y sus niveles de bateria
     *  - Coordenadas de todas las motos.
     *  - ID y coordenadas de las bases con bicis o patinetes.
     *
     */
    public void consultarVehiculosDisponibles() {
        
        // Se itera sobre la lista de motos
            // Se muestran las motos disponibles, su tipo, sus coordenadas y su nivel de batería
        
        // Se itera sobre la lista de bases
            // Se muestra la base en caso de que tenga alguna bici o patinete disponible
            // Se muestra los vehículos disponibles que existen, su tipo, sus coordenadas y su nivel de batería
        
    }
    
    /**
     * Alquila un vehículo
     *
     */
    public void alquilarVehiculo(Ubicacion locInicial, Ubicacion locFinal, Vehiculo.TipoVehiculo tipoV) {
        if (tipoV == Vehiculo.TipoVehiculo.BICI || tipoV == Vehiculo.TipoVehiculo.PATINETE) {
            // Base baseInicial = consultarBase();
            // Base baseFinal = consultarBase();
        } else if (tipoV == Vehiculo.TipoVehiculo.MOTO) {
            // Ubicacion coords = consultarCoordsViaje(); 
        }
    }
    
    public void reservarVehiculo() {
        if (tipoMembresia != TipoMembresia.PREMIUM) {
            return;
        }
    }
    
    /**
     * Informar de un problema con un vehículo o con una base de bicicletas o patinetes
     *
     */
    public void informarFalloMecanico(Vehiculo v, String descripcion) {
        if (v instanceof Moto) {
            // v.agregarFalloMecanico(descripcion);
        } else {
            // Se le pregunta al usuario qué base es la que tiene un fallo
            // Se añade el fallo al arraylist de la base
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
    
    @Override
    public Usuario clone() {
        // Llamamos a super.clone() para clonar los atributos de Persona y superiores
        Usuario usuarioCopia = (Usuario) super.clone();
    
        // Clonamos los atributos específicos de Usuario
        usuarioCopia.setSaldo(this.getSaldo());
        usuarioCopia.tipoMembresia = this.tipoMembresia;
    
        return usuarioCopia;
    }
}
