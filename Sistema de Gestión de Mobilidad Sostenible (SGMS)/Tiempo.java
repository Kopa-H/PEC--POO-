
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
    
    // instance variables - replace the example below with your own
    public int diasEntrePagos = 60;
    
    private int segundosPorInstante = 60;
    
    public int segundo = 0;
    public int minuto = 0;
    public int hora = 0;
    public int dia = 0;
    public int mes = 0;
    public int año = 0;
        
    public static final int MAX_VEL_TRANSCURSO_INSTANTES = 50;
    public int velocidadTranscursoInstantes = 0;

    /**
     * Constructor for objects of class TIempo
     */
    public Tiempo() {
    }
    
    public void setVelocidadTranscursoInstantes(int velocidadTranscursoInstantes) {
        this.velocidadTranscursoInstantes = velocidadTranscursoInstantes;
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
    
    public int getInstantesTotales() {
        return getSegundosTotales() / segundosPorInstante;
    }
    
    public void transcurrirInstante(Ciudad ciudad, Dinero dinero) {
        for (int i=0; i < segundosPorInstante; i++) {
            transcurrirSegundo(ciudad, dinero);
        }
    }
    
    public void revertirInstante() {
        for (int i=0; i < segundosPorInstante; i++) {
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
            // Tiempo objetivo en milisegundos (dependiendo de simulationSpeed)
            // Si simulationSpeed es 1, será 1000 ms, si es 2 será 500 ms, etc.
            long tiempoObjetivo = 1000L / velocidadTranscursoInstantes;
    
            // Calcular el tiempo de delay que falta para llegar al tiempo objetivo
            long tiempoRestante = tiempoObjetivo - duracionBucle;
    
            // Si el tiempo restante es positivo, aplicar el delay. Si es negativo, no hacer nada (el bucle ha tardado más del tiempo objetivo)
            if (tiempoRestante > 0) {
                Thread.sleep(tiempoRestante);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
