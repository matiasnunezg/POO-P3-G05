package espol.poo.controlador;

import espol.poo.modelo.RegistrarHorasDeSuenio;
import espol.poo.vista.VistaSuenio;
import java.time.LocalTime;
import java.util.ArrayList;

public class ControladorSuenio {

    private ArrayList<RegistrarHorasDeSuenio> registros;
    private VistaSuenio vista;

    public ControladorSuenio() {
        this.registros = new ArrayList<>();
        this.vista = new VistaSuenio();
    }
    
    // Método principal que gestiona el flujo del menú
    public void gestionarSuenio() {
        int opcion;
        do {
            opcion = vista.mostrarMenu();
            switch (opcion) {
                case 1 -> registrarNuevoSuenio();
                case 2 -> vista.mostrarReporteGrafico(registros);
                case 3 -> vista.mostrarMensaje("Saliendo del módulo de sueño...");
                default -> vista.mostrarMensaje("Opción inválida.");
            }
        } while (opcion != 3);
    }

    // Coordina la entrada de datos, creación del objeto y muestra del resultado
    private void registrarNuevoSuenio() {
        vista.mostrarMensaje("\n--- REGISTRAR HORAS DE SUEÑO ---");
        
        LocalTime inicio = vista.pedirHora("Ingrese la hora en que se acostó");
        LocalTime fin = vista.pedirHora("Ingrese la hora en que despertó");

        RegistrarHorasDeSuenio nuevoRegistro = new RegistrarHorasDeSuenio(inicio, fin);
        registros.add(nuevoRegistro);

        vista.mostrarResultadoInmediato(nuevoRegistro);
    }
    // Permite agregar registros desde fuera del controlador (útil para cargar datos)
    public void agregarRegistroManual(RegistrarHorasDeSuenio registro) {
        if (registro != null) {
            this.registros.add(registro);
        }
    }
}