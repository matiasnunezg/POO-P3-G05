package espol.poo.vista;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class VistaHidratacion {

    private Scanner sc = new Scanner(System.in);

    // ---------- MÉTODOS DE INTERFAZ PRINCIPAL ----------

    public void mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ DE HIDRATACIÓN ===");
        System.out.println("1. Registrar ingesta de agua");
        System.out.println("2. Establecer meta diaria");
        System.out.println("3. Ver progreso diario y meta");
        System.out.println("4. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    public int leerOpcion() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ---------- OPCIÓN 1: REGISTRAR INGESTA DE AGUA ----------

    public double solicitarCantidadAgua() {
        System.out.println("\n--- REGISTRAR INGESTA DE AGUA ---");
        System.out.print("Ingrese la cantidad de agua que ha tomado (en *mililitros*): ");
        try {
            return Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void mostrarRegistroAgua(double cantidad, double acumuladoAnterior, double acumuladoNuevo, double meta, double porcentaje) {
        System.out.println("\nRegistro de " + (int) cantidad + " ml añadido.\n");
        System.out.println("--- PROGRESO RÁPIDO ---");
        System.out.println("Meta Diaria: " + (int) meta + " ml");
        System.out.println("Acumulado Hoy: " + (int) acumuladoNuevo + " ml (Antes eran " + (int) acumuladoAnterior + " ml)");
        System.out.println("Progreso: " + generarBarraProgreso(porcentaje) + " " + (int) porcentaje + "% (" + (int) acumuladoNuevo + "/" + (int) meta + ")");
        presioneEnterParaContinuar();
    }

    // ---------- OPCIÓN 2: ESTABLECER META DIARIA ----------

    public double solicitarMetaDiaria() {
        System.out.println("\n--- ESTABLECER META DIARIA DE HIDRATACIÓN ---");
        System.out.print("Ingrese la nueva meta diaria de hidratación (en *mililitros*): ");
        try {
            return Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public boolean confirmarNuevaMeta(double nuevaMeta) {
        System.out.print("\n¿Confirma que la nueva meta diaria es *" + (int) nuevaMeta + " ml*? (S/N): ");
        String resp = sc.nextLine().trim().toUpperCase();
        return resp.equals("S");
    }

    public void mostrarMetaActualizada(double nuevaMeta, double acumuladoHoy, double porcentaje) {
        System.out.println("\nMeta diaria de hidratación actualizada a *" + (int) nuevaMeta + " ml* con éxito.\n");
        System.out.println("--- PROGRESO ACTUALIZADO ---");
        System.out.println("Acumulado Hoy: " + (int) acumuladoHoy + " ml");
        System.out.println("Nueva Meta: " + (int) nuevaMeta + " ml");
        System.out.println("Progreso: " + generarBarraProgreso(porcentaje) + " " + (int) porcentaje + "% (" + (int) acumuladoHoy + "/" + (int) nuevaMeta + ")");
        presioneEnterParaContinuar();
    }

    // ---------- OPCIÓN 3: VER PROGRESO DIARIO Y META ----------

    public void mostrarProgresoDetallado(
            String fecha,
            double meta,
            double acumulado,
            double faltante,
            double porcentaje,
            ArrayList<Double> cantidades,
            ArrayList<LocalTime> horas
    ) {
        System.out.println("\n--- PROGRESO DE HIDRATACIÓN DIARIA ---");
        System.out.println("Fecha: " + fecha + "\n");
        System.out.println("Meta Diaria (ML): " + (int) meta + " ml");
        System.out.println("Ingesta Acumulada: " + (int) acumulado + " ml\n");
        System.out.println("Falta: " + (int) faltante + " ml");
        System.out.println("Progreso: " + generarBarraProgreso(porcentaje) + " " + (int) porcentaje + "%\n");
        System.out.println("Historial de Registros de Hoy:");

        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("hh:mm a");

        if (cantidades.isEmpty()) {
            System.out.println("- No hay registros aún para hoy.");
        } else {
            for (int i = 0; i < cantidades.size(); i++) {
                String horaFormateada = horas.get(i).format(formatoHora);
                System.out.println("- " + horaFormateada + ": " + (int) Math.round(cantidades.get(i)) + " ml");
            }
        }

        System.out.println("-------------------------------------");
        System.out.println("¡Recuerda mantenerte hidratado!");
        presioneEnterParaContinuar();
    }

    // ---------- UTILIDADES DE VISTA ----------

    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }

    public void presioneEnterParaContinuar() {
        System.out.print("\nPresione [ENTER] para continuar...");
        sc.nextLine();
    }

    private String generarBarraProgreso(double porcentaje) {
        int total = 20; // longitud de la barra
        int llenos = (int) Math.round((porcentaje / 100) * total);
        StringBuilder barra = new StringBuilder("[");
        for (int i = 0; i < total; i++) {
            barra.append(i < llenos ? "=" : "-");
        }
        barra.append("]");
        return barra.toString();
    }
}