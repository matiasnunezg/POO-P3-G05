package espol.poo.vista;
import espol.poo.controlador.*;
import java.time.LocalTime;
import java.util.Scanner;

public class VistaSuenio {



    private ControladorSuenio controladorSueño = new ControladorSuenio();
    private Scanner sc = new Scanner(System.in);

    public void mostrarMenuSueño() {
        int opcion;
        do {
            System.out.println("\n--- MÓDULO DE SUEÑO ---");
            System.out.println("1. Registrar horas de sueño");
            System.out.println("2. Ver reporte semanal");
            System.out.println("0. Volver al menú principal");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1 -> registrarSueño();
                case 2 -> controladorSueño.mostrarReporteSemanal();
            }
        } while (opcion != 0);
    }

    private void registrarSueño() {
        System.out.print("Ingrese hora en que se acostó (HH:MM): ");
        LocalTime inicio = LocalTime.parse(sc.nextLine());
        System.out.print("Ingrese hora en que despertó (HH:MM): ");
        LocalTime fin = LocalTime.parse(sc.nextLine());
        controladorSueño.registrarSuenio(inicio, fin);
    }
}




