package espol.poo.vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class VistaSostenibilidad {

    private Scanner sc;

    public VistaSostenibilidad() {
        sc = new Scanner(System.in);
    }

    // Recorta texto muy largo para que no rompa la tabla
    private String ajustarTexto(String txt, int max) {
        if (txt.length() <= max) return txt;
        return txt.substring(0, max - 3) + "...";  // trunca y agrega "..."
    }

    // Centra un texto dentro de un ancho fijo (lo usamos para VECES)
    private String centrar(String texto, int ancho) {
        int espacios = ancho - texto.length();
        if (espacios <= 0) return texto; // por si acaso
        int izquierda = espacios / 2;
        int derecha = espacios - izquierda;
        return " ".repeat(izquierda) + texto + " ".repeat(derecha);
    }

    public String pedirSeleccionAcciones(LocalDate fechaActual, List<String> descripcionesAcciones) {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaTexto = fechaActual.format(fmt);

        System.out.println("\n--- REGISTRO DIARIO DE SOSTENIBILIDAD (" + fechaTexto + ") ---");
        System.out.println("Marque las acciones que realizó hoy (ingrese los números separados por coma, ej: 1, 3):\n");
        
        for (int i = 0; i < descripcionesAcciones.size(); i++) {
            System.out.println((i + 1) + ". " + descripcionesAcciones.get(i));
        }
        
        System.out.println();
        System.out.print("Ingrese sus selecciones: ");

        return sc.nextLine().trim();
    }

    public void mostrarConfirmacionAcciones(List<String> acciones, int puntosGanados) {

        System.out.println("\n----------------------------------------");
        System.out.println("Acciones de sostenibilidad registradas:");

        for (String a : acciones) {
            System.out.println("- " + a);
        }

        System.out.println("\n¡Excelente contribución al planeta hoy! ");
        System.out.println("Puntos de Sostenibilidad Acumulados: +" + puntosGanados + "\n");

        System.out.print("Presione [ENTER] para ver el resumen semanal");
        sc.nextLine();
    }

    public void mostrarResumenSemanal(
                LocalDate inicio,
                LocalDate fin,
                List<String> nombresAcciones,
                List<String> frecuenciaFormateada,
                List<String> logros,
                int diasConAccion,
                int diasTotales,
                int diasPerfectos,
                String tip
    ) {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("\n--- RESUMEN SEMANAL DE SOSTENIBILIDAD (" +
                inicio.format(fmt) + " - " + fin.format(fmt) + ") ---\n");

        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("FRECUENCIA DE ACCIONES");
        System.out.println("---------------------------------------------------------------------------------");

        int anchoAccion = 50;
        int anchoVeces  = 7;
        int anchoLogro  = 15;

        System.out.printf("%-" + anchoAccion + "s | %-" + anchoVeces + "s | %-" + anchoLogro + "s%n",
                "ACCIÓN", " VECES", "LOGRO");
        System.out.println("---------------------------------------------------|---------|-------------------");

        for (int i = 0; i < nombresAcciones.size(); i++) {

            String accion = ajustarTexto(nombresAcciones.get(i), anchoAccion);

            // primero ajustamos por si el texto de veces fuera más largo, luego lo centramos
            String vecesTexto = ajustarTexto(frecuenciaFormateada.get(i), anchoVeces);
            String veces  = centrar(vecesTexto, anchoVeces);

            String logro  = ajustarTexto(logros.get(i), anchoLogro);

            // 'veces' ya viene con el ancho correcto, así que usamos %s
            System.out.printf("%-" + anchoAccion + "s | %s | %-" + anchoLogro + "s%n",
                    accion, veces, logro);
        }

        System.out.println("\n----------------------------------------------------------------------");
        System.out.println("ANÁLISIS ECOLÓGICO");
        System.out.println("----------------------------------------------------------------------");

        double porcentaje = (diasConAccion * 100.0) / diasTotales;
        double porcentajePerfectos = (diasPerfectos * 100.0) / diasTotales;

        System.out.println("Días con al menos 1 acción de sostenibilidad: " +
                diasConAccion + " de " + diasTotales +
                " (" + String.format("%.0f", porcentaje) + "%)");
        System.out.println("Días con las 4 acciones completas: " +
                diasPerfectos + " de " + diasTotales +
                " (" + String.format("%.0f", porcentajePerfectos) + "%)\n");

        System.out.println("*Tip Ecológico de la Semana:* " + tip);
        System.out.println("----------------------------------------------------------------------\n");

        System.out.print("Presione [ENTER] para volver al menú de Sostenibilidad...");
        sc.nextLine();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

}
