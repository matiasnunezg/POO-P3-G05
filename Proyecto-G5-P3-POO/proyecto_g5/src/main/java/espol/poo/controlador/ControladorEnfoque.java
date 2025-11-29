package espol.poo.controlador;

import espol.poo.vista.VistaEnfoque;
import espol.poo.modelo.actividades.Actividad;
import espol.poo.modelo.actividades.ActividadAcademica;
import espol.poo.modelo.actividades.ActividadAcademica.TipoActividadAcademica; // Importa el Enum
import espol.poo.modelo.enfoques.TecnicasEnfoque;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para el módulo de Técnicas de Enfoque.
 * Contiene la lógica de negocio y coordina la Vista y el Modelo.
 */
public class ControladorEnfoque {

    private VistaEnfoque vista;
    private List<Actividad> actividades; // Referencia a la lista principal de la App
    private TecnicasEnfoque tecnicas; // Referencia a las reglas de enfoque

    /**
     * Constructor actualizado.
     * Recibe la Vista, el Modelo (lista) y las Reglas (tecnicas).
     */
    public ControladorEnfoque(VistaEnfoque vista, List<Actividad> actividades, TecnicasEnfoque tecnicas) {
        this.vista = vista;
        this.actividades = actividades;
        this.tecnicas = tecnicas;
    }

    /**
     * Método principal que inicia la gestión del menú de Enfoque.
     * Este es el bucle que muestra el menú de Enfoque (Pomodoro, Deep Work, Salir).
     */
    public void gestionarEnfoque() {
        int opcion;
        do {
            vista.mostrarMenuPrincipal();
            opcion = vista.leerOpcion();

            switch (opcion) {
                case 1:
                    iniciarPomodoro();
                    break;
                case 2:
                    iniciarDeepWork();
                    break;
                case 3:
                    vista.mostrarVolver();
                    break;
                default:
                    vista.mostrarOpcionInvalida();
                    break;
            }
        } while (opcion != 3);
    }

    /**
     * Lógica para la técnica Pomodoro (actualizada).
     */
    private void iniciarPomodoro() {
        vista.mostrarInicioPomodoro();
        Actividad actividad = seleccionarActividadParaEnfoque(); 
        if (actividad == null) return;

        // Obtiene las reglas desde el objeto tecnicas
        final int TOTAL_CICLOS = tecnicas.getPomodoroCiclosTotal();
        final int MINUTOS_TRABAJO = tecnicas.getPomodoroTrabajoMinutos();

        for (int i = 1; i <= TOTAL_CICLOS; i++) {
            vista.mostrarPomodoroCiclo(i, TOTAL_CICLOS, actividad.getNombre());
            vista.esperarEnter(); 
            vista.mostrarFinTrabajo();

            // Lógica del Modelo: Pasa los minutos de trabajo
            if (actividad instanceof ActividadAcademica) {
            ((ActividadAcademica) actividad).registrarSesion("Pomodoro", MINUTOS_TRABAJO);
            }
            
            if (i < TOTAL_CICLOS) {
                vista.mostrarDescanso();
                vista.esperarEnter(); 
                vista.mostrarFinDescanso();
                vista.esperarEnter();
            }
        }
        vista.mostrarPomodoroFinal();
        vista.esperarEnter();
    }

    /**
     * Lógica para la técnica Deep Work (actualizada).
     */
    private void iniciarDeepWork() {
        vista.mostrarInicioDeepWork();
        Actividad actividad = seleccionarActividadParaEnfoque();
        if (actividad == null) return;
        
        // Obtiene las reglas desde el objeto tecnicas
        final int MINUTOS_DEEP_WORK = tecnicas.getDeepWorkMinutos();

        vista.mostrarDeepWorkSesion(actividad.getNombre());
        vista.esperarEnter(); 
        vista.mostrarDeepWorkFinal();

        if (actividad instanceof ActividadAcademica) {
            ((ActividadAcademica) actividad).registrarSesion("Deep Work", MINUTOS_DEEP_WORK);
        }
        vista.esperarEnter();
    }

    /**
     * (ESTE ES EL MÉTODO QUE FALTABA)
     * Muestra al usuario solo las actividades que aplican para enfoque
     * (Tareas y Proyectos) y devuelve la que seleccione.
     * @return La Actividad seleccionada, o null si el usuario cancela.
     */
    private Actividad seleccionarActividadParaEnfoque() {
        // 1. Filtramos la lista principal
        List<Actividad> actividadesFiltradas = new ArrayList<>();
        
        for (Actividad a : actividades) {
            // Comprueba si es una ActividadAcademica
            if (a instanceof ActividadAcademica) {
                ActividadAcademica aa = (ActividadAcademica) a;
                
                // Obtiene el tipo (enum) de la actividad académica
                TipoActividadAcademica tipo = aa.getActividadAcademica(); 
                
                // Comprueba si es TAREA o PROYECTO y si el avance es menor a 100
                if ((tipo == TipoActividadAcademica.Tarea || tipo == TipoActividadAcademica.Proyecto) 
                        && a.getAvance() < 100) {
                    actividadesFiltradas.add(a);
                }
            }
            // Si es ActividadPersonal, simplemente la ignora
        }

        // 2. Bucle para que el usuario seleccione de la lista filtrada
        while (true) {
            // Llama al método de la vista para mostrar la lista
            vista.mostrarSeleccionActividad(actividadesFiltradas); 
            int id = vista.leerOpcion();
            
            if (id == 0) {
                return null; // El usuario quiere salir
            }

            // Busca la actividad por ID en la lista FILTRADA
            for (Actividad act : actividadesFiltradas) {
                if (act.getId() == id) {
                    return act; // Actividad encontrada
                }
            }
            
            // Si el ID no se encontró
            vista.mostrarOpcionInvalida();
        }
    }
}