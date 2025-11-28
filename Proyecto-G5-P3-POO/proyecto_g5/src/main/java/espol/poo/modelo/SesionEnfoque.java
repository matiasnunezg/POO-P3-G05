package espol.poo.modelo;

import java.time.LocalDate;

/**
 * Clase para almacenar una sesión de trabajo (Pomodoro, Deep Work).
 * Esto es necesario para el historial de la actividad.
 */
public class SesionEnfoque {
    private LocalDate fecha;
    private String tecnica;
    private int minutos;

    public SesionEnfoque(String tecnica, int minutos) {
        this.fecha = LocalDate.now(); // Se registra con la fecha actual
        this.tecnica = tecnica;
        this.minutos = minutos;
    }

    // Getters por si los necesitas para mostrar el historial
    public LocalDate getFecha() {
        return fecha;
    }

    public String getTecnica() {
        return tecnica;
    }

    public int getMinutos() {
        return minutos;
    }

    public String getDuracionFormateada() {
        if (minutos < 60){
            return minutos + " min";
        }

        int h = minutos / 60;
        int m = minutos % 60;

        if (m == 0) {
            return h + " h";
        }
        else{
            return h + " h " + m + " min";}
    }
    
}