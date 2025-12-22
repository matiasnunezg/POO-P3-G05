package espol.poo.sistemabienestarestudiantil.modelo.juegomemoria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JuegoMemoriaEco {

    private final List<String> cartas;     // 16 posiciones (8 pares)
    private final boolean[] descubiertas;  // true si ya quedó descubierta
    private int intentos;
    private int pares;

    public JuegoMemoriaEco() {
        this.cartas = generarCartas();
        this.descubiertas = new boolean[16];
        this.intentos = 0;
        this.pares = 0;
    }

    private List<String> generarCartas() {
        List<String> base = List.of("Árbol", "Agua", "Sol", "Flor", "Tierra", "Fuego", "Hoja", "Luz");
        List<String> baraja = new ArrayList<>();
        for (String p : base) {
            baraja.add(p);
            baraja.add(p);
        }
        Collections.shuffle(baraja);
        return baraja;
    }

    public String getCarta(int index) {
        return cartas.get(index);
    }

    public boolean estaDescubierta(int index) {
        return descubiertas[index];
    }

    public boolean intentarPar(int i1, int i2) {
        intentos++;

        if (cartas.get(i1).equals(cartas.get(i2))) {
            descubiertas[i1] = true;
            descubiertas[i2] = true;
            pares++;
            return true;
        }
        return false;
    }

    public int getIntentos() { return intentos; }
    public int getPares() { return pares; }
    public boolean juegoTerminado() { return pares == 8; }
}