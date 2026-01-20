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
import java.util.HashMap;
import java.util.Map;

// Importaciones
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadPersonal;
import espol.poo.sistemabienestarestudiantil.modelo.suenio.RegistrarHorasDeSuenio;
import espol.poo.sistemabienestarestudiantil.modelo.hidrataciones.RegistroHidratacion;
import espol.poo.sistemabienestarestudiantil.modelo.sostenibilidad.RegistroDiarioSostenible;

public class AppRepository {

    private static AppRepository instance;
    private Context context;

    // --- ARCHIVOS DE GUARDADO ---
    private final String FILE_ACTIVIDADES = "actividades.ser";
    private final String FILE_SUENIO = "historial_suenio.ser";
    private final String FILE_HIDRATACION = "historial_agua.ser";
    private final String FILE_META_HIDRATACION = "meta_agua.ser";
    private final String FILE_SOSTENIBILIDAD = "sostenibilidad.ser"; // <--- NUEVO

    // --- LISTAS ---
    private List<Actividad> listaActividades;
    private List<RegistrarHorasDeSuenio> listaSuenio;
    private List<RegistroHidratacion> listaHidratacion;
    private List<RegistroDiarioSostenible> listaSostenibilidad;

    // --- VARIABLES ---
    private Map<String, Double> historialMetas;
    private String fechaSeleccionadaRepo = "";

    // --- CONSTRUCTOR PRIVADO ---
    private AppRepository(Context context) {
        this.context = context;

        // 1. ACTIVIDADES
        listaActividades = new ArrayList<>();
        if (context != null) {
            cargarActividadesDelArchivo();
        } else {
            cargarDatosOriginalesActividades();
        }

        // 2. SUEÑO
        listaSuenio = new ArrayList<>();
        if (context != null) {
            cargarSuenioDelArchivo();
        } else {
            cargarDatosPruebaSuenio();
        }

        // 3. HIDRATACIÓN
        listaHidratacion = new ArrayList<>();
        historialMetas = new HashMap<>();

        if (context != null) {
            cargarHidratacionDelArchivo();
            cargarMetasDelArchivo();
        } else {
            cargarDatosPruebaHidratacion();
        }

        // 4. SOSTENIBILIDAD (MODIFICADO PARA CARGAR ARCHIVO)
        listaSostenibilidad = new ArrayList<>();
        if (context != null) {
            cargarSostenibilidadDelArchivo(); // <--- NUEVO
        } else {
            cargarDatosPruebaSostenibilidad();
        }
    }

