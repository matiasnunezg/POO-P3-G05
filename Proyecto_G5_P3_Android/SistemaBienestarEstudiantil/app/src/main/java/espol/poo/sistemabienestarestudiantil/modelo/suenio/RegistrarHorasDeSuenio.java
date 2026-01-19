package espol.poo.sistemabienestarestudiantil.modelo.suenio;

import java.io.Serializable; // OBLIGATORIO PARA RÚBRICA
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class RegistrarHorasDeSuenio implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalDate fechaRegistro;
    private double duracionHoras;

    public RegistrarHorasDeSuenio() {}

    public RegistrarHorasDeSuenio(LocalTime inicio, LocalTime fin) {
        this.horaInicio = inicio;
        this.horaFin = fin;
        this.fechaRegistro = LocalDate.now();
        calcularDuracion();
    }

    // Constructor para fechas pasadas (Rúbrica 18 Enero)
    public RegistrarHorasDeSuenio(LocalTime inicio, LocalTime fin, LocalDate fecha) {
        this.horaInicio = inicio;
        this.horaFin = fin;
        this.fechaRegistro = fecha;
        calcularDuracion();
    }

    private void calcularDuracion() {
        if (horaInicio == null || horaFin == null) return;
        long minutos;
        if (horaFin.isBefore(horaInicio)) {
            Duration hastaMedianoche = Duration.between(horaInicio, LocalTime.MAX);
            Duration desdeMedianoche = Duration.between(LocalTime.MIN, horaFin);
            minutos = hastaMedianoche.toMinutes() + desdeMedianoche.toMinutes() + 1;
        } else {
            minutos = Duration.between(horaInicio, horaFin).toMinutes();
        }
        this.duracionHoras = minutos / 60.0;
    }

    public double getDuracionHoras() { return duracionHoras; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
}