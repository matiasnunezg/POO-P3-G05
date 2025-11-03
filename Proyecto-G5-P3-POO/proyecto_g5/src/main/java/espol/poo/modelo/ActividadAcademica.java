package espol.poo.modelo;

public class ActividadAcademica extends Actividad{
    private String asignatura;
    private TipoActividadAcademica actividadAcademica;

    public enum TipoActividadAcademica{
        Tarea,
        Proyecto,
        Examen;
    }
    public TipoActividadAcademica getActividadAcademica(){
        return actividadAcademica;
    }
    public String getAsignatura(){
        return asignatura;
    }
    public ActividadAcademica(String nombre, TipoPrioridad prioridad, String fechaVencimiento, 
    int avance, int id, int tiempoEstimado, String fechaActual, String asignatura, TipoActividadAcademica actividadAcademica, String descrpcion){
    super(nombre, prioridad, fechaVencimiento, avance, id, tiempoEstimado, fechaActual, descrpcion);
        this.asignatura = asignatura;
        this.actividadAcademica = actividadAcademica;
    }
}
