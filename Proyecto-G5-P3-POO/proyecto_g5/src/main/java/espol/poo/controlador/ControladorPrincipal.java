package espol.poo.controlador;

// --- VISTAS ---
import espol.poo.vista.VistaPrincipal;
import espol.poo.vista.VistaActividad;
import espol.poo.vista.VistaHidratacion;
import espol.poo.vista.VistaEnfoque;
import espol.poo.vista.VistaJuegoMemoriaEco;

// --- MODELOS ---
import espol.poo.modelo.actividades.Actividad;
import espol.poo.modelo.actividades.ActividadAcademica;
import espol.poo.modelo.actividades.ActividadPersonal;
import espol.poo.modelo.actividades.Actividad.TipoPrioridad;
import espol.poo.modelo.actividades.ActividadAcademica.TipoActividadAcademica;
import espol.poo.modelo.actividades.ActividadPersonal.TipoActividadPersonal;
import espol.poo.modelo.enfoques.SesionEnfoque;
import espol.poo.modelo.enfoques.TecnicasEnfoque;
import espol.poo.modelo.hidrataciones.RegistroHidratacion;
import espol.poo.modelo.sostenibilidad.RegistroDiarioSostenible;
import espol.poo.modelo.sostenibilidad.RegistroSostenible;
import espol.poo.modelo.suenio.RegistrarHorasDeSuenio;

