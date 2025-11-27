package espol.poo.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    /* * 3. CLASE REGISTRO SOSTENIBLE (GESTOR)
     * Es el historial completo. Guarda una lista de "Registros Diarios".
     * Aquí se calculan las estadísticas semanales y frecuencias.
     */


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

    // Calcula cuántas veces se repitió cada acción (ID -> Cantidad)
    public Map<Integer, Integer> calcularFrecuencias() {
        Map<Integer, Integer> freq = new HashMap<>();
        for (RegistroDiarioSostenible r : registros) {
            for (AccionSostenible a : r.getAccionesDelDia()) {
                freq.put(a.getId(), freq.getOrDefault(a.getId(), 0) + 1);
            }
        }
        return freq;
    }

    public int contarDiasConAcciones() {
        int count = 0;
        for (RegistroDiarioSostenible r : registros) {
            if (!r.getAccionesDelDia().isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public int contarDiasPerfectos(int totalAcciones) {
        int count = 0;
        for (RegistroDiarioSostenible r : registros) {
            if (r.getAccionesDelDia().size() == totalAcciones) {
                count++;
            }
        }
        return count;
    }

    public String generarTipEcologico() {
        return "Tip: Reduce el uso de envases descartables llevando siempre tu termo.";
    }
}