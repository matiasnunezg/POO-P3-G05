package espol.poo.sistemabienestarestudiantil.ui.sostenibilidad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        // Botones
        findViewById(R.id.btnRegistrarAccion).setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistroSostenibilidadActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnVolver).setOnClickListener(v -> finish());

        // Cargar todo
        cargarDatos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos();
    }

    private void cargarDatos() {
        List<RegistroDiarioSostenible> lista = AppRepository.getInstance(this).getListaSostenibilidad();

        // 1. Configurar RecyclerView (LA LISTA NUEVA)
        RecyclerView recycler = findViewById(R.id.recyclerSostenibilidad);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        SostenibilidadAdapter adapter = new SostenibilidadAdapter(lista);
        recycler.setAdapter(adapter);

        // 2. Calcular Resumen (TU LÓGICA ANTERIOR INTACTA)
        calcularResumen(lista);
    }

    private void calcularResumen(List<RegistroDiarioSostenible> lista) {
        // ... (Aquí copias EXACTAMENTE el método calcularResumen que ya tenías)
        // ... Solo asegúrate de que use la lista que le pasamos por parámetro

        // COPIA ESTO DENTRO DE TU CLASE (Es tu lógica original):
        LocalDate hoy = LocalDate.now();
        LocalDate haceUnaSemana = hoy.minusDays(6);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM");

        TextView tvRango = findViewById(R.id.tvRangoFecha);
        tvRango.setText("(" + haceUnaSemana.format(fmt) + " - " + hoy.format(fmt) + ")");

        int cTransporte = 0, cImpresiones = 0, cEnvases = 0, cReciclaje = 0;
        int diasConAlMenosUna = 0, diasPerfectos = 0, totalDias = 7;

        for (RegistroDiarioSostenible r : lista) {
            if (r == null || r.getFecha() == null) continue;
            // Filtro semana
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

        actualizarFila(R.id.tvCountTransporte, R.id.tvBadgeTransporte, cTransporte, totalDias);
        actualizarFila(R.id.tvCountImpresiones, R.id.tvBadgeImpresiones, cImpresiones, totalDias);
        actualizarFila(R.id.tvCountEnvases, R.id.tvBadgeEnvases, cEnvases, totalDias);
        actualizarFila(R.id.tvCountReciclaje, R.id.tvBadgeReciclaje, cReciclaje, totalDias);

        TextView tvAnalisisUno = findViewById(R.id.tvDiasAlMenosUna);
        TextView tvAnalisisPerf = findViewById(R.id.tvDiasPerfectos);
        int porcUno = (int) ((diasConAlMenosUna * 100.0) / totalDias);
        int porcPerf = (int) ((diasPerfectos * 100.0) / totalDias);

        tvAnalisisUno.setText(String.format(Locale.getDefault(), "Días con al menos 1 acción: %d (%d%%)", diasConAlMenosUna, porcUno));
        tvAnalisisPerf.setText(String.format(Locale.getDefault(), "Días perfectos: %d (%d%%)", diasPerfectos, porcPerf));
    }

    private void actualizarFila(int idTextCount, int idTextBadge, int cantidad, int total) {
        TextView tvCount = findViewById(idTextCount);
        TextView tvBadge = findViewById(idTextBadge);
        tvCount.setText(cantidad + "/" + total);

        if (cantidad == 7) {
            tvBadge.setText("¡Excelente!"); tvBadge.setBackgroundColor(Color.parseColor("#1B5E20"));
        } else if (cantidad >= 5) {
            tvBadge.setText("Muy bien"); tvBadge.setBackgroundColor(Color.parseColor("#4CAF50"));
        } else if (cantidad >= 3) {
            tvBadge.setText("Regular"); tvBadge.setBackgroundColor(Color.parseColor("#FF9800"));
        } else {
            tvBadge.setText("Mejorar"); tvBadge.setBackgroundColor(Color.parseColor("#D32F2F"));
        }
        tvBadge.setTextColor(Color.WHITE);
    }
}