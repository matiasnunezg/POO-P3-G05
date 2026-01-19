package espol.poo.sistemabienestarestudiantil.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.sostenibilidad.RegistroDiarioSostenible;

public class RegistroSostenibilidadActivity extends AppCompatActivity {

    private CheckBox chkTransporte, chkImpresiones, chkEnvases, chkReciclaje;
    private TextView tvFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_sostenibilidad);

        // 1. Vincular los elementos de la pantalla
        tvFecha = findViewById(R.id.tvFechaHoy);
        chkTransporte = findViewById(R.id.chkTransporte);
        chkImpresiones = findViewById(R.id.chkImpresiones);
        chkEnvases = findViewById(R.id.chkEnvases);
        chkReciclaje = findViewById(R.id.chkReciclaje);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        // 2. Mostrar la fecha de hoy
        LocalDate hoy = LocalDate.now();
        tvFecha.setText("(" + hoy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")");

        // 3. Configurar el botón Guardar
        btnGuardar.setOnClickListener(v -> {
            List<Integer> idsSeleccionados = new ArrayList<>();

            if (chkTransporte.isChecked()) idsSeleccionados.add(1);
            if (chkImpresiones.isChecked()) idsSeleccionados.add(2);
            if (chkEnvases.isChecked()) idsSeleccionados.add(3);
            if (chkReciclaje.isChecked()) idsSeleccionados.add(4);

            // Crear el registro con la fecha de hoy y las acciones marcadas
            RegistroDiarioSostenible nuevoRegistro = new RegistroDiarioSostenible(hoy, idsSeleccionados);

            // Guardarlo en la "Caja" (Repositorio)
            AppRepository.getInstance(this).agregarRegistroSostenible(nuevoRegistro);

            Toast.makeText(this, "¡Registro guardado!", Toast.LENGTH_SHORT).show();
            finish(); // Cierra esta pantalla y vuelve a la anterior
        });

        // 4. Botón Cancelar
        btnCancelar.setOnClickListener(v -> finish());
    }
}