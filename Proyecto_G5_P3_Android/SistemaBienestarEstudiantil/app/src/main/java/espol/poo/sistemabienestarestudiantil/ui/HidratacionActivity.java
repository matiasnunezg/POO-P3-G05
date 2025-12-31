package espol.poo.sistemabienestarestudiantil.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import espol.poo.sistemabienestarestudiantil.R;

public class HidratacionActivity extends AppCompatActivity {
    private TextView tvFechaActual;
    private LinearLayout layoutSelectorFecha;
    private TextView tvMetaValor;
    private android.widget.ProgressBar progressCircular;
    private TextView tvPorcentaje;
    private double metaActual = 2500.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidratacion);

        // 1. Vincular vistas
        tvFechaActual = findViewById(R.id.tvFechaActual);
        layoutSelectorFecha = findViewById(R.id.layoutSelectorFecha);

        // 2. Colocar fecha actual por defecto
        mostrarFechaActual();

// 3. Configurar clic para abrir calendario
        layoutSelectorFecha.setOnClickListener(v -> abrirCalendario());

        // 4. Configurar Bloque Meta Diaria
        tvMetaValor = findViewById(R.id.tvMetaValor);
        tvPorcentaje = findViewById(R.id.tvPorcentaje);
        progressCircular = findViewById(R.id.progressCircular);
        android.widget.Button btnEstablecerMeta = findViewById(R.id.btnEstablecerMeta);

        btnEstablecerMeta.setOnClickListener(v -> abrirDialogoActualizarMeta());
    }

    private void mostrarFechaActual() {
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH) + 1; // OJO: enero = 0
        int anio = calendar.get(Calendar.YEAR);

        String fecha = String.format("%02d/%02d/%d", dia, mes, anio);
        tvFechaActual.setText(fecha);
    }

    private void abrirCalendario() {
        Calendar calendar = Calendar.getInstance();

        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int anio = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String fechaSeleccionada = String.format(
                            "%02d/%02d/%d",
                            dayOfMonth,
                            month + 1,
                            year
                    );
                    tvFechaActual.setText(fechaSeleccionada);
                },
                anio,
                mes,
                dia
        );

        datePickerDialog.show();
    }

    private void abrirDialogoActualizarMeta() {
        android.view.LayoutInflater inflater = getLayoutInflater();
        android.view.View dialogView = inflater.inflate(R.layout.dialog_actualizar_meta, null);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(dialogView);
        android.app.AlertDialog dialog = builder.create();

        TextView tvMetaActualDialog = dialogView.findViewById(R.id.tvMetaActualDialog);
        android.widget.EditText etNuevaMeta = dialogView.findViewById(R.id.etNuevaMeta);
        android.widget.Button btnGuardar = dialogView.findViewById(R.id.btnGuardarMeta);
        android.widget.Button btnCancelar = dialogView.findViewById(R.id.btnCancelarMeta);

        tvMetaActualDialog.setText((int)metaActual + " ml");

        btnGuardar.setOnClickListener(v -> {
            String valor = etNuevaMeta.getText().toString();
            if (!valor.isEmpty()) {
                metaActual = Double.parseDouble(valor);
                tvMetaValor.setText((int)metaActual + " ml");

                // Esto actualiza el círculo y el % a 0 cada vez que cambias la meta
                tvPorcentaje.setText("0%");
                progressCircular.setProgress(0);

                dialog.dismiss();
            } else {
                etNuevaMeta.setError("Ingrese un valor");
            }
        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}