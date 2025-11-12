package espol.poo.controlador;

import espol.poo.modelo.RegistroHidratacion;
import espol.poo.vista.VistaHidratacion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Controlador del módulo de hidratación.
 * Orquesta la comunicación entre el modelo (RegistroHidratacion)
 * y la vista (VistaHidratacion).
 */
public class ControlHidratacion {

    private VistaHidratacion vista;
    private ArrayList<RegistroHidratacion> registros; // lista de ingestas (historial)
    private double metaDiaria; // meta en ml

    // Constructor: recibe la vista y la meta inicial
    public ControlHidratacion(VistaHidratacion vista, double metaInicial) {
        this.vista = vista;
        this.metaDiaria = metaInicial;
        this.registros = new ArrayList<>();
    }

    // Alternativa: constructor que solo recibe la vista (meta por defecto)
    public ControlHidratacion(VistaHidratacion vista) {
        this(vista, 2500.0); // meta por defecto 2500 ml
    }

    // Método principal que gestiona el menú y el flujo
    // (La vista tiene 4 opciones: 1,2,3,4 volver)
    public void gestionarHidratacion() {
        int opcion;
        do {
            vista.mostrarMenuPrincipal();
            opcion = vista.leerOpcion();

            switch (opcion) {
                case 1:
                    opcionRegistrarIngesta();
                    break;
                case 2:
                    opcionActualizarMeta();
                    break;
                case 3:
                    opcionMostrarProgreso();
                    break;
                case 4:
                    vista.mostrarMensaje("Volviendo al menú principal...");
                    break;
                default:
                    vista.mostrarMensaje("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 4);
    }

    // ---------- OPCIONES ----------

    // Opción 1: registrar ingesta
    private void opcionRegistrarIngesta() {
        double cantidad = vista.solicitarCantidadAgua();
        if (cantidad <= 0) {
            vista.mostrarMensaje("Cantidad inválida. No se registró nada.");
            return;
        }

        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.now();

        double acumuladoPrevio = calcularTotalDelDia(fecha);
        double acumuladoNuevo = acumuladoPrevio + cantidad;

        // Crea un nuevo registro (modelo simple)
        RegistroHidratacion registro = new RegistroHidratacion(
                this.metaDiaria,
                cantidad,
                acumuladoNuevo,
                fecha,
                hora
        );

        registros.add(registro);

        double porcentaje = calcularPorcentaje(acumuladoNuevo, this.metaDiaria);

        // LLAMADA A LA VISTA CORRECTA
        vista.mostrarRegistroAgua(cantidad, acumuladoPrevio, acumuladoNuevo, this.metaDiaria, porcentaje);
    }

    // Opción 2: actualizar meta diaria
    private void opcionActualizarMeta() {
        double nuevaMeta = vista.solicitarMetaDiaria();
        if (nuevaMeta <= 0) {
            vista.mostrarMensaje("Meta inválida. No se realizó cambio.");
            return;
        }
        this.metaDiaria = nuevaMeta;

        // calcular acumulado de hoy para mostrar progreso actualizado
        LocalDate hoy = LocalDate.now();
        double acumuladoHoy = calcularTotalDelDia(hoy);
        double porcentaje = calcularPorcentaje(acumuladoHoy, this.metaDiaria);

        // LLAMADA A LA VISTA CORRECTA
        vista.mostrarMetaActualizada(nuevaMeta, acumuladoHoy, porcentaje);
    }

    // Opción 3: mostrar progreso (calcula con registros del día)
    private void opcionMostrarProgreso() {
        LocalDate hoy = LocalDate.now();
        double totalHoy = calcularTotalDelDia(hoy);
        double faltante = Math.max(0.0, this.metaDiaria - totalHoy);
        double porcentaje = calcularPorcentaje(totalHoy, this.metaDiaria);

        // construir listas de cantidades y horas del día (para que la vista muestre historial dentro del progreso)
        ArrayList<Double> cantidades = new ArrayList<>();
        ArrayList<LocalTime> horas = new ArrayList<>();

        for (RegistroHidratacion r : registros) {
            if (r.getFechaRegistro() != null && r.getFechaRegistro().equals(hoy)) {
                cantidades.add(r.getCantidadIngerida());
                horas.add(r.getHoraRegistro());
            }
        }

        // LLAMADA A LA VISTA CORRECTA
        String fechaStr = hoy.getDayOfMonth() + "/" + hoy.getMonthValue() + "/" + hoy.getYear();
        vista.mostrarProgresoDetallado(fechaStr, this.metaDiaria, totalHoy, faltante, porcentaje, cantidades, horas);
    }

    // ---------- MÉTODOS AUXILIARES ----------

    private double calcularTotalDelDia(LocalDate fecha) {
        double total = 0.0;
        for (RegistroHidratacion r : registros) {
            if (r.getFechaRegistro() != null && r.getFechaRegistro().equals(fecha)) {
                total += r.getCantidadIngerida();
            }
        }
        return total;
    }

    private double calcularPorcentaje(double acumulado, double meta) {
        if (meta <= 0) return 0.0;
        double pct = (acumulado / meta) * 100.0;
        if (pct > 100.0) pct = 100.0;
        return pct;
    }

    // ---------- Getters y Setters ----------

    public double getMetaDiaria() {
        return metaDiaria;
    }

    public void setMetaDiaria(double metaDiaria) {
        this.metaDiaria = metaDiaria;
    }

    public ArrayList<RegistroHidratacion> getRegistros() {
        return registros;
    }
}