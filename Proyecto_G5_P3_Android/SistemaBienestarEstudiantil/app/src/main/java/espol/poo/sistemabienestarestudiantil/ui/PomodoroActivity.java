package espol.poo.sistemabienestarestudiantil.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Locale;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;

import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica;

import espol.poo.sistemabienestarestudiantil.data.AppRepository;

public class PomodoroActivity extends AppCompatActivity {

    private TextView txtCronometro, txtNombreTarea, btn25, btn5, btn15;
    private Button btnIniciar, btnPausar, btnAccionFinal;

    private CountDownTimer countDownTimer;
    private long tiempoRestanteMilis;
    private boolean timerCorriendo = false;
    private int minutosSeleccionados; // Para guardar exactamente lo que el usuario eligió

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        // 1. Vincular componentes del XML
        txtCronometro = findViewById(R.id.txtCronometro);
        txtNombreTarea = findViewById(R.id.txtNombreTarea);
        btn25 = findViewById(R.id.btnTime25);
        btn5 = findViewById(R.id.btnTime5);
        btn15 = findViewById(R.id.btnTime15);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnPausar = findViewById(R.id.btnPausar);
        btnAccionFinal = findViewById(R.id.btnAccionFinal); // Botón Reiniciar/Finalizar

        // 2. Recibir nombre de la actividad
        String nombre = getIntent().getStringExtra("nombre_tarea");
        if (nombre != null) {
            txtNombreTarea.setText("Actividad: " + nombre);
        }

        // 3. Configuración inicial (25 min por defecto)
        setTiempoSeleccionado(25, btn25);

        // 4. Listeners para las pastillas de tiempo
        btn25.setOnClickListener(v -> setTiempoSeleccionado(25, btn25));
        btn5.setOnClickListener(v -> setTiempoSeleccionado(5, btn5));
        btn15.setOnClickListener(v -> setTiempoSeleccionado(15, btn15));

        // 5. Listeners de control
        btnIniciar.setOnClickListener(v -> iniciarTimer());
        btnPausar.setOnClickListener(v -> pausarTimer());

        // En tu XML el botón rojo dice "Reiniciar", pero podemos usarlo para salir
        // o puedes cambiarle el texto a "Finalizar" en el XML.
        btnAccionFinal.setOnClickListener(v -> {
            pausarTimer();
            registrarYSalir();
        });
    }

    private void setTiempoSeleccionado(int minutos, TextView vistaSeleccionada) {
        if (timerCorriendo) {
            Toast.makeText(this, "Detén el cronómetro para cambiar el tiempo", Toast.LENGTH_SHORT).show();
            return;
        }

        this.minutosSeleccionados = minutos;
        resetearEstiloPastillas();

        // Aplicar estilo de seleccionado
        vistaSeleccionada.setBackgroundResource(R.drawable.bg_pill_selected);
        vistaSeleccionada.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        tiempoRestanteMilis = minutos * 60000;
        actualizarTextoCronometro();
    }

    private void resetearEstiloPastillas() {
        TextView[] pastillas = {btn25, btn5, btn15};
        for (TextView p : pastillas) {
            p.setBackgroundResource(R.drawable.bg_pill_unselected);
            // Usa el color azul de tu técnica
            p.setTextColor(ContextCompat.getColor(this, android.R.color.black));
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
                registrarYSalir(); // Se guarda solo al llegar a cero
            }
        }.start();

        timerCorriendo = true;
    }

    private void registrarYSalir() {
        // 1. Detenemos el timer para evitar que siga corriendo en segundo plano
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // 2. Obtenemos el ID de la actividad enviado desde la pantalla anterior
        int idActividad = getIntent().getIntExtra("ID_EXTRA", -1);

        // 3. Buscamos la actividad en el Repositorio global
        Actividad actividad = AppRepository.getInstance().buscarActividadPorId(idActividad);

        // 4. Verificamos si es una actividad académica para registrar la sesión
        if (actividad instanceof ActividadAcademica) {
            ActividadAcademica acad = (ActividadAcademica) actividad;

            // Registramos la sesión con la técnica "Pomodoro"
            // y los minutos que el usuario seleccionó (25, 5 o 15)
            acad.registrarSesion("Pomodoro", minutosSeleccionados);

            Toast.makeText(this, "Sesión de Pomodoro registrada: " + minutosSeleccionados + " min", Toast.LENGTH_SHORT).show();
        } else {
            // Esto sucede si el ID es -1 o si es una Actividad Personal
            Toast.makeText(this, "No se pudo registrar: La actividad no permite historial de enfoque", Toast.LENGTH_LONG).show();
        }

        // 5. Cerramos la actividad actual para regresar a Detalles
        finish();
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