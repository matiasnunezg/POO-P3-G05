package espol.poo.sistemabienestarestudiantil.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.sostenibilidad.RegistroDiarioSostenible;

public class SostenibilidadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sostenibilidad);

        // Botón para ir a registrar
        Button btnRegistrar = findViewById(R.id.btnRegistrarAccion);
        btnRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistroSostenibilidadActivity.class);
            startActivity(intent);
        });

        // NUEVO: Botón para Volver
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> finish());

        // Calcular datos al iniciar
        calcularResumen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recalcular cuando volvemos de la pantalla de registro
        calcularResumen();
    }

    private void calcularResumen() {
        List<RegistroDiarioSostenible> lista = AppRepository.getInstance(this).getListaSostenibilidad();

        // 1. Fechas (Hoy - Hace 6 días)
        LocalDate hoy = LocalDate.now();
        LocalDate haceUnaSemana = hoy.minusDays(6);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM");

        TextView tvRango = findViewById(R.id.tvRangoFecha);
        tvRango.setText("(" + haceUnaSemana.format(fmt) + " - " + hoy.format(fmt) + ")");

        // 2. Contadores
        int cTransporte = 0;
        int cImpresiones = 0;
        int cEnvases = 0;
        int cReciclaje = 0;

        int diasConAlMenosUna = 0;
        int diasPerfectos = 0;
        int totalDias = 7;

        // 3. Recorrer la lista
        for (RegistroDiarioSostenible r : lista) {
            // Validar que el registro no sea nulo y la fecha tampoco
            if (r == null || r.getFecha() == null) continue;

            // Filtro: Solo contar si está dentro de la última semana
            if (!r.getFecha().isBefore(haceUnaSemana) && !r.getFecha().isAfter(hoy)) {

                List<Integer> ids = r.getAccionesSeleccionadasIds();

                if (ids.contains(1)) cTransporte++;
                if (ids.contains(2)) cImpresiones++;
                if (ids.contains(3)) cEnvases++;
                if (ids.contains(4)) cReciclaje++;

                if (ids.size() >= 1) diasConAlMenosUna++;
                if (ids.size() == 4) diasPerfectos++;
            }
        }

        // 4. Actualizar Tabla (Lógica del Semáforo)
        actualizarFila(R.id.tvCountTransporte, R.id.tvBadgeTransporte, cTransporte, totalDias);
        actualizarFila(R.id.tvCountImpresiones, R.id.tvBadgeImpresiones, cImpresiones, totalDias);
        actualizarFila(R.id.tvCountEnvases, R.id.tvBadgeEnvases, cEnvases, totalDias);
        actualizarFila(R.id.tvCountReciclaje, R.id.tvBadgeReciclaje, cReciclaje, totalDias);

        // 5. Actualizar Resumen Inferior
        TextView tvAnalisisUno = findViewById(R.id.tvDiasAlMenosUna);
        TextView tvAnalisisPerf = findViewById(R.id.tvDiasPerfectos);

        int porcUno = (totalDias > 0) ? (int) ((diasConAlMenosUna * 100.0) / totalDias) : 0;
        int porcPerf = (totalDias > 0) ? (int) ((diasPerfectos * 100.0) / totalDias) : 0;

        tvAnalisisUno.setText(String.format(Locale.getDefault(),
                "Días con al menos 1 acción sostenible: %d de %d (%d%%)", diasConAlMenosUna, totalDias, porcUno));

        tvAnalisisPerf.setText(String.format(Locale.getDefault(),
                "Días con las 4 acciones completas: %d de %d (%d%%)", diasPerfectos, totalDias, porcPerf));
    }

    private void actualizarFila(int idTextCount, int idTextBadge, int cantidad, int total) {
        TextView tvCount = findViewById(idTextCount);
        TextView tvBadge = findViewById(idTextBadge);

        tvCount.setText(cantidad + "/" + total);

        // LÓGICA DE COLORES
        if (cantidad == 7) {
            tvBadge.setText("¡Excelente!");
            tvBadge.setBackgroundColor(Color.parseColor("#1B5E20")); // Verde Oscuro
        } else if (cantidad >= 5) {
            tvBadge.setText("Muy bien");
            tvBadge.setBackgroundColor(Color.parseColor("#4CAF50")); // Verde
        } else if (cantidad >= 3) {
            tvBadge.setText("Regular");
            tvBadge.setBackgroundColor(Color.parseColor("#FF9800")); // Naranja
        } else {
            tvBadge.setText("Mejorar");
            tvBadge.setBackgroundColor(Color.parseColor("#D32F2F")); // Rojo
        }
        tvBadge.setTextColor(Color.WHITE);
    }
}