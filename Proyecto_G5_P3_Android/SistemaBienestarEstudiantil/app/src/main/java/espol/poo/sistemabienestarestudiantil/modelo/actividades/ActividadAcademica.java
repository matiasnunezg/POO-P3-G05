package espol.poo.sistemabienestarestudiantil.modelo.actividades; // <--- 1. Paquete corregido

import java.util.ArrayList;
import java.util.List;
import espol.poo.sistemabienestarestudiantil.modelo.enfoques.SesionEnfoque;
import espol.poo.sistemabienestarestudiantil.modelo.enfoques.TecnicasEnfoque;
// Si SesionEnfoque también la vas a poner en la carpeta 'modelo', no necesitas importarla.
// Si te sale error aquí, es porque aún no creas el archivo SesionEnfoque.java.
// import espol.poo.modelo.enfoques.SesionEnfoque;

public class ActividadAcademica extends Actividad {

    private String asignatura;
    private TipoActividadAcademica actividadAcademica;

    // Asumiendo que SesionEnfoque estará en el mismo paquete, si no, saldrá rojo hasta que la crees.
    private List<SesionEnfoque> historialSesiones = new ArrayList<>();

    public enum TipoActividadAcademica {
        Tarea,
        Proyecto,
        Examen;
    }

    public TipoActividadAcademica getActividadAcademica() {
        return actividadAcademica;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public ActividadAcademica(String nombre, TipoPrioridad prioridad, String fechaVencimiento,
                              int avance, int id, int tiempoEstimado, String fechaActual,
                              String asignatura, TipoActividadAcademica actividadAcademica, String descrpcion) {

        super(nombre, prioridad, fechaVencimiento, avance, id, tiempoEstimado, fechaActual, descrpcion);
        this.asignatura = asignatura;
        this.actividadAcademica = actividadAcademica;

        // 2. IMPORTANTE: Le avisamos al padre que esto es una actividad ACADÉMICA
        this.setTipoActividad(TipoActividad.Academica);
    }

    public void registrarSesion(String tecnica, int minutos) {

        if (this.historialSesiones == null) {
            this.historialSesiones = new ArrayList<>();
        }

        // Esto saldrá en rojo hasta que crees la clase SesionEnfoque
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