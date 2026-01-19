package espol.poo.sistemabienestarestudiantil.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// TUS IMPORTACIONES (Asegúrate que coincidan con tus paquetes)
import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadPersonal;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica.TipoActividadAcademica;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad.TipoPrioridad;

public class FormularioActividadActivity extends AppCompatActivity {

    // Vistas
    private EditText etNombre, etDesc, etFecha, etTiempo, etLugar, etAsignatura;
    private Spinner spCategoria, spPrioridad, spTipoAcademico;
    private LinearLayout layoutAcademico, layoutPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_actividad);

        inicializarVistas();
        configurarSpinners();
        configurarLogicaVisual(); // <--- Aquí ocurre la magia de ocultar/mostrar
        configurarFecha();

        // Botones
        findViewById(R.id.btnGuardar).setOnClickListener(v -> guardarDatos());
        findViewById(R.id.btnCancelar).setOnClickListener(v -> finish());
    }

    private void inicializarVistas() {
        etNombre = findViewById(R.id.etNombre);
        etDesc = findViewById(R.id.etDescripcion);
        etFecha = findViewById(R.id.etFecha);
        etTiempo = findViewById(R.id.etTiempo);
        etLugar = findViewById(R.id.etLugar);
        etAsignatura = findViewById(R.id.etAsignatura);

        layoutAcademico = findViewById(R.id.layoutAcademico);
        layoutPersonal = findViewById(R.id.layoutPersonal);

        spCategoria = findViewById(R.id.spinnerCategoria);
        spPrioridad = findViewById(R.id.spinnerPrioridad);
        spTipoAcademico = findViewById(R.id.spinnerTipoAcademico);
    }

    private void configurarSpinners() {
        // 1. Categoría
        String[] cats = {"Personal", "Académica"};
        spCategoria.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cats));

        // 2. Prioridad (Desde tu Enum)
        spPrioridad.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TipoPrioridad.values()));

        // 3. Tipo Académico (Desde tu Enum)
        spTipoAcademico.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TipoActividadAcademica.values()));
    }

    private void configurarLogicaVisual() {
        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = spCategoria.getSelectedItem().toString();
                if (seleccion.equals("Académica")) {
                    layoutAcademico.setVisibility(View.VISIBLE);
                    layoutPersonal.setVisibility(View.GONE);
                } else {
                    layoutAcademico.setVisibility(View.GONE);
                    layoutPersonal.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void configurarFecha() {
        etFecha.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, y, m, d) ->
                    etFecha.setText(String.format(Locale.getDefault(), "%02d/%02d/%d", d, m+1, y)),
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void guardarDatos() {
        // 1. Obtener datos básicos
        String nombre = etNombre.getText().toString();
        String fecha = etFecha.getText().toString();

        // Validación simple
        if (nombre.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Falta Nombre o Fecha", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener resto de datos
        TipoPrioridad prioridad = (TipoPrioridad) spPrioridad.getSelectedItem();
        String desc = etDesc.getText().toString();

        // Parseo seguro del tiempo (si está vacío es 0)
        int tiempo = 0;
        try {
            if (!etTiempo.getText().toString().isEmpty()) {
                tiempo = Integer.parseInt(etTiempo.getText().toString());
            }
        } catch (NumberFormatException e) {
            tiempo = 0;
        }

        // --- CORRECCIÓN DE ID: PEDIMOS EL SIGUIENTE (1, 2, 3...) ---
        int idNuevo = AppRepository.getInstance().getProximoId();

        // Fecha de hoy para registro
        String fechaHoy = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        Actividad nuevaActividad;
        String categoria = spCategoria.getSelectedItem().toString();

        // 2. Crear Objeto según categoría
        if (categoria.equals("Académica")) {
            nuevaActividad = new ActividadAcademica(
                    nombre,
                    prioridad,
                    fecha,
                    0,      // Avance inicial
                    idNuevo,
                    tiempo,
                    fechaHoy,
                    etAsignatura.getText().toString(), // Materia
                    (TipoActividadAcademica) spTipoAcademico.getSelectedItem(), // Tipo Enum
                    desc
            );
        } else {
            nuevaActividad = new ActividadPersonal(
                    nombre,
                    prioridad,
                    fecha,
                    0,      // Avance inicial
                    idNuevo,
                    tiempo,
                    fechaHoy,
                    etLugar.getText().toString(), // Lugar
                    null,   // TipoPersonal (Null si no se usa en este form)
                    desc
            );
        }

        // 3. Guardar en la lista global
        AppRepository.getInstance().agregarActividad(nuevaActividad);

        Toast.makeText(this, "Guardado Correctamente", Toast.LENGTH_SHORT).show();
        finish(); // Cierra la pantalla y vuelve a la lista
    }
}