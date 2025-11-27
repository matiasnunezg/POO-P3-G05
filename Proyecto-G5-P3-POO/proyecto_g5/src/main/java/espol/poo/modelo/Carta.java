package espol.poo.modelo;

    /* * 4. CLASE CARTA
     * Representa una ficha individual del juego.
     * Tiene un valor (texto/imagen) y sabe si está boca arriba o boca abajo.
     */


public class Carta {
    private String valor;
    private boolean descubierta;

    public Carta(String valor) {
        this.valor = valor;
        this.descubierta = false;
    }

    public String getValor() { return valor; }
    
    public boolean isDescubierta() { return descubierta; }
    
    public void setDescubierta(boolean descubierta) {
        this.descubierta = descubierta;
    }
}