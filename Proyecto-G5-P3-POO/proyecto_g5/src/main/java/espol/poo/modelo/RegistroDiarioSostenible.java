package espol.poo.modelo;
import java.time.LocalDate;
import java.util.List;

    /* * 2. CLASE REGISTRO DIARIO
     * Representa un día específico (ej: 14/10/2025).
     * Guarda la fecha y la lista de cosas que el usuario hizo ese día.
     */


public class RegistroDiarioSostenible {
private LocalDate fecha;
    private List<AccionSostenible> accionesDelDia;

    public RegistroDiarioSostenible(LocalDate fecha, List<AccionSostenible> accionesDelDia) {
        this.fecha = fecha;
        this.accionesDelDia = accionesDelDia;
    }

    public LocalDate getFecha() { return fecha; }
    public List<AccionSostenible> getAccionesDelDia() { return accionesDelDia; }

    public int calcularPuntos() {
        int total = 0;
        for (AccionSostenible a : accionesDelDia) {
            total += a.getPuntos();
        }
        return total;
    }
}
