package espol.poo.sistemabienestarestudiantil.ui.suenio;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalTime;
import java.util.Locale;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.suenio.RegistrarHorasDeSuenio;

public class RegistrarSuenioActivity extends AppCompatActivity {

    private EditText etInicio, etFin;
    private LocalTime horaInicio, horaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_suenio);

        // Conectamos con tus IDs
        etInicio = findViewById(R.id.etHoraInicioSueño);
        etFin = findViewById(R.id.etHoraFinSueño);

        etInicio.setOnClickListener(v -> mostrarReloj(true));
        etFin.setOnClickListener(v -> mostrarReloj(false));

        findViewById(R.id.btnGuardarSueño).setOnClickListener(v -> guardar());
        findViewById(R.id.btnCancelarSueño).setOnClickListener(v -> finish());
    }

    private void mostrarReloj(boolean esInicio) {
        TimePickerDialog dialog = new TimePickerDialog(this, (view, hour, minute) -> {
            LocalTime time = LocalTime.of(hour, minute);
            String texto = String.format(Locale.US, "%02d:%02d", hour, minute);
            if (esInicio) {
                horaInicio = time;
                etInicio.setText(texto);
            } else {
                horaFin = time;
                etFin.setText(texto);
            }
        }, 22, 0, true);
        dialog.show();
    }

    private void guardar() {
        if (horaInicio == null || horaFin == null) {
            Toast.makeText(this, "Por favor seleccione ambas horas", Toast.LENGTH_SHORT).show();
            return;
        }
        RegistrarHorasDeSuenio nuevo = new RegistrarHorasDeSuenio(horaInicio, horaFin);
        // Guardamos usando el Repositorio Seguro
        AppRepository.getInstance(this).agregarSuenio(nuevo);

        Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
        finish();
    }
}