
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
    
    public int vida = 0;
    public int segundo = 0;
    public int minuto = 0;
    public int hora = 0;
    public int dia = 0;
    public int mes = 0;
    public int año = 0;

    /**
     * Constructor for objects of class TIempo
     */
    public Tiempo() {
    }
    
    public void transcurrirSegundo(Ciudad ciudad, Dinero dinero) {
        segundo++;
        vida++;
        
        if (segundo == 60) {  // Verificar si hemos llegado a 60 segundos
            segundo = 0;  // Reiniciar los segundos a cero
            minuto++;
            
            if (minuto == 60) {  // Verificar si hemos llegado a 60 minutos
                minuto = 0;  // Reiniciar los minutos a cero
                hora++;
                
                if (hora == 24) {  // Verificar si hemos llegado a 24 horas
                    hora = 0;  // Reiniciar las horas a cero
                    dia++;
                    
                    if (dia == 30) {  // Verificar si hemos llegado a 30 días
                        dia = 0;  // Reiniciar los días a cero
                        mes++;
                    
                        if (mes == 12) {  // Verificar si hemos llegado a 12 meses
                            mes = 0;  // Reiniciar los meses a cero
                            año++;
                        }
                    }
                }
            }
        }
        
        dinero.verificarCobroDeTasas(ciudad, this);
    }
    
    public void revertirSegundo() {
        segundo--;  // Restamos un segundo
        vida--;
        
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
