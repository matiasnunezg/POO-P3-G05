package espol.poo.sistemabienestarestudiantil.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Importamos el modelo de Sueño
import espol.poo.sistemabienestarestudiantil.modelo.suenio.RegistrarHorasDeSuenio;

public class AppRepository {

    private static AppRepository instance;

    // --- 1. Variables para Actividades (Lo que ya tenías) ---
    private final List<String> actividades = new ArrayList<>();

    // --- 2. Variables para Sueño (Lo nuevo) ---
    private List<RegistrarHorasDeSuenio> listaSuenio;

    // Constructor privado
    private AppRepository() {
        // Inicializamos la lista de sueño
        listaSuenio = new ArrayList<>();

        // Cargamos los datos iniciales de ambos módulos
        seed();
    }

    public static synchronized AppRepository getInstance() {
        if (instance == null) {
            instance = new AppRepository();
        }
        return instance;
    }

    // Método para poblar datos de prueba (Seed)
    private void seed() {
        // --- Seed de Actividades ---
        actividades.add("Cita médica - 30/11 17:00");
        actividades.add("Proyecto POO - Avance 68%");
        actividades.add("Cuestionario Unidad 2 - 03/12");

        // --- Seed de Sueño (Datos de prueba) ---
        // Agregamos registros con fechas pasadas (ayer y anteayer)
        listaSuenio.add(new RegistrarHorasDeSuenio(
                LocalTime.of(23, 0),
                LocalTime.of(7, 0),
                LocalDate.now().minusDays(1)
        ));

        listaSuenio.add(new RegistrarHorasDeSuenio(
                LocalTime.of(1, 0),
                LocalTime.of(5, 30),
                LocalDate.now().minusDays(2)
        ));
    }

    // ================= SECCIÓN ACTIVIDADES =================
    public List<String> getActividades() {
        return actividades;
    }

    public void addActividad(String txt) {
        actividades.add(txt);
    }

    // ================= SECCIÓN SUEÑO =================
    public List<RegistrarHorasDeSuenio> getListaSuenio() {
        return listaSuenio;
    }

    public void agregarSuenio(RegistrarHorasDeSuenio registro) {
        // Agregamos al inicio (índice 0) para que aparezca primero en la lista visual
        listaSuenio.add(0, registro);
    }
}