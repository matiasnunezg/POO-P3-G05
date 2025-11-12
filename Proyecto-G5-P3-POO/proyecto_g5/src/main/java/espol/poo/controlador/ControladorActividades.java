package espol.poo.controlador;
import java.util.ArrayList;
import espol.poo.modelo.Actividad;
import espol.poo.modelo.ActividadAcademica;
import espol.poo.modelo.ActividadPersonal;
import espol.poo.modelo.Actividad.TipoPrioridad;
import espol.poo.modelo.ActividadAcademica.TipoActividadAcademica;
import espol.poo.modelo.ActividadPersonal.TipoActividadPersonal;
import espol.poo.vista.VistaActividad;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ControladorActividades{
    VistaActividad vista= new VistaActividad();
    private ArrayList<Actividad> listaDeActividades = new ArrayList<>();
    private ArrayList<ActividadAcademica> listaDeActividadesAcademicas = new ArrayList<>();
    private ArrayList<ActividadPersonal> listaDeActividadesPersonales = new ArrayList<>();
    public String getFechaActual() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaActual = LocalDate.now();
        return fechaActual.format(formatter);
    }

    public ControladorActividades(ArrayList<Actividad> lActividades, ArrayList<ActividadAcademica>lActividadesAcademicas,ArrayList<ActividadPersonal> lActividadesPersonales){
        this.listaDeActividades = lActividades;
        this.listaDeActividadesAcademicas = lActividadesAcademicas;
        this.listaDeActividadesPersonales = lActividadesPersonales;

    }
    
    public void crearActividad(){
        int opcion = vista.pedirtipoactividad();
        if (opcion == 1){
            TipoActividadAcademica tipo = vista.pedirTipoActividadAcademica();
            String nombre = vista.pedirTextoNoVacio("Ingrese nombre de actividad academica: ");
            TipoPrioridad prioridad = vista.pedirPrioridad();
            String asignatura = vista.pedirTextoNoVacio("Ingrese asignatura de la actividad: ");
            String fechavencimiento = vista.pedirfechaVencimiento()+" "+vista.pedirHoraVencimiento();
            String fechaActual = getFechaActual();
            String descripcion = vista.pedirTextoNoVacio("Ingrese descripcion de la actividad: ");
            int tiempoestimado = vista.pedirNumeroPositivo("Ingrese el tiempo estimado: ");
            ActividadAcademica actividadacademica= new ActividadAcademica(nombre,prioridad,fechavencimiento,0,listaDeActividades.size()+1,tiempoestimado,fechaActual,asignatura,tipo,descripcion);
            this.listaDeActividades.add(actividadacademica);
            this.listaDeActividadesAcademicas.add(actividadacademica);}
        else{
            TipoActividadPersonal tipo = vista.pedirTipoActividadPersonal();
            String nombre = vista.pedirTextoNoVacio("Ingrese nombre de actividad personal: ");
            TipoPrioridad prioridad = vista.pedirPrioridad();
            String lugar = vista.pedirTextoNoVacio("Ingrese lugar de la actividad: ");
            String fechavencimiento = vista.pedirfechaVencimiento()+" "+vista.pedirHoraVencimiento();
            String fechaActual = getFechaActual();
            String descripcion = vista.pedirTextoNoVacio("Ingrese descripcion de la actividad: ");
            int tiempoestimado = vista.pedirNumeroPositivo("Ingrese el tiempo estimado: ");
            ActividadPersonal actividadpersonal= new ActividadPersonal(nombre,prioridad,fechavencimiento,0,listaDeActividades.size()+1,tiempoestimado,fechaActual,lugar,tipo,descripcion);
            this.listaDeActividades.add(actividadpersonal);
            this.listaDeActividadesPersonales.add(actividadpersonal);}
        }
        

public void eliminarActividad() {
    vista.mostrarMensaje("--- 4. Eliminar Actividad ---");
    visualizarActividades();
    int id = vista.pedirIdActividad(listaDeActividades.size());
    if (id == 0) {
        vista.mostrarMensaje("Cancelando...");
        return; 
    }
    int indice = id - 1; 
    Actividad actividadAEliminar = listaDeActividades.get(indice);
    boolean confirmar = vista.pedirConfirmacionEliminar(actividadAEliminar.getNombre());

    if (confirmar) {
        listaDeActividades.remove(indice);
        if (actividadAEliminar instanceof ActividadAcademica) {
            listaDeActividadesAcademicas.remove(actividadAEliminar);
        } else if (actividadAEliminar instanceof ActividadPersonal) {
            listaDeActividadesPersonales.remove(actividadAEliminar);
        }
        
        vista.mostrarMensaje("Actividad '" + actividadAEliminar.getNombre() + "' eliminada con éxito.");
    } else {
        vista.mostrarMensaje("Eliminación cancelada.");
    }
}
public void visualizarActividades() {
    
    int filtro = vista.pedirFiltroActividades();
    ArrayList<Actividad> listaFiltrada = new ArrayList<>();
    
    switch (filtro) {
        case 1: 
            listaFiltrada = new ArrayList<>(this.listaDeActividades);
            break;
        case 2: 
            for (Actividad a : this.listaDeActividades) {
                if (a instanceof ActividadAcademica) {
                    listaFiltrada.add(a);
                }
            }
            break;
        case 3: 
            for (Actividad a : this.listaDeActividades) {
                if (a instanceof ActividadPersonal) {
                    listaFiltrada.add(a);
                }
            }
            break;
    }

    vista.mostrarListaActividades(listaFiltrada);


    boolean verDetalles = vista.pedirConfirmacion("¿Desea ver detalles de una actividad?");


    if (verDetalles) {

        int id = vista.pedirIdActividad(listaFiltrada.size());
        
        if (id != 0) {
            Actividad actividadADetallar = buscarActividadPorId(id, listaFiltrada);
            
            if (actividadADetallar != null) {
                vista.mostrarDetalle(actividadADetallar);
            } else {
                vista.mostrarError();
            }
        }
    
    }
    
   
}

private Actividad buscarActividadPorId(int id, ArrayList<Actividad> lista) {
    for (Actividad a : lista) {
        if (a.getId() == id) {
            return a;
        }
    }
    return null;
}
    public void registrarAvance() {
    vista.mostrarMensaje("--- 3. Registrar Avance ---");
    visualizarActividades();
    int id = vista.pedirIdActividad(listaDeActividades.size()); // Asumo que se llama así
    if (id == 0) {
        vista.mostrarMensaje("Cancelando...");
        return; 
    }

    int avance = 0;
    int validacion = 0;
    while (validacion == 0){
        int valoringresado = vista.pedirNumeroPositivo("Ingrese el nuevo porcentaje de avance");
        if (vista.verificarRango(valoringresado, 0, 100) == false){
            vista.mostrarError();}
        else{
            avance = valoringresado;
            validacion++;
        }
    }
    Actividad actividad = listaDeActividades.get(id - 1);

    actividad.setAvance(avance); 
    
    vista.mostrarMensaje("Avance de '" + actividad.getNombre() + "' actualizado a " + avance + "%.");
}
    public void gestionarActividades() {
    int opcion; 
    boolean volver = false;

    while (!volver) {
        opcion = vista.pedirOpcionGestion(); 

        switch (opcion) {
            case 1:
                visualizarActividades();
                break;
            case 2:
                crearActividad();
                break;
            case 3:
                registrarAvance();
                break;
            case 4:
                eliminarActividad();
                break;
            case 5:
                volver = true;
                vista.mostrarMensaje("Volviendo al menú principal...");
                break;
            default:
                vista.mostrarError();
        }
    }
}
    
    }


