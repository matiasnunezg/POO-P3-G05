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

public class DeepWorkActivity extends AppCompatActivity {

    private TextView txtCronometro, txtNombreTarea, btn45, btn60, btn90;
    private Button btnIniciar, btnPausar, btnAccionFinal;
    private CountDownTimer countDownTimer;
    private long tiempoRestanteMilis;
    private boolean timerCorriendo = false;
    private int minutosSeleccionados = 45;
    private boolean esModoPrueba = false; // Control de precisión para el registro de 5 seg

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_work);

        // 1. Vincular componentes
        txtCronometro = findViewById(R.id.txtCronometro);
        txtNombreTarea = findViewById(R.id.txtNombreTarea);
        btn45 = findViewById(R.id.btnTime45);
        btn60 = findViewById(R.id.btnTime60);
        btn90 = findViewById(R.id.btnTime90);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnPausar = findViewById(R.id.btnPausar);
        btnAccionFinal = findViewById(R.id.btnAccionFinal);

        // 2. Configuración inicial
        String nombre = getIntent().getStringExtra("nombre_tarea");
        if (nombre != null) txtNombreTarea.setText("Actividad: " + nombre);

        // Iniciamos con 45 min por defecto
        setTiempoSeleccionado(45, btn45, false);

        // 3. Listeners de selección de tiempo
        btn45.setOnClickListener(v -> setTiempoSeleccionado(45, btn45, false));
        btn60.setOnClickListener(v -> setTiempoSeleccionado(60, btn60, false));

        // BOTÓN MODO PRUEBA (5 SEGUNDOS)
        btn90.setOnClickListener(v -> {
            if (timerCorriendo) return;
            resetearEstiloPastillas();
            btn90.setBackgroundResource(R.drawable.bg_pill_selected);
            btn90.setTextColor(Color.WHITE);
            this.esModoPrueba = true;
            this.tiempoRestanteMilis = 5 * 1000; // 5 segundos exactos
            actualizarTextoCronometro();
        });

        // 4. Controles del cronómetro
        btnIniciar.setOnClickListener(v -> iniciarTimer());

        btnPausar.setOnClickListener(v -> {
            if (countDownTimer != null) countDownTimer.cancel();
            timerCorriendo = false;
        });

        // REINICIAR: Detiene el tiempo y avisa que no se guarda
        btnAccionFinal.setOnClickListener(v -> {
            if (countDownTimer != null) countDownTimer.cancel();
            Toast.makeText(this, "Sesión cancelada (No guardada)", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void iniciarTimer() {
        if (timerCorriendo) return;
        countDownTimer = new CountDownTimer(tiempoRestanteMilis, 1000) {
            @Override
            public void onTick(long l) {
                tiempoRestanteMilis = l;
                actualizarTextoCronometro();
            }

            @Override
            public void onFinish() {
                timerCorriendo = false;
                tiempoRestanteMilis = 0;
                actualizarTextoCronometro();
                guardarYSalirAutomatico();
            }
        }.start();
        timerCorriendo = true;
    }

    private void guardarYSalirAutomatico() {
        int idActividad = getIntent().getIntExtra("ID_EXTRA", -1);
        AppRepository repo = AppRepository.getInstance(this);
        Actividad actividad = repo.buscarActividadPorId(idActividad);

        if (actividad instanceof ActividadAcademica) {
            // Si es modo prueba enviamos 5, sino convertimos minutos a segundos
            int segundosARegistrar = esModoPrueba ? 5 : (minutosSeleccionados * 60);

            ((ActividadAcademica) actividad).registrarSesion("Deep Work", segundosARegistrar);

            // Persistencia: Guardado físico en archivo .ser
            repo.guardarActividadesEnArchivo();

            Toast.makeText(this, "Felicidades, Sesión guardada automáticamente", Toast.LENGTH_LONG).show();
        }

        // Delay de 1 segundo para ver el 00:00 antes de cerrar
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
        TextView[] ps = {btn45, btn60, btn90};
        for (TextView p : ps) {
            p.setBackgroundResource(R.drawable.bg_pill_unselected);
            p.setTextColor(Color.BLACK);
        }
    }

    private void actualizarTextoCronometro() {
        int m = (int) (tiempoRestanteMilis / 1000) / 60;
        int s = (int) (tiempoRestanteMilis / 1000) % 60;
        txtCronometro.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
    }
}