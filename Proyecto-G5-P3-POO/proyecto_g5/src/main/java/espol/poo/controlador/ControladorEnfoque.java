package espol.poo.controlador;

import espol.poo.vista.VistaEnfoque;
import espol.poo.modelo.TecnicasEnfoque; 


public class ControladorEnfoque {

    private VistaEnfoque vista = new VistaEnfoque();
    private TecnicasEnfoque modelo; 

    public ControladorEnfoque(TecnicasEnfoque modelo) {
        this.modelo = modelo;
    }
    
    public void gestionarTecnicas() {
        int opcion; 
        
        while (true) {
            
            vista.mostrarMenuPrincipal(
                modelo.getPomodoroTrabajoMinutos(), 
                modelo.getPomodoroDescansoMinutos(), 
                modelo.getDeepWorkMinutos()
            );
            
            opcion = vista.obtenerOpcionMenu(); 

            switch (opcion) {
                case 1:
                    iniciarPomodoro();
                    break;
                case 2:
                    iniciarDeepWork();
                    break;
                case 3:
                    vista.mostrarVolver();
                    return; 
                default:
                    vista.mostrarOpcionInvalida();
            }
        }
    }

    private void iniciarPomodoro() {
        vista.mostrarInicioPomodoro();
        vista.mostrarSeleccionActividad(); 
        String input = vista.obtenerActividad(); 
        if (input.equals("0")) return;
        
        int ciclosCompletados = 0;
       
        int totalCiclos = modelo.getPomodoroCiclosTotal(); 
        int trabajoMinutos = modelo.getPomodoroTrabajoMinutos(); 
        int descansoMinutos = modelo.getPomodoroDescansoMinutos(); 
        

        for (int ciclo = 1; ciclo <= totalCiclos; ciclo++) {
            vista.mostrarPomodoroCiclo(ciclo, totalCiclos, trabajoMinutos);
            vista.esperarEnter();

            vista.mostrarFinTrabajo();
            ciclosCompletados = ciclo; 

            if (ciclo < totalCiclos) {
                vista.mostrarDescanso(descansoMinutos);
                vista.esperarEnter();
                vista.mostrarFinDescanso();
                vista.esperarEnter();
            }
        }
        
      
        int avanceGanado = modelo.calcularAvancePomodoro(ciclosCompletados);
        vista.mostrarPomodoroFinal(avanceGanado);
    }

    private void iniciarDeepWork() {
        vista.mostrarInicioDeepWork();
        vista.mostrarSeleccionActividad();
        String input = vista.obtenerActividad();
        if (input.equals("0")) return;
        
     
        int deepWorkMinutos = modelo.getDeepWorkMinutos(); 

        vista.mostrarDeepWorkSesion(deepWorkMinutos);
        vista.esperarEnter(); 
        
        vista.mostrarFinTrabajo();
        
       
        int avanceGanado = modelo.calcularAvanceDeepWork();
        vista.mostrarDeepWorkFinal(avanceGanado);
    }
}