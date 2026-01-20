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

        // Inicialización de componentes
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
            // Validar campos vacíos
            if(etP1.getText().toString().isEmpty() || etP2.getText().toString().isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa los parciales", Toast.LENGTH_SHORT).show();
                return;
            }

            // Capturar datos como ENTEROS
            int p1 = Integer.parseInt(etP1.getText().toString());
            int p2 = Integer.parseInt(etP2.getText().toString());
            int prac = etPractico.getText().toString().isEmpty() ? 0 : Integer.parseInt(etPractico.getText().toString());
            int mej = etMejoramiento.getText().toString().isEmpty() ? 0 : Integer.parseInt(etMejoramiento.getText().toString());

            // Validar que no se pasen de 100
            if (p1 > 100 || p2 > 100 || prac > 100 || mej > 100) {
                Toast.makeText(this, "Las notas no pueden ser mayores a 100", Toast.LENGTH_SHORT).show();
                return;
            }

            int ponderacion = (rgPonderacion.getCheckedRadioButtonId() == R.id.rb70) ? 70 : 100;

            // Procesar con el Modelo
            CalculadoraPromedio calc = new CalculadoraPromedio(p1, p2, prac, mej, ponderacion);
            double resultado = calc.calcularNotaFinal();
            String mensaje = calc.determinarEstado(resultado);

            // Mostrar resultados en la vista
            layoutResultado.setVisibility(View.VISIBLE);
            tvNota.setText(String.format("%.2f", resultado)); // Dos decimales exactos
            tvMensaje.setText(mensaje);

            // Cambiar colores dinámicamente según la regla de 60.00
            if (resultado >= 60.0) {
                int verde = 0xFF2E7D32;
                tvNota.setTextColor(verde);
                tvMensaje.setTextColor(verde);
            } else {
                int rojo = 0xFFC62828;
                tvNota.setTextColor(rojo);
                tvMensaje.setTextColor(rojo);
            }

        } catch (NumberFormatException e) {
            // Error si el usuario pone decimales o letras
            Toast.makeText(this, "Error: Solo se permiten números enteros (0-100)", Toast.LENGTH_SHORT).show();
        }
    }
}