package espol.poo.vista;

import java.util.List;
import java.util.Scanner;
// Importa las clases del Modelo que se usan en los métodos
import espol.poo.modelo.Actividad;
import espol.poo.modelo.ActividadAcademica; 

/**
 * Vista para el módulo de Técnicas de Enfoque.
 * Se encarga de TODA la interacción con la consola (imprimir y escanear).
 */
public class VistaEnfoque {
    
    private Scanner sc;

    /**
     * Constructor. Inicializa el Scanner.
     */
    public VistaEnfoque() {
        this.sc = new Scanner(System.in);
    }

    // --- MÉTODOS DE LECTURA DE USUARIO ---

    /**
     * Lee un número del teclado.
     * Incluye manejo de errores si el usuario no escribe un número.
     * @return El número ingresado por el usuario, o -1 si no es un número.
     */
    public int leerOpcion() {
        try {
            // Lee la línea completa y la convierte a entero
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            // Si el usuario escribe "abc" o presiona Enter sin nada,
            // esto previene que el programa se caiga.
            return -1; // Retorna un valor inválido para que el controlador lo maneje
        }
    }

    /**
     * Pausa la ejecución hasta que el usuario presione ENTER.
     */
    public void esperarEnter() {
        sc.nextLine();
    }
    
    // --- MÉTODOS DE IMPRESIÓN (PANTALLAS) ---

    /**
     * [cite_start]Muestra el menú principal de Técnicas de Enfoque[cite: 58].
     */
    public void mostrarMenuPrincipal() {
        System.out.println("\n--- T É C N I C A S   D E  E N F O Q U E ---");
        System.out.println("1. Iniciar Pomodoro (25 min Trabajo / 5 min Descanso)");
        System.out.println("2. Iniciar Deep Work (Sesión Larga de 90 min)");
        System.out.println("3. Volver al Menú Principal");
        System.out.print("Ingrese su opción: ");
    }

    /**
     * [cite_start]Muestra una lista dinámica de actividades seleccionables[cite: 58].
     * (Actualizado para leer el tipo desde el enum de ActividadAcademica)
     * @param actividades La lista de actividades filtradas (Tareas, Proyectos)
     */
    public void mostrarSeleccionActividad(List<Actividad> actividades) {
        System.out.println("\nSeleccione la Actividad a trabajar:");
        System.out.println("ID | TIPO     | NOMBRE");
        System.out.println("-------------------------------------------");
        
        if (actividades.isEmpty()) {
            System.out.println("No hay actividades (Tareas o Proyectos) pendientes para trabajar.");
        } else {
            for (Actividad act : actividades) {
                // Sabemos que el controlador ya filtró esta lista.
                ActividadAcademica aa = (ActividadAcademica) act;
                
                // Obtenemos el nombre del enum (ej: "TAREA" o "PROYECTO")
                String tipo = aa.getActividadAcademica().name().toUpperCase(); 
                
                System.out.printf("%-2d | %-8s | %s\n", 
                    act.getId(), 
                    tipo, // Muestra TAREA o PROYECTO
                    act.getNombre());
            }
        }
        System.out.print("\nIngrese ID de la actividad (o 0 para salir): ");
    }

    /**
     * Muestra un mensaje genérico de opción inválida.
     */
    public void mostrarOpcionInvalida() {
        System.out.println("\n*** Opción no válida. Intente nuevamente. ***");
    }

    /**
     * Muestra un mensaje al salir del módulo.
     */
    public void mostrarVolver() {
        System.out.println("Volviendo al menú principal...\n");
    }

    public void mostrarInicioPomodoro() {
        System.out.println("\n--- INICIAR POMODORO ---");
    }

    public void mostrarPomodoroCiclo(int ciclo, int total, String nombreActividad) {
        System.out.printf("\n>>> TRABAJANDO EN '%s' <<<\n", nombreActividad);
        System.out.printf("Técnica: Pomodoro | Ciclo: %d/%d\n", ciclo, total);
        System.out.println("Tiempo de Trabajo: 25:00 minutos restantes");
        System.out.println("[Presione ENTER para finalizar el trabajo]");
    }

    public void mostrarFinTrabajo() {
        System.out.println("\n--- ¡TIEMPO DE TRABAJO TERMINADO! ---");
        System.out.println("Sesión registrada (avance de la actividad actualizado).");
    }

    public void mostrarDescanso() {
        System.out.println("\nAhora toma un DESCANSO: 05:00 minutos");
        System.out.println("[Presione ENTER para iniciar el descanso]");
    }

    public void mostrarFinDescanso() {
        System.out.println("[Descanso finalizado, presione ENTER para continuar]");
    }

    public void mostrarPomodoroFinal() {
        System.out.println("\n--- SESIÓN POMODORO COMPLETADA ---");
        System.out.println("El uso de la técnica Pomodoro ha sido registrado correctamente.");
        System.out.println("[Presione ENTER para volver al menú de Enfoque]");
    }

    // --- Pantallas de Deep Work ---
    
    public void mostrarInicioDeepWork() {
        System.out.println("\n--- INICIAR DEEP WORK ---");
    }

    public void mostrarDeepWorkSesion(String nombreActividad) {
        System.out.printf("\n>>> TRABAJANDO EN '%s' <<<\n", nombreActividad);
        System.out.println("Técnica: Deep Work");
        System.out.println("Tiempo de trabajo: 90:00 minutos restantes");
        System.out.println("[Presione ENTER para finalizar la sesión]");
    }

    public void mostrarDeepWorkFinal() {
        System.out.println("\n--- ¡SESIÓN DEEP WORK COMPLETADA! ---");
        System.out.println("Sesión registrada (avance actualizado en base al tiempo trabajado).");
        System.out.println("[Presione ENTER para volver al menú de Enfoque]");
    }
}