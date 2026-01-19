package espol.poo.sistemabienestarestudiantil.data;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Importaciones de Modelos
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadPersonal;
import espol.poo.sistemabienestarestudiantil.modelo.suenio.RegistrarHorasDeSuenio;
import espol.poo.sistemabienestarestudiantil.modelo.hidrataciones.RegistroHidratacion;
import espol.poo.sistemabienestarestudiantil.modelo.sostenibilidad.RegistroDiarioSostenible;

public class AppRepository {

    private static AppRepository instance;
    private Context context;

    // Archivos
    private final String FILE_ACTIVIDADES = "actividades.ser";
    private final String FILE_SUENIO = "historial_suenio.ser";

    // --- VARIABLES DE ALMACENAMIENTO ---
    private List<Actividad> listaActividades;
    private List<RegistrarHorasDeSuenio> listaSuenio;
    private List<RegistroHidratacion> listaHidratacion;
    private List<RegistroDiarioSostenible> listaSostenibilidad;

    // Variables extras
    private double metaDiaria = 2500.0;
    private String fechaSeleccionadaRepo = "";

    // --- CONSTRUCTOR PRIVADO ---
    private AppRepository(Context context) {
        this.context = context;

        // 1. ACTIVIDADES (Corregido con los datos reales de tus compañeros)
        listaActividades = new ArrayList<>();
        if (context != null) {
            cargarActividadesDelArchivo();
        } else {
            // Si no hay archivo, cargamos los datos obligatorios
            cargarDatosOriginalesActividades();
        }

        // 2. SUEÑO (Tu parte)
        listaSuenio = new ArrayList<>();
        if (context != null) {
            cargarSuenioDelArchivo();
        } else {
            cargarDatosPruebaSuenio();
        }

        // 3. HIDRATACIÓN (Seguro)
        listaHidratacion = new ArrayList<>();

        // 4. SOSTENIBILIDAD (Seguro)
        listaSostenibilidad = new ArrayList<>();
        try {
            List<Integer> idsAyer = new ArrayList<>();
            idsAyer.add(1); idsAyer.add(2); idsAyer.add(3); idsAyer.add(4);
            listaSostenibilidad.add(new RegistroDiarioSostenible(LocalDate.now().minusDays(1), idsAyer));
        } catch (Exception e) { }
    }

