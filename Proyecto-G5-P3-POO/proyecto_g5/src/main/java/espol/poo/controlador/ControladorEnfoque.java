package espol.poo.controlador;

import java.util.Scanner;

public class ControladorEnfoque {
    private Scanner sc;

    public ControladorEnfoque(Scanner sc) {
        this.sc = sc;
    }


    public int obtenerOpcionMenu() {
        int opcion;
        try {
            opcion = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            opcion = 0;
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
