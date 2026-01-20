package espol.poo.sistemabienestarestudiantil.ui;
//Menu Principal
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

        // 1. Inicializar la "Base de Datos"
        AppRepository.getInstance();

        // 2. Configurar los botones
        configurarBotones();
    }

    private void configurarBotones() {
        // Gestión de Actividades
        findViewById(R.id.btnActividades).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaActividadesActivity.class);
            startActivity(intent);
        });

        // Hidratación
        findViewById(R.id.btnHidratacion).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HidratacionActivity.class);
            startActivity(intent);
        });

        // --- AQUÍ ESTÁ EL CAMBIO ---
        // Ahora el botón 3 abre TU pantalla de Sueño
        findViewById(R.id.btnSuenio).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SuenioActivity.class);
            startActivity(intent);
        });
        // ---------------------------

        // Sostenibilidad (Sigue en construcción por ahora)
        findViewById(R.id.btnSostenibilidad).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SostenibilidadActivity.class);
            startActivity(intent);
        });

        // Juego de Memoria
        findViewById(R.id.btnJuego).setOnClickListener(v -> {
            // CAMBIO: Ahora dirigimos a la Intro, no al juego directo
            Intent intent = new Intent(MainActivity.this, IntroJuegoActivity.class);
            startActivity(intent);
        });

        // Salir
        findViewById(R.id.btnSalir).setOnClickListener(v -> finishAffinity());
    }
}