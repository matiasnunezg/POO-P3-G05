package espol.poo.vista;

import espol.poo.modelo.Usuario;
import espol.poo.controlador.ControlHidratacion;

import java.util.Scanner;

public class VistaHidratacion {

    private Scanner scanner;
    private Usuario usuario;
    private ControlHidratacion control;

    public VistaHidratacion() {
        scanner = new Scanner(System.in);
    }

    // Método principal de inicio
    public void iniciarPrograma() {
        System.out.println("======================================");
        System.out.println("   BIENVENIDO AL SISTEMA DE HIDRATACION");
        System.out.println("======================================");

        // Crear usuario
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese su edad: ");
        int edad = scanner.nextInt();

        System.out.print("Ingrese su peso (kg): ");
        double peso = scanner.nextDouble();

        System.out.print("Ingrese su meta diaria de agua (ml): ");
        double meta = scanner.nextDouble();

        // Crear usuario y controlador
        usuario = new Usuario(nombre, edad, peso, meta);
        control = new ControlHidratacion(usuario);

        System.out.println("\n¡Usuario registrado exitosamente!\n");

        // Mostrar menú principal
        mostrarMenu();
    }

    // Menú principal del programa
    private void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n========= MENU PRINCIPAL =========");
            System.out.println("1. Registrar nueva ingesta de agua");
            System.out.println("2. Mostrar historial de hoy");
            System.out.println("3. Mostrar resumen diario");
            System.out.println("4. Actualizar meta diaria");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> registrarIngesta();
                case 2 -> control.mostrarHistorialDelDia();
                case 3 -> control.mostrarResumen();
                case 4 -> actualizarMeta();
                case 5 -> System.out.println("¡Gracias por usar el sistema de hidratación!");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }

        } while (opcion != 5);
    }

    // Registrar nueva cantidad de agua
    private void registrarIngesta() {
        System.out.print("Ingrese la cantidad de agua (ml): ");
        double cantidad = scanner.nextDouble();
        control.registrarIngesta(cantidad);
    }

    // Actualizar meta de agua
    private void actualizarMeta() {
        System.out.print("Ingrese la nueva meta diaria (ml): ");
        double nuevaMeta = scanner.nextDouble();
        control.actualizarMetaDiaria(nuevaMeta);
    }

    // Método main para ejecutar el programa directamente
    public static void main(String[] args) {
        VistaHidratacion vista = new VistaHidratacion();
        vista.iniciarPrograma();
    }
}