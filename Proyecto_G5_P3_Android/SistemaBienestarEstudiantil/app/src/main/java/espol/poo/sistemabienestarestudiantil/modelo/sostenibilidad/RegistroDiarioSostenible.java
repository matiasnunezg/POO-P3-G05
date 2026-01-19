package espol.poo.sistemabienestarestudiantil.modelo.sostenibilidad;

import java.time.LocalDate;
import java.util.List;

public class RegistroDiarioSostenible {
    // CAMBIO CLAVE: Usamos LocalDate para poder filtrar por semana
    private LocalDate fecha;
    private List<Integer> accionesSeleccionadasIds;

    public RegistroDiarioSostenible(LocalDate fecha, List<Integer> accionesSeleccionadasIds) {
        this.fecha = fecha;
        this.accionesSeleccionadasIds = accionesSeleccionadasIds;
    }

    public LocalDate getFecha() { return fecha; }

    public List<Integer> getAccionesSeleccionadasIds() { return accionesSeleccionadasIds; }

    // Mantenemos tu método de puntos adaptado
    public int calcularPuntos(List<AccionPuntos> accionesPuntos) {
        int total = 0;
        if (accionesSeleccionadasIds == null) return 0;

        for (Integer id : accionesSeleccionadasIds) {
            for (AccionPuntos ap : accionesPuntos) {
                if (ap.getId() == id) {
                    total += ap.getPuntos();
                }
            }
        }
        return total;
    }

    // Métodos de ayuda para la Activity (Booleanos rápidos)
    public boolean isUsoTransporteSostenible() { return accionesSeleccionadasIds.contains(1); }
    public boolean isSinImpresiones() { return accionesSeleccionadasIds.contains(2); }
    public boolean isSinEnvasesDescartables() { return accionesSeleccionadasIds.contains(3); }
    public boolean isReciclaje() { return accionesSeleccionadasIds.contains(4); }
}