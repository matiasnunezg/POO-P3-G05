package espol.poo.modelo;
import java.util.ArrayList;
import java.util.List;

public class ActividadAcademica extends Actividad{
    private String asignatura;
    private TipoActividadAcademica actividadAcademica;
    private List<SesionEnfoque> historialSesiones = new ArrayList<>();

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

public void registrarSesion(String tecnica, int minutos) {
    
    if (this.historialSesiones == null) {
        this.historialSesiones = new ArrayList<>();
    }
    this.historialSesiones.add(new SesionEnfoque(tecnica, minutos));
    double tiempoEstimadoMinutos = this.getTiempoEstimado(); 

    if (tiempoEstimadoMinutos > 0) {
        

        int avanceSesion = (int) Math.round((minutos / tiempoEstimadoMinutos) * 100);
        int nuevoAvance = this.getAvance() + avanceSesion;
        if (nuevoAvance >= 100) {
            this.setAvance(100);
        } else {
            this.setAvance(nuevoAvance);
        }
    }
}

    public List<SesionEnfoque> getHistorialSesiones() {
        return historialSesiones;
    }

}
