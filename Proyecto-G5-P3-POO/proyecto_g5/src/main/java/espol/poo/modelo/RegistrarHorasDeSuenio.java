package espol.poo.modelo;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Duration;

public class RegistrarHorasDeSuenio {

    private LocalTime horaInicio;
    private LocalTime horaFin;
    private double duracionHoras;
    private double metaRecomendada = 8.0;
    private LocalDate fechaRegistro;

    public RegistrarHorasDeSuenio(LocalTime inicio, LocalTime fin) {
        this.horaInicio = inicio;
        this.horaFin = fin;
        this.fechaRegistro = LocalDate.now();
        calcularDuracion();
    }

    public void calcularDuracion(){
        Duration duracion;
        if( horaFin.isBefore(horaInicio)){
            duracion = Duration.between(horaInicio, horaFin.plusHours(24));
        }
        else{
            duracion = Duration.between(horaInicio, horaFin);
        }
        duracionHoras = duracion.toMinutes() / 60.0;
    }

    public boolean cumpleMeta() {
        return duracionHoras >= metaRecomendada;
    }

    public String mostrarResumen() {
        String resultado = String.format("""
        --- RESUMEN DE HOY ---
        Sueño registrado: %.1fh
        Recomendación estándar: %.1fh
        Meta alcanzada: %s
        """, duracionHoras, metaRecomendada, (cumpleMeta() ? "SÍ " : "NO"));
        return resultado;
    }

    public double getDuracionHoras() { 
        return duracionHoras; 
    }

    public LocalDate getFechaRegistro() { 
        return fechaRegistro; 
    }
}





