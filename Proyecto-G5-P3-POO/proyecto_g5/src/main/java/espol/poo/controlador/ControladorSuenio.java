package espol.poo.controlador;
import espol.poo.modelo.*;
import java.time.*;
import java.util.*;

public class ControladorSuenio {
    private  ArrayList<RegistrarHorasDeSuenio> registros = new ArrayList<>();

    public void registrarSuenio(LocalTime inicio, LocalTime fin){
        RegistrarHorasDeSuenio registro = new RegistrarHorasDeSuenio(inicio, fin);
        registros.add(registro);
        System.out.println(registro.mostrarResumen());
    }

    public void mostrarReporteSemanal() {
        System.out.println("\n--- REPORTE SEMANAL DE SUEÑO ---");
        System.out.println("DÍA | HORAS DORMIDAS | GRÁFICO");
        System.out.println("---------------------------------------");
        for (RegistrarHorasDeSuenio r : registros) {
            int bloques = (int) Math.round(r.getDuracionHoras());
            String grafico = "▓".repeat(bloques) + "░".repeat(8 - bloques);
            System.out.printf("%s | %.1fh | %s\n",
                r.getFechaRegistro().getDayOfWeek().toString().substring(0,3),
                r.getDuracionHoras(), grafico);
        }
        System.out.println("---------------------------------------\n");
    }
}

