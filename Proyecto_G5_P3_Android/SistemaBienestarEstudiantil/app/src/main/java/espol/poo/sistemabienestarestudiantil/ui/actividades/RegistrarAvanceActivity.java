package espol.poo.sistemabienestarestudiantil.ui.actividades;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;

public class RegistrarAvanceActivity extends AppCompatActivity {

    private EditText etId, etNombre, etAvanceActual, etNuevoAvance;
    private int idActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_avance);

        // 1. Vincular las vistas del XML
        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombre);
        etAvanceActual = findViewById(R.id.etAvanceActual);
        etNuevoAvance = findViewById(R.id.etNuevoAvance);

        // 2. Recibir los datos enviados desde la lista
        idActividad = getIntent().getIntExtra("ID_EXTRA", -1);
        String nombre = getIntent().getStringExtra("NOMBRE_EXTRA");
        int avance = getIntent().getIntExtra("AVANCE_EXTRA", 0);

        // 3. Mostrar los datos (Solo lectura)
        etId.setText(String.valueOf(idActividad));
        etNombre.setText(nombre);
        etAvanceActual.setText(avance + "%");

        // 4. Configurar el botón GUARDAR para mostrar el diálogo
        findViewById(R.id.btnGuardar).setOnClickListener(v -> mostrarConfirmacion());

        // 5. Configurar el botón CANCELAR
        findViewById(R.id.btnCancelar).setOnClickListener(v -> finish());
    }

    private void mostrarConfirmacion() {
        // Validar que el usuario haya escrito algo
        String nuevoAvanceStr = etNuevoAvance.getText().toString();

        if (nuevoAvanceStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa el nuevo porcentaje", Toast.LENGTH_SHORT).show();
            return;
        }

        int nuevoAvance = Integer.parseInt(nuevoAvanceStr);
        // Validar rango lógico (0 a 100)
        if (nuevoAvance < 0 || nuevoAvance > 100) {
            Toast.makeText(this, "El avance debe estar entre 0 y 100", Toast.LENGTH_SHORT).show();
            return;
        }

        // --- AQUÍ ESTÁ EL DIÁLOGO EXACTO QUE PEDISTE ---
        new AlertDialog.Builder(this)
                // No ponemos título para que se parezca más a tu imagen, o puedes poner "Confirmación"
                .setMessage("¿Está seguro de realizar esta acción?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Si dice SÍ, guardamos
                    guardarAvance(nuevoAvance);
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Si dice NO, solo cerramos el diálogo (no hace falta código extra)
                    dialog.dismiss();
                })
                .show();
    }

    private void guardarAvance(int nuevoAvance) {
        // 1. Buscamos la actividad real en la "base de datos" (Repositorio)
        Actividad actividad = AppRepository.getInstance(this).buscarActividadPorId(idActividad);

        if (actividad != null) {
            // 2. Actualizamos el avance
            actividad.setAvance(nuevoAvance);

            Toast.makeText(this, "Avance actualizado correctamente", Toast.LENGTH_SHORT).show();

            // 3. Cerramos la pantalla para volver a la lista
            finish();
        } else {
            Toast.makeText(this, "Error: No se encontró la actividad", Toast.LENGTH_SHORT).show();
        }
    }
}