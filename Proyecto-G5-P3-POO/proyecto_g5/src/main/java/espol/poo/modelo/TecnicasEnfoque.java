package espol.poo.modelo;

/**
 * Modelo que almacena la configuración de las técnicas de enfoque.
 * Centraliza las "reglas" (ej. tiempos, ciclos) en un solo lugar.
 */
public class TecnicasEnfoque {

    // Los valores están definidos por el proyecto
    private final int pomodoroTrabajoMinutos = 25;
    private final int pomodoroDescansoMinutos = 5;
    private final int pomodoroCiclosTotal = 4;
    private final int deepWorkMinutos = 90;
    
    // El constructor ahora está vacío, ya que los valores son constantes
    public TecnicasEnfoque() {
    }

    // --- Getters para que el Controlador los use ---
    
    public int getPomodoroTrabajoMinutos() { return pomodoroTrabajoMinutos; }
    public int getPomodoroDescansoMinutos() { return pomodoroDescansoMinutos; }
    public int getPomodoroCiclosTotal() { return pomodoroCiclosTotal; }
    public int getDeepWorkMinutos() { return deepWorkMinutos; }
    
    // --- LOS MÉTODOS DE CALCULAR AVANCE SE ELIMINAN DE AQUÍ ---
    // (Esta lógica se moverá al Controlador y a ActividadAcademica)
}