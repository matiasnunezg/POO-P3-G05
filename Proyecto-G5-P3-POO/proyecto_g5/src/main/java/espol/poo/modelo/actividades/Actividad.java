package espol.poo.modelo.actividades;

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
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }
    public int getAvance() {
        return avance;
    }
    public int getId() {
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
    public void setAvance(int avance){
        this.avance = avance;
    }
    public TipoActividad getTipoActividad(){
        return tipoActividad;
    }

    public boolean estaVencida(){
        if (fechaVencimiento == fechaActual){
            return true;
        }
        else{
            return false;
        }
    }
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
    public Actividad(){
    }
    public Actividad(String nombre, TipoPrioridad prioridad, String fechaVencimiento) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.fechaVencimiento = fechaVencimiento;
        this.avance = 0; 
    }

    public String getTiempoEstimadoFormateado() {
        int minutos = this.getTiempoEstimado();

        if (minutos < 60) {
            return minutos + " min";
        }
        int horas = minutos / 60;
        int minsRest = minutos % 60;

        if (minsRest == 0){ 
            return horas + " h";
        }
        else{
            return horas + " h " + minsRest + " min";
        }
        }

}
