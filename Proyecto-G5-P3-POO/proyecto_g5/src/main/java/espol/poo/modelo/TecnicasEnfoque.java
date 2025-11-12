package espol.poo.modelo;

import espol.poo.vista.VistaEnfoque;
import espol.poo.controlador.ControladorEnfoque;
import java.util.Scanner;

public class TecnicasEnfoque {

    private VistaEnfoque vista;
    private ControladorEnfoque ctrl;

    public TecnicasEnfoque() {
        Scanner sc = new Scanner(System.in);
        this.vista = new VistaEnfoque();
        this.ctrl = new ControladorEnfoque(sc);
    }

    public void mostrarMenu() {
        int opcion = 0;

        while (opcion != 3) {
            vista.mostrarMenuPrincipal();
            opcion = ctrl.obtenerOpcionMenu();

            switch (opcion) {
                case 1 -> iniciarPomodoro();
                case 2 -> iniciarDeepWork();
                case 3 -> vista.mostrarVolver();
                default -> vista.mostrarOpcionInvalida();
            }
        }
    }

    private void iniciarPomodoro() {
        vista.mostrarInicioPomodoro();
        vista.mostrarSeleccionActividad();
        String input = ctrl.obtenerActividad();
        if (input.equals("0")) return;

        int ciclos = 4;
        for (int i = 1; i <= ciclos; i++) {
            vista.mostrarPomodoroCiclo(i, ciclos);
            ctrl.esperarEnter();

            vista.mostrarFinTrabajo();

            if (i < ciclos) {
                vista.mostrarDescanso();
                ctrl.esperarEnter();
                vista.mostrarFinDescanso();
                ctrl.esperarEnter();
            }
        }
        vista.mostrarPomodoroFinal();
    }

    private void iniciarDeepWork() {
        vista.mostrarInicioDeepWork();
        vista.mostrarSeleccionActividad();
        String input = ctrl.obtenerActividad();
        if (input.equals("0")) return;

        vista.mostrarDeepWorkSesion();
        ctrl.esperarEnter();
        vista.mostrarDeepWorkFinal();
    }

    public static void main(String[] args) {
        TecnicasEnfoque app = new TecnicasEnfoque();
        app.mostrarMenu();
        
    }
    
}