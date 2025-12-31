package espol.poo.sistemabienestarestudiantil.data;

import java.util.ArrayList;
import java.util.List;

import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;

public class AppRepository {

    private static AppRepository instance;
    private List<Actividad> listaActividades;

    // Constructor privado (Singleton)
    private AppRepository() {
        listaActividades = new ArrayList<>();
        // Aquí podrías agregar datos de prueba si quisieras
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
}