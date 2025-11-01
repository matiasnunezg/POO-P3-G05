package espol.poo.modelo;

public class ActividadPersonal extends Actividad{
    private String lugar;
    private TipoActividadPersonal actividadPersonal;
    public enum TipoActividadPersonal{
        Citas,
        Ejercicio,
        Hobbies;
    }
    public TipoActividadPersonal getActividadPersonal(){
        return actividadPersonal;
    }
    public String getLugar(){
        return lugar;
    }
    public ActividadPersonal(String nombre, TipoPrioridad prioridad, String fechaVencimiento, 
    int avance, String id, int tiempoEstimado, String fechaActual, String lugar, TipoActividadPersonal actividadPersonal){
    super(nombre, prioridad, fechaVencimiento, avance, id, tiempoEstimado, fechaActual);
        this.lugar = lugar;
    }
    public ActividadPersonal(String nombre, TipoPrioridad prioridad, String fechaVencimiento, String lugar){
    super(nombre, prioridad, fechaVencimiento);
        this.lugar = lugar;
    }
    public ActividadPersonal(){
    super();
    }
}
