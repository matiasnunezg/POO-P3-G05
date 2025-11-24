package espol.poo.modelo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class RegistrarHorasDeSuenio {

    private LocalTime horaInicio;
    private LocalTime horaFin;
    private double duracionTotalHoras;
    private LocalDate fechaRegistro;
    private final double META_HORAS = 8.0;

    public RegistrarHorasDeSuenio(LocalTime inicio, LocalTime fin) {
        this.horaInicio = inicio;
        this.horaFin = fin;
        this.fechaRegistro = LocalDate.now();
        calcularDuracion();
    }

    // Calcula la duración en horas, considerando si el sueño cruzó la medianoche
    private void calcularDuracion() {
        long minutos;
        
        if (horaFin.isBefore(horaInicio)) {
            // Si la hora fin es menor, significa que despertó al día siguiente
            Duration hastaMedianoche = Duration.between(horaInicio, LocalTime.MAX);
            Duration desdeMedianoche = Duration.between(LocalTime.MIN, horaFin);
            minutos = hastaMedianoche.toMinutes() + desdeMedianoche.toMinutes() + 1;
        } else {
            // Siesta o sueño dentro del mismo día
            minutos = Duration.between(horaInicio, horaFin).toMinutes();
        }

        this.duracionTotalHoras = minutos / 60.0;
    }

    // Obtiene solo la parte entera de las horas (ej: 7.5 -> 7)
    public int getHorasEnteras() {
        return (int) duracionTotalHoras;
    }

    // Obtiene los minutos restantes (ej: 7.5 -> 30)
    public int getMinutosRestantes() {
        return (int) ((duracionTotalHoras - getHorasEnteras()) * 60);
    }
    
    // Genera el mensaje de estado comparando con la meta de 8 horas
    public String getMensajeMeta() {
        long minutosTotales = (long) (duracionTotalHoras * 60);
        long minutosMeta = (long) (META_HORAS * 60);
        long diferencia = minutosMeta - minutosTotales;

        if (diferencia <= 0) {
            return "SÍ. Meta cumplida.";
        } else {
            long horasFaltan = diferencia / 60;
            long minsFaltan = diferencia % 60;
            if (horasFaltan > 0) return "NO. Faltan " + horasFaltan + "h " + minsFaltan + "m.";
            return "NO. Faltan " + minsFaltan + " minutos.";
        }
    }

    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public double getDuracionTotalHoras() { return duracionTotalHoras; }
}