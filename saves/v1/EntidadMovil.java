import java.util.Random;
import java.util.ArrayList;

/**
 * Clase EntidadMovil que representa un objeto que puede moverse por el tablero.
 */
public class EntidadMovil extends Entidad {
    public enum Direcciones {
        UP, DOWN, RIGHT, LEFT
    }
    
    private boolean enTrayecto;
    private ArrayList<Direcciones> trayecto;

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

    /**
     * Método para mover la entidad hacia arriba (disminuye la coordenada Y).
     */
    public void moverArriba() {
        mover(0, -1); // Mover hacia arriba disminuyendo Y
    }
    
    /**
     * Método para mover la entidad hacia abajo (aumenta la coordenada Y).
     */
    public void moverAbajo() {
        mover(0, 1); // Mover hacia abajo aumentando Y
    }
    
    /**
     * Método para mover la entidad hacia la izquierda (disminuye la coordenada X).
     */
    public void moverIzquierda() {
        mover(-1, 0); // Mover hacia la izquierda disminuyendo X
    }
    
    /**
     * Método para mover la entidad hacia la derecha (aumenta la coordenada X).
     */
    public void moverDerecha() {
        mover(1, 0); // Mover hacia la derecha aumentando X
    }
    
    /**
     * Método común para mover la entidad en cualquier dirección.
     * @param deltaX El cambio en la coordenada X.
     * @param deltaY El cambio en la coordenada Y.
     */
    private void mover(int deltaX, int deltaY) {       
        // Calcular la distancia que la entidad puede moverse en cada dirección
        int distancia = 1; // Distancia predeterminada de 1 unidad
    
        // Ajuste según el tipo de entidad
        if (this instanceof Moto) {
            distancia = 3; // Moto puede moverse 3 unidades
        } else if (this instanceof Bicicleta || this instanceof Patinete) {
            distancia = 2; // Bicicleta o patinete se mueve 2 unidades
        }
    
        // Calcular el máximo movimiento permitido en cada dirección
        int maxMovimientoX = deltaX == 0 ? 0 : Math.min(distancia, (deltaX > 0 ? Ciudad.COLUMNS - 1 - ubicacion.getPosX() : ubicacion.getPosX()));
        int maxMovimientoY = deltaY == 0 ? 0 : Math.min(distancia, (deltaY > 0 ? Ciudad.ROWS - 1 - ubicacion.getPosY() : ubicacion.getPosY()));
    
        // Aplicar el movimiento calculado en X y Y
        ubicacion.setPosX(ubicacion.getPosX() + Math.min(deltaX * maxMovimientoX, distancia));
        ubicacion.setPosY(ubicacion.getPosY() + Math.min(deltaY * maxMovimientoY, distancia));
    }

    public void moverRandom() {
        RandomGenerator randomGenerator = new RandomGenerator();
        Direcciones direccion = randomGenerator.getDireccionRandom();
        
        // Usamos un switch para mover la entidad en la dirección aleatoria
        switch (direccion) {
            case UP:
                moverArriba(); // Llamamos al método para mover arriba
                break;
            case DOWN:
                moverAbajo(); // Llamamos al método para mover abajo
                break;
            case RIGHT:
                moverDerecha(); // Llamamos al método para mover derecha
                break;
            case LEFT:
                moverIzquierda(); // Llamamos al método para mover izquierda
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
    public void planearTrayecto(int destinoX, int destinoY) {
        int desplazX, desplazY;
        Ubicacion ubi = getUbicacion();
        
        // Se calcula el desplazamiento en x y en y que debe realizar
        desplazX = destinoX - ubi.getPosX();
        desplazY = destinoY - ubi.getPosY();
        
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
        
        System.out.println("Se ha planeado un proyecto desde (" + ubi.getPosX() + " | " + ubi.getPosY() + ") hacia (" + destinoX + " | " + destinoY + ")");
    }
    
    private void seguirTrayecto() {
        // Verificamos si aún hay movimientos por hacer
        if (!trayecto.isEmpty()) {
            // Tomamos el primer movimiento en la lista
            Direcciones siguienteMovimiento = trayecto.remove(0);
    
            // Actualizamos la ubicación dependiendo de la dirección
            Ubicacion ubi = getUbicacion();
            switch (siguienteMovimiento) {
                case RIGHT:
                    ubi.setPosX(ubi.getPosX() + 1);
                    break;
                case LEFT:
                    ubi.setPosX(ubi.getPosX() - 1);
                    break;
                case UP:
                    ubi.setPosY(ubi.getPosY() + 1);
                    break;
                case DOWN:
                    ubi.setPosY(ubi.getPosY() - 1);
                    break;
            }
        } else {
            // Si el trayecto está vacío, significa que se ha llegado al destino
            enTrayecto = false;
            System.out.println("Trayecto completado.");
        }
    }

    
    public boolean isEnTrayecto() {
        return enTrayecto;
    }
    
    public void actuar(Ciudad ciudad) {      
        if (enTrayecto) {
            seguirTrayecto();
        } else {
            if (this instanceof Persona) {
                // Intentar planear trayecto hacia base o moto con una probabilidad del 5%
                if (intentarPlanearTrayecto(ciudad, Base.class, "base") || intentarPlanearTrayecto(ciudad, Moto.class, "moto")) {
                    return;
                }
                
                // Si no se cumplen las probabilidades, mover de manera aleatoria
                moverRandom();
            }
        }
    }
    
    private boolean intentarPlanearTrayecto(Ciudad ciudad, Class<?> tipoEntidad, String nombreEntidad) {
        double probabilidad = 0.5;
        
        if (Math.random() < probabilidad) {
            
            // Encontrar la entidad más cercana
            Ubicacion ubiEntidad = ciudad.encontrarEntidadMasCercana(getUbicacion(), tipoEntidad);
            if (ubiEntidad == null) {
                return false;
            }
            // Planear trayecto hacia la entidad
            planearTrayecto(ubiEntidad.getPosX(), ubiEntidad.getPosY());
            
            // Imprimir mensaje con el toString de la entidad que ha planeado el trayecto
            System.out.println(this.toString() + " ha planeado un trayecto hacia la " + nombreEntidad + " más cercana.");
            return true;
        }
        return false;
    }
}