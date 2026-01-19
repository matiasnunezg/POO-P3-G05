package espol.poo.sistemabienestarestudiantil.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadPersonal;
import espol.poo.sistemabienestarestudiantil.modelo.suenio.RegistrarHorasDeSuenio;
import espol.poo.sistemabienestarestudiantil.modelo.hidrataciones.RegistroHidratacion;

public class AppRepository {

    private static AppRepository instance;

    // --- VARIABLES DE ALMACENAMIENTO ---
    
    // Actividades 
    private List<Actividad> listaActividades;
    
    // Sueño 
    private List<RegistrarHorasDeSuenio> listaSuenio;
    
    // Hidratación
    private List<RegistroHidratacion> listaHidratacion;
    private double metaDiaria = 2500.0;
    private String fechaSeleccionadaRepo = ""; 

    // --- CONSTRUCTOR PRIVADO (SINGLETON) ---
    private AppRepository() {
        // 1. Inicializar Actividades
        listaActividades = new ArrayList<>();
        listaActividades.add(new ActividadAcademica(
                "Leer capítulo 5",
                Actividad.TipoPrioridad.Baja,
                "2026-01-19",
                70,
                1,
                120,
                LocalDate.now().toString(),
                "Física",
                ActividadAcademica.TipoActividadAcademica.Tarea,
                "Aprender teoría y practicar ejercicios"// Materia (dato extra)
        ));
        listaActividades.add(new ActividadAcademica(
                "Examen 2do Parcial",
                Actividad.TipoPrioridad.Alta,
                "2026-01-23",
                0,
                2,
                180,
                LocalDate.now().toString(),
                "Programación Orientada a Objetos",
                ActividadAcademica.TipoActividadAcademica.Proyecto,
                "Repasar teoría y practicar ejercicios"// Materia (dato extra)
        ));
        listaActividades.add(new ActividadAcademica(
                "Proyecto Intro",
                Actividad.TipoPrioridad.Alta,
                "2026-01-30",
                70,
                3,
                150,
                LocalDate.now().toString(),
                "Introducción a la Mecatrónica",
                ActividadAcademica.TipoActividadAcademica.Proyecto,
                "Realizar diapositivas y terminar maqueta"// Materia (dato extra)
        ));
        listaActividades.add(new ActividadPersonal(
                "Viaje a Montañita",
                Actividad.TipoPrioridad.Alta,
                "2026-02-15",
                0,
                4,
                4800,
                LocalDate.now().toString(),
                "Montañita",
                ActividadPersonal.TipoActividadPersonal.Hobbies,
                "Conocer sitios turísticos y ir de fiesta con amigos"// Materia (dato extra)
        ));

        // 2. Inicializar Sueño y cargar datos prueba
        listaSuenio = new ArrayList<>();
        cargarDatosPruebaSuenio();

        // 3. Inicializar Hidratación
        listaHidratacion = new ArrayList<>();
    }

    public static synchronized AppRepository getInstance() {
        if (instance == null) {
            instance = new AppRepository();
        }
        return instance;
    }

    // ==========================================
    //           MÓDULO DE ACTIVIDADES
    // ==========================================

    public List<Actividad> getListaActividades() {
        return listaActividades;
    }

    public void agregarActividad(Actividad actividad) {
        listaActividades.add(actividad);
    }

    public Actividad buscarActividadPorId(int idBuscado) {
        for (Actividad a : listaActividades) {
            if (a.getId() == idBuscado) {
                return a;
            }
        }
        return null;
    }

    public int getProximoId() {
        int maxId = 0;
        for (Actividad a : listaActividades) {
            if (a.getId() > maxId) {
                maxId = a.getId();
            }
        }
        return maxId + 1;
    }

    // ==========================================
    //            MÓDULO DE SUEÑO 
    // ==========================================

    public List<RegistrarHorasDeSuenio> getListaSuenio() {
        return listaSuenio;
    }

    public void agregarSuenio(RegistrarHorasDeSuenio registro) {
        // Agregamos al inicio para que aparezca primero en la lista
        listaSuenio.add(0, registro);
    }

    private void cargarDatosPruebaSuenio() {
        listaSuenio.add(new RegistrarHorasDeSuenio(LocalTime.of(23, 0), LocalTime.of(7, 0), LocalDate.now().minusDays(1)));
        listaSuenio.add(new RegistrarHorasDeSuenio(LocalTime.of(1, 30), LocalTime.of(6, 0), LocalDate.now().minusDays(2)));
    }

    // ==========================================
    //         MÓDULO DE HIDRATACIÓN (AMIGO)
    // ==========================================

    public List<RegistroHidratacion> getListaHidratacion() {
        return listaHidratacion;
    }

    public void agregarRegistroHidratacion(RegistroHidratacion registro) {
        listaHidratacion.add(registro);
    }

    public double getMetaDiaria() {
        return metaDiaria;
    }

    public void setMetaDiaria(double meta) {
        this.metaDiaria = meta;
    }

    public double getTotalConsumido() {
        double total = 0;
        for (RegistroHidratacion r : listaHidratacion) {
            total += r.getCantidadMl();
        }
        return total;
    }

    public String getFechaSeleccionadaRepo() {
        return fechaSeleccionadaRepo;
    }

    public void setFechaSeleccionadaRepo(String fecha) {
        this.fechaSeleccionadaRepo = fecha;
    }
}