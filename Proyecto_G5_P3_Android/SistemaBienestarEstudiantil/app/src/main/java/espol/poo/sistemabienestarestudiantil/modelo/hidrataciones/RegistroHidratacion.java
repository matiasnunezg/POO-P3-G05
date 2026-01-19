package espol.poo.sistemabienestarestudiantil.modelo.hidrataciones;

import java.io.Serializable; // <--- 1. IMPORT NECESARIO

/**
 * Modelo compatible con API 24 que representa una toma de agua.
 * Se utilizan Strings para fecha y hora para evitar errores de compatibilidad.
 */
public class RegistroHidratacion implements Serializable { // <--- 2. IMPLEMENTAR INTERFAZ

    // 3. IDENTIFICADOR DE VERSIÓN (Seguridad para guardar archivos)
    private static final long serialVersionUID = 1L;

    // ---------- ATRIBUTOS ----------
    private double cantidadMl;       // Ej: 250.0
    private String fecha;            // Cambiado a String para compatibilidad API 24
    private String hora;             // Cambiado a String para compatibilidad API 24

    // ---------- CONSTRUCTOR ----------
    public RegistroHidratacion(double cantidadMl, String fecha, String hora) {
        this.cantidadMl = cantidadMl;
        this.fecha = fecha;
        this.hora = hora;
    }

    // ---------- GETTERS Y SETTERS ----------

    public double getCantidadMl() {
        return cantidadMl;
    }

    public void setCantidadMl(double cantidadMl) {
        this.cantidadMl = cantidadMl;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}