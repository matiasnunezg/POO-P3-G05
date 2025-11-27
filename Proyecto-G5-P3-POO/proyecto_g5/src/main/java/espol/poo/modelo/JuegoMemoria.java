package espol.poo.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

  /* * 5. CLASE JUEGO MEMORIA (CONTROL DE REGLAS)
     * Crea el mazo, baraja las cartas y verifica si dos cartas son par.
     */


public class JuegoMemoria {
    private List<Carta> cartas;
    private int paresEncontrados;

    public JuegoMemoria(List<String> valores) {
        cartas = new ArrayList<>();
        // Por cada valor recibido, creamos 2 cartas para formar el par
        for (String v : valores) {
            cartas.add(new Carta(v));
            cartas.add(new Carta(v));
        }
        Collections.shuffle(cartas); // Barajar aleatoriamente
    }

    public List<Carta> getCartas() { return cartas; }
    public int getParesEncontrados() { return paresEncontrados; }

    // Lógica para verificar si dos posiciones elegidas son iguales
    public boolean seleccionarCartas(int i, int j) {
        // Validamos que los índices existan y no sean la misma carta
        if (i < 0 || j < 0 || i >= cartas.size() || j >= cartas.size() || i == j) {
            return false; 
        }

        Carta c1 = cartas.get(i);
        Carta c2 = cartas.get(j);

        if (c1.getValor().equals(c2.getValor())) {
            c1.setDescubierta(true);
            c2.setDescubierta(true);
            paresEncontrados++;
            return true;
        }
        return false;
    }
}