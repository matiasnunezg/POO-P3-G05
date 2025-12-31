package espol.poo.sistemabienestarestudiantil.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.databinding.ActivitySuenioBinding;

public class SuenioActivity extends AppCompatActivity {

    private ActivitySuenioBinding binding;
    private SuenioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuenioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarLista();

        // Botón para ir al formulario
        binding.btnNuevoRegistro.setOnClickListener(v -> {
            startActivity(new Intent(this, RegistrarSuenioActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refrescar la lista al volver del formulario
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void configurarLista() {
        // Conectar Adapter con Repositorio
        adapter = new SuenioAdapter(AppRepository.getInstance().getListaSuenio());
        binding.recyclerSuenio.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerSuenio.setAdapter(adapter);
    }
}