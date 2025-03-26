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
    
        // Aquí se podría añadir alguna verificación para evitar avanzar a la entidad si tiene algo en frente. De momento no se considera necesario.
        ubicacion.setPosX(nuevaPosX);
        ubicacion.setPosY(nuevaPosY);
        
        // Si la entidad móvil por moverse es un vehículo se resta su batería
        if (this instanceof Vehiculo vehiculo) {
            vehiculo.restarBateria();
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
                System.out.println("\n" + "Dirección no válida.");
                break;
        }
    }
    
    /**
     * Método que hace que el vehículo inicie un trayecto
     */
    public void planearTrayecto(Ubicacion ubiDestino, Entidad entidadDestino) {
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
        this.enTrayecto = true;
        this.entidadDestino = entidadDestino;
        
        if (entidadDestino != null) {
            System.out.println("\n" + toSimpleString() + " ha comenzado un viaje hacia " + entidadDestino.toSimpleString());
        } else {
            System.out.println("\n" + toSimpleString() + " ha comenzado un viaje hacia " + ubiDestino.toString());
        }
    }
    
    public void terminarTrayecto() {  
        System.out.println(this.toSimpleString() + " ha dejado de seguir a " + entidadDestino.toSimpleString());
    
        // Se abandona el trayecto
        enTrayecto = false;
        siguiendoEntidad = false;
        ubicacionDestino = null;
        entidadDestino = null;
    }
    
    public boolean isVehiculoMalEstado() {
        // Si la entidad que se está moviendo es un vehículo y se dirige un usuario se queda sin batería, se termina su trayecto y por ende el de su pasajero
        if (this instanceof Usuario && entidadDestino instanceof Vehiculo vehiculo) {
            // Si el vehículo NO está en condiciones para ser alquilado:
            if (vehiculo.tieneAlertaFalloMecanico() || vehiculo.getPorcentajeBateria() < 0) {
                terminarTrayecto();
                return true;
            }
        }
        
        return false;
    }
    
    public void seguirTrayecto(Ciudad ciudad) {       
        // Verificamos si aún hay movimientos por hacer
        if (!trayecto.isEmpty()) {
            // Se verifica que la entidad seguida NO está en movimiento. En este caso se termina el trayecto
            if (entidadDestino instanceof EntidadMovil && ((EntidadMovil) entidadDestino).enTrayecto) {
                terminarTrayecto();
            }
            
            // Si la entidad que se está moviendo es un vehículo y se dirige un usuario se queda sin batería o tiene un fallo, se termina su trayecto y por ende el de su pasajero
            if (isVehiculoMalEstado()) {
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
            RandomGenerator randomGenerator = new RandomGenerator();
    
            enTrayecto = false;
            siguiendoEntidad = false;
            ubicacionDestino = null;
    
            if ((this instanceof Bicicleta || this instanceof Patinete) && entidadDestino instanceof Base) {               
                ((Base) entidadDestino).agregarVehiculoDisponible((Vehiculo) this);
            }
            
             // Si se ha llegado a una base, se verifica si hay una bici o patinete y se coge para ir a una dirección random
            if (this instanceof Usuario usuario && entidadDestino instanceof Base baseDestino) {
                Vehiculo vehiculoEscogido = randomGenerator.getVehiculoDisponibleRandom(baseDestino);
                
                if (vehiculoEscogido != null) {
                    Base baseEscogida = (Base) randomGenerator.getEntidadRandom(ciudad, baseDestino, Base.class);
                    
                    if (baseEscogida != null) {
                        // El usuario alerta de fallo mecánico si la base lo tiene
                        if (baseEscogida.tieneFalloMecanico()) {
                            usuario.alertarFalloMecanico(baseEscogida);
                        }
                        
                        vehiculoEscogido.planearTrayecto(baseEscogida.getUbicacion(), baseEscogida);

                        empezarSeguimiento(vehiculoEscogido);
                        
                        baseDestino.vehiculosDisponibles.remove(vehiculoEscogido);
                    } else {
                        System.out.println("\n" + "No hay bases destino disponibles en la ciudad.");
                    }
                } else {
                    System.out.println("\n" + "No hay vehículos disponibles en la base.");
                }
            }
    
            // Si se ha llegado a una moto, se sube y planea un trayecto hacia una dirección aleatoria
            if (this instanceof Usuario && entidadDestino instanceof Moto moto) {
                // La moto comienza un rumbo hacia una posición aleatoria de la ciudad
                Ubicacion ubicacion = randomGenerator.getUbicacionLibreRandom(ciudad);
                moto.planearTrayecto(ubicacion, null);
            
                empezarSeguimiento(moto);
            }
    
            entidadDestino = null;
        }
    }
    
    public void empezarSeguimiento(EntidadMovil entidadPorSeguir) {
        entidadSeguida = entidadPorSeguir;
        siguiendoEntidad = true;
        
        System.out.println("\n" + this.toSimpleString() + " ha comenzado a seguir a " + entidadSeguida.toSimpleString());
    }
    
    public void abandonarSeguimiento() {
        System.out.println("\n" + this.toSimpleString() + " ha dejado de seguir a " + entidadSeguida.toSimpleString());
        
        entidadSeguida = null;
        siguiendoEntidad = false;
    }
    
    public boolean isEnTrayecto() {
        return enTrayecto;
    }
    
    public void actuar(Ciudad ciudad) {
        super.actuar(ciudad);
        
        if (enTrayecto) {
            seguirTrayecto(ciudad);
        } else {
            // La entidad actualiza su estado de seguimiento si ha dejado de seguir a la entidad
            if (entidadSeguida != null && !entidadSeguida.enTrayecto) {
                abandonarSeguimiento();
            }
            
            if (siguiendoEntidad && entidadSeguida != null) {
                seguirEntidadMovil();
            }
        }
    }
    
    public void seguirEntidadMovil() {
        if (this instanceof Usuario usuario && entidadSeguida.tieneFalloMecanico()) {
            usuario.alertarFalloMecanico(entidadSeguida);
        }
        
        // Si la entidad seguida es un vehículo y está en mal estado, se deja de seguir
        if (isVehiculoMalEstado()) {
            return;
        }
        
        ubicacion.setUbicacion(entidadSeguida.getUbicacion());
    }
    
    public void intentarPlanearTrayecto(Ciudad ciudad, Class<?> tipoEntidad) {
        double probabilidad = 0.01;
        
        if (Math.random() < probabilidad) {
            
            // Encontrar la entidad más cercana
            Entidad entidad = ciudad.encontrarEntidadUsableMasCercana(this, tipoEntidad);
            
            if (entidad == null || entidad.getUbicacion() == null) {
                return;
            }
            
            Ubicacion ubiEntidad = entidad.getUbicacion();
        
            // Planear trayecto hacia la entidad
            planearTrayecto(ubiEntidad, entidad);
        }
    }
    
    @Override
    public String toString() {
        String str = super.toString();
        
        if (enTrayecto) {
            str += "  |  Destino = " + ubicacionDestino.toString();
        }
        
        if (siguiendoEntidad) {
            str += "  |  entidadSeguida = " + entidadSeguida.toSimpleString();
        }
        
        if (entidadDestino != null) {
            str += "  |  entidadDestino = [" + entidadDestino.toSimpleString() + "]"; 
        }
        
        return str;
    }
}