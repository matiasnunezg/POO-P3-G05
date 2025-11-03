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
    public void crearActividad(){
        int opcion = vista.pedirtipoactividad();
        if (opcion == 1){
            TipoActividadAcademica tipo = vista.pedirTipoActividadAcademica();
            String nombre = vista.pedirTextoNoVacio("Ingrese nombre de actividad academica: ");
            TipoPrioridad prioridad = vista.pedirPrioridad();
            String asignatura = vista.pedirTextoNoVacio("Ingrese asignatura de la actividad: ");
            String fechavencimiento = vista.pedirfechaVencimiento();
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
            String fechavencimiento = vista.pedirfechaVencimiento();
            String fechaActual = getFechaActual();
            String descripcion = vista.pedirTextoNoVacio("Ingrese descripcion de la actividad: ");
            int tiempoestimado = vista.pedirNumeroPositivo("Ingrese el tiempo estimado: ");
            ActividadPersonal actividadpersonal= new ActividadPersonal(nombre,prioridad,fechavencimiento,0,listaDeActividades.size()+1,tiempoestimado,fechaActual,lugar,tipo,descripcion);
            this.listaDeActividades.add(actividadpersonal);
            this.listaDeActividadesPersonales.add(actividadpersonal);}
        }
        

public void eliminarActividad() {
    System.out.println("--- 4. Eliminar Actividad ---");
    visualizarActividades();
    int id = vista.pedirIdActividad(listaDeActividades.size());
    if (id == 0) {
        System.out.println("Cancelando...");
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
    System.out.println("-- LISTADO DE ACTIVIDADES PENDIENTES --");
    System.out.printf("%-3s | %-10s | %-40s | %-12s | %-10s | %-8s%n",
            "ID", "TIPO", "NOMBRE", "VENCIMIENTO", "PRIORIDAD", "AVANCE");
    
    // Ajusté esta línea para que coincida con el formato (40 guiones para NOMBRE)
    System.out.println("----|------------|----------------------------------------|--------------|------------|----------");

    // Inicia el bucle
    for (Actividad a : listaDeActividades) {
        
        if (a instanceof ActividadAcademica) {
            ActividadAcademica academica = (ActividadAcademica) a;
            String tipoAcademico = academica.getActividadAcademica().toString();
            System.out.printf("%-3s | %-10s | %-40s | %-12s | %-10s | %-8s%n",
                    String.valueOf(a.getId()), tipoAcademico, a.getNombre(), a.getFechaVencimiento(), a.getPrioridad(), a.getAvance());
        } 
        // Es mejor usar 'else if' por si en el futuro añades más tipos
        else if (a instanceof ActividadPersonal) { 
            ActividadPersonal personal = (ActividadPersonal) a;
            String tipoPersonal = personal.getActividadPersonal().toString();
            System.out.printf("%-3s | %-10s | %-40s | %-12s | %-10s | %-8s%n",
                    String.valueOf(a.getId()), tipoPersonal, a.getNombre(), a.getFechaVencimiento(), a.getPrioridad(), a.getAvance());
        }
        // La línea de guiones y la llave extra se eliminaron de aquí
        
    } // <-- Esta llave CIERRA el 'for' loop
    
    // La línea final va AQUÍ, *después* de que termine el bucle
    System.out.println("------------------------------------------------------------------------------------------------------");
    
} // <-- Esta llave CIERRA el método 'visualizarActividades'
    public void detalleActividad(){
    int id = vista.pedirIdActividad(listaDeActividades.size());
    System.out.println("==============================================================");
    System.out.println("==============================================================");
    Actividad actividadmostrada = listaDeActividades.get(id -1);
    if (actividadmostrada instanceof ActividadAcademica){
        ActividadAcademica academica = (ActividadAcademica) actividadmostrada;
        System.out.println("Detalles de "+academica.getActividadAcademica().toString());
        System.out.println("==============================================================");
        System.out.println("==============================================================");
        System.out.println("Nombre: "+actividadmostrada.getNombre());
        System.out.println("Tipo: "+academica.getActividadAcademica().toString());
        System.out.println("Asignatura: "+academica.getAsignatura());
        System.out.println("Prioridad: "+actividadmostrada.getPrioridad());
        if (actividadmostrada.getAvance() == 100){
            System.out.println("Estado: Terminada");
        }
        else{
            System.out.println("Estado: En curso");
        }
        System.out.println("Fecha Limite: "+actividadmostrada.getFechaVencimiento());
        System.out.println("Tiempo Estimado Total: "+actividadmostrada.getFechaVencimiento());
        System.out.println("Avance Actual: "+actividadmostrada.getAvance());}
    else{
        ActividadPersonal personal = (ActividadPersonal) actividadmostrada;
        System.out.println("Detalles de "+personal.getActividadPersonal());
        System.out.println("==============================================================");
        System.out.println("==============================================================");
        System.out.println("Nombre: "+actividadmostrada.getNombre());
        System.out.println("Tipo: "+personal.getActividadPersonal().toString());
        System.out.println("Lugar: "+personal.getLugar());
        System.out.println("Prioridad: "+actividadmostrada.getPrioridad());
        if (actividadmostrada.getAvance() == 100){
            System.out.println("Estado: Terminada");
        }
        else{
            System.out.println("Estado: En curso");
        }
        System.out.println("Fecha Limite: "+actividadmostrada.getFechaVencimiento());
        System.out.println("Tiempo Estimado Total: "+actividadmostrada.getFechaVencimiento());
        System.out.println("Avance Actual: "+actividadmostrada.getAvance());}
    }
    public void registrarAvance() {
    System.out.println("--- 3. Registrar Avance ---");
    visualizarActividades();
    int id = vista.pedirIdActividad(listaDeActividades.size()); // Asumo que se llama así
    if (id == 0) {
        System.out.println("Cancelando...");
        return; 
    }

    int avance = 0;
    int validacion = 0;
    while (validacion == 0){
        int valoringresado = vista.pedirNumeroPositivo("Ingreso el nuevo porcentaje de avance");
        if (vista.verificarRango(valoringresado, 0, 100) == false){
            System.out.println("Número no admisible");}
        else{
            avance = valoringresado;
            validacion++;
        }
    }
    // 4. Obtén el objeto (Recuerda restar 1 al ID)
    Actividad actividad = listaDeActividades.get(id - 1);

    // 5. ¡LA SINTAXIS CORRECTA!
    actividad.setAvance(avance); // Se llama 'setAvance', no 'set'
    
    System.out.println("Avance de '" + actividad.getNombre() + "' actualizado a " + avance + "%.");
    // (Opcional: llamar a vista.mostrarMensaje("Avance actualizado..."))
}
    public void gestionarActividades() {
    // Saca la declaración de 'opcion' fuera del bucle
    int opcion; 
    boolean volver = false;

    while (!volver) {
        
        // Mueve la llamada a la vista AQUÍ, DENTRO del bucle
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
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                // Asumo que tienes un método así en tu vista
                // vista.mostrarError("Opción no válida. Intente de nuevo."); 
                System.out.println("Opción no válida. Intente de nuevo.");
        }
    }
}
        
    }


