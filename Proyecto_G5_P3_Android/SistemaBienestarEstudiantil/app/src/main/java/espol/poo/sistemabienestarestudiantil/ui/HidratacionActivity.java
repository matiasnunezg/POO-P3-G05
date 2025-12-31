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

        // Vincular el botón verde de la interfaz principal
        android.widget.Button btnRegistrarToma = findViewById(R.id.btnRegistrarToma);
        tvTotalConsumido = findViewById(R.id.tvTotalConsumido);
        containerRegistros = findViewById(R.id.containerRegistros);

        btnRegistrarToma.setOnClickListener(v -> abrirDialogoRegistroToma());

        // --- BLOQUE FINAL: BOTÓN VOLVER AL MENÚ ---
        // Buscamos el botón naranja por el ID que definiste en el XML
        android.widget.Button btnVolverMenu = findViewById(R.id.btnVolverMenu);

        btnVolverMenu.setOnClickListener(v -> {
            // 'finish()' cierra la pantalla actual y te regresa
            // automáticamente a la 'MainActivity'
            finish();
        });
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
    private void abrirDialogoRegistroToma() {
        android.view.LayoutInflater inflater = getLayoutInflater();
        android.view.View dialogView = inflater.inflate(R.layout.dialog_registrar_toma, null);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(dialogView);
        android.app.AlertDialog dialog = builder.create();

        android.widget.EditText etCantidad = dialogView.findViewById(R.id.etCantidadToma);
        android.widget.EditText etHora = dialogView.findViewById(R.id.etHoraToma);
        android.widget.Button btnGuardar = dialogView.findViewById(R.id.btnGuardarToma);
        android.widget.Button btnCancelar = dialogView.findViewById(R.id.btnCancelarToma);

        btnGuardar.setOnClickListener(v -> {
            String cantStr = etCantidad.getText().toString();
            String horaStr = etHora.getText().toString();

            if (!cantStr.isEmpty() && !horaStr.isEmpty()) {
                double ml = Double.parseDouble(cantStr);

                // 1. Sumar al total y actualizar texto inferior
                totalConsumido += ml;
                tvTotalConsumido.setText((int)totalConsumido + " ml");

                // 2. Calcular porcentaje y actualizar círculo azul
                int porcentaje = (int) ((totalConsumido / metaActual) * 100);
                if (porcentaje > 100) porcentaje = 100;
                tvPorcentaje.setText(porcentaje + "%");
                progressCircular.setProgress(porcentaje);

                // 3. Crear la franja azul en la lista de registros
                agregarRegistroVisual(ml, horaStr);

                dialog.dismiss();
            } else {
                if(cantStr.isEmpty()) etCantidad.setError("Ingrese cantidad");
                if(horaStr.isEmpty()) etHora.setError("Ingrese hora");
            }
        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void agregarRegistroVisual(double ml, String hora) {
        // Eliminar el mensaje "No hay registros" si es el primero
        View sinReg = findViewById(R.id.tvSinRegistros);
        if (sinReg != null) containerRegistros.removeView(sinReg);

        // Crear la franja azul dinámicamente
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

        containerRegistros.addView(nuevoItem, 0); // Agrega al inicio para que el último se vea arriba
    }
}