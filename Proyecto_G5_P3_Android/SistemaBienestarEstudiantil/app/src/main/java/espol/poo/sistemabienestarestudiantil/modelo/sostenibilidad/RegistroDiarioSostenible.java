package espol.poo.sistemabienestarestudiantil.modelo.sostenibilidad;

import java.util.List;

public class RegistroDiarioSostenible {
    private String fecha; // Cambiado a String para compatibilidad
    private List<Integer> accionesSeleccionadasIds;

    public RegistroDiarioSostenible(String fecha, List<Integer> accionesSeleccionadasIds) {
        this.fecha = fecha;
        this.accionesSeleccionadasIds = accionesSeleccionadasIds;
    }

    public String getFecha() { return fecha; }
    public List<Integer> getAccionesSeleccionadasIds() { return accionesSeleccionadasIds; }

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