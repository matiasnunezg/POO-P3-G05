package espol.poo.controlador;

import espol.poo.modelo.sostenibilidad.AccionPuntos;
import espol.poo.modelo.sostenibilidad.RegistroDiarioSostenible;
import espol.poo.modelo.sostenibilidad.RegistroSostenible;
import espol.poo.vista.VistaSostenibilidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControladorSostenibilidad {
    
    private static class DatosAccion {
        int id;
        String descripcion;
        int puntos;
        public DatosAccion(int id, String d, int p) { 
            this.id = id;
            this.descripcion = d; 
            this.puntos = p; 
        }
        public int getId() { return id; }
        public String getDescripcion() { return descripcion; }
        public int getPuntos() { return puntos; }
    }
    
    private VistaSostenibilidad vista;
    private RegistroSostenible registroModelo;
    
    private List<DatosAccion> accionesDisponiblesList; 
    private final int TOTAL_ACCIONES = 4;

    public ControladorSostenibilidad(RegistroSostenible registroInicial) {
        this.vista = new VistaSostenibilidad();
        this.registroModelo = registroInicial; 
        configurarAcciones();
    }

    private void configurarAcciones() {
        accionesDisponiblesList = new ArrayList<>();

        accionesDisponiblesList.add(new DatosAccion(1, "Usé transporte público, bicicleta o caminé.", 5));
        accionesDisponiblesList.add(new DatosAccion(2, "No realicé impresiones.", 5));
        accionesDisponiblesList.add(new DatosAccion(3, "No utilicé envases descartables (usé mi termo/taza).", 5));
        accionesDisponiblesList.add(new DatosAccion(4, "Separé y reciclé materiales (vidrio, plástico, papel).", 5));
    }
    
    private DatosAccion buscarAccionPorId(int id) {
        for (DatosAccion accion : accionesDisponiblesList) {
            if (accion.getId() == id) {
                return accion;
            }
        }
        return null; 
    }
    
    private List<AccionPuntos> obtenerAccionesPuntosParaModelo() {
        List<AccionPuntos> listaPuntos = new ArrayList<>();
        for (DatosAccion accion : accionesDisponiblesList) {
            listaPuntos.add(new AccionPuntos(accion.getId(), accion.getPuntos()));
        }
        return listaPuntos;
    }

    public void iniciarRegistroDiario() {
        
        LocalDate hoy = LocalDate.now(); 
        
        List<String> descripcionesParaVista = new ArrayList<>();
        for (DatosAccion accion : accionesDisponiblesList) {
            descripcionesParaVista.add(accion.getDescripcion());
        }

        String seleccionInput = vista.pedirSeleccionAcciones(hoy, descripcionesParaVista);

        List<Integer> accionesSeleccionadasIds = procesarSeleccion(seleccionInput);
        
        if (accionesSeleccionadasIds.isEmpty()) {
            vista.mostrarMensaje("No se registró ninguna acción. Volviendo al resumen.");
            mostrarResumenSemanal();
            return;
        }
        
        RegistroDiarioSostenible registroDia = new RegistroDiarioSostenible(hoy, accionesSeleccionadasIds);
        registroModelo.agregarRegistro(registroDia);
        
        int puntosDia = registroDia.calcularPuntos(obtenerAccionesPuntosParaModelo()); 
        
        List<String> nombres = new ArrayList<>();
        for (Integer id : accionesSeleccionadasIds) {
            DatosAccion accion = buscarAccionPorId(id);
            if (accion != null) {
                nombres.add(accion.getDescripcion());
            }
        }
            
        vista.mostrarConfirmacionAcciones(nombres, puntosDia);
        
        mostrarResumenSemanal();
    }

    private List<Integer> procesarSeleccion(String input) {
        List<Integer> seleccionadas = new ArrayList<>();
        
        String[] idsStr = input.replaceAll("\\s+", "").split(",");
        
        for (String idStr : idsStr) {
            try {
                int id = Integer.parseInt(idStr);
                if (id > 0 && id <= accionesDisponiblesList.size()) {
                    seleccionadas.add(id);
                }
            } catch (NumberFormatException e) {
            }
        }
        return seleccionadas;
    }

    private void mostrarResumenSemanal() {
        List<RegistroDiarioSostenible> registros = registroModelo.getRegistros();

        if (registros.isEmpty()) {
            vista.mostrarMensaje("No hay datos suficientes para generar el resumen semanal.");
            return;
        }

        LocalDate inicioRango = registros.get(0).getFecha();
        LocalDate finRango = registros.get(0).getFecha();

        for (RegistroDiarioSostenible r : registros) {
            if (r.getFecha().isBefore(inicioRango)) {
                inicioRango = r.getFecha();
            }
            if (r.getFecha().isAfter(finRango)) {
                finRango = r.getFecha();
            }
        }
        
        Map<Integer, Integer> frecuencias = registroModelo.calcularFrecuencias();
        int diasConAccion = registroModelo.contarDiasConAcciones();
        int diasPerfectos = registroModelo.contarDiasPerfectos(TOTAL_ACCIONES);
        String tip = registroModelo.generarTipEcologico();

        List<String> nombresAcciones = new ArrayList<>();
        List<String> frecuenciaFormateada = new ArrayList<>();
        List<String> logros = new ArrayList<>();
        
        for (DatosAccion accion : accionesDisponiblesList) {
            int id = accion.getId();
            int freq = frecuencias.getOrDefault(id, 0);
            
            nombresAcciones.add(accion.getDescripcion()); 
            frecuenciaFormateada.add(String.valueOf(freq));
            
            if (freq >= 3) {
                logros.add("¡Superado!");
            } else {
                logros.add("Inténtalo más");
            }
        }
        
        vista.mostrarResumenSemanal(
            inicioRango,
            finRango,
            nombresAcciones,
            frecuenciaFormateada,
            logros,
            diasConAccion,
            registros.size(), 
            diasPerfectos,
            tip
        );
    }
}
