package espol.poo.vista;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import espol.poo.modelo.RegistrarHorasDeSuenio;

public class VistaSuenio {

    private Scanner sc = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("\n===== MENÚ SUEÑO =====");
        System.out.println("1. Registrar horas de sueño");
        System.out.println("2. Mostrar registros");
        System.out.println("3. Generar reporte semanal");
        System.out.println("4. Salir");
        return pedirEntero("Ingrese una opción: ");
    }

    public LocalTime pedirHora(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (HH:MM): ");
            String input = sc.nextLine();
            try {
                return LocalTime.parse(input);
            } catch (Exception e) {
                System.out.println(" Formato inválido. Ejemplo válido: 23:45");
            }
        }
    }

    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }

    public void mostrarResumen(String texto) {
        System.out.println("\n===== RESUMEN DE SUEÑO =====");
        System.out.println(texto);
    }

    public void mostrarLista(List<RegistrarHorasDeSuenio> registros) {
        System.out.println("\n===== REGISTROS DE SUEÑO =====");

        if (registros.isEmpty()) {
            System.out.println("No hay registros.");
            return;
        }

        int i = 1;
        for (RegistrarHorasDeSuenio r : registros) {
            System.out.println(i++ + ". Fecha: " + r.getFechaRegistro() +
                    " | Duración: " + String.format("%.1f h", r.getDuracionHoras()));
        }
    }

    private int pedirEntero(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }
}
