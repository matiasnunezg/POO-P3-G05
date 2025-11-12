package espol.poo.vista;

import java.util.InputMismatchException;
import java.util.Scanner; 


public class VistaEnfoque {

    private Scanner sc = new Scanner(System.in); 
    
  
    public void mostrarMenuPrincipal(int pomodoroTrabajo, int pomodoroDescanso, int deepWork) {
        System.out.println("\n--- T É C N I C A S   D E   E N F O Q U E ---");
        System.out.println("1. Iniciar Pomodoro (" + pomodoroTrabajo + " min Trabajo / " + pomodoroDescanso + " min Descanso)");
        System.out.println("2. Iniciar Deep Work (Sesión Larga de " + deepWork + " min)");
        System.out.println("3. Volver al Menú Principal");
    }
    
    public void mostrarPomodoroCiclo(int cicloActual, int totalCiclos, int trabajoMinutos) {
        System.out.printf("\nTécnica: Pomodoro | Ciclo: %d/%d\n", cicloActual, totalCiclos);
        System.out.println("Tiempo de Trabajo: " + trabajoMinutos + ":00 minutos restantes");
        System.out.println("[Presione ENTER para finalizar el trabajo]");
    }
    
    public void mostrarDescanso(int descansoMinutos) {
        System.out.println("\nTiempo de descanso: " + descansoMinutos + ":00 minutos");
        System.out.println("Presione ENTER para iniciar el descanso...");
    }

    public void mostrarDeepWorkSesion(int deepWorkMinutos) {
        System.out.println("Técnica: Deep Work");
        System.out.println("Tiempo de trabajo: " + deepWorkMinutos + ":00 minutos restantes");
        System.out.println("[Presione ENTER para finalizar la sesión]");
    }
    

    
    public void mostrarOpcionInvalida() {
        System.out.println("Opción no válida. Intente nuevamente.");
    }

    public void mostrarVolver() {
        System.out.println("Volviendo al menú principal...");
    }

    public void mostrarInicioPomodoro() {
        System.out.println("\n--- INICIAR POMODORO ---");
    }
    
    public void mostrarInicioDeepWork() {
        System.out.println("\n--- INICIAR DEEP WORK ---");
    }

    public void mostrarSeleccionActividad() {
        System.out.println("Seleccione la Actividad a trabajar (Simulación):");
        System.out.println("ID | TIPO | NOMBRE");
        System.out.println("1 | TAREA     | Cuestionario Unidad 2");
        System.out.println("2 | PROYECTO  | Sistema Gestión Tiempo (Fase 1)");
        System.out.print("Ingrese ID de la actividad (o 0 para salir): ");
        
    }
    
    public void mostrarFinTrabajo() {
        System.out.println("\n--- ¡TIEMPO DE TRABAJO TERMINADO! ---");
    }

    public void mostrarFinDescanso() {
        System.out.println("[Descanso finalizado, presione ENTER para continuar]");
    }

    public void mostrarPomodoroFinal(int avance) {
        System.out.println("\n--- SESIÓN POMODORO COMPLETADA ---");
        System.out.println("Avance simulado registrado: **+" + avance + "%**.");
    }
    
    public void mostrarDeepWorkFinal(int avance) {
        System.out.println("--- SESIÓN DEEP WORK COMPLETADA ---");
        System.out.println("Avance simulado registrado: **+" + avance + "%**.");
    }
    
 
    public int obtenerOpcionMenu() {
        int opcion = 0;
        while (opcion == 0) { 
            System.out.print("Ingrese su opción: ");
            try {
                opcion = sc.nextInt(); 
                sc.nextLine(); 
                
                if (opcion < 1 || opcion > 3) {
                    System.out.println("Opción no válida. Intente nuevamente.");
                    opcion = 0; 
                }
            } catch (InputMismatchException e) {
                sc.nextLine(); 
                System.out.println("Error: Debe ingresar solo números.");
                opcion = 0; 
            }
        }
        return opcion;
    }

    public String obtenerActividad() {
        return sc.nextLine();
    }
    
    public void esperarEnter() {
        sc.nextLine();
    }
}