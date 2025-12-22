package espol.poo.sistemabienestarestudiantil.data;

import java.util.ArrayList;
import java.util.List;

public class AppRepository {

    private static AppRepository instance;

    // Por ahora solo demostrativo: “actividades” como Strings
    private final List<String> actividades = new ArrayList<>();

    private AppRepository() {
        seed();
    }

    public static AppRepository getInstance() {
        if (instance == null) instance = new AppRepository();
        return instance;
    }

    private void seed() {
        actividades.add("Cita médica - 30/11 17:00");
        actividades.add("Proyecto POO - Avance 68%");
        actividades.add("Cuestionario Unidad 2 - 03/12");
    }

    public List<String> getActividades() {
        return actividades;
    }

    public void addActividad(String txt) {
        actividades.add(txt);
    }
}
