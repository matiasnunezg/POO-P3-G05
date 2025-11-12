package espol.poo.controlador;

import espol.poo.modelo.RegistroHidratacion;
import espol.poo.vista.VistaHidratacion;

public class ControlHidratacion {

    private RegistroHidratacion registro;
    private VistaHidratacion vista;

    // Constructor: recibe modelo y vista
    public ControlHidratacion(RegistroHidratacion registro, VistaHidratacion vista) {
        this.registro = registro;
        this.vista = vista;
    }

    // Método principal que controla el flujo del programa
    public void iniciar() {
        int opcion;
        do {
            vista.mostrarMenuPrincipal();
            opcion = vista.leerOpcion();

            switch (opcion) {
                case 1:
                    registrarIngesta();
                    break;
                case 2:
                    actualizarMeta();
                    break;
                case 3:
                    mostrarProgreso();
                    break;
                case 4:
                    mostrarHistorial();
                    break;
                case 5:
                    vista.mostrarMensaje("Saliendo del programa... ¡Recuerda mantenerte hidratado!");
                    break;
                default:
                    vista.mostrarMensaje("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 5);
    }

    // Registrar nueva ingesta de agua
    private void registrarIngesta() {
        double cantidad = vista.solicitarCantidadAgua();
        if (cantidad > 0) {
            registro.registrarIngesta(cantidad);
            vista.mostrarMensaje("Ingesta registrada correctamente.");
        } else {
            vista.mostrarMensaje("La cantidad debe ser mayor que 0.");
        }
    }

    // Actualizar la meta diaria
    private void actualizarMeta() {
        double nuevaMeta = vista.solicitarMetaDiaria();
        registro.establecerMetaDiaria(nuevaMeta);
        vista.mostrarMensaje("Meta diaria actualizada a " + nuevaMeta + " ml.");
    }

    // Mostrar el progreso del día
    private void mostrarProgreso() {
        double total = registro.calcularAcumulado();
        double meta = registro.getMetaDiaria();
        double porcentaje = registro.obtenerProgreso();
        vista.mostrarProgreso(total, meta, porcentaje);
    }

    // Mostrar historial de ingestas
    private void mostrarHistorial() {
        vista.mostrarHistorial(registro.getCantidades(), registro.getHoras());
    }
}