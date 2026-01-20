package espol.poo.sistemabienestarestudiantil.ui.calculadora; // Ajusta a tu carpeta de UI

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.modelo.calculadora.CalculadoraPromedio;

public class CalculadoraActivity extends AppCompatActivity {

    private EditText etP1, etP2, etPractico, etMejoramiento;
    private TextView tvNota, tvMensaje;
    private RadioGroup rgPonderacion;
    private View layoutResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        // Enlazar componentes
        etP1 = findViewById(R.id.etP1);
        etP2 = findViewById(R.id.etP2);
        etPractico = findViewById(R.id.etPractico);
        etMejoramiento = findViewById(R.id.etMejoramiento);
        tvNota = findViewById(R.id.tvResultadoNota);
        tvMensaje = findViewById(R.id.tvResultadoMensaje);
        rgPonderacion = findViewById(R.id.rgPonderacion);
        layoutResultado = findViewById(R.id.layoutResultado);

        Button btnCalcular = findViewById(R.id.btnCalcular);
        Button btnVolver = findViewById(R.id.btnVolver);

        btnCalcular.setOnClickListener(v -> ejecutarCalculo());
        btnVolver.setOnClickListener(v -> finish());
    }

    private void ejecutarCalculo() {
        try {
            // Validar entradas
            if(etP1.getText().toString().isEmpty() || etP2.getText().toString().isEmpty()) {
                Toast.makeText(this, "Ingresa las notas de los parciales", Toast.LENGTH_SHORT).show();
                return;
            }

            double p1 = Double.parseDouble(etP1.getText().toString());
            double p2 = Double.parseDouble(etP2.getText().toString());
            double prac = etPractico.getText().toString().isEmpty() ? 0 : Double.parseDouble(etPractico.getText().toString());
            double mej = etMejoramiento.getText().toString().isEmpty() ? 0 : Double.parseDouble(etMejoramiento.getText().toString());

            int ponderacion = (rgPonderacion.getCheckedRadioButtonId() == R.id.rb70) ? 70 : 100;

            // Instanciar Modelo
            CalculadoraPromedio calc = new CalculadoraPromedio(p1, p2, prac, mej, ponderacion);
            double resultado = calc.calcularNotaFinal();
            String mensaje = calc.determinarEstado(resultado);

            // ACTUALIZAR VISTA
            layoutResultado.setVisibility(View.VISIBLE);
            tvNota.setText(String.format("%.2f", resultado));
            tvMensaje.setText(mensaje);

            // Lógica de colores (Aprobado >= 60.0)
            if (resultado >= 60.0) {
                int colorVerde = 0xFF2E7D32;
                tvNota.setTextColor(colorVerde);
                tvMensaje.setTextColor(colorVerde);
            } else {
                int colorRojo = 0xFFC62828;
                tvNota.setTextColor(colorRojo);
                tvMensaje.setTextColor(colorRojo);
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Datos no válidos", Toast.LENGTH_SHORT).show();
        }
    }
}