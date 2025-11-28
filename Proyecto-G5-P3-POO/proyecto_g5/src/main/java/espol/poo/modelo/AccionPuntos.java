package espol.poo.modelo;

public class AccionPuntos {

    private int id;
    private int puntos;

    public AccionPuntos(int id, int puntos) {
        this.id = id;
        this.puntos = puntos;
    }

    public int getId() {
        return id;
    }

    public int getPuntos() {
        return puntos;
    }
}
