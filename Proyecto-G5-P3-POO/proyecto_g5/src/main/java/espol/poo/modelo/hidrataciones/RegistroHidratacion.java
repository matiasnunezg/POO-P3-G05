package espol.poo.modelo.hidrataciones;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase modelo que representa un registro de hidratación diaria.
 * Contiene solo atributos, constructores y métodos de acceso (get y set).
 */
public class RegistroHidratacion {

    // ====== Atributos ======
    private double metaDiaria;          // Meta de agua en mililitros
    private double cantidadIngerida;    // Cantidad registrada en una ingesta
    private double acumuladoDiario;     // Total acumulado en el día
    private LocalDate fechaRegistro;    // Fecha del registro
    private LocalTime horaRegistro;     // Hora del registro

    // ====== Constructores ======
    public RegistroHidratacion() {
        this.metaDiaria = 0.0;
        this.cantidadIngerida = 0.0;
        this.acumuladoDiario = 0.0;
        this.fechaRegistro = LocalDate.now();
        this.horaRegistro = LocalTime.now();
    }

    public RegistroHidratacion(double metaDiaria, double cantidadIngerida, double acumuladoDiario,
        LocalDate fechaRegistro, LocalTime horaRegistro) {
        this.metaDiaria = metaDiaria;
        this.cantidadIngerida = cantidadIngerida;
        this.acumuladoDiario = acumuladoDiario;
        this.fechaRegistro = fechaRegistro;
        this.horaRegistro = horaRegistro;
    }

    // ====== Métodos Get y Set ======

    public double getMetaDiaria() {
        return metaDiaria;
    }

    public void setMetaDiaria(double metaDiaria) {
        this.metaDiaria = metaDiaria;
    }

    public double getCantidadIngerida() {
        return cantidadIngerida;
    }

    public void setCantidadIngerida(double cantidadIngerida) {
        this.cantidadIngerida = cantidadIngerida;
    }

    public double getAcumuladoDiario() {
        return acumuladoDiario;
    }

    public void setAcumuladoDiario(double acumuladoDiario) {
        this.acumuladoDiario = acumuladoDiario;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalTime getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(LocalTime horaRegistro) {
        this.horaRegistro = horaRegistro;
    }
}