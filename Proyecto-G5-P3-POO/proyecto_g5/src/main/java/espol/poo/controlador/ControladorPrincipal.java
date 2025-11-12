package espol.poo.controlador;
import espol.poo.vista.VistaPrincipal;
import espol.poo.modelo.*;
import espol.poo.modelo.Actividad.TipoPrioridad;
import espol.poo.modelo.ActividadAcademica.TipoActividadAcademica;
import espol.poo.modelo.ActividadPersonal.TipoActividadPersonal;
import java.util.*;
import espol.poo.vista.VistaHidratacion;
import espol.poo.controlador.ControlHidratacion;


public class ControladorPrincipal {
    private VistaPrincipal vistaPrincipal;

    // Repositorios globales
    private ArrayList<Actividad> listaDeActividades = new ArrayList<>();
    private ArrayList<ActividadAcademica> listaDeActividadesAcademicas = new ArrayList<>();
    private ArrayList<ActividadPersonal> listaDeActividadesPersonales = new ArrayList<>();
    private ArrayList<RegistroHidratacion> registrosHidratacion = new ArrayList<>();

    
    //private List<RegistroHidratacion> registrosHidratacion;
    //private List<RegistrarHorasDeSueno> registrosSueno; //Falta implementar
    //private List<RegistroDiarioSostenible> registrosSostenibilidad; //Falta implementar

    // Controladores secundarios
    private ControladorActividades controladorActividad;
    private ControlHidratacion controladorHidratacion;

    // private ControladorHidratacion controladorHidratacion; 
    // private ControladorSueno controladorSueno;
    // private ControladorSostenibilidad controladorSostenibilidad; 
    // private ControladorEnfoque controladorEnfoque; 
    // private ControladorJuego controladorJuego; 

    public ControladorPrincipal() {
        this.vistaPrincipal = new VistaPrincipal();
        // Comentado por ahora 
        //this.actividades = new ArrayList<>(); 
        //this.registrosHidratacion = new ArrayList<>();
        //this.registrosSueno = new ArrayList<>();
        //this.registrosSostenibilidad = new ArrayList<>();
    }

    public void inicializarApp() {
        System.out.println("Inicializando aplicación...");

        // Actividades personales y académicas (de ejemplo)
    ActividadPersonal citaMedica = new ActividadPersonal(
            "Cita médica",                 // nombre
            TipoPrioridad.Alta,            // prioridad
            "30/11/2025 17:00",            // fechaVencimiento
            0,                             // avance
            1,                             // id
            45,                            // tiempoEstimado (min)
            "30/11/2025",                  // fechaActual (puedes ajustar)
            "Clínica Central",             // lugar
            TipoActividadPersonal.Citas, // actividadPersonal
            "Revisión general con el doctor" // descripcion
    );
        listaDeActividades.add(citaMedica);
        listaDeActividadesPersonales.add(citaMedica);

    ActividadAcademica proyecto = new ActividadAcademica(
            "Sistema Gestión Tiempo (Fase 1)",      // nombre
            TipoPrioridad.Alta,                     // prioridad
            "25/11/2025 23:59",                     // fechaVencimiento
            0,                                     // avance (%)
            2,                                      // id
            60,                                     // tiempoEstimado (horas o min según tu modelo)
            "25/11/2025",                           // fechaActual
            "Programación Orientada a Objetos",     // asignatura
            TipoActividadAcademica.Proyecto, // actividadAcademica
            "Implementación de la lógica de POO en Java" // descrpcion
    );
        listaDeActividades.add(proyecto);
        listaDeActividadesAcademicas.add(proyecto);

    ActividadAcademica tarea = new ActividadAcademica(
            "Cuestionario Unidad 2",          // nombre
            TipoPrioridad.Media,              // prioridad
            "03/12/2025 23:59",               // fechaVencimiento
            0,                                // avance
            3,                                // id
            120,                              // tiempoEstimado
            "03/12/2025",                     // fechaActual
            "Matemática Discreta",            // asignatura
            TipoActividadAcademica.Tarea, // actividadAcademica
            "Entrega en Aula Virtual"         // descrpcion
    );
        listaDeActividades.add(tarea);
        listaDeActividadesAcademicas.add(tarea);

    ActividadAcademica examen = new ActividadAcademica(
            "Examen Parcial",                 // nombre
            TipoPrioridad.Alta,               // prioridad
            "10/12/2025 09:00",               // fechaVencimiento
            0,                               // avance
            4,                                // id
            120,                              // tiempoEstimado
            "10/12/2025",                     // fechaActual
            "Cálculo Avanzado",               // asignatura
            TipoActividadAcademica.Examen, // actividadAcademica
            "Evaluación parcial de la materia" // descrpcion
    );
        listaDeActividades.add(examen);
        listaDeActividadesAcademicas.add(examen);
    
    RegistroHidratacion registro1 = new RegistroHidratacion(
        2000.0,        // meta diaria (ml)
        500.0,         // cantidad ingerida
        500.0,         // acumulado diario
        java.time.LocalDate.of(2025, 11, 23), // fecha
        java.time.LocalTime.of(9, 30)         // hora
    );

    RegistroHidratacion registro2 = new RegistroHidratacion(
        2000.0,        // meta diaria (ml)
        300.0,         // cantidad ingerida
        800.0,         // acumulado diario (suma con la anterior)
        java.time.LocalDate.of(2025, 11, 24), // fecha
        java.time.LocalTime.of(11, 0)         // hora
    );

    registrosHidratacion.add(registro1);
    registrosHidratacion.add(registro2);
    // Registros simulados (si tus clases de registro usan otros parámetros, avísame)
    //registrosHidratacion.add(new RegistroHidratacion("23/11/2025", "09:30", 500));
    //registrosHidratacion.add(new RegistroHidratacion("24/11/2025", "11:00", 300));

    //registrosSueno.add(new RegistrarHorasDeSueno("23/11/2025", "23:30", "07:00", 7.5));
    //registrosSueno.add(new RegistrarHorasDeSueno("24/11/2025", "00:15", "08:00", 7.75));

    //registrosSostenibilidad.add(new RegistroDiarioSostenible("23/11/2025", true, true, false, true));
    //registrosSostenibilidad.add(new RegistroDiarioSostenible("24/11/2025", false, true, true, true));

    System.out.println("Datos iniciales cargados correctamente.\n");
    }

