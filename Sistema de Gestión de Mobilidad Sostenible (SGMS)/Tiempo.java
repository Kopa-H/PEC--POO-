import java.awt.Color;

/**
 * Write a description of class TIempo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Tiempo {
    // Conversión de unidades de tiempo a segundos
    private int segundosEnMinuto = 60;
    private int segundosEnHora = 60 * segundosEnMinuto;
    private int segundosEnDia = 24 * segundosEnHora;
    private int segundosEnMes = 30 * segundosEnDia; // Suponiendo meses de 30 días
    private int segundosEnAño = 12 * segundosEnMes; // Suponiendo años de 12 meses de 30 días cada uno
    
    private int segundosInternosPorCiclo = 5 * 60; // Se mide en segundosInternos / ciclo. Indica el número de segundos que transcurren en cada ciclo del bucle
    private int velocidad = 0; // La velocidad se mide en ciclos / segundoReal
    public static final int MAX_VELOCIDAD = 50; // La máxima velocidad es que se ejecuten 50 ciclos del bucle por cada segundo real.
    // 50 ciclos por segundo indican que la velocidad máxima son 60*50 segundos internos / segundo real
    
    public int segundo = 0;
    public int minuto = 0;
    public int hora = 0;
    public int dia = 0;
    public int mes = 0;
    public int año = 0;
    
    // instance variables - replace the example below with your own
    public int diasEntrePagos = 60;
    
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
    
    // La velocidad del tiempo se mide en cuántos segundos dura un ciclo
    public int getVelocidad() {
        return velocidad;
    }
    
    public Color getColorHora() {
        int horaActual = hora;
    
        // Definir colores para distintos rangos de hora
        Color color = Color.BLACK;
    
        if (horaActual >= 6 && horaActual < 12) {
            // Color claro para la mañana (6 AM - 12 PM)
            color = new Color(255, 255, 200);  // Un color claro, amarillo pálido
        } else if (horaActual >= 12 && horaActual < 18) {
            // Color aún más claro para la tarde (12 PM - 6 PM)
            color = new Color(255, 255, 100);  // Un color amarillo claro
        } else if (horaActual >= 18 && horaActual < 24) {
            // Color oscuro para la noche (6 PM - 12 AM)
            color = new Color(50, 50, 50);    // Un color gris oscuro
        } else if (horaActual >= 0 && horaActual < 6) {
            // Color más oscuro para la madrugada (12 AM - 6 AM)
            color = new Color(20, 20, 20);    // Un color casi negro
        }
    
        return color;
    }

    public int getSegundosTotales() {
        // Calcular el total de segundos acumulados
        return segundo +
          (minuto * segundosEnMinuto) +
          (hora * segundosEnHora) +
          (dia * segundosEnDia) +
          (mes * segundosEnMes) +
          (año * segundosEnAño);
    }
    
    public int getCiclosTotales() {
        return getSegundosTotales() / segundosInternosPorCiclo;
    }
    
    public void transcurrirCiclo(Ciudad ciudad, Dinero dinero) {
        for (int i=0; i < segundosInternosPorCiclo; i++) {
            transcurrirSegundo(ciudad, dinero);
        }
    }
    
    public void revertirCiclo() {
        for (int i=0; i < segundosInternosPorCiclo; i++) {
            revertirSegundo();
        }
    }
    
    public void transcurrirSegundo(Ciudad ciudad, Dinero dinero) {
        segundo++;
        
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
    
    public void gestionarTranscursoTiempo(long duracionBucle) {        
        try {
            // Calcular los milisegundos de un ciclo en función de la velocidad.
            // Si la velocidad es 0, el bucle no avanza.
            if (velocidad > 0) {
                // Tiempo objetivo en milisegundos: 1000 ms (1 segundo) dividido por la velocidad (ciclos/segundo real)
                long tiempoObjetivo = 1000L / velocidad;
    
                // Calcular el tiempo restante para alcanzar el tiempo objetivo después de la duración del bucle
                long tiempoRestante = tiempoObjetivo - duracionBucle;
    
                // Si el tiempo restante es positivo, aplicar el delay para alcanzar el objetivo.
                // Si es negativo, significa que el bucle ya ha tardado más de lo deseado, y no se debe aplicar un delay.
                if (tiempoRestante > 0) {
                    Thread.sleep(tiempoRestante);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
