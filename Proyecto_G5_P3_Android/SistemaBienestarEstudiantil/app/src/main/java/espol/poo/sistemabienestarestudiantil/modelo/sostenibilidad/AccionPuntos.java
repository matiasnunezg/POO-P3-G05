package espol.poo.sistemabienestarestudiantil.modelo.sostenibilidad;

import java.io.Serializable;

public class AccionPuntos implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int puntos;

    public AccionPuntos(int id, int puntos) {
        this.id = id;
        this.puntos = puntos;
    }

    public int getId() { return id; }
    public int getPuntos() { return puntos; }
}