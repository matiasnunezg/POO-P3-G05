package espol.poo.modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class RegistroHidratacion {

    //  ATRIBUTOS 
    private double metaDiaria;                  
    private double acumuladoDiario;             
    private ArrayList<Double> historialDiario;  
    private LocalDate fechaActual;              

    // CONSTRUCTOR 
    public RegistroHidratacion(double metaDiaria) {
        this.metaDiaria = metaDiaria;
        this.acumuladoDiario = 0.0;
        this.historialDiario = new ArrayList<>();
        this.fechaActual = LocalDate.now(); 
    }

    // MÉTODOS 
    // Registrar una nueva ingesta de agua
    public void registrarIngesta(double cantidad) {
        if (cantidad > 0) {
            historialDiario.add(cantidad);
            acumuladoDiario += cantidad;
            System.out.println("Ingesta registrada: " + cantidad + " ml a las " + LocalTime.now());
        } else {
            System.out.println("Error: la cantidad debe ser mayor que 0.");
        }
    }

    // Establecer o actualizar la meta diaria
    public void establecerMetaDiaria(double nuevaMeta) {
        if (nuevaMeta > 0) {
            this.metaDiaria = nuevaMeta;
            System.out.println("Meta diaria actualizada a " + nuevaMeta + " ml.");
        } else {
            System.out.println("Error: la meta debe ser mayor que 0.");
        }
    }

    //  Calcular el total acumulado del día
    public double calcularAcumulado() {
        return acumuladoDiario;
    }

    //  Obtener el porcentaje de progreso
    public double obtenerProgreso() {
        if (metaDiaria == 0) return 0;
        double progreso = (acumuladoDiario / metaDiaria) * 100;
        return Math.min(progreso, 100); 
    }

    //  Obtener historial del día actual
    public void obtenerHistorialDelDia() {
        System.out.println("Historial del " + fechaActual + ":");
        if (historialDiario.isEmpty()) {
            System.out.println("No hay registros de ingesta aún.");
        } else {
            for (int i = 0; i < historialDiario.size(); i++) {
                System.out.println("Ingesta " + (i + 1) + ": " + historialDiario.get(i) + " ml");
            }
        }
    }

    
    // GETTERS
    public double getMetaDiaria() {
        return metaDiaria;
    }

    public double getAcumuladoDiario() {
        return acumuladoDiario;
    }

    public LocalDate getFechaActual() {
        return fechaActual;
    }

    public ArrayList<Double> getHistorialDiario() {
        return historialDiario;
    }

    // NUEVOS GETTERS AÑADIDOS
    public LocalDate getFecha() {
        return fechaActual;
    }

    public double getMeta() {
        return metaDiaria;
    }

    public double getAcumulado() {
        return acumuladoDiario;
    }

    public ArrayList<Double> getHistorial() {
        return historialDiario;
    }
}