package espol.poo.modelo.juegomemoria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MODELO del juego de memoria ecológico.
 * Se encarga de generar el tablero y manejar la lógica de los pares.
 */
public class JuegoMemoriaEco {

    private List<String> cartas;     // valores (pares)
    private boolean[] descubiertas;  // qué cartas están descubiertas
    private int intentos;
    private int paresEncontrados;

    public JuegoMemoriaEco() {
        this.cartas = generarCartas();
        this.descubiertas = new boolean[16];
        this.intentos = 0;
        this.paresEncontrados = 0;
    }

    private List<String> generarCartas() {
        List<String> base = List.of("Árbol", "Agua", "Sol", "Flor", "Tierra", "Fuego", "Hoja", "Luz");
        List<String> baraja = new ArrayList<>();
        for (String palabra : base) {
            baraja.add(palabra);
            baraja.add(palabra); // duplicar pares
        }
        Collections.shuffle(baraja);
        return baraja;
    }

    public boolean descubrir(int pos1, int pos2) {
        intentos++;
        if (cartas.get(pos1).equals(cartas.get(pos2))) {
            descubiertas[pos1] = true;
            descubiertas[pos2] = true;
            paresEncontrados++;
            return true;
        }
        return false;
    }

    public boolean[] getDescubiertas() {
        return descubiertas;
    }

    public String getCarta(int index) {
        return cartas.get(index);
    }

    public int getIntentos() {
        return intentos;
    }

    public int getParesEncontrados() {
        return paresEncontrados;
    }

    public boolean juegoTerminado() {
        return paresEncontrados == 8;
    }
}
