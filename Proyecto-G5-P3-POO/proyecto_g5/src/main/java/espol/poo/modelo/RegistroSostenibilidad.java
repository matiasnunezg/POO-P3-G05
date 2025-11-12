package espol.poo.modelo;

public class RegistroSostenibilidad {
    
    private String descripcionAccion;
    private String fechaRegistro;
    private int puntosGanados;

    public RegistroSostenibilidad(String descripcionAccion, String fechaRegistro, int puntosGanados) {
        this.descripcionAccion = descripcionAccion;
        this.fechaRegistro = fechaRegistro;
        this.puntosGanados = puntosGanados;
    }

    public String getDescripcionAccion() {
        return descripcionAccion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public int getPuntosGanados() {
        return puntosGanados;
    }
    
}
