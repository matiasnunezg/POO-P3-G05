package espol.poo.sistemabienestarestudiantil.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;

public class MainActivity extends AppCompatActivity {

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

        // Sueño
        findViewById(R.id.btnSuenio).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SuenioActivity.class);
            startActivity(intent);
        });

        // Sostenibilidad (Sigue en construcción por ahora)
        findViewById(R.id.btnSostenibilidad).setOnClickListener(v ->
                android.widget.Toast.makeText(this, "Sostenibilidad: En construcción", android.widget.Toast.LENGTH_SHORT).show()
        );

        // Juego de Memoria
        findViewById(R.id.btnJuego).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, IntroJuegoActivity.class);
            startActivity(intent);
        });

        // Salir
        findViewById(R.id.btnSalir).setOnClickListener(v -> finishAffinity());
    }
}