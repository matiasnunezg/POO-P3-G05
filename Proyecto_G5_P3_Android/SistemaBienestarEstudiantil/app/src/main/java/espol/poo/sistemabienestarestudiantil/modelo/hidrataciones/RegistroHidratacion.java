package espol.poo.sistemabienestarestudiantil.modelo.hidrataciones;

import java.time.LocalDate;
import java.time.LocalTime;

 * Modelo simple que representa una toma de agua.
 * NO contiene lógica, solo datos.
 */
public class RegistroHidratacion {

    // ---------- ATRIBUTOS ----------
    private double cantidadMl;       // Ej: 250 ml
    private LocalDate fecha;          // Ej: 12/11/2025
    private LocalTime hora;           // Ej: 10:30 AM

    // ---------- CONSTRUCTOR ----------
    public RegistroHidratacion(double cantidadMl, LocalDate fecha, LocalTime hora) {
        this.cantidadMl = cantidadMl;
        this.fecha = fecha;
        this.hora = hora;
    }

    // ---------- GETTERS Y SETTERS ----------

    public double getCantidadMl() {
        return cantidadMl;
    }

    public void setCantidadMl(double cantidadMl) {
        this.cantidadMl = cantidadMl;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
