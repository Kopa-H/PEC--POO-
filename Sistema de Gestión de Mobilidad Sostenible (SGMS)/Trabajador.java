
/**
 * Write a description of class Trabajador here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
abstract public class Trabajador extends Persona
{
    // Almacena el vehículo que el trabajador tiene asignado
    Entidad entidadAsignada = null;
    
    /**
     * Constructor for objects of class Trabajador
     */
    public Trabajador(int posX, int posY)
    {
        super(posX, posY);
    }
    
    public Entidad getEntidadAsignada() {
        return entidadAsignada;
    }
    
    public void setEntidadAsignada(Entidad entidadAsignada) {
        this.entidadAsignada = entidadAsignada;
    }
    
    public boolean isTrabajando() {
        if (entidadAsignada != null) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void actuar(Ciudad ciudad) {
        super.actuar(ciudad);
        
        // Si no tiene ninguna entidad asignada, busca uno que necesite atención
        if (entidadAsignada == null) {
            
            if (!ciudad.getAutonomiaEntidades()) {
                return;
            }
            
            // Se itera sobre todos las entidades y se intenta asignar a su trabajador correspondiente
            for (Entidad entidad : ciudad.getEntidades()) {
                if (intentarAsignarEntidad(ciudad, entidad)) {
                    entidadAsignada = entidad;
                    Impresora.printColorClase(this.getClass(), "\n" + toSimpleString() + " se ha asignado " + entidadAsignada.toSimpleString() + " para trabajar");
                }                   
            }
        } else {
            
            // Si el trabajador no está en trayecto
            if (!enTrayecto) {
            
                // Si el trabajador aún no se encuentra donde la entidad asignada
                if (!(entidadAsignada.getUbicacion().equals(this.getUbicacion()))) {
                    planearTrayecto(entidadAsignada.getUbicacion(), entidadAsignada);
                    return;
                }
                
                // Si el tanto el trabajador como la entidad están en una base, y están en la misma ubicación, A TRABAJAR!
                if (ciudad.posicionOcupadaPor(this.getUbicacion(), Base.class)) {
                    this.trabajar();
                
                } else if (ciudad.posicionOcupadaPor(entidadAsignada.getUbicacion(), Vehiculo.class)) {
                    // Si se ha llegado al vehículo, pero NO está en una base, se lleva a la base más cercana
                    
                    // Dado que la entidad es un vehículo, se hace el cast
                    Vehiculo vehiculoAsignado = (Vehiculo) entidadAsignada;
                    
                    // La persona planea un trayecto hacia la base más cercana
                    Base baseCercana = (Base) ciudad.encontrarEntidadUsableMasCercana(this, Base.class);
                    
                    // Si no hay ningun base disponible, modifica su trabajo para reparar bases
                    if (baseCercana == null) {
                        terminarTrabajo();
                        Impresora.printColorClase(this.getClass(), "\n" + toSimpleString() + " ha abandonado su trabajo con la intención de reparar bases con urgencia");
                        intentarAsignarEntidad(ciudad, ciudad.encontrarEntidadConFalloMecanico(Base.class));
                        
                    } else {
                        Impresora.printColorClase(this.getClass(), "\n" + toSimpleString() + " se lleva arrastrando a " + vehiculoAsignado.toSimpleString() + " a la base más cercana para trabajar");
                        planearTrayecto(baseCercana.getUbicacion(), baseCercana);
                        
                        // El vehículo sigue a la persona
                        vehiculoAsignado.empezarSeguimiento(ciudad, this);
                    }
                    
                }
            }
        }
    }
    
    abstract public void trabajar();
    
    public void terminarTrabajo() {
        Impresora.printColorClase(this.getClass(), "\n" + "El trabajador " + this.toSimpleString() + " ha terminado su trabajo con " + entidadAsignada.toSimpleString());
        entidadAsignada = null;
    }
    
    abstract public boolean intentarAsignarEntidad(Ciudad ciudad, Entidad entidad);
    
    @Override
    public String toString() {
        String str = super.toString();  // Llamamos al toString de Trabajador
        if (entidadAsignada != null) {
            str += "  |  Entidad asignada: [" + entidadAsignada.toSimpleString() + " ]";  // Añadimos el vehículo si está asignado
        }
        return str;
    }
}
