import java.util.Random;
import java.util.ArrayList;

/**
 * Clase EntidadMovil que representa un objeto que puede moverse por el tablero.
 */
public abstract class EntidadMovil extends Entidad {
    public enum Direcciones {
        UP, DOWN, RIGHT, LEFT
    }
    
    public boolean enTrayecto;
    Ubicacion ubicacionDestino;
    
    private ArrayList<Direcciones> trayecto;
    
    private Entidad entidadDestino; // Cuando una entidad móvil va en dirección a una entidad, entidadDestino es la entidad de destino.
    
    private EntidadMovil entidadSeguida; // Direccion que está siguiendo entidad movil
    private boolean siguiendoEntidad; // Variable booleana que registra si la entidad está siguiendo a otra entidad
    
    /**
     * Constructor para objetos de la clase EntidadMovil.
     * Inicializa la ubicación en las coordenadas (0,0) por defecto.
     */
    public EntidadMovil() {       
        super();
    }
    
    /**
     * Constructor que permite inicializar la EntidadMovil en una ubicación específica.
     * 
     * @param x Coordenada X inicial
     * @param y Coordenada Y inicial
     */
    public EntidadMovil(int x, int y) {
        super(x, y);
    }
    
    // Métodos get
    public EntidadMovil getEntidadSeguida() {
        return entidadSeguida;
    }
    
    public Entidad getEntidadDestino() {
        return entidadDestino;
    }

    public boolean isSiguiendoEntidad() {
        return siguiendoEntidad;
    }
    
    /**
     * Método para mover la entidad hacia arriba (disminuye la coordenada Y).
     */
    public void moverArriba(Ciudad ciudad) {
        mover(0, -1, ciudad); // Mover hacia arriba disminuyendo Y
    }
    
    /**
     * Método para mover la entidad hacia abajo (aumenta la coordenada Y).
     */
    public void moverAbajo(Ciudad ciudad) {
        mover(0, 1, ciudad); // Mover hacia abajo aumentando Y
    }
    
    /**
     * Método para mover la entidad hacia la izquierda (disminuye la coordenada X).
     */
    public void moverIzquierda(Ciudad ciudad) {
        mover(-1, 0, ciudad); // Mover hacia la izquierda disminuyendo X
    }
    
    /**
     * Método para mover la entidad hacia la derecha (aumenta la coordenada X).
     */
    public void moverDerecha(Ciudad ciudad) {
        mover(1, 0, ciudad); // Mover hacia la derecha aumentando X
    }
    
    /**
     * Método común para mover la entidad en cualquier dirección.
     * @param deltaX El cambio en la coordenada X.
     * @param deltaY El cambio en la coordenada Y.
     */
    private void mover(int deltaX, int deltaY, Ciudad ciudad) {
        int distancia = 1; // Distancia predeterminada de 1 unidad
    
        if (this instanceof Moto) {
            // distancia = 3; // Moto puede moverse 3 unidades
        } else if (this instanceof Bicicleta || this instanceof Patinete) {
            // distancia = 2; // Bicicleta o patinete se mueve 2 unidades
        }
    
        // Calcular el máximo movimiento permitido en cada dirección
        int nuevaPosX = ubicacion.getPosX() + Math.min(deltaX * Math.min(distancia, (deltaX > 0 ? Ciudad.COLUMNS - 1 - ubicacion.getPosX() : ubicacion.getPosX())), distancia);
        int nuevaPosY = ubicacion.getPosY() + Math.min(deltaY * Math.min(distancia, (deltaY > 0 ? Ciudad.ROWS - 1 - ubicacion.getPosY() : ubicacion.getPosY())), distancia);
        Ubicacion nuevaUbi = new Ubicacion(nuevaPosX, nuevaPosY);
    
        // Verificar si la nueva posición está ocupada cuando NO es el destino final y no se es un vehículo (los vehículos se almacenan dentro)
        if (!nuevaUbi.equals(ubicacionDestino) && ciudad.posicionOcupada(nuevaUbi) && !(this instanceof Vehiculo)) {
            System.out.println("La posición (" + nuevaPosX + ", " + nuevaPosY + ") está ocupada, la entidad " + toString() + " no avanza.");
            return;
        } else {
            // Si no está ocupada, realizar el movimiento
            ubicacion.setPosX(nuevaPosX);
            ubicacion.setPosY(nuevaPosY);
        }
        
        // Si la entidad móvil por moverse es un vehículo se resta su batería
        if (this instanceof Vehiculo) {
            ((Vehiculo) this).restarBateria();
        }
    }

