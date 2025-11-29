package espol.poo.controlador;

import espol.poo.modelo.juegomemoria.JuegoMemoriaEco;
import espol.poo.vista.VistaJuegoMemoriaEco;

/**
 * CONTROLADOR del juego de memoria ecológico.
 * Coordina la lógica entre modelo y vista.
 */ 
public class ControladorJuegoMemoriaEco {

    private JuegoMemoriaEco modelo;
    private VistaJuegoMemoriaEco vista;

    public ControladorJuegoMemoriaEco(VistaJuegoMemoriaEco vista) {
        this.vista = vista;
        this.modelo = new JuegoMemoriaEco();
    }

    private void imprimirUbicacionDePares() {
        System.out.println("\n=== UBICACIÓN DE PARES (SOLO PARA REVISIÓN) ===");

        // Para evitar imprimir duplicados
        java.util.HashSet<String> yaMostrados = new java.util.HashSet<>();

        for (int i = 0; i < 16; i++) {
            String carta = modelo.getCarta(i);

            if (!yaMostrados.contains(carta)) {
                // buscar su pareja
                for (int j = i + 1; j < 16; j++) {
                    if (modelo.getCarta(j).equals(carta)) {
                        System.out.printf("%-10s → posiciones %d y %d%n",
                                carta, (i + 1), (j + 1));
                        yaMostrados.add(carta);
                        break;
                    }
                }
            }
        }

        System.out.println("===============================================\n");
    }


    public void iniciarJuego() {
        vista.mostrarBienvenida();
        // Mostrar ubicación exacta de los pares (solo para revisión)
        imprimirUbicacionDePares();
        vista.pausar();

        while (!modelo.juegoTerminado()) {
            mostrarTableroTemporal();

            int pos1 = vista.leerPosicion("Seleccione la primera carta (1-16): ");
            int pos2 = vista.leerPosicion("Seleccione la segunda carta (1-16): ");

            boolean acierto = modelo.descubrir(pos1, pos2);

            mostrarTableroTemporal(pos1, pos2);

            if (acierto) vista.mostrarMensaje("¡Par encontrado!");
            else vista.mostrarMensaje("No coinciden.");

            vista.mostrarEstado(modelo.getIntentos(), modelo.getParesEncontrados());
            vista.pausar();
        }

        vista.mostrarMensaje("\n¡Felicidades! Has encontrado los 8 pares en " 
                + modelo.getIntentos() + " intentos.\n");
    }

    private void mostrarTableroTemporal(int... cartasMostradas) {
        String[] visibles = new String[16];
        boolean[] descubiertas = modelo.getDescubiertas();

        for (int i = 0; i < 16; i++) {
            boolean mostrar = descubiertas[i];
            for (int j : cartasMostradas) {
                if (i == j) mostrar = true;
            }
            visibles[i] = mostrar ? modelo.getCarta(i) : String.valueOf(i + 1);
        }
        vista.mostrarTablero(visibles);
    }
}
