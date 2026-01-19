package espol.poo.sistemabienestarestudiantil.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.Locale;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.suenio.RegistrarHorasDeSuenio;

public class SuenioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suenio);

        // Botón Registrar
        Button btnNuevo = findViewById(R.id.btnNuevoRegistroSueño);
        btnNuevo.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistrarSuenioActivity.class);
            startActivity(intent);
        });

        // Botón Volver (Rúbrica)
        Button btnVolver = findViewById(R.id.btnVolverSueño);
        btnVolver.setOnClickListener(v -> finish());

        actualizarLista();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarLista();
    }

    private void actualizarLista() {
        TextView tvLista = findViewById(R.id.tvListaRegistrosSueño);
        // Usamos getInstance(this) para asegurar que cargue archivos
        List<RegistrarHorasDeSuenio> lista = AppRepository.getInstance().getListaSuenio();

        if (lista == null || lista.isEmpty()) {
            tvLista.setText("No hay registros de sueño.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (RegistrarHorasDeSuenio r : lista) {
                String item = String.format(Locale.getDefault(),
                        "📅 %s\n⏰ %s - %s\n⏳ %.1f horas\n────────────────\n",
                        r.getFechaRegistro(), r.getHoraInicio(), r.getHoraFin(), r.getDuracionHoras());
                sb.append(item);
            }
            tvLista.setText(sb.toString());
        }
    }
}