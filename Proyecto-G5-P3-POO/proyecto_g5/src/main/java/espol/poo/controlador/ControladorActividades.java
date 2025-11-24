package espol.poo.controlador;

import java.util.ArrayList;
import java.util.List; // <<< Es mejor usar la interfaz List
import espol.poo.modelo.Actividad;
import espol.poo.modelo.ActividadAcademica;
import espol.poo.modelo.ActividadPersonal;
import espol.poo.modelo.Actividad.TipoPrioridad;
import espol.poo.modelo.ActividadAcademica.TipoActividadAcademica;
import espol.poo.modelo.ActividadPersonal.TipoActividadPersonal;
import espol.poo.vista.VistaActividad;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// La clase se declara aquí
public class ControladorActividades {

    // --- 1. ATRIBUTOS ---
    // Los atributos se declaran aquí, fuera de los métodos.
    private VistaActividad vista;
    private List<Actividad> listaDeActividades; // <<< LA ÚNICA LISTA (la maestra)

    // --- 2. CONSTRUCTOR ---
    /**
     * Constructor Corregido (Inyección de Dependencias).
     * Recibe sus herramientas (la Vista y el Modelo) desde App.
     */
    public ControladorActividades(VistaActividad vista, List<Actividad> lActividades) {
        this.vista = vista;
        this.listaDeActividades = lActividades; // Usa la lista que le pasó App
    }

    // --- 3. MÉTODOS ---
    
