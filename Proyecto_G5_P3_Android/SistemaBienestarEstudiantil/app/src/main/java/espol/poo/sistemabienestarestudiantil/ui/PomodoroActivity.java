package espol.poo.sistemabienestarestudiantil.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica;

public class PomodoroActivity extends AppCompatActivity {

    private TextView txtCronometro, txtNombreTarea, btn25, btn5seg, btn15;
    private Button btnIniciar, btnPausar, btnAccionFinal;
    private CountDownTimer countDownTimer;
    private long tiempoRestanteMilis;
    private boolean timerCorriendo = false;
    private int minutosSeleccionados = 25;
    private boolean esModoPrueba = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        txtCronometro = findViewById(R.id.txtCronometro);
        txtNombreTarea = findViewById(R.id.txtNombreTarea);
        btn25 = findViewById(R.id.btnTime25);
        btn5seg = findViewById(R.id.btnTime5);
        btn15 = findViewById(R.id.btnTime15);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnPausar = findViewById(R.id.btnPausar);
        btnAccionFinal = findViewById(R.id.btnAccionFinal);

        String nombre = getIntent().getStringExtra("nombre_tarea");
        if (nombre != null) txtNombreTarea.setText("Actividad: " + nombre);

        // Configuración inicial por defecto
        setTiempoSeleccionado(25, btn25, false);

        btn25.setOnClickListener(v -> setTiempoSeleccionado(25, btn25, false));
        btn15.setOnClickListener(v -> setTiempoSeleccionado(15, btn15, false));

        btn5seg.setOnClickListener(v -> {
            if (timerCorriendo) return;
            resetearEstiloPastillas();
            btn5seg.setBackgroundResource(R.drawable.bg_pill_selected);
            btn5seg.setTextColor(Color.WHITE);
            this.esModoPrueba = true;
            this.tiempoRestanteMilis = 5 * 1000;
            actualizarTextoCronometro();
        });

        btnIniciar.setOnClickListener(v -> iniciarTimer());

        btnPausar.setOnClickListener(v -> {
            if (countDownTimer != null) countDownTimer.cancel();
            timerCorriendo = false;
        });

        // REINICIAR: Cancela la sesión y avisa al usuario
        btnAccionFinal.setOnClickListener(v -> {
            if (countDownTimer != null) countDownTimer.cancel();
            Toast.makeText(this, "Sesión cancelada (No guardada)", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void iniciarTimer() {
        if (timerCorriendo) return;
        countDownTimer = new CountDownTimer(tiempoRestanteMilis, 1000) {
            @Override public void onTick(long l) {
                tiempoRestanteMilis = l;
                actualizarTextoCronometro();
            }
            @Override public void onFinish() {
                timerCorriendo = false;
                tiempoRestanteMilis = 0;
                actualizarTextoCronometro();
                guardarSesionFinal();
            }
        }.start();
        timerCorriendo = true;
    }

    private void guardarSesionFinal() {
        int idActividad = getIntent().getIntExtra("ID_EXTRA", -1);
        AppRepository repo = AppRepository.getInstance(this);
        Actividad actividad = repo.buscarActividadPorId(idActividad);

        if (actividad instanceof ActividadAcademica) {
            int segundosARegistrar = esModoPrueba ? 5 : (minutosSeleccionados * 60);
            ((ActividadAcademica) actividad).registrarSesion("Pomodoro", segundosARegistrar);

            // Guardado físico en archivo .ser
            repo.guardarActividadesEnArchivo();

            Toast.makeText(this, "Felicidades, Sesión guardada automáticamente", Toast.LENGTH_LONG).show();
        }

        // Cierre con retraso para permitir ver el 00:00
        txtCronometro.postDelayed(this::finish, 1000);
    }

    private void setTiempoSeleccionado(int min, TextView vista, boolean prueba) {
        if (timerCorriendo) return;
        this.minutosSeleccionados = min;
        this.esModoPrueba = prueba;
        resetearEstiloPastillas();
        vista.setBackgroundResource(R.drawable.bg_pill_selected);
        vista.setTextColor(Color.WHITE);
        this.tiempoRestanteMilis = (long) min * 60000;
        actualizarTextoCronometro();
    }

    private void resetearEstiloPastillas() {
        TextView[] ps = {btn25, btn5seg, btn15};
        for (TextView p : ps) {
            p.setBackgroundResource(R.drawable.bg_pill_unselected);
            p.setTextColor(Color.parseColor("#2E4091"));
        }
    }

    private void actualizarTextoCronometro() {
        int m = (int) (tiempoRestanteMilis / 1000) / 60;
        int s = (int) (tiempoRestanteMilis / 1000) % 60;
        txtCronometro.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
    }
}