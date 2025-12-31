package espol.poo.sistemabienestarestudiantil.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import espol.poo.sistemabienestarestudiantil.R;
import java.util.Locale;

public class PomodoroActivity extends AppCompatActivity {

    private TextView txtCronometro, txtNombreTarea, btn25, btn5, btn15;
    private Button btnIniciar, btnPausar, btnReiniciar;
    private CountDownTimer countDownTimer;
    private long tiempoRestanteMilis;
    private boolean timerCorriendo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        txtCronometro = findViewById(R.id.txtCronometro);
        txtNombreTarea = findViewById(R.id.txtNombreTarea);
        btn25 = findViewById(R.id.btnTime25);
        btn5 = findViewById(R.id.btnTime5);
        btn15 = findViewById(R.id.btnTime15);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnPausar = findViewById(R.id.btnPausar);
        btnReiniciar = findViewById(R.id.btnAccionFinal);

        String nombre = getIntent().getStringExtra("nombre_tarea");
        if (nombre != null) txtNombreTarea.setText("Actividad: " + nombre);

        setTiempoSeleccionado(25, btn25);

        btn25.setOnClickListener(v -> setTiempoSeleccionado(25, btn25));
        btn5.setOnClickListener(v -> setTiempoSeleccionado(5, btn5));
        btn15.setOnClickListener(v -> setTiempoSeleccionado(15, btn15));

        btnIniciar.setOnClickListener(v -> iniciarTimer());
        btnPausar.setOnClickListener(v -> pausarTimer());

        // Al presionar Reiniciar/Finalizar vuelve atrás
        btnReiniciar.setOnClickListener(v -> {
            pausarTimer();
            finish();
        });
    }

    private void setTiempoSeleccionado(int minutos, TextView vistaSeleccionada) {
        if (timerCorriendo) return;

        btn25.setBackgroundResource(R.drawable.bg_pill_unselected);
        btn25.setTextColor(ContextCompat.getColor(this, R.color.colorAzulDeep));
        btn5.setBackgroundResource(R.drawable.bg_pill_unselected);
        btn5.setTextColor(ContextCompat.getColor(this, R.color.colorAzulDeep));
        btn15.setBackgroundResource(R.drawable.bg_pill_unselected);
        btn15.setTextColor(ContextCompat.getColor(this, R.color.colorAzulDeep));

        vistaSeleccionada.setBackgroundResource(R.drawable.bg_pill_selected);
        vistaSeleccionada.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        tiempoRestanteMilis = minutos * 60000;
        actualizarTextoCronometro();
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
                Toast.makeText(PomodoroActivity.this, "¡Tiempo terminado!", Toast.LENGTH_SHORT).show();
            }
        }.start();
        timerCorriendo = true;
    }

    private void pausarTimer() {
        if (countDownTimer != null) countDownTimer.cancel();
        timerCorriendo = false;
    }

    private void actualizarTextoCronometro() {
        int minutos = (int) (tiempoRestanteMilis / 1000) / 60;
        int segundos = (int) (tiempoRestanteMilis / 1000) % 60;
        txtCronometro.setText(String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos));
    }
}