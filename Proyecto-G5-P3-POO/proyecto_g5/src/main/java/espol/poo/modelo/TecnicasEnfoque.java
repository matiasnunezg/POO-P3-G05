package espol.poo.modelo;


public class TecnicasEnfoque {


    private final int pomodoroTrabajoMinutos;
    private final int pomodoroDescansoMinutos;
    private final int pomodoroCiclosTotal;
    private final int deepWorkMinutos;
    private final int avanceBasePorCiclo;
    private final int avanceBaseDeepWork;

    public TecnicasEnfoque(int pomodoroTrabajoMinutos, int pomodoroDescansoMinutos, int pomodoroCiclosTotal,
        int deepWorkMinutos, int avanceBasePorCiclo, int avanceBaseDeepWork) {
        this.pomodoroTrabajoMinutos = pomodoroTrabajoMinutos;
        this.pomodoroDescansoMinutos = pomodoroDescansoMinutos;
        this.pomodoroCiclosTotal = pomodoroCiclosTotal;
        this.deepWorkMinutos = deepWorkMinutos;
        this.avanceBasePorCiclo = avanceBasePorCiclo;
        this.avanceBaseDeepWork = avanceBaseDeepWork;
        
    }

    
    public int getPomodoroTrabajoMinutos() { return pomodoroTrabajoMinutos; }
    public int getPomodoroDescansoMinutos() { return pomodoroDescansoMinutos; }
    public int getPomodoroCiclosTotal() { return pomodoroCiclosTotal; }
    public int getDeepWorkMinutos() { return deepWorkMinutos; }


    public int calcularAvancePomodoro(int ciclosCompletados) {
        if (ciclosCompletados > 0) {
            return this.avanceBasePorCiclo * ciclosCompletados; 
        }
        return 0;
    }

    public int calcularAvanceDeepWork() {
        
        return this.avanceBaseDeepWork;
    }
}