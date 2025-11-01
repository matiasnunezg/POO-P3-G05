package espol.poo.modelo;

public class Actividad {

    private String nombre;
    private TipoPrioridad prioridad;
    private TipoActividad tipoActividad;
    private String fechaVencimiento;
    private int avance; 
    private String id;
    private int tiempoEstimado;
    private String fechaActual;
    private String descripcion;

    public enum TipoPrioridad{
        Alta,
        Media,
        Baja;
    }

    public enum TipoActividad{
        Personal,
        Academica;
    }

    public String getNombre() {
        return nombre;
    }  
    public TipoPrioridad getPrioridad() {
        return prioridad;
    }
    public String getfechaVencimiento() {
        return fechaVencimiento;
    }
    public int getAvance() {
        return avance;
    }
    public String getId() {
        return id;
    }
    public int getTiempoEstimado() {
        return tiempoEstimado;
    }
    public String getFechaActual() {
        return fechaActual;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public void registrarAvance(int avance){
        this.avance = avance;
    }
    public TipoActividad getTipoActividad(){
        return tipoActividad;
    }
    public Actividad(String nombre, TipoPrioridad prioridad, String fechaVencimiento, 
    int avance, String id, int tiempoEstimado, String fechaActual) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.fechaVencimiento = fechaVencimiento;
        this.avance = avance;
        this.id = id;
        this.tiempoEstimado = tiempoEstimado;
        this.fechaActual = fechaActual;
    }
    public Actividad(){
    }
    public Actividad(String nombre, TipoPrioridad prioridad, String fechaVencimiento) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.fechaVencimiento = fechaVencimiento;
        this.avance = 0; 
    }

}
