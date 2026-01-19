package espol.poo.sistemabienestarestudiantil.modelo.enfoques;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.io.Serializable;

public class SesionEnfoque implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fecha;
    private String tecnica;
    private int tiempoSegundos;

    public SesionEnfoque(String tecnica, int segundos) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        this.fecha = sdf.format(new Date());
        this.tecnica = tecnica;
        this.tiempoSegundos = segundos;
    }

    public String getFecha() { return fecha; }
    public String getTecnica() { return tecnica; }

    public String getDuracionFormateada() {
        // Lógica: Si es menos de 60 segundos, muestra segundos. Si no, muestra minutos.
        if (tiempoSegundos < 60) {
            return tiempoSegundos + " seg";
        } else {
            int totalMinutos = tiempoSegundos / 60;
            if (totalMinutos < 60) {
                return totalMinutos + " min";
            } else {
                int h = totalMinutos / 60;
                int m = totalMinutos % 60;
                return (m == 0) ? h + " h" : h + " h " + m + " min";
            }
        }
    }
}