    // --- SINGLETON HÍBRIDO ---
    public static synchronized AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository(context.getApplicationContext());
        }
        if (instance.context == null && context != null) {
            instance.context = context.getApplicationContext();
            instance.cargarSuenioDelArchivo();
            instance.cargarHidratacionDelArchivo();
            instance.cargarMetasDelArchivo();
            instance.cargarActividadesDelArchivo();
            instance.cargarSostenibilidadDelArchivo(); // <--- NUEVO
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
    //           MÓDULO DE SUEÑO
    // ==========================================
    public List<RegistrarHorasDeSuenio> getListaSuenio() { return listaSuenio; }

    public void agregarSuenio(RegistrarHorasDeSuenio registro) {
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
    //           MÓDULO DE HIDRATACIÓN
    // ==========================================
    public List<RegistroHidratacion> getListaHidratacion() { return listaHidratacion; }

    public void agregarRegistroHidratacion(RegistroHidratacion registro) {
        listaHidratacion.add(registro);
        if (context != null) guardarHidratacionEnArchivo();
    }

    private void cargarDatosPruebaHidratacion() {
        listaHidratacion.add(new RegistroHidratacion(500.0, "19/01/2026", "13:00 PM"));
        listaHidratacion.add(new RegistroHidratacion(300.0, "19/01/2026", "17:00 PM"));
    }

    public double getMetaDiaria(String fecha) {
        if (historialMetas != null && historialMetas.containsKey(fecha)) {
            return historialMetas.get(fecha);
        }
        return 2500.0;
    }

    public void setMetaDiaria(double meta, String fecha) {
        if (historialMetas == null) historialMetas = new HashMap<>();
        historialMetas.put(fecha, meta);
        if (context != null) guardarMetasEnArchivo();
    }

    public double getMetaDiaria() { return 2500.0; }

    public double getTotalConsumido() {
        double total = 0;
        for (RegistroHidratacion r : listaHidratacion) { total += r.getCantidadMl(); }
        return total;
    }

    public String getFechaSeleccionadaRepo() { return fechaSeleccionadaRepo; }
    public void setFechaSeleccionadaRepo(String fecha) { this.fechaSeleccionadaRepo = fecha; }

    private void guardarHidratacionEnArchivo() {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_HIDRATACION, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listaHidratacion);
            oos.close(); fos.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    private void cargarHidratacionDelArchivo() {
        try {
            FileInputStream fis = context.openFileInput(FILE_HIDRATACION);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listaHidratacion = (List<RegistroHidratacion>) ois.readObject();
            ois.close(); fis.close();
        } catch (Exception e) {
            listaHidratacion = new ArrayList<>();
            cargarDatosPruebaHidratacion();
            if (context != null) guardarHidratacionEnArchivo();
        }
    }

    private void guardarMetasEnArchivo() {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_META_HIDRATACION, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(historialMetas);
            oos.close(); fos.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    private void cargarMetasDelArchivo() {
        try {
            FileInputStream fis = context.openFileInput(FILE_META_HIDRATACION);
            ObjectInputStream ois = new ObjectInputStream(fis);
            historialMetas = (Map<String, Double>) ois.readObject();
            ois.close(); fis.close();
        } catch (Exception e) {
            historialMetas = new HashMap<>();
            if (context != null) guardarMetasEnArchivo();
        }
    }

    // ==========================================
    //           MÓDULO DE ACTIVIDADES
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

    public void guardarActividadesEnArchivo() {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_ACTIVIDADES, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listaActividades);
            oos.close(); fos.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void cargarActividadesDelArchivo() {
        try {
            FileInputStream fis = context.openFileInput(FILE_ACTIVIDADES);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listaActividades = (List<Actividad>) ois.readObject();
            ois.close(); fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            listaActividades = new ArrayList<>();
            cargarDatosOriginalesActividades();
        }
    }

    private void cargarDatosOriginalesActividades() {
        try {
            listaActividades.add(new ActividadPersonal("Viaje a Montañita", Actividad.TipoPrioridad.Media, "2026-02-15", 0, 1, 4800, LocalDate.now().toString(), "Montañita", ActividadPersonal.TipoActividadPersonal.Hobbies, "Viaje con amigos"));
            listaActividades.add(new ActividadAcademica("Leer capítulo 5", Actividad.TipoPrioridad.Baja, "2026-01-19", 70, 2, 120, LocalDate.now().toString(), "Física", ActividadAcademica.TipoActividadAcademica.Tarea, "Teoría"));
            listaActividades.add(new ActividadAcademica("Examen Parcial", Actividad.TipoPrioridad.Alta, "2026-01-23", 0, 3, 180, LocalDate.now().toString(), "POO", ActividadAcademica.TipoActividadAcademica.Examen, "Estudiar"));
            listaActividades.add(new ActividadAcademica("Proyecto Final", Actividad.TipoPrioridad.Alta, "2026-01-30", 0, 4, 150, LocalDate.now().toString(), "Mecatrónica", ActividadAcademica.TipoActividadAcademica.Proyecto, "Maqueta"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ==========================================
    //           MÓDULO DE SOSTENIBILIDAD
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


        if (context != null) guardarSostenibilidadEnArchivo();
    }


    private void guardarSostenibilidadEnArchivo() {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_SOSTENIBILIDAD, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listaSostenibilidad);
            oos.close(); fos.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    
    private void cargarSostenibilidadDelArchivo() {
        try {
            FileInputStream fis = context.openFileInput(FILE_SOSTENIBILIDAD);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listaSostenibilidad = (List<RegistroDiarioSostenible>) ois.readObject();
            ois.close(); fis.close();
        } catch (Exception e) {
            listaSostenibilidad = new ArrayList<>();
            cargarDatosPruebaSostenibilidad();
            if (context != null) guardarSostenibilidadEnArchivo();
        }
    }

    private void cargarDatosPruebaSostenibilidad() {
        try {
            if (listaSostenibilidad.isEmpty()) {
                List<Integer> idsAyer = new ArrayList<>();
                idsAyer.add(1); idsAyer.add(2); idsAyer.add(3); idsAyer.add(4);
                listaSostenibilidad.add(new RegistroDiarioSostenible(LocalDate.now().minusDays(1), idsAyer));
            }
        } catch (Exception e) { }
    }
}
