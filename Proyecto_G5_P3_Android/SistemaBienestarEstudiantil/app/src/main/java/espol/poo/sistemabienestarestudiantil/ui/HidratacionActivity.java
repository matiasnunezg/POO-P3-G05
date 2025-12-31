package espol.poo.sistemabienestarestudiantil.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.hidrataciones.RegistroHidratacion;

public class HidratacionActivity extends AppCompatActivity {
    private TextView tvFechaActual;
    private LinearLayout layoutSelectorFecha;
    private TextView tvMetaValor;
    private android.widget.ProgressBar progressCircular;
    private TextView tvPorcentaje;
    private double metaActual = 2500.0;
    private TextView tvTotalConsumido;
    private LinearLayout containerRegistros;
    private double totalConsumido = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidratacion);

        // 1. Vincular vistas
        tvFechaActual = findViewById(R.id.tvFechaActual);
        layoutSelectorFecha = findViewById(R.id.layoutSelectorFecha);
        tvMetaValor = findViewById(R.id.tvMetaValor);
        tvPorcentaje = findViewById(R.id.tvPorcentaje);
        progressCircular = findViewById(R.id.progressCircular);
        tvTotalConsumido = findViewById(R.id.tvTotalConsumido);
        containerRegistros = findViewById(R.id.containerRegistros);

        // 2. Mostrar fecha por defecto
        mostrarFechaActual();

        // 3. Listeners de botones principales
        layoutSelectorFecha.setOnClickListener(v -> abrirCalendario());

        findViewById(R.id.btnEstablecerMeta).setOnClickListener(v -> abrirDialogoActualizarMeta());

        findViewById(R.id.btnRegistrarToma).setOnClickListener(v -> abrirDialogoRegistroToma());

        findViewById(R.id.btnVolverMenu).setOnClickListener(v -> finish());

        // 4. CARGAR DATOS DEL REPOSITORIO (Autoguardado al entrar)
        cargarDatosDesdeRepositorio();
    }

    private void cargarDatosDesdeRepositorio() {
        // Cargar Meta
        metaActual = AppRepository.getInstance().getMetaDiaria();
        tvMetaValor.setText((int)metaActual + " ml");

        // Cargar Consumo Total
        totalConsumido = AppRepository.getInstance().getTotalConsumido();
        tvTotalConsumido.setText((int)totalConsumido + " ml");

        // Actualizar UI del círculo
        int porcentaje = (int) ((totalConsumido / metaActual) * 100);
        if (porcentaje > 100) porcentaje = 100;
        tvPorcentaje.setText(porcentaje + "%");
        progressCircular.setProgress(porcentaje);

        // Cargar la lista visual
        for (RegistroHidratacion reg : AppRepository.getInstance().getListaHidratacion()) {
            // Se usa reg.getHora() que ahora manejaremos como String para evitar errores de API
            agregarRegistroVisual(reg.getCantidadMl(), reg.getHora().toString());
        }
    }

    private void mostrarFechaActual() {
        Calendar calendar = Calendar.getInstance();
        String fecha = String.format("%02d/%02d/%d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));
        tvFechaActual.setText(fecha);
    }

    private void abrirCalendario() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            tvFechaActual.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void abrirDialogoActualizarMeta() {
        android.view.View dialogView = getLayoutInflater().inflate(R.layout.dialog_actualizar_meta, null);
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this).setView(dialogView).create();

        TextView tvMetaActualDialog = dialogView.findViewById(R.id.tvMetaActualDialog);
        android.widget.EditText etNuevaMeta = dialogView.findViewById(R.id.etNuevaMeta);

        tvMetaActualDialog.setText((int)metaActual + " ml");

        dialogView.findViewById(R.id.btnGuardarMeta).setOnClickListener(v -> {
            String valor = etNuevaMeta.getText().toString();
            if (!valor.isEmpty()) {
                metaActual = Double.parseDouble(valor);
                tvMetaValor.setText((int)metaActual + " ml");

                // Guardar meta en el repositorio
                AppRepository.getInstance().setMetaDiaria(metaActual);

                // Actualizar círculo con la nueva meta
                int porcentaje = (int) ((totalConsumido / metaActual) * 100);
                tvPorcentaje.setText((porcentaje > 100 ? 100 : porcentaje) + "%");
                progressCircular.setProgress(porcentaje > 100 ? 100 : porcentaje);

                dialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.btnCancelarMeta).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void abrirDialogoRegistroToma() {
        android.view.View dialogView = getLayoutInflater().inflate(R.layout.dialog_registrar_toma, null);
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this).setView(dialogView).create();

        android.widget.EditText etCantidad = dialogView.findViewById(R.id.etCantidadToma);
        android.widget.EditText etHora = dialogView.findViewById(R.id.etHoraToma);

        dialogView.findViewById(R.id.btnGuardarToma).setOnClickListener(v -> {
            String cantStr = etCantidad.getText().toString();
            String horaStr = etHora.getText().toString();

            // ... dentro de btnGuardar.setOnClickListener ...
            if (!cantStr.isEmpty() && !horaStr.isEmpty()) {
                double ml = Double.parseDouble(cantStr);

                // 1. Actualizar la interfaz (Círculo, textos, etc.)
                totalConsumido += ml;
                tvTotalConsumido.setText((int)totalConsumido + " ml");
                int porcentaje = (int) ((totalConsumido / metaActual) * 100);
                tvPorcentaje.setText((porcentaje > 100 ? 100 : porcentaje) + "%");
                progressCircular.setProgress(porcentaje > 100 ? 100 : porcentaje);
                agregarRegistroVisual(ml, horaStr);

                // 2. GUARDAR DATOS REALES (Sin nulos para evitar que la app se cierre)
                String fechaActual = tvFechaActual.getText().toString(); // Tomamos la fecha de la pantalla

                // Creamos el objeto con los Strings (ml, fecha, hora escrita por usuario)
                RegistroHidratacion nuevoReg = new RegistroHidratacion(ml, fechaActual, horaStr);

                // Guardamos en el AppRepository
                AppRepository.getInstance().agregarRegistroHidratacion(nuevoReg);

                dialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.btnCancelarToma).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void agregarRegistroVisual(double ml, String hora) {
        View sinReg = findViewById(R.id.tvSinRegistros);
        if (sinReg != null) containerRegistros.removeView(sinReg);

        TextView nuevoItem = new TextView(this);
        nuevoItem.setText((int)ml + " ml - " + hora);
        nuevoItem.setBackgroundColor(android.graphics.Color.parseColor("#E3F2FD"));
        nuevoItem.setPadding(30, 30, 30, 30);
        nuevoItem.setTextColor(android.graphics.Color.BLACK);
        nuevoItem.setTextSize(16f);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 15);
        nuevoItem.setLayoutParams(params);

        containerRegistros.addView(nuevoItem, 0);
    }
}