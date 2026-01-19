package espol.poo.sistemabienestarestudiantil.modelo.sostenibilidad;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class RegistroDiarioSostenible implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDate fecha;
    private List<Integer> accionesSeleccionadasIds;

    public RegistroDiarioSostenible(LocalDate fecha, List<Integer> accionesSeleccionadasIds) {
        this.fecha = fecha;
        this.accionesSeleccionadasIds = accionesSeleccionadasIds;
    }
    public LocalDate getFecha() { return fecha; }
    public List<Integer> getAccionesSeleccionadasIds() { return accionesSeleccionadasIds; }

    public int calcularPuntos(List<AccionPuntos> accionesPuntos) {
        int total = 0;
        if (accionesSeleccionadasIds == null) return 0;
        for (Integer id : accionesSeleccionadasIds) {
            for (AccionPuntos ap : accionesPuntos) {
                if (ap.getId() == id) total += ap.getPuntos();
            }
        }
        return total;
    }
    public boolean isUsoTransporteSostenible() { return accionesSeleccionadasIds.contains(1); }
    public boolean isSinImpresiones() { return accionesSeleccionadasIds.contains(2); }
    public boolean isSinEnvasesDescartables() { return accionesSeleccionadasIds.contains(3); }
    public boolean isReciclaje() { return accionesSeleccionadasIds.contains(4); }
}