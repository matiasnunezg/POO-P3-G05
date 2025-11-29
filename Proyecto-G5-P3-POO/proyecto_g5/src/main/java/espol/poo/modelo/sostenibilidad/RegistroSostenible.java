package espol.poo.modelo.sostenibilidad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroSostenible {

    private List<RegistroDiarioSostenible> registros;

    public RegistroSostenible() {
        this.registros = new ArrayList<>();
    }

    public void agregarRegistro(RegistroDiarioSostenible reg) {
        this.registros.add(reg);
    }

    public List<RegistroDiarioSostenible> getRegistros() {
        return registros;
    }

    // Calcula cuántas veces se repitió cada acción (por ID)
    public Map<Integer, Integer> calcularFrecuencias() {
        Map<Integer, Integer> freq = new HashMap<>();

        for (RegistroDiarioSostenible r : registros) {
            for (int idAccion : r.getAccionesSeleccionadasIds()) {
                freq.put(idAccion, freq.getOrDefault(idAccion, 0) + 1);
            }
        }
        return freq;
    }

    public int contarDiasConAcciones() {
        int count = 0;
        for (RegistroDiarioSostenible r : registros) {
            if (!r.getAccionesSeleccionadasIds().isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public int contarDiasPerfectos(int totalAcciones) {
        int count = 0;
        for (RegistroDiarioSostenible r : registros) {
            if (r.getAccionesSeleccionadasIds().size() == totalAcciones) {
                count++;
            }
        }
        return count;
    }

    public String generarTipEcologico() {
        return "Tip: Reduce el uso de envases descartables llevando siempre tu termo.";
    }
}
