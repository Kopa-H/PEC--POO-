import java.util.ArrayList;
import java.awt.Color;

/**
 * Write a description of class Vehiculo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
abstract public class Vehiculo extends EntidadMovil
{
    // Número de casillas que pueden recorrerse con la batería al tope. Se usa junto al porcentaje para calcular la distancia máxima que puede recorrer un vehículo.
    // Las subclases pueden modificar este valor para ajustar la capacidad de sus baterías.
    protected final int DISTANCIA_MAX_BATERIA;
    
    // Contiene el valor de distancia que puede recorrer el vehículo (indicador de la batería)
    private int autonomiaBateria;
    
    private ArrayList<FalloMecanico> fallosMecanicos;
    
    public enum TipoVehiculo {
        MOTO, BICI, PATINETE;
    }

    /**
     * Constructor for objects of class Vehiculo
     */
    public Vehiculo(int posX, int posY, int distanciaMaxBateria) {
        // initialise instance variables
        super(posX, posY);
        
        this.DISTANCIA_MAX_BATERIA = distanciaMaxBateria;
        autonomiaBateria = distanciaMaxBateria;
        
        fallosMecanicos = new ArrayList<>();
    }
       
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int getPorcentajeBateria() {
        // Calcular el porcentaje de batería restante
        return (int) Math.round((double) autonomiaBateria / DISTANCIA_MAX_BATERIA * 100);
    }
    
    public int getAutonomiaBateria() {
        return autonomiaBateria;
    }

    public void setAutonomiaBateria(int autonomiaBateria) {
        this.autonomiaBateria = autonomiaBateria;
    }
    
    public void restarBateria() {
        if (autonomiaBateria > 0) {
            autonomiaBateria--;
        } else {
            System.out.println("La batería del vehículo " + toString() + " se ha agotado!");  
        }
    }
    
    public void sumarBateria() {
        if (autonomiaBateria < DISTANCIA_MAX_BATERIA) {
            autonomiaBateria++;
        } else {
            System.out.println("La batería del vehículo " + toString() + " está llena!");  
        }
    }

    @Override
    public Vehiculo clone() {
        // Llamamos a super.clone() para clonar los atributos de EntidadMovil y superiores
        Vehiculo vehiculoCopia = (Vehiculo) super.clone();
    
        // Clonamos los atributos específicos de Vehiculo
        vehiculoCopia.setAutonomiaBateria(this.autonomiaBateria);
    
        // Clonamos la lista de fallos mecánicos si no es nula
        if (this.fallosMecanicos != null) {
            vehiculoCopia.fallosMecanicos = new ArrayList<>(this.fallosMecanicos);
        }
    
        return vehiculoCopia;
    }

    
    @Override
    public String toString() {
        return super.toString() + "  |  Porcentaje batería: " + getPorcentajeBateria() + "%";
    }
}
