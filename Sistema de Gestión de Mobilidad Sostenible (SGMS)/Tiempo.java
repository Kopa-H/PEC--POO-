
/**
 * Write a description of class TIempo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Tiempo
{
    // instance variables - replace the example below with your own
    public int diasEntrePagos = 60;
    private int instantes;
    private int segundosPorInstante = 30;
    
    private int segundo = 0;
    private int minuto = 0;
    private int hora = 0;
    private int dia = 0;
    private int mes = 0;
    private int año = 0;

    /**
     * Constructor for objects of class TIempo
     */
    public Tiempo() {
    }
    
    public void transcurrirInstante(Ciudad ciudad, Dinero dinero) {
        instantes++;
        
        for (int i=0; i < segundosPorInstante; i++) {
            transcurrirSegundo(); 
        }
        
        dinero.verificarCobroDeTasas(ciudad, this);
    }
    
    private void transcurrirSegundo() {
        segundo++;
        
        if (segundo % 60 == 0) {
            minuto++;
            
            if (minuto % 60 == 0) {
                hora++;
                
                if (hora % 24 == 0) {
                    dia++;
                    
                    if (dia % 30 == 0) {
                        mes++;
                
                        if (mes % 12 == 0) {
                            año++;
                        }
                    }
                }
            }
        }
    }
    
    public void revertirInstante() {
        instantes--;  // Restamos un instante
    
        for (int i = 0; i < segundosPorInstante; i++) {
            revertirSegundo();  // Revertimos el segundo para cada uno de los segundos en un instante
        }
    }
    
    private void revertirSegundo() {
        segundo--;  // Restamos un segundo
        
        if (segundo < 0) {
            segundo = 59;  // Si el segundo es negativo, retrocedemos a 59
            minuto--;  // Restamos un minuto
    
            if (minuto < 0) {
                minuto = 59;  // Si el minuto es negativo, retrocedemos a 59
                hora--;  // Restamos una hora
    
                if (hora < 0) {
                    hora = 23;  // Si la hora es negativa, retrocedemos a 23
                    dia--;  // Restamos un día
    
                    if (dia < 1) {
                        dia = 30;  // Si el día es menor a 1, retrocedemos a 30 (asumiendo que todos los meses tienen 30 días en este ejemplo)
                        mes--;  // Restamos un mes
    
                        if (mes < 1) {
                            mes = 12;  // Si el mes es menor a 1, retrocedemos a diciembre
                            año--;  // Restamos un año
                        }
                    }
                }
            }
        }
    }
}
