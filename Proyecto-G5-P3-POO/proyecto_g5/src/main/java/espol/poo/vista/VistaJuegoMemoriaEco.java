package espol.poo.vista;

import java.util.Scanner;

/**
 * VISTA del juego de memoria ecológico.
 * Muestra el tablero y maneja la interacción por consola.
 */
public class VistaJuegoMemoriaEco {

    private Scanner sc = new Scanner(System.in);

    public void mostrarBienvenida() {
        System.out.println("\n--- JUEGO DE MEMORIA ECOLÓGICO ---");
        System.out.println("¡Encuentra los 8 pares!\n");
    }

    public void mostrarTablero(String[] visibles) {
        for (int i = 0; i < 16; i++) {
            if (i % 4 == 0) System.out.println("+------------+------------+------------+------------+");
            System.out.printf("| %-10s", visibles[i]);
            if (i % 4 == 3) System.out.println("|");
        }
        System.out.println("+------------+------------+------------+------------+");
    }

    public int leerPosicion(String texto) {
        System.out.print(texto);
        while (true) {
            try {
                int pos = Integer.parseInt(sc.nextLine()) - 1;
                if (pos >= 0 && pos < 16) return pos;
            } catch (Exception e) {}
            System.out.print("Posición inválida. Intente nuevamente: ");
        }
    }

    public void mostrarEstado(int intentos, int pares) {
        System.out.println("\nTotal de Intentos: " + intentos + " | Pares Encontrados: " + pares + "/8");
    }

    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }

    public void pausar() {
        System.out.println("\nPresione [ENTER] para continuar...");
        sc.nextLine();
    }
}
