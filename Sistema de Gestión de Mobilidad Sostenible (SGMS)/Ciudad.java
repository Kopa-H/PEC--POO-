import java.util.ArrayList;
import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;

public class Ciudad {
    // Variables de instancia
    
    private CopyOnWriteArrayList<Entidad> entidades;
    
    public static final int ROWS = 50;
    public static final int COLUMNS = 50;

    /**
     * Constructor para objetos de la clase Ciudad.
     */ 
    public Ciudad() {
        entidades = new CopyOnWriteArrayList<>(); // Inicializamos el lista de personas       
    }
    
    public CopyOnWriteArrayList<Entidad> getEntidades() {
        return entidades;
    }
    
    public void setEntidades(CopyOnWriteArrayList<Entidad> newEntidades) {
        entidades = newEntidades;
    }
    
    // Método para añadir una entidad a la ciudad
    public void addElement(Entidad entidad) {
        entidades.add(entidad);
    }
    
        /**
     * Método para agregar n entidades de un tipo específico en ubicaciones aleatorias.
     * 
     * @param cantidad La cantidad de entidades a agregar.
     * @param tipoEntidad El tipo de entidad a agregar (por ejemplo, Usuario, Moto, Base).
     * @param ciudad La ciudad en la que se agregan las entidades.
     * @return La última entidad creada.
     */
    public <T extends Entidad> T agregarEntidad(Simulacion simulacion, int cantidad, Class<T> claseEntidad) {
        T ultimaEntidad = null;  // Variable para almacenar la última entidad creada
        RandomGenerator randomGenerator = new RandomGenerator();
        
        for (int i = 0; i < cantidad; i++) {
            Ubicacion ubi = randomGenerator.getUbicacionLibreRandom(this);
            
            try {
                // Crear la entidad de acuerdo con el tipo y la ubicación
                T entidad = claseEntidad.getConstructor(int.class, int.class).newInstance(ubi.getPosX(), ubi.getPosY());
                ultimaEntidad = entidad;  // Actualizar la última entidad creada
    
                // Añadir la entidad a la ciudad
                addElement((Entidad) entidad);
                Color color = entidad.getColor();
                simulacion.mostrarEntidad(entidad.getUbicacion(), color);
                
                System.out.println("Se ha añadido una " + claseEntidad.getSimpleName() + " en: " + ubi.toString());
                
                // Si no existe ninguna base y se está añadiendo un vehículo, se agrega una base
                if (encontrarEntidad(Base.class, 0) == null) {
                    agregarBase(simulacion, 2, 2);
                    System.out.println("Se ha añadido una Base dado que no existía ninguna en: " + ubi.toString());
                }
            
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return ultimaEntidad;  // Retornar la última entidad creada
    }
    
        
    public void agregarBase(Simulacion simulacion, int numBicicletas, int numPatinetes) {
        Base base = agregarEntidad(simulacion, 1, Base.class);
        
        for (int i = 0; i < numBicicletas; i++) {
            // Añadir bici
            Bicicleta bici = new Bicicleta(base.getUbicacion().getPosX(), base.getUbicacion().getPosY());
            base.agregarVehiculoDisponible(bici);
            addElement(bici); 
            System.out.println("Se ha añadido una bici a la base.");
        }
        
        for (int i = 0; i < numPatinetes; i++) {
            // Añadir patinete
            Patinete patinete = new Patinete(base.getUbicacion().getPosX(), base.getUbicacion().getPosY());
            base.agregarVehiculoDisponible(patinete);
            addElement(patinete); 
            System.out.println("Se ha añadido un patinete a la base.");
        }
    }
    
    public Entidad encontrarEntidad(Class<?> claseEntidad, int indiceEntidad) {
        for (Entidad entidad : entidades) {
            if (claseEntidad.isInstance(entidad) && entidad.getId() == indiceEntidad) {
                return entidad;
            }
        }
        
        return null;
    }
    
    public Entidad encontrarEntidadUsableMasCercana(EntidadMovil entidadBuscando, Class<?> claseEntidad) {
        Entidad entidadMasCercana = null;
        int distanciaMinima = Integer.MAX_VALUE; // Inicializamos con el valor más alto posible
    
        // Iteramos sobre las entidades
        for (Entidad entidad : entidades) {
            // Comprobamos si la entidad es una instancia del tipo proporcionado
            if (claseEntidad.isInstance(entidad)) {
                
                // SE REALIZAN COMPROBACIONES DE QUE LA ENTIDAD ES VÁLIDA PARA SER USADA
                
                // Si la entidad tiene activado una alerta de fallo mecánico, se ignora
                if (entidad.tieneAlertaFalloMecanico()) {
                    continue;
                }
                
                // Si la entidad es una instancia de EntidadMovil, verificamos si está en trayecto
                if (entidad instanceof EntidadMovil) {
                    EntidadMovil entidadMovil = (EntidadMovil) entidad;
                    if (entidadMovil.enTrayecto) {
                        continue; // Si está en trayecto, la ignoramos
                    }
                }
                
                // Si la entidad es un Vehículo y quien busca es una Persona
                if (entidad instanceof Vehiculo && entidadBuscando instanceof Usuario) {
                    Vehiculo vehiculo = (Vehiculo) entidad;
                    Usuario usuario = (Usuario) entidadBuscando;
    
                    // Si el vehículo tiene menos del 10% de batería, lo descartamos
                    if (vehiculo.getPorcentajeBateria() < 10) {
                        continue;
                    }
    
                    // Si el vehículo tiene menos del 20% de batería y la persona no es premium, lo descartamos
                    if (vehiculo.getPorcentajeBateria() < 20 && !usuario.getTipoMembresia().equals(Usuario.TipoMembresia.PREMIUM)) {
                        continue;
                    }
    
                    // También debería añadir la lógica para descartar bicis o patinetes si no hay batería suficiente para alcanzar una base
                }
                
    
                // Calculamos la distancia Manhattan entre la ubicación actual y la entidad
                int distancia = Math.abs(entidad.getUbicacion().getPosX() - entidadBuscando.getUbicacion().getPosX()) 
                              + Math.abs(entidad.getUbicacion().getPosY() - entidadBuscando.getUbicacion().getPosY());
    
                // Si la distancia calculada es menor que la distancia mínima, actualizamos la entidad más cercana
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    entidadMasCercana = entidad;
                }
            }
        }
    
        // Devolvemos la entidad más cercana, o null si no se encontró ninguna
        return entidadMasCercana;
    }
    
    public Entidad encontrarEntidadConFalloMecanico(Class<?> claseEntidad) {
        Entidad entidadEncontrada = null;
        
        // Iteramos sobre las entidades
        for (Entidad entidad : entidades) {
            // Comprobamos si la entidad es una instancia del tipo proporcionado
            if (claseEntidad.isInstance(entidad) && entidad.tieneFalloMecanico()) {
                entidadEncontrada = entidad;
                break;
            }
        }
        
        return entidadEncontrada;
    }
    
    public boolean existeTrabajadorConEntidadAsignada(Entidad entidadAsignada) {
             // Iteramos sobre las entidades
        for (Entidad entidad : entidades) {
            // Comprobamos si la entidad es una instancia del tipo proporcionado
            if (entidad instanceof Trabajador trabajador && trabajador.getEntidadAsignada() == entidadAsignada) {
                return true;
            }
        }
        
        return false;
    }
    
    // Método que verifica si una posición está ocupada
    public boolean posicionOcupada(Ubicacion ubicacion) {
        // Recorrer las entidades existentes y verificar si alguna está en la misma posición
        for (Entidad entidad : entidades) {
            if (entidad.getUbicacion().getPosX() == ubicacion.getPosX() && entidad.getUbicacion().getPosY() == ubicacion.getPosY()) {
                return true; // La posición está ocupada
            }
        }
        return false; // La posición no está ocupada
    }

    public boolean posicionOcupadaPor(Ubicacion ubicacion, Class<?> claseEntidad) {
        // Iterar sobre las entidades existentes
        for (Entidad entidad : entidades) {
            // Comprobamos si la entidad es una instancia del tipo proporcionado
            if (claseEntidad.isInstance(entidad)) {
                
                if (entidad.getUbicacion().getPosX() == ubicacion.getPosX() && entidad.getUbicacion().getPosY() == ubicacion.getPosY()) {
                    return true; // La posición está ocupada por una entidad del tipo especificado
                }
            }
        }
        return false; // La posición no está ocupada por una entidad del tipo especificado
    }
}