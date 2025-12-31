package espol.poo.sistemabienestarestudiantil.data;
import espol.poo.sistemabienestarestudiantil.modelo.hidrataciones.RegistroHidratacion;
import java.util.ArrayList;
import java.util.List;

import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;

public class AppRepository {

    private static AppRepository instance;
    private List<Actividad> listaActividades;
    // Añade estas dos líneas debajo de listaActividades
    private List<RegistroHidratacion> listaHidratacion;
    private double metaDiaria = 2500.0;
    private String fechaSeleccionadaRepo = ""; // Guardará la fecha como texto

    // Constructor privado (Singleton)
    private AppRepository() {
        listaActividades = new ArrayList<>();
        // Inicializa la lista de hidratación aquí
        listaHidratacion = new ArrayList<>();
    }

    // Método para obtener la única instancia (Singleton)
    public static AppRepository getInstance() {
        if (instance == null) {
            instance = new AppRepository();
        }
        return instance;
    }

    // Método para obtener la lista (Ya lo tenías)
    public List<Actividad> getListaActividades() {
        return listaActividades;
    }

    // --- ESTE ES EL QUE TE FALTABA ---
    public void agregarActividad(Actividad actividad) {
        listaActividades.add(actividad);
    }

    public Actividad buscarActividadPorId(int idBuscado) {
        // Asegúrate que esta variable 'listaDeActividades' se llame IGUAL que la que definiste arriba en la clase
        for (Actividad a : listaActividades) {
            if (a.getId() == idBuscado) {
                return a; // ¡Encontrado!
            }
        }
        return null; // No se encontró
    }

    public int getProximoId() {
        int maxId = 0;

        // Buscamos el ID más alto que exista en la lista
        for (Actividad a : listaActividades) {
            if (a.getId() > maxId) {
                maxId = a.getId();
            }
        }

        // Retornamos el siguiente (Si no hay nada, devuelve 1)
        return maxId + 1;
    }

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