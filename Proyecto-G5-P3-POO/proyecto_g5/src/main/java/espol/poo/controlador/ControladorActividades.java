package espol.poo.controlador;
import java.util.ArrayList;
import espol.poo.modelo.Actividad;
import espol.poo.modelo.ActividadAcademica;
import espol.poo.modelo.ActividadPersonal;

public class ControladorActividades{
    private ArrayList<Actividad> listaDeActividades;
    public void crearActividad(int opcion){
        if (opcion == 1){
            ActividadAcademica actividadacademica = new ActividadAcademica();
            this.listaDeActividades.add(actividadacademica);
        }
        else{
            ActividadPersonal actividadpersonal = new ActividadPersonal();
            this.listaDeActividades.add(actividadpersonal);
        }
    }
    public void eliminarActividad(int id){
        listaDeActividades.remove(id - 1);
    }

    public void visualizarActividades(){
        
    }




}
