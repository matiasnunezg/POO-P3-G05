package espol.poo.modelo;



// ==========================================================================
// CLASE PRINCIPAL: CONTENEDOR DE TODO EL MODELO
// Esta clase agrupa todas las funcionalidades para Sostenibilidad y Juego.
// =========================================================================

    // ######################################################################
    //                          MÓDULO 1: SOSTENIBILIDAD
    //        (Clases para registrar acciones, días y calcular puntos)
    // ######################################################################

public class AccionSostenible {
private int id;
    private String descripcion;
    private int puntos;

    public AccionSostenible(int id, String descripcion, int puntos) {
        this.id = id;
        this.descripcion = descripcion;
        this.puntos = puntos;
    }

    public int getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public int getPuntos() { return puntos; }
}