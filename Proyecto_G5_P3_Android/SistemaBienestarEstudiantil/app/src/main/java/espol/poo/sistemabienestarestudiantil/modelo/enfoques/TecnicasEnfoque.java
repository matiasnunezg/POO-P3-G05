package espol.poo.sistemabienestarestudiantil.modelo.enfoques; // <--- El nuevo paquete

/**
 * Modelo que almacena la configuración de las técnicas de enfoque.
 */
public class TecnicasEnfoque {

    // Los valores están definidos por el proyecto
    private final int pomodoroTrabajoMinutos = 25;
    private final int pomodoroDescansoMinutos = 5;
    private final int pomodoroCiclosTotal = 4;
    private final int deepWorkMinutos = 90;

    public TecnicasEnfoque() {
        // Constructor vacío
    }

    // --- Getters ---
    public int getPomodoroTrabajoMinutos() { return pomodoroTrabajoMinutos; }
    public int getPomodoroDescansoMinutos() { return pomodoroDescansoMinutos; }
    public int getPomodoroCiclosTotal() { return pomodoroCiclosTotal; }
    public int getDeepWorkMinutos() { return deepWorkMinutos; }
}