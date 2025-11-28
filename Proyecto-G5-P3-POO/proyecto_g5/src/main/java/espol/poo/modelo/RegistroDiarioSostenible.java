package espol.poo.modelo;

import java.time.LocalDate;
import java.util.List;

public class RegistroDiarioSostenible {

    private LocalDate fecha;
    // Guarda los IDs de las acciones seleccionadas (1,2,3,4)
    private List<Integer> accionesSeleccionadasIds;

    public RegistroDiarioSostenible(LocalDate fecha, List<Integer> accionesSeleccionadasIds) {
        this.fecha = fecha;
        this.accionesSeleccionadasIds = accionesSeleccionadasIds;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public List<Integer> getAccionesSeleccionadasIds() {
        return accionesSeleccionadasIds;
    }

    // Calcula los puntos del día usando la tabla (id -> puntos)
    public int calcularPuntos(List<AccionPuntos> accionesPuntos) {
        int total = 0;
        for (Integer id : accionesSeleccionadasIds) {
            for (AccionPuntos ap : accionesPuntos) {
                if (ap.getId() == id) {
                    total += ap.getPuntos();
                }
            }
        }
        return total;
    }
}