    public void moverRandom(Ciudad ciudad) {
        RandomGenerator randomGenerator = new RandomGenerator();
        Direcciones direccion = randomGenerator.getDireccionRandom();
        
        // Usamos un switch para mover la entidad en la dirección aleatoria
        switch (direccion) {
            case UP:
                moverArriba(ciudad); // Llamamos al método para mover arriba
                break;
            case DOWN:
                moverAbajo(ciudad); // Llamamos al método para mover abajo
                break;
            case RIGHT:
                moverDerecha(ciudad); // Llamamos al método para mover derecha
                break;
            case LEFT:
                moverIzquierda(ciudad); // Llamamos al método para mover izquierda
                break;
            default:
                // En caso de que no haya dirección (aunque no debería suceder)
                System.out.println("Dirección no válida.");
                break;
        }
    }
    
    /**
     * Método que hace que el vehículo inicie un trayecto
     */
    public void planearTrayecto(Ubicacion ubiDestino) {
        int actualX, actualY, destinoX, destinoY, desplazX, desplazY;
        
        destinoX = ubiDestino.getPosX();
        destinoY = ubiDestino.getPosY();
        
        actualX = getUbicacion().getPosX();
        actualY = getUbicacion().getPosY();
        
        ubicacionDestino = new Ubicacion();
        ubicacionDestino.setUbicacion(destinoX, destinoY);
        
        // Se calcula el desplazamiento en x y en y que debe realizar
        desplazX = destinoX - actualX;
        desplazY = destinoY - actualY;
        
        // Inicializar la lista de trayecto
        trayecto = new ArrayList<>();
        
        // Añadir movimientos en el eje X (hacia derecha o izquierda)
        if (desplazX > 0) {
            for (int i = 0; i < desplazX; i++) {
                trayecto.add(Direcciones.RIGHT);
            }
        } else if (desplazX < 0) {
            for (int i = 0; i < Math.abs(desplazX); i++) {
                trayecto.add(Direcciones.LEFT);
            }
        }

        // Añadir movimientos en el eje Y (hacia arriba o abajo)
        if (desplazY > 0) {
            for (int i = 0; i < desplazY; i++) {
                trayecto.add(Direcciones.UP);
            }
        } else if (desplazY < 0) {
            for (int i = 0; i < Math.abs(desplazY); i++) {
                trayecto.add(Direcciones.DOWN);
            }
        }

        // Activar estado de trayecto
        enTrayecto = true;
    }
    
    public void terminarTrayecto() {
            // Se abandona el trayecto
            enTrayecto = false;
            siguiendoEntidad = false;
            ubicacionDestino = null;
            entidadDestino = null;
    }
    
    public void seguirTrayecto(Ciudad ciudad) {
        // Verificamos si aún hay movimientos por hacer
        if (!trayecto.isEmpty()) {
            // Se verifica que la entidad seguida NO está en movimiento
            if (entidadDestino instanceof EntidadMovil && ((EntidadMovil) entidadDestino).enTrayecto) {
    
                if (entidadDestino != null) {
                    System.out.println("La entidad " + toString() + " ha abandonado su trayecto a " + entidadDestino.toString() + " porque está en movimiento!");
                } else {
                    System.out.println("La entidad " + toString() + " ha abandonado su trayecto a " + ubicacionDestino.toString() + " porque está en movimiento!");
                }
    
                terminarTrayecto();
            }
            
            // Si la entidad que se está moviendo es un vehículo y se queda sin batería, se termina su trayecto y por ende el de su pasajero
            if (entidadDestino instanceof Vehiculo && ((Vehiculo) entidadDestino).getPorcentajeBateria() < 0) {
                System.out.println("La entidad " + toString() + " ha abandonado su trayecto a " + entidadDestino.toString() + " porque la batería está agotada!");
                terminarTrayecto();
                return;
            }
    
            // Tomamos el primer movimiento en la lista
            Direcciones siguienteMovimiento = trayecto.remove(0);
    
            // Llamamos al método 'mover' con el cambio de dirección correspondiente
            Ubicacion ubi = getUbicacion();
            int deltaX = 0, deltaY = 0;
    
            // Definir los valores de deltaX y deltaY según la dirección
            switch (siguienteMovimiento) {
                case RIGHT:
                    deltaX = 1;
                    break;
                case LEFT:
                    deltaX = -1;
                    break;
                case UP:
                    deltaY = 1;
                    break;
                case DOWN:
                    deltaY = -1;
                    break;
            }
    
            // Llamar al método 'mover' con los cambios calculados
            mover(deltaX, deltaY, ciudad);
    
        } else {
            // Si se ha terminado el trayecto
            if (entidadDestino != null) {
                System.out.println("La entidad " + toString() + " ha completado su trayecto a " + entidadDestino.toString() + "!");
            } else {
                System.out.println("La entidad " + toString() + " ha completado su trayecto a " + ubicacionDestino.toString() + "!");
            }
    
            enTrayecto = false;
            siguiendoEntidad = false;
            ubicacionDestino = null;
    
            // Si se ha llegado a una base, se verifica si hay una bici o patinete y se coge para ir a una dirección random
            if (entidadDestino instanceof Base) {
                // Aquí iría la lógica para revisar las entidades dentro de la base y actuar en consecuencia
            }
    
            // Si se ha llegado a una moto, se sube y la moto planea un trayecto hacia una dirección aleatoria
            if (entidadDestino instanceof Moto) {
                empezarSeguimiento((EntidadMovil) entidadDestino);
    
                // La moto comienza un rumbo hacia una posición aleatoria de la ciudad
                RandomGenerator randomGenerator = new RandomGenerator();
                Ubicacion ubicacion = randomGenerator.getUbicacionLibreRandom(ciudad);
                entidadSeguida.planearTrayecto(ubicacion);
                System.out.println(toString() + " ha comenzado un viaje hacia " + ubicacionDestino);
            }
    
            entidadDestino = null;
        }
    }
    
