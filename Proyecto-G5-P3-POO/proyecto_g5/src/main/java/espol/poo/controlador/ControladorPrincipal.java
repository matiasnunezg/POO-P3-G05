package espol.poo.controlador;

// --- VISTAS ---
import espol.poo.vista.VistaPrincipal;
import espol.poo.vista.VistaActividad;
import espol.poo.vista.VistaHidratacion;
import espol.poo.vista.VistaEnfoque; // <<< Importado
import espol.poo.vista.VistaJuegoMemoriaEco;
// <<< Importado
// --- MODELOS ---
import espol.poo.modelo.*;
import espol.poo.modelo.Actividad.TipoPrioridad;
import espol.poo.modelo.ActividadAcademica.TipoActividadAcademica;
import espol.poo.modelo.ActividadPersonal.TipoActividadPersonal;

// --- UTILIDADES ---
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;


public class ControladorPrincipal {
    private VistaPrincipal vistaPrincipal;
    private VistaActividad vistaActividad;
    private VistaEnfoque vistaEnfoque;
    private VistaHidratacion vistaHidratacion;
    
    // Repositorios globales
    private ArrayList<Actividad> listaDeActividades;
    private ArrayList<RegistroHidratacion> registrosHidratacion;
    private ArrayList<RegistrarHorasDeSuenio> registrosSueno;
    private TecnicasEnfoque tecnicas;

    // Controladores secundarios
    private ControladorActividades controladorActividad;
    private ControlHidratacion controladorHidratacion;
    private ControladorJuegoMemoriaEco controladorJuegoEco;
    private ControladorEnfoque controladorEnfoque;    
    private ControladorSuenio controladorSuenio;

    public ControladorPrincipal() {
        this.vistaPrincipal = new VistaPrincipal();
        this.vistaActividad = new VistaActividad();
        this.vistaEnfoque = new VistaEnfoque();
        this.vistaHidratacion = new VistaHidratacion();

        // Inicializar Modelos (Listas y Reglas)
        this.listaDeActividades = new ArrayList<>();
        this.registrosHidratacion = new ArrayList<>();
        this.registrosSueno = new ArrayList<>();
        this.tecnicas = new TecnicasEnfoque();
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
        
    
    RegistroHidratacion registro1 = new RegistroHidratacion(
        2500.0,        // meta diaria (ml)
        500.0,         // cantidad ingerida
        500.0,         // acumulado diario
        java.time.LocalDate.of(2025, 11, 23), // fecha
        java.time.LocalTime.of(9, 30)         // hora
    );

    RegistroHidratacion registro2 = new RegistroHidratacion(
        2500.0,        // meta diaria (ml)
        300.0,         // cantidad ingerida
        800.0,         // acumulado diario (suma con la anterior)
        java.time.LocalDate.of(2025, 11, 24), // fecha
        java.time.LocalTime.of(11, 0)         // hora
    );

    registrosHidratacion.add(registro1);
    registrosHidratacion.add(registro2);

RegistrarHorasDeSuenio sueno_registro1 = new RegistrarHorasDeSuenio(
        LocalTime.of(23, 30),
        LocalTime.of(7, 0)
);
RegistrarHorasDeSuenio sueno_registro2 = new RegistrarHorasDeSuenio(
        LocalTime.of(0, 15),
        LocalTime.of(8, 0)
);

// Simulamos fechas 23 y 24 de noviembre manualmente
// (esto solo afecta la visualización, no la lógica)
try {
    java.lang.reflect.Field fechaField = RegistrarHorasDeSuenio.class.getDeclaredField("fechaRegistro");
    fechaField.setAccessible(true);
    fechaField.set(sueno_registro1, LocalDate.of(2025, 11, 23));
    fechaField.set(sueno_registro2, LocalDate.of(2025, 11, 24));
} catch (Exception e) {
    e.printStackTrace();
    }

    registrosSueno.add(sueno_registro1);
    registrosSueno.add(sueno_registro2);

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
                case 2 -> abrirTecnicasEnfoque();
                case 3 -> abrirControlHidratacion();
                case 4 -> abrirRegistroSueno();
                case 5 -> System.out.println("\nSostenibilidad: pendiente de implementar.");
                case 6 -> abrirJuegoMemoria();
                case 7 -> System.out.println("\nSaliendo del sistema...");
                default -> System.out.println("Opción no válida, intente nuevamente.");
            }
        } while (opcion != 7);
    }

    // ==========================
    // Métodos de apertura
    // ==========================
    private void abrirGestionActividades() {
        if (controladorActividad == null) {
            controladorActividad = new ControladorActividades(vistaActividad, listaDeActividades);
        }
        controladorActividad.gestionarActividades();
    }

    private void abrirControlHidratacion() {
        VistaHidratacion vistaHidratacion = new VistaHidratacion();
        controladorHidratacion = new ControlHidratacion(vistaHidratacion);
        controladorHidratacion.getRegistros().addAll(registrosHidratacion);
        controladorHidratacion.gestionarHidratacion();
}

    private void abrirJuegoMemoria() {
        VistaJuegoMemoriaEco vistaJuego = new VistaJuegoMemoriaEco();
        controladorJuegoEco = new ControladorJuegoMemoriaEco(vistaJuego);
        controladorJuegoEco.iniciarJuego();
}

    private void abrirRegistroSueno() {
    if (controladorSuenio == null) {
        controladorSuenio = new ControladorSuenio();
        // Cargamos los registros preexistentes
        for (RegistrarHorasDeSuenio r : registrosSueno) {
            controladorSuenio.registrarManual(r);
        }
    }
    controladorSuenio.gestionarSuenio();
    }

    public void registrarManual(RegistrarHorasDeSuenio registroSueno) {
        registrosSueno.add(registroSueno);
    }

    private void abrirTecnicasEnfoque() {
        if (controladorEnfoque == null) {
            // Inyección de Dependencias
            controladorEnfoque = new ControladorEnfoque(vistaEnfoque, listaDeActividades, tecnicas);
        }
        controladorEnfoque.gestionarEnfoque();
    }


}