    /**
     * Devuelve la fecha actual como un String formateado.
     */
    public String getFechaActual() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaActual = LocalDate.now();
        return fechaActual.format(formatter);
    }

    /**
     * Bucle principal para gestionar las actividades (CRUD).
     */
    public void gestionarActividades() {
        int opcion;
        boolean volver = false;

        while (!volver) {
            opcion = vista.pedirOpcionGestion(); // Asumo que la vista tiene este método
            
            switch (opcion) {
                case 1:
                    visualizarActividades();
                    break;
                case 2:
                    crearActividad();
                    break;
                case 3:
                    registrarAvance();
                    break;
                case 4:
                    eliminarActividad();
                    break;
                case 5:
                    volver = true;
                    vista.mostrarMensaje("Volviendo al menú principal...");
                    break;
                default:
                    vista.mostrarError("Opción no válida."); // Asumo que la vista tiene este método
            }
        }
    }

    /**
     * Pide datos a la vista y crea una nueva Actividad
     */
    public void crearActividad() {
        int opcion = vista.pedirtipoactividad();
        if (opcion == 1) {
            // --- Crear Actividad Académica ---
            TipoActividadAcademica tipo = vista.pedirTipoActividadAcademica();
            String nombre = vista.pedirTextoNoVacio("Ingrese nombre de actividad academica: ");
            TipoPrioridad prioridad = vista.pedirPrioridad();
            String asignatura = vista.pedirTextoNoVacio("Ingrese asignatura de la actividad: ");
            String fechavencimiento = vista.pedirfechaVencimiento() + " " + vista.pedirHoraVencimiento();
            String fechaActual = getFechaActual();
            String descripcion = vista.pedirTextoNoVacio("Ingrese descripcion de la actividad: ");
            int tiempoestimado = vista.pedirNumeroPositivo("Ingrese el tiempo estimado: ");
            
            // Crea el ID basado en el tamaño actual de la lista
            int nuevoId = listaDeActividades.size() + 1;
            
            ActividadAcademica actividadacademica = new ActividadAcademica(nombre, prioridad, fechavencimiento, 0, nuevoId, tiempoestimado, fechaActual, asignatura, tipo, descripcion);
            
            // Se añade SOLO a la lista maestra
            this.listaDeActividades.add(actividadacademica);
            vista.mostrarMensaje("Actividad Académica '" + nombre + "' creada con éxito.");

        } else {
            // --- Crear Actividad Personal ---
            TipoActividadPersonal tipo = vista.pedirTipoActividadPersonal();
            String nombre = vista.pedirTextoNoVacio("Ingrese nombre de actividad personal: ");
            TipoPrioridad prioridad = vista.pedirPrioridad();
            String lugar = vista.pedirTextoNoVacio("Ingrese lugar de la actividad: ");
            String fechavencimiento = vista.pedirfechaVencimiento() + " " + vista.pedirHoraVencimiento();
            String fechaActual = getFechaActual();
            String descripcion = vista.pedirTextoNoVacio("Ingrese descripcion de la actividad: ");
            int tiempoestimado = vista.pedirNumeroPositivo("Ingrese el tiempo estimado: ");
            
            // Crea el ID basado en el tamaño actual de la lista
            int nuevoId = listaDeActividades.size() + 1;

            ActividadPersonal actividadpersonal = new ActividadPersonal(nombre, prioridad, fechavencimiento, 0, nuevoId, tiempoestimado, fechaActual, lugar, tipo, descripcion);
            
            // Se añade SOLO a la lista maestra
            this.listaDeActividades.add(actividadpersonal);
            vista.mostrarMensaje("Actividad Personal '" + nombre + "' creada con éxito.");
        }
    }

    /**
     * Elimina una actividad de la lista maestra.
     */
    public void eliminarActividad() {
        vista.mostrarMensaje("--- 4. Eliminar Actividad ---");
        // Muestra todas las actividades
        vista.mostrarListaActividades(new ArrayList<>(this.listaDeActividades));
        
        int id = vista.pedirIdActividad(listaDeActividades.size());
        if (id == 0) {
            vista.mostrarMensaje("Cancelando...");
            return;
        }

        // Lógica de borrado más segura (buscando por ID)
        Actividad actividadAEliminar = buscarActividadPorId(id, this.listaDeActividades);

        if (actividadAEliminar == null) {
            vista.mostrarError("ID no encontrado."); // Mensaje de error específico
            return;
        }
        
        boolean confirmar = vista.pedirConfirmacionEliminar(actividadAEliminar.getNombre());

        if (confirmar) {
            // Se elimina SOLO de la lista maestra
            listaDeActividades.remove(actividadAEliminar);
            vista.mostrarMensaje("Actividad '" + actividadAEliminar.getNombre() + "' eliminada con éxito.");
        } else {
            vista.mostrarMensaje("Eliminación cancelada.");
        }
    }

    /**
     * Muestra actividades con filtros.
     */
    public void visualizarActividades() {
        int filtro = vista.pedirFiltroActividades();
        ArrayList<Actividad> listaFiltrada = new ArrayList<>();

        switch (filtro) {
            case 1: // Todas
                listaFiltrada = new ArrayList<>(this.listaDeActividades);
                break;
            case 2: // Solo Académicas
                for (Actividad a : this.listaDeActividades) {
                    if (a instanceof ActividadAcademica) {
                        listaFiltrada.add(a);
                    }
                }
                break;
            case 3: // Solo Personales
                for (Actividad a : this.listaDeActividades) {
                    if (a instanceof ActividadPersonal) {
                        listaFiltrada.add(a);
                    }
                }
                break;
        }

        vista.mostrarListaActividades(listaFiltrada);

        boolean verDetalles = vista.pedirConfirmacion("¿Desea ver detalles de una actividad?");
        if (verDetalles) {
            int id = vista.pedirIdActividad(listaFiltrada.size());
            if (id != 0) {
                // Busca en la LISTA FILTRADA
                Actividad actividadADetallar = buscarActividadPorId(id, listaFiltrada); 
                if (actividadADetallar != null) {
                    vista.mostrarDetalle(actividadADetallar);
                } else {
                    vista.mostrarError("ID no válido de la lista filtrada."); // Mensaje de error específico
                }
            }
        }
    }

    /**
     * Registra un nuevo porcentaje de avance para una actividad.
     */
    public void registrarAvance() {
        vista.mostrarMensaje("--- 3. Registrar Avance ---");

        // 1. Crear una lista temporal solo para las actividades incompletas.
        ArrayList<Actividad> listaIncompletas = new ArrayList<>();
        
        // 2. Revisar la lista maestra
        for (Actividad a : this.listaDeActividades) {
            // 3. Añadir SÓLO las que tengan avance MENOR A 100
            if (a.getAvance() < 100) {
                listaIncompletas.add(a);
            }
        }

        // 4. Mostrar SÓLO la lista filtrada
        vista.mostrarListaActividades(listaIncompletas); 
        
        // 5. Pedir el ID
        int id = vista.pedirIdActividad(listaIncompletas.size()); 
        if (id == 0) {
            vista.mostrarMensaje("Cancelando...");
            return; 
        }

        // 6. Buscar la actividad (¡OJO! Busca en la lista 'incompletas')
        Actividad actividad = buscarActividadPorId(id, listaIncompletas); 
        
        if (actividad == null) {
            vista.mostrarError("ID no válido."); // Mensaje de error específico
            return;
        }

        // 7. Pedir el nuevo avance (tu lógica está bien)
        int avance = 0;
        int validacion = 0;
        while (validacion == 0){
            int valoringresado = vista.pedirNumeroPositivo("Ingrese el nuevo porcentaje de avance (0-100)");
            if (vista.verificarRango(valoringresado, 0, 100) == false){ // Asumo que la vista tiene este método
                vista.mostrarError("El avance debe estar entre 0 y 100.");
            }
            else{
                avance = valoringresado;
                validacion++;
            }
        }
        
        // 8. Actualizar la actividad (el objeto original se actualiza)
        actividad.setAvance(avance); 
        
        vista.mostrarMensaje("Avance de '" + actividad.getNombre() + "' actualizado a " + avance + "%.");
    }

    /**
     * Método de ayuda para buscar por ID en cualquier lista.
     */
    private Actividad buscarActividadPorId(int id, List<Actividad> lista) {
        for (Actividad a : lista) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    /**
     * Getter para la lista principal (si otro controlador la necesitara).
     */
    public List<Actividad> getListaDeActividades() {
        return this.listaDeActividades;
    }

} // <<< La clase termina aquí