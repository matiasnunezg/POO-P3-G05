package espol.poo.sistemabienestarestudiantil.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicializar la "Base de Datos" (Modelo)
        AppRepository.getInstance();

        // 2. Configurar los botones
        configurarBotones();
    }

    private void configurarBotones() {
        // --- AQUÍ ESTÁ EL CAMBIO (TU PARTE) ---
        // Antes solo mostraba mensaje, ahora abre TU lista
        findViewById(R.id.btnActividades).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaActividadesActivity.class);
            startActivity(intent);
        });


        // --- HIDRATACIÓN (Ya estaba conectado por tu amigo) ---
        findViewById(R.id.btnHidratacion).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HidratacionActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnSuenio).setOnClickListener(v -> mostrarMensaje("Registro de Sueño: En construcción"));
        findViewById(R.id.btnSostenibilidad).setOnClickListener(v -> mostrarMensaje("Sostenibilidad: En construcción"));

        // --- OPCIÓN DEL JUEGO (Lo dejé tal cual estaba en tu código) ---
        findViewById(R.id.btnJuego).setOnClickListener(v -> {
            // Nota: Si JuegoMemoriaActivity no existe aún, comenta estas 2 líneas para que no de error
            Intent intent = new Intent(MainActivity.this, JuegoMemoriaActivity.class);
            startActivity(intent);
        });

        // --- SALIR ---
        findViewById(R.id.btnSalir).setOnClickListener(v -> {
            finishAffinity();
        });
    }

    // Método auxiliar (respetando el código de tu amigo)
    private void mostrarMensaje(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }
}