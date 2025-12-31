package espol.poo.sistemabienestarestudiantil.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import espol.poo.sistemabienestarestudiantil.R;

public class IntroJuegoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_juego);

        Button btnIniciar = findViewById(R.id.btnIniciarJuego);
        Button btnVolver = findViewById(R.id.btnVolverMenu);

        // Al dar clic en INICIAR, vamos al Tablero (JuegoMemoriaActivity)
        btnIniciar.setOnClickListener(v -> {
            Intent intent = new Intent(IntroJuegoActivity.this, JuegoMemoriaActivity.class);
            startActivity(intent);
        });

        // Al dar clic en VOLVER, cerramos esta pantalla y regresamos al Main
        btnVolver.setOnClickListener(v -> finish());
    }
}