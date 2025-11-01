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
    int avance, String id, int tiempoEstimado, String fechaActual, String asignatura, TipoActividadAcademica actividadAcademica){
    super(nombre, prioridad, fechaVencimiento, avance, id, tiempoEstimado, fechaActual);
        this.asignatura = asignatura;
    }
    public ActividadAcademica(String nombre, TipoPrioridad prioridad, String fechaVencimiento, String asignatura){
    super(nombre, prioridad, fechaVencimiento);
        this.asignatura = asignatura;
    }
    public ActividadAcademica(){
    super();
    }
}
