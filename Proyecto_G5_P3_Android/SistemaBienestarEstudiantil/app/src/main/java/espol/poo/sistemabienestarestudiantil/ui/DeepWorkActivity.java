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
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica;
import java.util.Locale;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;

import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica;

import espol.poo.sistemabienestarestudiantil.data.AppRepository;

public class DeepWorkActivity extends AppCompatActivity {

    private TextView txtCronometro, txtNombreTarea, btn45, btn60, btn90;
    private Button btnIniciar, btnPausar, btnAccionFinal;
    private CountDownTimer countDownTimer;
    private long tiempoRestanteMilis;
    private boolean timerCorriendo = false;
    private int minutosSeleccionados; // Para guardar en el historial

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_work);

        txtCronometro = findViewById(R.id.txtCronometro);
        txtNombreTarea = findViewById(R.id.txtNombreTarea);
        btn45 = findViewById(R.id.btnTime45);
        btn60 = findViewById(R.id.btnTime60);
        btn90 = findViewById(R.id.btnTime90);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnPausar = findViewById(R.id.btnPausar);
        btnAccionFinal = findViewById(R.id.btnAccionFinal);

        String nombre = getIntent().getStringExtra("nombre_tarea");
        if (nombre != null) txtNombreTarea.setText("Actividad: " + nombre);

        setTiempoSeleccionado(45, btn45); // Por defecto

        btn45.setOnClickListener(v -> setTiempoSeleccionado(45, btn45));
        btn60.setOnClickListener(v -> setTiempoSeleccionado(60, btn60));
        btn90.setOnClickListener(v -> setTiempoSeleccionado(90, btn90));

        btnIniciar.setOnClickListener(v -> iniciarTimer());
        btnPausar.setOnClickListener(v -> pausarTimer());
        btnAccionFinal.setOnClickListener(v -> registrarYSalir()); // Registro manual
    }

    private void setTiempoSeleccionado(int minutos, TextView vistaSeleccionada) {
        if (timerCorriendo) return;
        this.minutosSeleccionados = minutos;
        resetearEstiloPastillas();
        vistaSeleccionada.setBackgroundResource(R.drawable.bg_pill_selected);
        vistaSeleccionada.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        tiempoRestanteMilis = minutos * 60000;
        actualizarTextoCronometro();
    }

    private void iniciarTimer() {
        if (timerCorriendo) return;
        countDownTimer = new CountDownTimer(tiempoRestanteMilis, 1000) {
            @Override public void onTick(long millis) {
                tiempoRestanteMilis = millis;
                actualizarTextoCronometro();
            }
            @Override public void onFinish() {
                timerCorriendo = false;
                registrarYSalir(); // Registro automático al terminar
            }
        }.start();
        timerCorriendo = true;
    }

    private void registrarYSalir() {
        // 1. Detener el contador si aún está activo para evitar fugas de memoria
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // 2. Obtener el ID de la actividad que recibimos por el Intent
        int idActividad = getIntent().getIntExtra("ID_EXTRA", -1);

        // 3. Buscar la actividad real en el Repositorio (Singleton)
        Actividad actividad = AppRepository.getInstance().buscarActividadPorId(idActividad);

        // 4. Verificar que sea una Actividad Académica (las únicas que tienen historial)
        if (actividad instanceof ActividadAcademica) {
            // Casteamos para acceder al método registrarSesion
            ActividadAcademica acad = (ActividadAcademica) actividad;

            // Usamos la variable 'minutosSeleccionados' que se actualiza al tocar las pastillas (45, 60, 90)
            acad.registrarSesion("Deep Work", minutosSeleccionados);

            Toast.makeText(this, "Sesión de Deep Work guardada: " + minutosSeleccionados + " min", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error: No se pudo registrar la sesión", Toast.LENGTH_SHORT).show();
        }

        // 5. Finalizar la actividad para regresar a la pantalla anterior (Detalles o Lista)
        finish();
    }

    private void resetearEstiloPastillas() {
        TextView[] pastillas = {btn45, btn60, btn90};
        for (TextView p : pastillas) {
            p.setBackgroundResource(R.drawable.bg_pill_unselected);
            p.setTextColor(ContextCompat.getColor(this, R.color.colorAzulDeep));
        }
    }

    private void pausarTimer() {
        if (countDownTimer != null) countDownTimer.cancel();
        timerCorriendo = false;
    }

    private void actualizarTextoCronometro() {
        int m = (int) (tiempoRestanteMilis / 1000) / 60;
        int s = (int) (tiempoRestanteMilis / 1000) % 60;
        txtCronometro.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
    }
}