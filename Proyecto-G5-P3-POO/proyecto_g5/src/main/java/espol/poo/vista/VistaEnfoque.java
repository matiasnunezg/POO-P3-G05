package espol.poo.vista;

public class VistaEnfoque {
    public void mostrarMenuPrincipal() {
        System.out.println("\n--- T É C N I C A S   D E   E N F O Q U E ---");
        System.out.println("1. Iniciar Pomodoro (25 min Trabajo / 5 min Descanso)");
        System.out.println("2. Iniciar Deep Work (Sesión Larga de 90 min)");
        System.out.println("3. Volver al Menú Principal");
        System.out.print("Ingrese su opción: ");
    }

    public void mostrarSeleccionActividad() {
        System.out.println("\nSeleccione la Actividad a trabajar:");
        System.out.println("ID | TIPO | NOMBRE");
        System.out.println("1 | TAREA     | Cuestionario Unidad 2");
        System.out.println("2 | PROYECTO  | Sistema Gestión Tiempo (Fase 1)");
        System.out.print("\nIngrese ID de la actividad (o 0 para salir): ");
        
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

    public void mostrarPomodoroCiclo(int ciclo, int total) {
        System.out.printf("Técnica: Pomodoro | Ciclo: %d/%d\n", ciclo, total);
        System.out.println("Tiempo de Trabajo: 25:00 minutos restantes");
        System.out.println("[Presione ENTER para finalizar el trabajo]");
    }

    public void mostrarFinTrabajo() {
        System.out.println("\n--- ¡TIEMPO DE TRABAJO TERMINADO! ---");
        System.out.println("Sesión registrada (avance de la actividad actualizado).");
    }

    public void mostrarDescanso() {
        System.out.println("\nTiempo de descanso: 05:00 minutos");
        System.out.println("[Presione ENTER para iniciar el descanso]");
    }

    public void mostrarFinDescanso() {
        System.out.println("[Descanso finalizado, presione ENTER para continuar]");
    }

    public void mostrarPomodoroFinal() {
        System.out.println("\n--- SESIÓN POMODORO COMPLETADA ---");
        System.out.println("El uso de la técnica Pomodoro ha sido registrado correctamente.");
    }

    
    public void mostrarInicioDeepWork() {
        System.out.println("\n--- INICIAR DEEP WORK ---");
    }

    public void mostrarDeepWorkSesion() {
        System.out.println("Técnica: Deep Work");
        System.out.println("Tiempo de trabajo: 90:00 minutos restantes");
        System.out.println("[Presione ENTER para finalizar la sesión]");
    }

    public void mostrarDeepWorkFinal() {
        System.out.println("\n--- ¡SESIÓN DEEP WORK COMPLETADA! ---");
        System.out.println("Sesión registrada (avance actualizado en base al tiempo trabajado).");
    }

}
