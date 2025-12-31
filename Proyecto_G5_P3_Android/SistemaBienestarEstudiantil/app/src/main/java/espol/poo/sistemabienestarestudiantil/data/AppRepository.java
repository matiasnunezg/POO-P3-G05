package espol.poo.sistemabienestarestudiantil.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;
import espol.poo.sistemabienestarestudiantil.modelo.suenio.RegistrarHorasDeSuenio;

public class AppRepository {

    private static AppRepository instance;

    // Variables de almacenamiento
    private List<Actividad> listaActividades;
    private List<RegistrarHorasDeSuenio> listaSuenio;

    // Constructor privado (Singleton)
    private AppRepository() {
        // Inicializar Actividades
        listaActividades = new ArrayList<>();

        // Inicializar Sueño
        listaSuenio = new ArrayList<>();
        cargarDatosPruebaSuenio();
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
    //             MÓDULO DE SUEÑO
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
}