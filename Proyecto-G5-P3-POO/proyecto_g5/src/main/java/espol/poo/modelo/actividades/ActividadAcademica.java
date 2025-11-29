package espol.poo.modelo.actividades;
import java.util.ArrayList;
import java.util.List;
import espol.poo.modelo.enfoques.SesionEnfoque;

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
    
    // --- PASO 1: AÑADIR AL HISTORIAL ---
    // (Asegúrate de que esta lista esté inicializada en tu constructor)
    if (this.historialSesiones == null) {
        this.historialSesiones = new ArrayList<>();
    }
    this.historialSesiones.add(new SesionEnfoque(tecnica, minutos));

    
    // --- PASO 2: ACTUALIZAR EL AVANCE (CORREGIDO) ---
    
    // Obtenemos el tiempo estimado (ej. 60)
    double tiempoEstimadoMinutos = this.getTiempoEstimado(); 

    if (tiempoEstimadoMinutos > 0) {
        
        // Los 'minutos' de la sesión (ej. 90) también están en minutos.
        // ¡Ya no multiplicamos por 60!
        
        // Calcula el porcentaje de esta sesión (ej. (90 / 60) * 100 = 150%)
        int avanceSesion = (int) Math.round((minutos / tiempoEstimadoMinutos) * 100);
        
        // Suma el avance nuevo al que ya existía (ej. 7% + 150% = 157%)
        int nuevoAvance = this.getAvance() + avanceSesion;
        
        // --- Lógica para que no pase de 100 ---
        if (nuevoAvance >= 100) {
            this.setAvance(100);
        } else {
            this.setAvance(nuevoAvance);
        }
    }
}
    
    // <<< AÑADIDO: Getter para el historial
    public List<SesionEnfoque> getHistorialSesiones() {
        return historialSesiones;
    }

}
