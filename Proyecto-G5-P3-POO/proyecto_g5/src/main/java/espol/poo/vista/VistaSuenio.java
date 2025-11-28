package espol.poo.vista;

import espol.poo.modelo.RegistrarHorasDeSuenio;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale; 
import java.util.Scanner;

public class VistaSuenio {

    private Scanner sc = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("\n===== MENÚ SUEÑO =====");
        System.out.println("1. Registrar horas de sueño");
        System.out.println("2. Generar reporte semanal gráfico");
        System.out.println("3. Salir");
        return pedirEntero("Ingrese una opción: ");
    }

    // Solicita una hora al usuario y valida el formato HH:MM
    public LocalTime pedirHora(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (Formato HH:MM, 24h): ");
            String input = sc.nextLine();
            try {
                return LocalTime.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Formato incorrecto. Intente de nuevo (Ej: 23:30).");
            }
        }
    }

    // Muestra el resumen detallado inmediatamente después de un registro
    public void mostrarResultadoInmediato(RegistrarHorasDeSuenio registro) {
        System.out.println("\n----------------------------------------");
        System.out.println("Calculando...");
        System.out.println("");

        String etiquetaInicio = (registro.getHoraFin().isBefore(registro.getHoraInicio())) 
                                ? "(Día anterior)" : "(Día actual)";
        
        System.out.println("Hora de inicio: " + registro.getHoraInicio() + " " + etiquetaInicio);
        System.out.println("Hora de fin:    " + registro.getHoraFin() + " (Día actual)");
        System.out.println("");
        
        System.out.printf("Se han registrado **%d horas y %d minutos** de sueño.\n", 
                registro.getHorasEnteras(), registro.getMinutosRestantes());
        
        System.out.println("\n--- RESUMEN DE HOY ---");
        System.out.printf("Sueño Registrado: %dh %dm\n", registro.getHorasEnteras(), registro.getMinutosRestantes());
        System.out.println("Recomendación Estándar: 8h 00m");
        System.out.println("Meta alcanzada: " + registro.getMensajeMeta());
        System.out.println("----------------------------------------");
    }

    // Genera una tabla con gráfico de barras ASCII
    public void mostrarReporteGrafico(List<RegistrarHorasDeSuenio> registros) {
        System.out.println("\n--- REPORTE SEMANAL DE SUEÑO (Últimos 7 días) ---");
        System.out.println("Meta Recomendada: 8 horas\n");
        
        System.out.printf("%-10s | %-12s | %s\n", "DIA", "HRS DORMIDAS", "GRÁFICO (1 unidad = 1 hora)");
        System.out.println("-----------|--------------|-----------------------------------");

        if (registros.isEmpty()) {
            System.out.println("          No hay registros recientes.");
            return;
        }

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("EEE d/MM", new Locale("es", "ES"));

        for (RegistrarHorasDeSuenio r : registros) {
            String fechaStr = r.getFechaRegistro().format(formatoFecha);
            fechaStr = fechaStr.substring(0, 1).toUpperCase() + fechaStr.substring(1);

            double horas = r.getDuracionTotalHoras();
            String barra = generarBarraAscii(horas);
            String comentario = (horas >= 8.0) ? "(¡Meta Superada!)" : (horas < 6.0 ? "(Por debajo de la meta)" : "");

            System.out.printf("%-10s | %-12s | %s %s\n", 
                    fechaStr, 
                    String.format(java.util.Locale.US, "%.1fh", horas),
                    barra, 
                    comentario);
        }
        
        System.out.println("\nPresione [ENTER] para volver al menú ...");
        sc.nextLine(); 
    }

    // Crea visualmente la barra de progreso
    private String generarBarraAscii(double horas) {
        StringBuilder sb = new StringBuilder();
        int bloquesLlenos = (int) horas; 
        
        for (int i = 0; i < bloquesLlenos; i++) {
            sb.append("▓");
        }
        
        double resto = horas - bloquesLlenos;
        if (resto > 0) {
            sb.append("░");
        }
        return sb.toString();
    }
    
    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }

    private int pedirEntero(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }
    }
}

//vista suenio