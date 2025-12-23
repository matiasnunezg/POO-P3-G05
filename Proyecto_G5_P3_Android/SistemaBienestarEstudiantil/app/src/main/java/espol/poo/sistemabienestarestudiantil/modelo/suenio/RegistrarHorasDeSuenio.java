package espol.poo.sistemabienestarestudiantil.modelo.suenio;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class RegistrarHorasDeSuenio {
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalDate fechaRegistro;
    private double duracionHoras;

    public RegistrarHorasDeSuenio(LocalTime inicio, LocalTime fin) {
        this.horaInicio = inicio;
        this.horaFin = fin;
        this.fechaRegistro = LocalDate.now();
        calcularDuracion();
    }

    // Constructor para cargar datos de prueba con fecha específica
    public RegistrarHorasDeSuenio(LocalTime inicio, LocalTime fin, LocalDate fecha) {
        this.horaInicio = inicio;
        this.horaFin = fin;
        this.fechaRegistro = fecha;
        calcularDuracion();
    }

    private void calcularDuracion() {
        long minutos;
        if (horaFin.isBefore(horaInicio)) {
            // Caso: cruzó la medianoche
            Duration hastaMedianoche = Duration.between(horaInicio, LocalTime.MAX);
            Duration desdeMedianoche = Duration.between(LocalTime.MIN, horaFin);
            minutos = hastaMedianoche.toMinutes() + desdeMedianoche.toMinutes() + 1;
        } else {
            // Mismo día
            minutos = Duration.between(horaInicio, horaFin).toMinutes();
        }
        this.duracionHoras = minutos / 60.0;
    }

    public double getDuracionHoras() { return duracionHoras; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
}