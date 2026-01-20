package espol.poo.sistemabienestarestudiantil.ui.suenio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.suenio.RegistrarHorasDeSuenio;

public class SuenioActivity extends AppCompatActivity {

    private SuenioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suenio);

        // 1. Configurar la lista (RecyclerView)
        RecyclerView recyclerView = findViewById(R.id.recyclerSuenio);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Obtener datos (Con contexto para cargar archivo)
        List<RegistrarHorasDeSuenio> lista = AppRepository.getInstance(this).getListaSuenio();

        // 3. Poner el adaptador
        adapter = new SuenioAdapter(lista);
        recyclerView.setAdapter(adapter);

        // Botón Registrar
        Button btnNuevo = findViewById(R.id.btnNuevoRegistroSueño);
        btnNuevo.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistrarSuenioActivity.class);
            startActivity(intent);
        });

        // Botón Volver
        Button btnVolver = findViewById(R.id.btnVolverSueño);
        btnVolver.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar lista al volver de registrar
        if (adapter != null) adapter.notifyDataSetChanged();
    }
}