    // --- SINGLETON HÍBRIDO ---
    public static synchronized AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository(context.getApplicationContext());
        }
        if (instance.context == null && context != null) {
            instance.context = context.getApplicationContext();
            instance.cargarSuenioDelArchivo();
        }
        return instance;
    }

    public static synchronized AppRepository getInstance() {
        if (instance == null) {
            instance = new AppRepository(null);
        }
        return instance;
    }

    // ==========================================
    // MÓDULO DE SUEÑO
    // ==========================================

    public List<RegistrarHorasDeSuenio> getListaSuenio() { return listaSuenio; }

    public void agregarSuenio(RegistrarHorasDeSuenio registro) {
        // Anti-Duplicados
        RegistrarHorasDeSuenio duplicado = null;
        for (RegistrarHorasDeSuenio r : listaSuenio) {
            if (r.getFechaRegistro().isEqual(registro.getFechaRegistro())) {
                duplicado = r;
                break;
            }
        }
        if (duplicado != null) listaSuenio.remove(duplicado);

        listaSuenio.add(0, registro);
        if (context != null) guardarSuenioEnArchivo();
    }

    private void cargarDatosPruebaSuenio() {
        if (listaSuenio.isEmpty()) {
            listaSuenio.add(new RegistrarHorasDeSuenio(LocalTime.of(22, 0), LocalTime.of(6, 0), LocalDate.of(2026, 1, 18)));
            listaSuenio.add(new RegistrarHorasDeSuenio(LocalTime.of(14, 0), LocalTime.of(16, 0), LocalDate.of(2026, 1, 18)));
        }
    }

    private void guardarSuenioEnArchivo() {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_SUENIO, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listaSuenio);
            oos.close(); fos.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    private void cargarSuenioDelArchivo() {
        try {
            FileInputStream fis = context.openFileInput(FILE_SUENIO);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listaSuenio = (List<RegistrarHorasDeSuenio>) ois.readObject();
            ois.close(); fis.close();
        } catch (Exception e) {
            listaSuenio = new ArrayList<>();
            cargarDatosPruebaSuenio();
            guardarSuenioEnArchivo();
        }
    }

    // ==========================================
    // MÓDULO DE ACTIVIDADES (ADAPTADO A TUS COMPAÑEROS)
    // ==========================================

    public List<Actividad> getListaActividades() { return listaActividades; }

    public void agregarActividad(Actividad actividad) {
        listaActividades.add(actividad);
        if (context != null) guardarActividadesEnArchivo();
    }

    public void eliminarActividad(int id) {
        listaActividades.removeIf(a -> a.getId() == id);
        if (context != null) guardarActividadesEnArchivo();
    }

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

    private void guardarActividadesEnArchivo() {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_ACTIVIDADES, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listaActividades);
            oos.close(); fos.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    private void cargarActividadesDelArchivo() {
        try {
            FileInputStream fis = context.openFileInput(FILE_ACTIVIDADES);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listaActividades = (List<Actividad>) ois.readObject();
            ois.close(); fis.close();
        } catch (Exception e) {
            listaActividades = new ArrayList<>();
            cargarDatosOriginalesActividades();
            if (context != null) guardarActividadesEnArchivo();
        }
    }

    // --- CORRECCIÓN EXACTA SEGÚN TUS ARCHIVOS ---
    private void cargarDatosOriginalesActividades() {
        try {
            // 1. Actividad Personal: Usamos .Hobbies (vimos en tu código que existe)
            // Constructor: nombre, prioridad, fechaVenc, avance, id, tiempo, fechaActual, lugar, tipoPersonal, desc
            listaActividades.add(new ActividadPersonal(
                    "Viaje a Montañita",
                    Actividad.TipoPrioridad.Media,
                    "2026-02-15",
                    0,
                    1,
                    4800,
                    LocalDate.now().toString(),
                    "Montañita",
                    ActividadPersonal.TipoActividadPersonal.Hobbies, // <-- Correcto
                    "Viaje con amigos"
            ));

            // 2. Tarea
            listaActividades.add(new ActividadAcademica(
                    "Leer capítulo 5",
                    Actividad.TipoPrioridad.Baja,
                    "2026-01-19",
                    70,
                    2,
                    120,
                    LocalDate.now().toString(),
                    "Física",
                    ActividadAcademica.TipoActividadAcademica.Tarea, // <-- Correcto
                    "Teoría"
            ));

            // 3. Examen
            listaActividades.add(new ActividadAcademica(
                    "Examen Parcial",
                    Actividad.TipoPrioridad.Alta,
                    "2026-01-23",
                    0,
                    3,
                    180,
                    LocalDate.now().toString(),
                    "POO",
                    ActividadAcademica.TipoActividadAcademica.Examen, // <-- Correcto
                    "Estudiar"
            ));

            // 4. Proyecto
            listaActividades.add(new ActividadAcademica(
                    "Proyecto Final",
                    Actividad.TipoPrioridad.Alta,
                    "2026-01-30",
                    0,
                    4,
                    150,
                    LocalDate.now().toString(),
                    "Mecatrónica",
                    ActividadAcademica.TipoActividadAcademica.Proyecto, // <-- Correcto
                    "Maqueta"
            ));

        } catch (Exception e) {
            // Si por alguna razón los compañeros cambiaron algo más, esto evita que la app explote
            e.printStackTrace();
        }
    }

    // ==========================================
    // MÓDULO DE HIDRATACIÓN (INTACTO)
    // ==========================================

    public List<RegistroHidratacion> getListaHidratacion() { return listaHidratacion; }
    public void agregarRegistroHidratacion(RegistroHidratacion registro) { listaHidratacion.add(registro); }
    public double getMetaDiaria() { return metaDiaria; }
    public void setMetaDiaria(double meta) { this.metaDiaria = meta; }

    public double getTotalConsumido() {
        double total = 0;
        for (RegistroHidratacion r : listaHidratacion) { total += r.getCantidadMl(); }
        return total;
    }
    public String getFechaSeleccionadaRepo() { return fechaSeleccionadaRepo; }
    public void setFechaSeleccionadaRepo(String fecha) { this.fechaSeleccionadaRepo = fecha; }

    // ==========================================
    // MÓDULO DE SOSTENIBILIDAD (INTACTO)
    // ==========================================

    public List<RegistroDiarioSostenible> getListaSostenibilidad() { return listaSostenibilidad; }

    public void agregarRegistroSostenible(RegistroDiarioSostenible nuevoRegistro) {
        RegistroDiarioSostenible existente = null;
        for (RegistroDiarioSostenible r : listaSostenibilidad) {
            if (r.getFecha().isEqual(nuevoRegistro.getFecha())) {
                existente = r;
                break;
            }
        }
        if (existente != null) listaSostenibilidad.remove(existente);
        listaSostenibilidad.add(0, nuevoRegistro);
    }
}