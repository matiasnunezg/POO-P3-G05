package espol.poo.sistemabienestarestudiantil.modelo.actividades; // <--- 1. CAMBIO IMPORTANTE: Tu nuevo paquete

public class Actividad {

    private String nombre;
    private TipoPrioridad prioridad;
    private TipoActividad tipoActividad;
    private String fechaVencimiento;
    private int avance;
    private int id;
    private int tiempoEstimado;
    private String fechaActual;
    private String descripcion;

    // Enums (Están perfectos aquí dentro)
    public enum TipoPrioridad {
        Alta, Media, Baja;
    }

    public enum TipoActividad {
        Personal, Academica;
    }

    // --- CONSTRUCTORES ---

    public Actividad() {
    }

    // Constructor completo
    public Actividad(String nombre, TipoPrioridad prioridad, String fechaVencimiento,
                     int avance, int id, int tiempoEstimado, String fechaActual, String descripcion) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.fechaVencimiento = fechaVencimiento;
        this.avance = avance;
        this.id = id;
        this.tiempoEstimado = tiempoEstimado;
        this.fechaActual = fechaActual;
        this.descripcion = descripcion;
    }

    // Constructor simple (útil para pruebas rápidas)
    public Actividad(String nombre, TipoPrioridad prioridad, String fechaVencimiento) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.fechaVencimiento = fechaVencimiento;
        this.avance = 0;
    }

    // --- GETTERS Y SETTERS ---

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public TipoPrioridad getPrioridad() { return prioridad; }

    public String getFechaVencimiento() { return fechaVencimiento; }

    public int getAvance() { return avance; }
    public void setAvance(int avance) { this.avance = avance; }

    public int getId() { return id; }

    public int getTiempoEstimado() { return tiempoEstimado; }

    public String getFechaActual() { return fechaActual; }

    public String getDescripcion() { return descripcion; }

    public TipoActividad getTipoActividad() { return tipoActividad; }
    // Agregamos un setter por si acaso lo necesitas las clases hijas
    public void setTipoActividad(TipoActividad tipoActividad) { this.tipoActividad = tipoActividad; }


    // --- MÉTODOS DE LÓGICA ---

    // 2. CORRECCIÓN: Usar .equals() para comparar Strings
    public boolean estaVencida() {
        if (fechaVencimiento != null && fechaVencimiento.equals(fechaActual)) {
            return true;
        } else {
            return false;
        }
    }

    public String getTiempoEstimadoFormateado() {
        int minutos = this.getTiempoEstimado();

        if (minutos < 60) {
            return minutos + " min";
        }
        int horas = minutos / 60;
        int minsRest = minutos % 60;

        if (minsRest == 0) {
            return horas + " h";
        } else {
            return horas + " h " + minsRest + " min";
        }
    }

    public void setTiempoEstimado(int tiempo) {
        this.tiempoEstimado = tiempo;
    }

}