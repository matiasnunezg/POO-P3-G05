package espol.poo.sistemabienestarestudiantil.ui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalTime;
import java.util.Locale;

import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.databinding.ActivityRegistrarSuenioBinding;
import espol.poo.sistemabienestarestudiantil.modelo.suenio.RegistrarHorasDeSuenio;

public class RegistrarSuenioActivity extends AppCompatActivity {

    private ActivityRegistrarSuenioBinding binding;
    private LocalTime horaInicio, horaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrarSuenioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar selectores de hora
        binding.etHoraInicio.setOnClickListener(v -> mostrarReloj(true));
        binding.etHoraFin.setOnClickListener(v -> mostrarReloj(false));

        binding.btnGuardar.setOnClickListener(v -> guardarRegistro());
        binding.btnCancelar.setOnClickListener(v -> finish());
    }

    private void mostrarReloj(boolean esInicio) {
        TimePickerDialog dialog = new TimePickerDialog(this, (view, hour, minute) -> {
            LocalTime time = LocalTime.of(hour, minute);
            String texto = String.format(Locale.US, "%02d:%02d", hour, minute);

            if (esInicio) {
                horaInicio = time;
                binding.etHoraInicio.setText(texto);
            } else {
                horaFin = time;
                binding.etHoraFin.setText(texto);
            }
        }, 22, 0, true);
        dialog.show();
    }

    private void guardarRegistro() {
        if (horaInicio == null || horaFin == null) {
            Toast.makeText(this, "Por favor seleccione ambas horas", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto y guardar en Repositorio
        RegistrarHorasDeSuenio nuevo = new RegistrarHorasDeSuenio(horaInicio, horaFin);
        AppRepository.getInstance(this).agregarSuenio(nuevo);

        Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
        finish(); // Volver a la pantalla anterior
    }
}