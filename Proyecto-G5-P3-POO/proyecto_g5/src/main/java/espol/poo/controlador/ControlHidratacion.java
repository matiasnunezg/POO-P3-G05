package espol.poo.controlador;

import espol.poo.modelo.RegistroHidratacion;
import espol.poo.modelo.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControlHidratacion {

    // ATRIBUTOS
    private Usuario usuario;                        
    private List<RegistroHidratacion> registros;    
    private double metaDiaria;                      

 
    // CONSTRUCTORES
    // Constructor recomendado: recibe Usuario y meta explícita 
    public ControlHidratacion(Usuario usuario, double metaDiaria) {
        this.usuario = usuario;
        this.metaDiaria = metaDiaria;
        this.registros = new ArrayList<>();
        // opcional: crear registro inicial del día
        this.registros.add(new RegistroHidratacion(metaDiaria));
    }

    // Constructor alternativo: solo meta (sin usuario) 
    public ControlHidratacion(double metaDiaria) {
        this.usuario = null;
        this.metaDiaria = metaDiaria;
        this.registros = new ArrayList<>();
        this.registros.add(new RegistroHidratacion(metaDiaria));
    }

    // Constructor alternativo: solo usuario, usa meta por defecto
    public ControlHidratacion(Usuario usuario) {
        this(usuario, 2000.0); 
    }


    // MÉTODOS PRINCIPALES

    public void registrarIngesta(double cantidad) {
        RegistroHidratacion registroHoy = obtenerRegistroDelDia(LocalDate.now());
        registroHoy.registrarIngesta(cantidad);
    }

    public void actualizarMetaDiaria(double nuevaMeta) {
        this.metaDiaria = nuevaMeta;
        // actualizar meta en el registro del día (si existe)
        RegistroHidratacion registroHoy = obtenerRegistroDelDia(LocalDate.now());
        registroHoy.establecerMetaDiaria(nuevaMeta);
        if (usuario != null) {
            System.out.println("Meta diaria actualizada a " + nuevaMeta + " ml para " + usuario.getNombre());
        } else {
            System.out.println("Meta diaria actualizada a " + nuevaMeta + " ml.");
        }
    }

    public double calcularTotalDelDia() {
        RegistroHidratacion registroHoy = obtenerRegistroDelDia(LocalDate.now());
        return registroHoy.getAcumuladoDiario();
    }

    public double obtenerPorcentajeProgreso() {
        RegistroHidratacion registroHoy = obtenerRegistroDelDia(LocalDate.now());
        return registroHoy.obtenerProgreso();
    }

    public void mostrarHistorialDelDia() {
        RegistroHidratacion registroHoy = obtenerRegistroDelDia(LocalDate.now());
        registroHoy.obtenerHistorialDelDia();
    }

    public void mostrarResumen() {
        RegistroHidratacion registroHoy = obtenerRegistroDelDia(LocalDate.now());
        double total = registroHoy.getAcumuladoDiario();
        double progreso = registroHoy.obtenerProgreso();

        System.out.println("\n--- Resumen de hidratación ---");
        System.out.println("Usuario: " + (usuario != null ? usuario.getNombre() : "Sin usuario"));
        System.out.println("Fecha: " + registroHoy.getFechaActual());
        System.out.println("Meta diaria: " + metaDiaria + " ml");
        System.out.println("Total ingerido hoy: " + total + " ml");
        System.out.println("Progreso: " + String.format("%.2f", progreso) + "%");
        System.out.println("-------------------------------\n");
    }


    // MÉTODOS AUXILIARES

    // Devuelve el registro del día dado, o crea uno si no existe 
    private RegistroHidratacion obtenerRegistroDelDia(LocalDate fecha) {
        for (RegistroHidratacion r : registros) {
            if (r.getFechaActual().equals(fecha)) {
                return r;
            }
        }
        RegistroHidratacion nuevo = new RegistroHidratacion(metaDiaria);
        registros.add(nuevo);
        return nuevo;
    }


    // GETTERS / SETTERS


    public double getMetaDiaria() {
        return metaDiaria;
    }

    public void setMetaDiaria(double metaDiaria) {
        this.metaDiaria = metaDiaria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<RegistroHidratacion> getRegistros() {
        return registros;
    }
}