    public void empezarSeguimiento(EntidadMovil entidadPorSeguir) {
        entidadSeguida = entidadPorSeguir;
        siguiendoEntidad = true;
    }

    
    public boolean isEnTrayecto() {
        return enTrayecto;
    }
    
    public void actuar(Ciudad ciudad) {
        if (enTrayecto) {
            seguirTrayecto(ciudad);
        } else {
            // La entidad actualiza su estado de seguimiento si ha dejado de seguir a la entidad
            if (entidadSeguida != null && !entidadSeguida.enTrayecto) {
                entidadSeguida = null;
                siguiendoEntidad = false;
            }
            
            if (siguiendoEntidad && entidadSeguida != null) {
                seguirEntidadMovil(entidadSeguida);
            }
        }
    }
    
    public void seguirEntidadMovil(Entidad entidad) {
        ubicacion.setUbicacion(entidad.getUbicacion());
    }
    
    public boolean intentarPlanearTrayecto(Ciudad ciudad, Class<?> tipoEntidad) {
        double probabilidad = 0.01;
        
        if (Math.random() < probabilidad) {
            
            // Encontrar la entidad más cercana
            Entidad entidad = ciudad.encontrarEntidadMasCercana(this, tipoEntidad);
            
            if (entidad == null || entidad.getUbicacion() == null) {
                return false;
            }
            
            Ubicacion ubiEntidad = entidad.getUbicacion();
        
            // Planear trayecto hacia la entidad
            planearTrayecto(ubiEntidad);
            
            entidadDestino = entidad;
            
            // Imprimir mensaje con el toString de la entidad que ha planeado el trayecto
            System.out.println(this.toString() + " ha planeado un trayecto hacia " + entidadDestino.toString() + " más cercana.");
            return true;
        }
        return false;
    }
    
    @Override
    public Entidad clone() {

        // Clonamos las propiedades de la superclase adyacente
        EntidadMovil entidadCopia = (EntidadMovil) super.clone();

        // Clonamos los atributos específicos de esta clase
        entidadCopia.enTrayecto = this.enTrayecto;
        if (this.ubicacionDestino != null) {
            entidadCopia.ubicacionDestino = this.ubicacionDestino.clone(); // Si es necesario clonar, como es un objeto Ubicacion
        }
        if (this.trayecto != null) {
            entidadCopia.trayecto = new ArrayList<>(this.trayecto); // Crea una nueva lista con los mismos elementos (si quieres duplicar la lista, no solo la referencia)
        }
        entidadCopia.entidadDestino = this.entidadDestino; // Dependiendo del tipo, puede que necesites hacer un clon de entidadDestino si es un objeto mutable
        entidadCopia.entidadSeguida = this.entidadSeguida; // Lo mismo con entidadSeguida
        entidadCopia.siguiendoEntidad = this.siguiendoEntidad;
        
        return entidadCopia;
    }
    
    @Override
    public String toString() {
        String str = super.toString();
        
        if (enTrayecto) {
            str += "  |  Destino = " + ubicacionDestino.toString();
        }
        
        if (siguiendoEntidad) {
            str += "  |  siguiendoEntidad = " + isSiguiendoEntidad();
        }
        
        if (entidadDestino != null) {
            str += "  |  entidadDestino = [" + entidadDestino.toString() + "]"; 
        }
        
        return str;
    }
}