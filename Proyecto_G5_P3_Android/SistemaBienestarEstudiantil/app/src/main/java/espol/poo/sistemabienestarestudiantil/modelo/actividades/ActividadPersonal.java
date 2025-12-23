package espol.poo.sistemabienestarestudiantil.modelo.actividades; // <--- 1. Paquete corregido

public class ActividadPersonal extends Actividad {

    private String lugar;
    private TipoActividadPersonal actividadPersonal;

    public enum TipoActividadPersonal {
        Citas,
        Ejercicio,
        Hobbies;
    }

    public TipoActividadPersonal getActividadPersonal() {
        return actividadPersonal;
    }

    public String getLugar() {
        return lugar;
    }

    public ActividadPersonal(String nombre, TipoPrioridad prioridad, String fechaVencimiento,
                             int avance, int id, int tiempoEstimado, String fechaActual,
                             String lugar, TipoActividadPersonal actividadPersonal, String descripcion) {

        super(nombre, prioridad, fechaVencimiento, avance, id, tiempoEstimado, fechaActual, descripcion);
        this.lugar = lugar;
        this.actividadPersonal = actividadPersonal;

        // 2. IMPORTANTE: Le avisamos al padre que esto es una actividad PERSONAL
        this.setTipoActividad(TipoActividad.Personal);
    }
}