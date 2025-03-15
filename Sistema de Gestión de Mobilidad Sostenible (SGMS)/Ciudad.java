import java.util.ArrayList;
import java.awt.Color;

public class Ciudad {
    // Variables de instancia
    
    private ArrayList<Entidad> entidades; // Lista de entidades móviles en la ciudad
    
    public static final int ROWS = 50;
    public static final int COLUMNS = 50;

    /**
     * Constructor para objetos de la clase Ciudad.
     */ 
    public Ciudad() {
        entidades = new ArrayList<>(); // Inicializamos el lista de personas       
    }
    
    public ArrayList<Entidad> getEntidades() {
        return entidades;
    }
    
    public void setEntidades(ArrayList<Entidad> newEntidades) {
        entidades = newEntidades;
    }
    
    // Método para añadir una entidad a la ciudad
    public void addElement(Entidad entidad) {
        entidades.add(entidad);
    }
    
    public Entidad encontrarEntidadUsableMasCercana(EntidadMovil entidadBuscando, Class<?> tipoEntidad) {
        Entidad entidadMasCercana = null;
        int distanciaMinima = Integer.MAX_VALUE; // Inicializamos con el valor más alto posible
    
        // Iteramos sobre las entidades
        for (Entidad entidad : entidades) {
            // Comprobamos si la entidad es una instancia del tipo proporcionado
            if (tipoEntidad.isInstance(entidad)) {
                
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
    
        if (entidadMasCercana == null) {
            System.out.println("No se ha encontrado ninguna entidad");
        }
    
        // Devolvemos la entidad más cercana, o null si no se encontró ninguna
        return entidadMasCercana;
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

    public boolean posicionOcupadaPor(Ubicacion ubicacion, Class<?> tipoEntidad) {
        // Iterar sobre las entidades existentes
        for (Entidad entidad : entidades) {
            // Comprobamos si la entidad es una instancia del tipo proporcionado
            if (tipoEntidad.isInstance(entidad)) {
                
                if (entidad.getUbicacion().getPosX() == ubicacion.getPosX() && entidad.getUbicacion().getPosY() == ubicacion.getPosY()) {
                    return true; // La posición está ocupada por una entidad del tipo especificado
                }
            }
        }
        return false; // La posición no está ocupada por una entidad del tipo especificado
    }
}