// --- UTILIDADES ---
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ControladorPrincipal {

    private VistaPrincipal vistaPrincipal;
    private VistaActividad vistaActividad;
    private VistaEnfoque vistaEnfoque;
    private VistaHidratacion vistaHidratacion;
    private RegistroSostenible registroSostenible;

    // Repositorios globales
<<<<<<< Updated upstream
    private ArrayList<Actividad> listaDeActividades;
    private ArrayList<RegistroHidratacion> registrosHidratacion;
    private ArrayList<RegistrarHorasDeSuenio> registrosSueno;
    private TecnicasEnfoque tecnicas;
=======
    private ArrayList<Actividad> listaDeActividades = new ArrayList<>();
    private ArrayList<ActividadAcademica> listaDeActividadesAcademicas = new ArrayList<>();
    private ArrayList<ActividadPersonal> listaDeActividadesPersonales = new ArrayList<>();
    
    //private List<RegistroHidratacion> registrosHidratacion;   
    //private List<RegistrarHorasDeSueno> registrosSueno; //Falta implementar
    //private List<RegistroDiarioSostenible> registrosSostenibilidad; //Falta implementar
>>>>>>> Stashed changes

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
        this.registroSostenible = new RegistroSostenible();

        // Inicializar Modelos (Listas y Reglas)
        this.listaDeActividades = new ArrayList<>();
        this.registrosHidratacion = new ArrayList<>();
        this.registrosSueno = new ArrayList<>();
        this.tecnicas = new TecnicasEnfoque();
    }

    public void inicializarApp() {
        System.out.println("Inicializando aplicación...");

        // 1) Actividad Personal: Cita médica (30 nov)
        ActividadPersonal citaMedica = new ActividadPersonal(
                "Cita médica",
                TipoPrioridad.Alta,
                "30/11/2025 17:00",
                0, // avance
                1, // id
                45, // tiempoEstimado
                "30/11/2025",
                "Clínica Central",
                TipoActividadPersonal.Citas,
                "Revisión general con el doctor"
        );
        listaDeActividades.add(citaMedica);

        // 2) Proyecto académico con 70% de avance
        ActividadAcademica proyecto = new ActividadAcademica(
                "Sistema Gestión Tiempo (Fase 1)",
                TipoPrioridad.Alta,
                "30/11/2025 23:59",
                68,  // >>> AVANCE INICIAL = 68%
                2,
                3600, // tiempo estimado total (min)
                "28/11/2025",
                "Programación Orientada a Objetos",
                TipoActividadAcademica.Proyecto,
                "Implementación de la lógica de POO en Java"
        );

        // >>> AGREGO LAS 2 SESIONES POMODORO AL PROYECTO PARA LLEGAR A 70%
        proyecto.registrarSesion("Pomodoro", 25);
        proyecto.registrarSesion("Pomodoro", 25);

        // ====== SIMULAR FECHAS ANTIGUAS EN CADA SESIÓN ======
        List<SesionEnfoque> historial = proyecto.getHistorialSesiones();

        try {
            java.lang.reflect.Field fechaField = SesionEnfoque.class.getDeclaredField("fecha");
            fechaField.setAccessible(true);

            // Primera sesión → 27/11/2025
            fechaField.set(historial.get(0), java.time.LocalDate.of(2025, 11, 27));

            // Segunda sesión → 28/11/2025
            fechaField.set(historial.get(1), java.time.LocalDate.of(2025, 11, 28));

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaDeActividades.add(proyecto);

        // 3) Tarea académica
        ActividadAcademica tarea = new ActividadAcademica(
                "Cuestionario Unidad 2",
                TipoPrioridad.Media,
                "03/12/2025 23:59",
                0, // avance
                3,
                90,
                "27/11/2025",
                "Matemática Discreta",
                TipoActividadAcademica.Tarea,
                "Entrega en Aula Virtual"
        );
        listaDeActividades.add(tarea);

        // 4) Examen académico
        ActividadAcademica examen = new ActividadAcademica(
                "Examen Parcial",
                TipoPrioridad.Alta,
                "10/12/2025 09:00",
                0,
                4,
                120,
                "26/11/2025",
                "Cálculo Avanzado",
                TipoActividadAcademica.Examen,
                "Evaluación parcial de la materia"
        );
        listaDeActividades.add(examen);

        RegistroHidratacion registro1 = new RegistroHidratacion(
                2500.0,                     // meta diaria (ml)
                500.0,                      // cantidad ingerida en ese momento
                500.0,                      // acumulado diario
                java.time.LocalDate.of(2025, 11, 23), // FECHA SIMULADA
                java.time.LocalTime.of(9, 30)         // HORA SIMULADA
        );

        RegistroHidratacion registro2 = new RegistroHidratacion(
                2500.0,                     // meta diaria (ml)
                300.0,                      // cantidad ingerida en ese momento
                800.0,                      // acumulado diario
                java.time.LocalDate.of(2025, 11, 24), // FECHA SIMULADA
                java.time.LocalTime.of(11, 0)         // HORA SIMULADA
        );

        registrosHidratacion.add(registro1);
        registrosHidratacion.add(registro2);

        // --- CARGA DE DATOS DE SUEÑO ---
        RegistrarHorasDeSuenio sueno_registro1 = new RegistrarHorasDeSuenio(
                LocalTime.of(23, 30),
                LocalTime.of(4, 0)
        );
        RegistrarHorasDeSuenio sueno_registro2 = new RegistrarHorasDeSuenio(
                LocalTime.of(0, 15),
                LocalTime.of(8, 0)
        );


        try {
            java.lang.reflect.Field fechaField = RegistrarHorasDeSuenio.class.getDeclaredField("fechaRegistro");
            fechaField.setAccessible(true);
            fechaField.set(sueno_registro1, LocalDate.of(2025, 11, 28));
            fechaField.set(sueno_registro2, LocalDate.of(2025, 11, 29));
        } catch (Exception e) {
            e.printStackTrace();
        }

        registrosSueno.add(sueno_registro1);
        registrosSueno.add(sueno_registro2);

        // --- CARGA DE DATOS DE SOSTENIBILIDAD (23 y 24 de noviembre) ---

        // Día 23/11/2025 → Acciones: Transporte público (1), Reciclaje (4)
        List<Integer> acciones23 = new ArrayList<>();
        acciones23.add(1);
        acciones23.add(4);
        RegistroDiarioSostenible s23 = new RegistroDiarioSostenible(
                LocalDate.of(2025, 11, 23),
                acciones23
        );
        registroSostenible.agregarRegistro(s23);

        // Día 24/11/2025 → Acciones: No imprimir (2), No usar desechables (3)
        List<Integer> acciones24 = new ArrayList<>();
        acciones24.add(2);
        acciones24.add(3);
        RegistroDiarioSostenible s24 = new RegistroDiarioSostenible(
                LocalDate.of(2025, 11, 24),
                acciones24
        );
        registroSostenible.agregarRegistro(s24);

        System.out.println("Datos iniciales cargados correctamente.\n");
    }

    // Menú principal
    public void iniciarMenuPrincipal() {
        int opcion;
        inicializarApp(); // Carga los datos
        do {
            vistaPrincipal.mostrarMenu();
            opcion = vistaPrincipal.leerOpcion();

            switch (opcion) {
                case 1 -> abrirGestionActividades();
                case 2 -> abrirTecnicasEnfoque();
                case 3 -> abrirControlHidratacion();
                case 4 -> abrirRegistroSueno();
                case 5 -> abrirSostenibilidad();
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
        // Asegúrate de usar la misma instancia si es posible, o recargar la lista
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
            
            // Cargamos los registros que inicializamos en 'inicializarApp'
            // hacia el controlador de sueño.
            for (RegistrarHorasDeSuenio r : registrosSueno) {
                // CORRECCIÓN: Usamos 'agregarRegistroManual' que definimos en ControladorSuenio
                controladorSuenio.agregarRegistroManual(r);
            }
        }
        controladorSuenio.gestionarSuenio();
    }

    // Este método es del ControladorPrincipal (para añadir a su propia lista)
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


    private void abrirSostenibilidad() {
    ControladorSostenibilidad controladorSost = new ControladorSostenibilidad(registroSostenible);
    controladorSost.iniciarRegistroDiario();
    }

}