    // Menú principal
    public void iniciarMenuPrincipal() {
        int opcion;
        inicializarApp();
        do {
            vistaPrincipal.mostrarMenu();
            opcion = vistaPrincipal.leerOpcion();

            switch (opcion) {
                case 1 -> abrirGestionActividades();
                case 2 -> System.out.println("Técnicas de enfoque: pendiente de implementar.");
                case 3 -> abrirControlHidratacion();
                case 4 -> System.out.println("Registro de sueño: pendiente de implementar.");
                case 5 -> System.out.println("Sostenibilidad: pendiente de implementar.");
                case 6 -> System.out.println("Juego de memoria: pendiente de implementar.");
                case 7 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida, intente nuevamente.");
            }
        } while (opcion != 7);
    }

    // ==========================
    // Métodos de apertura
    // ==========================
    private void abrirGestionActividades() {
        if (controladorActividad == null) {
            controladorActividad = new ControladorActividades(listaDeActividades,listaDeActividadesAcademicas,listaDeActividadesPersonales);
        }
        controladorActividad.gestionarActividades();
    }

    private void abrirControlHidratacion() {
        VistaHidratacion vistaHidratacion = new VistaHidratacion();
        controladorHidratacion = new ControlHidratacion(vistaHidratacion);
        controladorHidratacion.getRegistros().addAll(registrosHidratacion);
        controladorHidratacion.gestionarHidratacion();
}

    /*
    private void abrirTecnicasEnfoque() {
        if (controladorEnfoque == null) {
            controladorEnfoque = new ControladorEnfoque(actividades);
        }
        controladorEnfoque.mostrarMenu();
    }

    private void abrirControlHidratacion() {
        if (controladorHidratacion == null) {
            controladorHidratacion = new ControladorHidratacion(registrosHidratacion);
        }
        controladorHidratacion.mostrarMenu();
    }

    private void abrirRegistroSueno() {
        if (controladorSueno == null) {
            controladorSueno = new ControladorSueno(registrosSueno);
        }
        controladorSueno.mostrarMenu();
    }

    private void abrirRegistroSostenibilidad() {
        if (controladorSostenibilidad == null) {
            controladorSostenibilidad = new ControladorSostenibilidad(registrosSostenibilidad);
        }
        controladorSostenibilidad.mostrarMenu();
    }

    private void abrirJuegoMemoria() {
        if (controladorJuego == null) {
            controladorJuego = new ControladorJuego();
        }
        controladorJuego.iniciarJuego();
    }
    */

}
