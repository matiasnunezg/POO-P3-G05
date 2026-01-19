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

        // 2. LOGICA DE FECHA: Cargar del repo o mostrar la de hoy
        String fechaGuardada = AppRepository.getInstance(this).getFechaSeleccionadaRepo();
        if (fechaGuardada != null && !fechaGuardada.isEmpty()) {
            tvFechaActual.setText(fechaGuardada);
        } else {
            mostrarFechaActual();
        }

        // 3. Listeners de botones principales
        layoutSelectorFecha.setOnClickListener(v -> abrirCalendario());

        findViewById(R.id.btnEstablecerMeta).setOnClickListener(v -> abrirDialogoActualizarMeta());

        findViewById(R.id.btnRegistrarToma).setOnClickListener(v -> abrirDialogoRegistroToma());

        findViewById(R.id.btnVolverMenu).setOnClickListener(v -> finish());

        // 4. CARGAR DATOS DEL REPOSITORIO (Autoguardado al entrar)
        cargarDatosDesdeRepositorio();
    }

    private void cargarDatosDesdeRepositorio() {
        // 1. OBTENER LA FECHA VISIBLE PRIMERO
        // (La necesitamos para pedir la meta específica de este día al Repositorio)
        String fechaVisible = tvFechaActual.getText().toString();

        // 2. CARGAR META ESPECÍFICA DE LA FECHA
        // Le pasamos la fecha al repositorio. Si no existe meta para ese día, devolverá 2500.
        metaActual = AppRepository.getInstance(this).getMetaDiaria(fechaVisible);
        tvMetaValor.setText((int)metaActual + " ml");

        // 3. Limpiar el contenedor visual antes de cargar nuevos datos
        containerRegistros.removeAllViews();
        totalConsumido = 0.0; // Reiniciamos para calcular solo lo del día seleccionado

        // 4. Filtrar y cargar solo los registros que coincidan con la fecha seleccionada
        for (RegistroHidratacion reg : AppRepository.getInstance(this).getListaHidratacion()) {
            // Comparamos String con equals()
            if (reg.getFecha().equals(fechaVisible)) {
                totalConsumido += reg.getCantidadMl();
                agregarRegistroVisual(reg.getCantidadMl(), reg.getHora());
            }
        }

        // 5. Actualizar UI de progreso y totales del día
        tvTotalConsumido.setText((int)totalConsumido + " ml");

        int porcentaje = (int) ((totalConsumido / metaActual) * 100);
        if (porcentaje > 100) porcentaje = 100;

        tvPorcentaje.setText(porcentaje + "%");
        progressCircular.setProgress(porcentaje);

        // Si no hay registros, poner texto de aviso
        if (containerRegistros.getChildCount() == 0) {
            TextView tvSin = new TextView(this);
            tvSin.setId(R.id.tvSinRegistros);
            tvSin.setText("No hay registros para este día");
            tvSin.setGravity(android.view.Gravity.CENTER);
            containerRegistros.addView(tvSin);
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
            String fechaSeleccionada = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);
            tvFechaActual.setText(fechaSeleccionada);

            AppRepository.getInstance(this).setFechaSeleccionadaRepo(fechaSeleccionada);

            // ACTUALIZACIÓN CLAVE: Recargar los datos (y la meta) al cambiar la fecha
            cargarDatosDesdeRepositorio();

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

                // --- CAMBIO IMPORTANTE: GUARDAR META ASOCIADA A LA FECHA ---
                String fechaActual = tvFechaActual.getText().toString();

                // Llamamos al nuevo método del repositorio que acepta (double, String)
                AppRepository.getInstance(this).setMetaDiaria(metaActual, fechaActual);

                // Recalcular porcentajes visuales inmediatamente
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
            String cantStr = etCantidad.getText().toString().trim();
            String horaStr = etHora.getText().toString().trim();
            // Regex simple para validar formato de hora
            String regexHora = "^(1[0-2]|0?[1-9]):[0-5][0-9]\\s?(AM|PM|am|pm)$";

            if (!cantStr.isEmpty() && !horaStr.isEmpty()) {
                if (!horaStr.matches(regexHora)) {
                    etHora.setError("Formato inválido. Use ej: 10:30 AM o 3:05 PM");
                    return;
                }

                try {
                    double ml = Double.parseDouble(cantStr);

                    totalConsumido += ml;
                    tvTotalConsumido.setText((int)totalConsumido + " ml");

                    int porcentaje = (int) ((totalConsumido / metaActual) * 100);
                    int progresoFinal = (porcentaje > 100) ? 100 : porcentaje;

                    tvPorcentaje.setText(progresoFinal + "%");
                    progressCircular.setProgress(progresoFinal);

                    agregarRegistroVisual(ml, horaStr);

                    String fechaActual = tvFechaActual.getText().toString();
                    RegistroHidratacion nuevoReg = new RegistroHidratacion(ml, fechaActual, horaStr);
                    AppRepository.getInstance(this).agregarRegistroHidratacion(nuevoReg);

                    dialog.dismiss();

                } catch (NumberFormatException e) {
                    etCantidad.setError("Ingrese un número válido");
                }
            } else {
                if(cantStr.isEmpty()) etCantidad.setError("Falta la cantidad");
                if(horaStr.isEmpty()) etHora.setError("Falta la hora");
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