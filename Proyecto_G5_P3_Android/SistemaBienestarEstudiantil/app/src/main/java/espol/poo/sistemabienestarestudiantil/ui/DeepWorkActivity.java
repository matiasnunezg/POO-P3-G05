package espol.poo.sistemabienestarestudiantil.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import espol.poo.sistemabienestarestudiantil.R;
import java.util.Locale;

public class DeepWorkActivity extends AppCompatActivity {

    private TextView txtCronometro, txtNombreTarea, btn45, btn60, btn90;
    private Button btnIniciar, btnPausar, btnAccionFinal;

    private CountDownTimer countDownTimer;
    private long tiempoRestanteMilis;
    private boolean timerCorriendo = false;
    private long tiempoOriginalSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_work);

        // 1. Vincular componentes del XML
        txtCronometro = findViewById(R.id.txtCronometro);
        txtNombreTarea = findViewById(R.id.txtNombreTarea);
        btn45 = findViewById(R.id.btnTime45);
        btn60 = findViewById(R.id.btnTime60);
        btn90 = findViewById(R.id.btnTime90);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnPausar = findViewById(R.id.btnPausar);
        btnAccionFinal = findViewById(R.id.btnAccionFinal); // Este es el botón "Finalizar"

        // 2. Recibir datos del Intent (Nombre de la actividad seleccionada)
        String nombre = getIntent().getStringExtra("nombre_tarea");
        if (nombre != null) {
            txtNombreTarea.setText("Actividad: " + nombre);
        }

        // 3. Configuración inicial (45 minutos por defecto)
        setTiempoSeleccionado(45, btn45);

        // 4. Listeners para cambio de tiempo (Pastillas)
        btn45.setOnClickListener(v -> setTiempoSeleccionado(45, btn45));
        btn60.setOnClickListener(v -> setTiempoSeleccionado(60, btn60));
        btn90.setOnClickListener(v -> setTiempoSeleccionado(90, btn90));

        // 5. Listeners para control del cronómetro
        btnIniciar.setOnClickListener(v -> iniciarTimer());
        btnPausar.setOnClickListener(v -> pausarTimer());

        // Al presionar Finalizar, se cierra la pantalla y vuelve a la lista
        btnAccionFinal.setOnClickListener(v -> {
            pausarTimer();
            Toast.makeText(this, "Sesión terminada", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void setTiempoSeleccionado(int minutos, TextView vistaSeleccionada) {
        if (timerCorriendo) {
            Toast.makeText(this, "Detén el cronómetro para cambiar el tiempo", Toast.LENGTH_SHORT).show();
            return;
        }

        // Resetear todas las pastillas al color claro
        resetearEstiloPastillas();

        // Aplicar color oscuro a la seleccionada
        vistaSeleccionada.setBackgroundResource(R.drawable.bg_pill_selected);
        vistaSeleccionada.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        // Actualizar valores de tiempo
        tiempoRestanteMilis = minutos * 60000;
        tiempoOriginalSeleccionado = tiempoRestanteMilis;
        actualizarTextoCronometro();
    }

    private void resetearEstiloPastillas() {
        TextView[] pastillas = {btn45, btn60, btn90};
        for (TextView p : pastillas) {
            p.setBackgroundResource(R.drawable.bg_pill_unselected);
            p.setTextColor(ContextCompat.getColor(this, R.color.colorAzulDeep));
        }
    }

    private void iniciarTimer() {
        if (timerCorriendo) return;

        countDownTimer = new CountDownTimer(tiempoRestanteMilis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tiempoRestanteMilis = millisUntilFinished;
                actualizarTextoCronometro();
            }

            @Override
            public void onFinish() {
                timerCorriendo = false;
                txtCronometro.setText("00:00");
                Toast.makeText(DeepWorkActivity.this, "¡Buen trabajo! Sesión completada.", Toast.LENGTH_LONG).show();
            }
        }.start();

        timerCorriendo = true;
    }

    private void pausarTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timerCorriendo = false;
    }

    private void actualizarTextoCronometro() {
        int minutos = (int) (tiempoRestanteMilis / 1000) / 60;
        int segundos = (int) (tiempoRestanteMilis / 1000) % 60;
        String tiempoFormateado = String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos);
        txtCronometro.setText(tiempoFormateado);
    }
}