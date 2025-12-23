package espol.poo.sistemabienestarestudiantil.modelo.enfoques; // <--- 1. Paquete arreglado

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SesionEnfoque {

    // 2. CAMBIO: Usamos String en vez de LocalDate para que coincida con Actividad
    // y para evitar errores en celulares antiguos.
    private String fecha;
    private String tecnica;
    private int minutos;

    public SesionEnfoque(String tecnica, int minutos) {
        // Obtenemos la fecha actual y la convertimos a texto
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        this.fecha = sdf.format(new Date());

        this.tecnica = tecnica;
        this.minutos = minutos;
    }

    // Getters
    public String getFecha() {
        return fecha;
    }

    public String getTecnica() {
        return tecnica;
    }

    public int getMinutos() {
        return minutos;
    }

    public String getDuracionFormateada() {
        if (minutos < 60){
            return minutos + " min";
        }

        int h = minutos / 60;
        int m = minutos % 60;

        if (m == 0) {
            return h + " h";
        }
        else{
            return h + " h " + m + " min";
        }
    }
}