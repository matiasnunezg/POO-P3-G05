package espol.poo.sistemabienestarestudiantil.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicializar la Base de Datos pasando el Contexto
        // Al tener el método híbrido, esto inicializa todo correctamente
        AppRepository.getInstance(this);

        configurarBotones();
    }

    private void configurarBotones() {
        findViewById(R.id.btnActividades).setOnClickListener(v -> startActivity(new Intent(this, ListaActividadesActivity.class)));
        findViewById(R.id.btnHidratacion).setOnClickListener(v -> startActivity(new Intent(this, HidratacionActivity.class)));
        findViewById(R.id.btnSuenio).setOnClickListener(v -> startActivity(new Intent(this, SuenioActivity.class)));
        findViewById(R.id.btnSostenibilidad).setOnClickListener(v -> startActivity(new Intent(this, SostenibilidadActivity.class)));
        findViewById(R.id.btnJuego).setOnClickListener(v -> startActivity(new Intent(this, IntroJuegoActivity.class)));
        findViewById(R.id.btnSalir).setOnClickListener(v -> finishAffinity());
    }
}