import java.util.List;

/**
 * Write a description of class Dinero here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Dinero {
    // Esto quizás debería moverse a otra clase más general
    private double PRECIO_EUROS_TASAS = 85.57;    

    /**
     * Constructor for objects of class Dinero
     */
    public Dinero() {
    }
    
    public void verificarCobroDeTasas(Ciudad ciudad, Tiempo tiempo) {
        List<Entidad> entidades = ciudad.obtenerEntidadesPorClase(Usuario.class);
        
        // Iteramos sobre las entidades y las casteamos a Usuario
        for (Entidad entidad : entidades) {
            
            if (entidad instanceof Usuario) {  // Verificar si la entidad es de tipo Usuario
                Usuario usuario = (Usuario) entidad;  // Hacer el casting explícito
                
                if (usuario.edad % tiempo.diasEntrePagos == 0) {
                    cobrarTasasUsuarios(usuario);
                }
            }
        }
    }
    
    // El metodo mediante el cual los usuarios pagan sus tasas
    private void cobrarTasasUsuarios(Usuario usuario) {
        if (usuario.getSaldo() - PRECIO_EUROS_TASAS < 0) {
            Impresora.printRojo("\nEl usuario " + usuario.toSimpleString() + " no ha podido pagar el importe de tasas periódico");
    
            // Calcular la cantidad necesaria para recargar el saldo
            double cantidadNecesaria = PRECIO_EUROS_TASAS - usuario.getSaldo();
            
            // Generar una cantidad aleatoria entre [cantidadNecesaria, 2 * cantidadNecesaria]
            double cantidadRecargar = cantidadNecesaria + (Math.random() * cantidadNecesaria); // Se añade algo más para la próxima con algo de aleatoriedads
            
            // Recargar el saldo con la cantidad generada
            usuario.recargarSaldo(cantidadRecargar);
            
            Impresora.printVerde("\n" + usuario.toSimpleString() + " ha recargado " + cantidadRecargar + "€");
            
            // Se llama a la propia función para proceder con el pago de las tasas
            cobrarTasasUsuarios(usuario);
        
        } else {
            // Si tiene suficiente saldo, paga las tasas
            usuario.setSaldo(usuario.getSaldo() - PRECIO_EUROS_TASAS);
            Impresora.printVerde("\nEl usuario " + usuario.toSimpleString() + " ha pagado las tasas de " + PRECIO_EUROS_TASAS + "€.");
        }
    }
}
