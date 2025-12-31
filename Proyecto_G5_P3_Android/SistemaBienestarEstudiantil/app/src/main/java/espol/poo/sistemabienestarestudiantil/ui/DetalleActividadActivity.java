package espol.poo.sistemabienestarestudiantil.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadPersonal;

public class DetalleActividadActivity extends AppCompatActivity {

    // Vistas Comunes
    private TextView txtTitulo, lblNombre, lblPrioridad, lblEstado, lblFecha, lblTiempo, lblAvance, lblDescripcion;

    // Contenedores (Layouts que vamos a ocultar/mostrar)
    private LinearLayout layoutInfoAcademica, layoutInfoPersonal;
    private CardView cardHistorial;

    // Vistas Específicas (hijas de los layouts anteriores)
    private TextView lblTipo, lblAsignatura, lblLugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_actividad);

        try {
            inicializarVistas();

            // 1. Obtener ID enviado desde la lista
            int idActividad = getIntent().getIntExtra("ID_EXTRA", -1);

            // 2. Buscar el objeto REAL en el repositorio
            Actividad actividad = AppRepository.getInstance().buscarActividadPorId(idActividad);

            // Validación de seguridad
            if (actividad == null) {
                Toast.makeText(this, "Error: Actividad no encontrada", Toast.LENGTH_SHORT).show();
                finish(); // Cerramos la pantalla si no existe
                return;
            }

            // 3. Mostrar los datos según el tipo
            mostrarDatos(actividad);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Ocurrió un error al cargar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Botón Volver
        findViewById(R.id.btnVolver).setOnClickListener(v -> finish());
    }

    private void inicializarVistas() {


        // Datos generales
        txtTitulo = findViewById(R.id.txtTituloDetalle);
        lblNombre = findViewById(R.id.lblNombre);
        lblPrioridad = findViewById(R.id.lblPrioridad);
        lblEstado = findViewById(R.id.lblEstado);
        lblFecha = findViewById(R.id.lblFecha);
        lblTiempo = findViewById(R.id.lblTiempo);
        lblAvance = findViewById(R.id.lblAvance);
        lblDescripcion = findViewById(R.id.lblDescripcion);

        // Contenedores
        layoutInfoAcademica = findViewById(R.id.layoutInfoAcademica);
        layoutInfoPersonal = findViewById(R.id.layoutInfoPersonal);
        cardHistorial = findViewById(R.id.cardHistorial);

        // Campos específicos
        lblTipo = findViewById(R.id.lblTipo);
        lblAsignatura = findViewById(R.id.lblAsignatura);
        lblLugar = findViewById(R.id.lblLugar);
    }



    private void mostrarDatos(Actividad a) {
        // --- 1. LLENAR DATOS COMUNES ---
        txtTitulo.setText("DETALLES (ID " + a.getId() + ")");
        lblNombre.setText("Nombre: " + a.getNombre());
        lblPrioridad.setText("Prioridad: " + a.getPrioridad());
        lblFecha.setText("Fecha Límite: " + a.getFechaVencimiento());
        lblTiempo.setText("Tiempo Estimado: " + a.getTiempoEstimado() + " min");
        lblAvance.setText("Avance Actual: " + a.getAvance() + "%");

        // Manejo seguro de la descripción (por si es null)
        if (a.getDescripcion() != null) {
            lblDescripcion.setText(a.getDescripcion());
        } else {
            lblDescripcion.setText("Sin descripción");
        }

        // Estado calculado
        String estado = (a.getAvance() >= 100) ? "Completada" : "En Curso";
        lblEstado.setText("Estado: " + estado);


        // --- 2. LÓGICA DE VISIBILIDAD SEGÚN TIPO ---

        if (a instanceof ActividadAcademica) {
            // === CASO ACADÉMICO ===
            ActividadAcademica acad = (ActividadAcademica) a;

            // Mostrar lo académico
            layoutInfoAcademica.setVisibility(View.VISIBLE);
            cardHistorial.setVisibility(View.VISIBLE); // Tabla de historial VISIBLE

            // Ocultar lo personal
            layoutInfoPersonal.setVisibility(View.GONE);

            // Llenar datos específicos (USANDO TUS MÉTODOS EXACTOS)
            lblTipo.setText("Tipo: " + acad.getActividadAcademica());
            lblAsignatura.setText("Asignatura: " + acad.getAsignatura());

        } else if (a instanceof ActividadPersonal) {
            // === CASO PERSONAL ===
            ActividadPersonal pers = (ActividadPersonal) a;

            // Mostrar lo personal
            layoutInfoPersonal.setVisibility(View.VISIBLE);

            // Ocultar lo académico y la tabla
            layoutInfoAcademica.setVisibility(View.GONE);
            cardHistorial.setVisibility(View.GONE); // Tabla de historial OCULTA

            // Llenar datos específicos (USANDO TUS MÉTODOS EXACTOS)
            lblLugar.setText("Lugar: " + pers.getLugar());
        }
    }
}