package espol.poo.vista;

import java.util.Scanner;

public class VistaPrincipal {
    private Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("====================================");
        System.out.println("  SISTEMA DE BIENESTAR ESTUDIANTIL");
        System.out.println("====================================");
        System.out.println("1. Gestión de Actividades");
        System.out.println("2. Técnicas de Enfoque");
        System.out.println("3. Control de Hidratación");
        System.out.println("4. Registrar Horas de Sueño");
        System.out.println("5. Registro Diario de Sostenibilidad");
        System.out.println("6. Juego de Memoria");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
}
