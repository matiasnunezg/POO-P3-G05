package espol.poo.vista;

import espol.poo.modelo.RegistroHidratacion;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalTime;

/**
 * Vista para el módulo de hidratación.
 * - SOLO lectura/escritura con el usuario (I/O).
 * - No crea objetos del modelo (esa responsabilidad es del controlador).
 */
public class VistaHidratacion {

    private Scanner scanner;

    public VistaHidratacion() {
        this.scanner = new Scanner(System.in);
    }

    // Menú principal (solo muestra)
    public void mostrarMenuPrincipal() {
        System.out.println("\n=== CONTROL DE HIDRATACIÓN ===");
        System.out.println("1. Registrar ingesta de agua");
        System.out.println("2. Actualizar meta diaria");
        System.out.println("3. Ver progreso diario");
        System.out.println("4. Ver historial del día");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // Solicita al usuario la cantidad de agua (ml) y la retorna
    public double solicitarCantidadAgua() {
        double cantidad = 0.0;
        boolean ok = false;
        do {
            System.out.print("Ingrese la cantidad de agua ingerida (ml): ");
            try {
                cantidad = scanner.nextDouble();
                scanner.nextLine(); // limpiar buffer
                if (cantidad <= 0) {
                    System.out.println("La cantidad debe ser mayor que 0.");
                } else {
                    ok = true;
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine();
            }
        } while (!ok);
        return cantidad;
    }

    // Solicita nueva meta diaria (ml)
    public double solicitarMetaDiaria() {
        double meta = 0.0;
        boolean ok = false;
        do {
            System.out.print("Ingrese la nueva meta diaria (ml): ");
            try {
                meta = scanner.nextDouble();
                scanner.nextLine();
                if (meta <= 0) {
                    System.out.println("La meta debe ser mayor que 0.");
                } else {
                    ok = true;
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine();
            }
        } while (!ok);
        return meta;
    }

    // Mostrar progreso: recibe valores calculados por el controlador
    public void mostrarProgreso(double total, double meta, double porcentaje) {
        System.out.println("\n--- PROGRESO DEL DÍA ---");
        System.out.println("Total ingerido: " + total + " ml");
        System.out.println("Meta diaria: " + meta + " ml");
        System.out.println("Progreso: " + String.format("%.1f", porcentaje) + " %");
    }

    // Mostrar historial: recibe listas de cantidades y horas (gestionado por el controlador)
    public void mostrarHistorial(ArrayList<Double> cantidades, ArrayList<LocalTime> horas) {
        System.out.println("\n--- HISTORIAL DE INGESTAS ---");
        if (cantidades == null || cantidades.isEmpty()) {
            System.out.println("No hay registros de ingesta.");
            return;
        }
        for (int i = 0; i < cantidades.size(); i++) {
            double cantidad = cantidades.get(i);
            LocalTime hora = horas.get(i);

            // obtener segundos con un decimal máximo
            double segundos = hora.getSecond() + hora.getNano() / 1_000_000_000.0;
            String segStr = String.format("%.1f", segundos);

            System.out.println((i + 1) + ". " + cantidad + " ml - Hora: "
                    + String.format("%02d", hora.getHour()) + ":"
                    + String.format("%02d", hora.getMinute()) + ":"
                    + segStr);
        }
    }

    // Mostrar un único registro (usa getters del modelo)
    public void mostrarRegistro(RegistroHidratacion r) {
        if (r == null) {
            System.out.println("Registro vacío.");
            return;
        }
        // Usa los getters existentes en tu modelo
        System.out.println("\n--- Detalle del Registro ---");
        System.out.println("Cantidad ingesta: " + r.getCantidadIngerida() + " ml");
        LocalTime h = r.getHoraRegistro();
        if (h != null) {
            double segundos = h.getSecond() + h.getNano() / 1_000_000_000.0;
            String segStr = String.format("%.1f", segundos);
            System.out.println("Hora: " + String.format("%02d", h.getHour()) + ":"
                    + String.format("%02d", h.getMinute()) + ":"
                    + segStr);
        }
        System.out.println("Fecha: " + r.getFechaRegistro());
        System.out.println("Acumulado del día (según modelo): " + r.getAcumuladoDiario() + " ml");
        System.out.println("Meta diaria: " + r.getMetaDiaria() + " ml");
    }

    // Mostrar mensajes genéricos o confirmaciones
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    // Leer opción del menú (devolver int)
    public int leerOpcion() {
        int opcion = -1;
        try {
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer
        } catch (Exception e) {
            scanner.nextLine();
            opcion = -1;
        }
        return opcion;
    }

    // Método pausa (presiona ENTER)
    public void pausar() {
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}