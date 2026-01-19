package espol.poo.sistemabienestarestudiantil.data;

import android.content.Context;
import java.io.*;
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
    private static Context appContext; // Necesario para acceder al almacenamiento de Android
    private static final String FILE_NAME = "hidratacion_data.dat"; // Archivo binario

    // --- VARIABLES DE ALMACENAMIENTO ---
    private List<Actividad> listaActividades;
    private List<RegistrarHorasDeSuenio> listaSuenio;
    private List<RegistroHidratacion> listaHidratacion;
    private double metaDiaria = 2500.0;
    private String fechaSeleccionadaRepo = "";

    // --- CONSTRUCTOR PRIVADO (SINGLETON) ---
    private AppRepository() {
        // 1. Inicializar Actividades con tus datos originales
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
                "Aprender teoría y practicar ejercicios"
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
                "Repasar teoría y practicar ejercicios"
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
                "Realizar diapositivas y terminar maqueta"
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
                "Conocer sitios turísticos y ir de fiesta con amigos"
        ));

        // 2. Inicializar Sueño
        listaSuenio = new ArrayList<>();
        cargarDatosPruebaSuenio();

        // 3. Inicializar Hidratación cargando desde disco si existe
        listaHidratacion = cargarHidratacionDeDisco();
    }

    public static synchronized AppRepository getInstance() {
        if (instance == null) {
            instance = new AppRepository();
        }
        return instance;
    }

    // Método para inicializar el contexto desde MainActivity
    public static void initContext(Context context) {
        appContext = context.getApplicationContext();
    }

    // ==========================================
    //       LÓGICA DE SERIALIZACIÓN (IO)
    // ==========================================

    private void guardarHidratacionEnDisco() {
        if (appContext == null) return;
        try (FileOutputStream fos = appContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(listaHidratacion);
            oos.writeDouble(metaDiaria);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<RegistroHidratacion> cargarHidratacionDeDisco() {
        if (appContext == null) return new ArrayList<>();

        File file = new File(appContext.getFilesDir(), FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (FileInputStream fis = appContext.openFileInput(FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            List<RegistroHidratacion> datos = (ArrayList<RegistroHidratacion>) ois.readObject();
            this.metaDiaria = ois.readDouble();
            return datos;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ==========================================
    // MÓDULO DE ACTIVIDADES
    // ==========================================

    public List<Actividad> getListaActividades() { return listaActividades; }
    public void agregarActividad(Actividad actividad) { listaActividades.add(actividad); }
    public Actividad buscarActividadPorId(int idBuscado) {
        for (Actividad a : listaActividades) {
            if (a.getId() == idBuscado) return a;
        }
        return null;
    }
    public int getProximoId() {
        int maxId = 0;
        for (Actividad a : listaActividades) {
            if (a.getId() > maxId) maxId = a.getId();
        }
        return maxId + 1;
    }

    // ==========================================
    // MÓDULO DE SUEÑO
    // ==========================================

    public List<RegistrarHorasDeSuenio> getListaSuenio() { return listaSuenio; }
    public void agregarSuenio(RegistrarHorasDeSuenio registro) { listaSuenio.add(0, registro); }
    private void cargarDatosPruebaSuenio() {
        listaSuenio.add(new RegistrarHorasDeSuenio(LocalTime.of(23, 0), LocalTime.of(7, 0), LocalDate.now().minusDays(1)));
        listaSuenio.add(new RegistrarHorasDeSuenio(LocalTime.of(1, 30), LocalTime.of(6, 0), LocalDate.now().minusDays(2)));
    }

    // ==========================================
    // MÓDULO DE HIDRATACIÓN
    // ==========================================

    public List<RegistroHidratacion> getListaHidratacion() {
        return listaHidratacion;
    }

    public void agregarRegistroHidratacion(RegistroHidratacion registro) {
        listaHidratacion.add(registro);
        guardarHidratacionEnDisco(); // Auto-guardado
    }

    public double getMetaDiaria() { return metaDiaria; }

    public void setMetaDiaria(double meta) {
        this.metaDiaria = meta;
        guardarHidratacionEnDisco(); // Auto-guardado
    }

    public double getTotalConsumido() {
        double total = 0;
        for (RegistroHidratacion r : listaHidratacion) {
            total += r.getCantidadMl();
        }
        return total;
    }

    public String getFechaSeleccionadaRepo() { return fechaSeleccionadaRepo; }
    public void setFechaSeleccionadaRepo(String fecha) { this.fechaSeleccionadaRepo = fecha; }

}