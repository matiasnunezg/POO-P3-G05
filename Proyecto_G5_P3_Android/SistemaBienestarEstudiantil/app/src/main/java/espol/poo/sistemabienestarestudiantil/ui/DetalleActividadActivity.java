package espol.poo.sistemabienestarestudiantil.ui;

import android.content.Intent; // IMPORTANTE: Añadido para la navegación
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.List;
import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadPersonal;
import espol.poo.sistemabienestarestudiantil.modelo.enfoques.SesionEnfoque;

public class DetalleActividadActivity extends AppCompatActivity {

    private TextView txtTitulo, lblNombre, lblPrioridad, lblEstado, lblFecha, lblTiempo, lblAvance, lblDescripcion;
    private LinearLayout layoutInfoAcademica, layoutInfoPersonal, contenedorFilasHistorial;
    private CardView cardHistorial;
    private TextView lblTipo, lblAsignatura, lblLugar;
    private int idActividadActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_actividad);

        // 1. Vincular el botón
        View btnVolver = findViewById(R.id.btnVolver);

        // 2. Configurar el clic inmediatamente
        if (btnVolver != null) {
            btnVolver.setOnClickListener(v -> {
                finish(); // Cierra esta pantalla y regresa a la anterior
            });
        }

        // 3. Luego cargar lo demás
        try {
            inicializarVistas();
            cargarDatosActividad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Este método se ejecuta cuando regresas del Pomodoro o DeepWork
    @Override
    protected void onRestart() {
        super.onRestart();

        Actividad a = AppRepository.getInstance(this).buscarActividadPorId(idActividadActual);
        if (a != null) {
            mostrarDatos(a);
        }
    }

    private void cargarDatosActividad() {
        int idActividad = getIntent().getIntExtra("ID_EXTRA", -1);

        // --- AGREGA ESTA LÍNEA OBLIGATORIAMENTE ---
        this.idActividadActual = idActividad;
        // ------------------------------------------

        // Aquí también debes agregar el (this)
        Actividad actividad = AppRepository.getInstance(this).buscarActividadPorId(idActividad);

        if (actividad == null) {
            Toast.makeText(this, "Error: Actividad no encontrada", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mostrarDatos(actividad);
    }

    private void inicializarVistas() {
        txtTitulo = findViewById(R.id.txtTituloDetalle);
        lblNombre = findViewById(R.id.lblNombre);
        lblPrioridad = findViewById(R.id.lblPrioridad);
        lblEstado = findViewById(R.id.lblEstado);
        lblFecha = findViewById(R.id.lblFecha);
        lblTiempo = findViewById(R.id.lblTiempo);
        lblAvance = findViewById(R.id.lblAvance);
        lblDescripcion = findViewById(R.id.lblDescripcion);

        layoutInfoAcademica = findViewById(R.id.layoutInfoAcademica);
        layoutInfoPersonal = findViewById(R.id.layoutInfoPersonal);
        cardHistorial = findViewById(R.id.cardHistorial);
        contenedorFilasHistorial = findViewById(R.id.contenedorFilasHistorial);

        lblTipo = findViewById(R.id.lblTipo);
        lblAsignatura = findViewById(R.id.lblAsignatura);
        lblLugar = findViewById(R.id.lblLugar);
    }

    private void mostrarDatos(Actividad a) {
        txtTitulo.setText("DETALLES (ID " + a.getId() + ")");
        lblNombre.setText("Nombre: " + a.getNombre());
        lblPrioridad.setText("Prioridad: " + a.getPrioridad());
        lblFecha.setText("Fecha Límite: " + a.getFechaVencimiento());
        lblTiempo.setText("Tiempo Estimado: " + a.getTiempoEstimado() + " min");
        lblAvance.setText("Avance Actual: " + a.getAvance() + "%");
        lblDescripcion.setText(a.getDescripcion() != null ? a.getDescripcion() : "Sin descripción");

        String estado = (a.getAvance() >= 100) ? "Completada" : "En Curso";
        lblEstado.setText("Estado: " + estado);

        if (a instanceof ActividadAcademica) {
            ActividadAcademica acad = (ActividadAcademica) a;
            layoutInfoAcademica.setVisibility(View.VISIBLE);
            layoutInfoPersonal.setVisibility(View.GONE);
            lblTipo.setText("Tipo: " + acad.getActividadAcademica());
            lblAsignatura.setText("Asignatura: " + acad.getAsignatura());

            // --- LISTENERS PARA LAS TÉCNICAS (POMODORO / DEEP WORK) ---
            // Asegúrate de que estos IDs existan en tu XML de Detalles
            View btnPomo = findViewById(R.id.btnPomodoro);
            if (btnPomo != null) {
                btnPomo.setOnClickListener(v -> {
                    Intent intent = new Intent(this, PomodoroActivity.class);
                    intent.putExtra("ID_EXTRA", a.getId());
                    intent.putExtra("nombre_tarea", a.getNombre());
                    startActivity(intent);
                });
            }

            View btnDeep = findViewById(R.id.btnDeepWork);
            if (btnDeep != null) {
                btnDeep.setOnClickListener(v -> {
                    Intent intent = new Intent(this, DeepWorkActivity.class);
                    intent.putExtra("ID_EXTRA", a.getId());
                    intent.putExtra("nombre_tarea", a.getNombre());
                    startActivity(intent);
                });
            }

            // --- HISTORIAL DINÁMICO ---
            List<SesionEnfoque> sesiones = acad.getHistorialSesiones();
            if (sesiones != null && !sesiones.isEmpty()) {
                cardHistorial.setVisibility(View.VISIBLE);
                contenedorFilasHistorial.removeAllViews();

                for (SesionEnfoque s : sesiones) {
                    View fila = getLayoutInflater().inflate(R.layout.item_historial_fila, null);
                    ((TextView) fila.findViewById(R.id.txtFechaRow)).setText(s.getFecha());
                    ((TextView) fila.findViewById(R.id.txtTecnicaRow)).setText(s.getTecnica());
                    ((TextView) fila.findViewById(R.id.txtMinutosRow)).setText(s.getDuracionFormateada());
                    contenedorFilasHistorial.addView(fila);
                }
            } else {
                cardHistorial.setVisibility(View.GONE);
            }
        } else if (a instanceof ActividadPersonal) {
            ActividadPersonal pers = (ActividadPersonal) a;
            layoutInfoPersonal.setVisibility(View.VISIBLE);
            layoutInfoAcademica.setVisibility(View.GONE);
            cardHistorial.setVisibility(View.GONE);
            lblLugar.setText("Lugar: " + pers.getLugar());
        }
    }
}