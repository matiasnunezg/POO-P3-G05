package espol.poo.controlador;

import espol.poo.modelo.RegistrarHorasDeSuenio;
import espol.poo.vista.VistaSuenio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ControladorSuenio {

    private ArrayList<RegistrarHorasDeSuenio> registros = new ArrayList<>();
    private VistaSuenio vista = new VistaSuenio();

    public void gestionarSuenio() {
        int opcion;

        do {
            opcion = vista.mostrarMenu();

            switch (opcion) {
                case 1 -> registrarHoras();
                case 2 -> mostrarRegistros();
                case 3 -> generarReporteSemanal();
                case 4 -> vista.mostrarMensaje("Saliendo del módulo de sueño...");
                default -> vista.mostrarMensaje("Opción inválida.");
            }

        } while (opcion != 4);
    }

    private void registrarHoras() {
        vista.mostrarMensaje("\n--- Registrar Horas de Sueño ---");

        LocalTime inicio = vista.pedirHora("Ingrese la hora de acostarse");
        LocalTime fin = vista.pedirHora("Ingrese la hora de despertarse");

        RegistrarHorasDeSuenio registro = new RegistrarHorasDeSuenio(inicio, fin);
        registros.add(registro);

        vista.mostrarResumen(registro.generarResumen());
    }

    private void mostrarRegistros() {
        vista.mostrarLista(registros);
    }

    private void generarReporteSemanal() {
        vista.mostrarMensaje("\n===== REPORTE SEMANAL =====");

        LocalDate hace7dias = LocalDate.now().minusDays(7);

        double total = 0;
        int contador = 0;

        for (RegistrarHorasDeSuenio r : registros) {
            if (!r.getFechaRegistro().isBefore(hace7dias)) {
                total += r.getDuracionHoras();
                contador++;
            }
        }

        if (contador == 0) {
            vista.mostrarMensaje("No hay registros en la última semana.");
            return;
        }

        double promedio = total / contador;

        vista.mostrarMensaje("Horas totales: " + String.format("%.1f h", total));
        vista.mostrarMensaje("Promedio diario: " + String.format("%.1f h", promedio));
    }
}
