
/**
 * Write a description of class EntidadFija here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EntidadFija extends Entidad
{
     public EntidadFija() {       
        super();
    }
    /**
     * Constructor for objects of class EntidadFija
     */
    public EntidadFija(int x, int y)
    {
        super(x, y);
    }
    
    @Override
    public EntidadFija clone() {
        // Crear una nueva instancia de EntidadMovil
        EntidadFija entidadFijaCopia = new EntidadFija();
    
        // Clonar los atributos comunes de la superclase Entidad
        super.cloneCommonAttributes(entidadFijaCopia);
    
        return entidadFijaCopia;
    }
}
