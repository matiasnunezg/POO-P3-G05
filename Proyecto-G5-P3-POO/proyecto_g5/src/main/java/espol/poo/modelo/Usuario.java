package espol.poo.modelo;

public class Usuario {
    // Atributos básicos del usuario
    private String nombre;
    private int edad;
    private double peso; // en kilogramos
    private RegistroHidratacion registroHidratacion;

    // Constructor
    public Usuario(String nombre, int edad, double peso, double metaDiariaAgua) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.registroHidratacion = new RegistroHidratacion(metaDiariaAgua);
    }

    // Métodos getter y setter
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public RegistroHidratacion getRegistroHidratacion() {
        return registroHidratacion;
    }

    public void setRegistroHidratacion(RegistroHidratacion registroHidratacion) {
        this.registroHidratacion = registroHidratacion;
    }

    // Mostrar resumen del usuario
    public void mostrarInformacion() {
        System.out.println("Usuario: " + nombre);
        System.out.println("Edad: " + edad + " años");
        System.out.println("Peso: " + peso + " kg");
        System.out.println("Meta diaria de agua: " + registroHidratacion.getMetaDiaria() + " ml");
    }
}
