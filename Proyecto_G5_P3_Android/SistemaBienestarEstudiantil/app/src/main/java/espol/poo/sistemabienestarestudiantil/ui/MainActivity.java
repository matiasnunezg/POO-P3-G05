package espol.poo.sistemabienestarestudiantil.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.ui.HidratacionActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicializar la "Base de Datos" (Modelo)
        // Esto asegura que las listas existan antes de usar cualquier otra pantalla
        AppRepository.getInstance();

        // 2. Configurar los botones
        configurarBotones();
    }

    private void configurarBotones() {
        // --- OPCIONES PENDIENTES (Muestran mensaje temporal) ---
        findViewById(R.id.btnActividades).setOnClickListener(v -> mostrarMensaje("Gestión de Actividades: En construcción"));
        findViewById(R.id.btnEnfoque).setOnClickListener(v -> mostrarMensaje("Técnicas de Enfoque: En construcción"));
        findViewById(R.id.btnHidratacion).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HidratacionActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnSuenio).setOnClickListener(v -> mostrarMensaje("Registro de Sueño: En construcción"));
        findViewById(R.id.btnSostenibilidad).setOnClickListener(v -> mostrarMensaje("Sostenibilidad: En construcción"));

        // --- OPCIÓN DEL JUEGO (La que vamos a programar ahora) ---
        findViewById(R.id.btnJuego).setOnClickListener(v -> {
            // Navegamos a la actividad del juego
            Intent intent = new Intent(MainActivity.this, JuegoMemoriaActivity.class);
            startActivity(intent);
        });

        // --- SALIR ---
        findViewById(R.id.btnSalir).setOnClickListener(v -> {
            // Cierra la aplicación completamente y quita las actividades de la pila
            finishAffinity();
        });
    }

    // Método auxiliar para no repetir código de Toast
    private void mostrarMensaje(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    
}