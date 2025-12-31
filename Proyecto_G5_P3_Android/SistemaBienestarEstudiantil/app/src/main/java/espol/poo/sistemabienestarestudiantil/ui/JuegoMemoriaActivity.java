package espol.poo.sistemabienestarestudiantil.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import espol.poo.sistemabienestarestudiantil.R;
// Asegúrate de que este import coincida con donde guardaste la clase del modelo:
import espol.poo.sistemabienestarestudiantil.modelo.juegomemoria.JuegoMemoriaEco;

public class JuegoMemoriaActivity extends AppCompatActivity {

    // --- VISTA (Componentes UI) ---
    private Button[] botones = new Button[16]; // Array para manejar los 16 botones fácilmente
    private TextView tvIntentos, tvPares;
    private Button btnVolver;

    // --- MODELO (Lógica pura) ---
    private JuegoMemoriaEco juego;

    // --- VARIABLES DE CONTROL INTERNO ---
    private int primerIndice = -1; // Guarda el índice de la primera carta tocada (-1 = ninguna)
    private boolean bloqueado = false; // Evita clics locos mientras esperamos que se oculten cartas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_memoria);

        // 1. Inicializar el Modelo
        juego = new JuegoMemoriaEco();

        // 2. Conectar la Vista
        tvIntentos = findViewById(R.id.tvIntentos);
        tvPares = findViewById(R.id.tvPares);
        btnVolver = findViewById(R.id.btnVolver);

        // 3. Inicializar los 16 botones dinámicamente
        inicializarBotones();

        // 4. Configurar botón de salir
        btnVolver.setOnClickListener(v -> finish());
    }

    private void inicializarBotones() {
        for (int i = 0; i < 16; i++) {
            // Buscamos el ID del botón por nombre: "btn_card_0", "btn_card_1", etc.
            String buttonID = "btn_card_" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

            botones[i] = findViewById(resID);

            // Asignamos la posición final 'index' para usarla dentro del listener
            final int index = i;
            botones[i].setOnClickListener(v -> manejarClickCarta(index));
        }
    }

    private void manejarClickCarta(int indice) {
        // A) Validaciones de seguridad
        if (bloqueado) return; // Si estamos esperando (delay), no hacer nada
        if (juego.estaDescubierta(indice)) return; // Si ya está resuelta, ignorar
        if (indice == primerIndice) return; // Si tocó la misma carta dos veces, ignorar

        // B) Mostrar la carta seleccionada (Visual)
        mostrarCarta(indice);

        // C) Lógica de selección
        if (primerIndice == -1) {
            // Es la PRIMERA carta que voltea
            primerIndice = indice;
        } else {
            // Es la SEGUNDA carta. Comparamos.
            boolean sonIguales = juego.intentarPar(primerIndice, indice);
            actualizarMarcadores();

            if (sonIguales) {
                // ¡ÉXITO! Las cartas se quedan visibles.
                primerIndice = -1; // Reseteamos para el siguiente turno
                verificarFinDelJuego();
            } else {
                // FALLO. Esperamos 1 segundo y las ocultamos de nuevo.
                bloqueado = true; // Bloqueamos clics para que el usuario no toque nada más

                // Handler nos permite ejecutar código con retraso
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    ocultarCarta(primerIndice);
                    ocultarCarta(indice);
                    primerIndice = -1;
                    bloqueado = false; // Desbloqueamos
                }, 1000); // 1000 milisegundos = 1 segundo
            }
        }
    }

    // --- Métodos Auxiliares de Vista ---

    private void mostrarCarta(int i) {
        botones[i].setText(juego.getCarta(i)); // Pone el texto (Ej: "Árbol")
        botones[i].setBackgroundColor(0xFF81C784); // Cambia a verde más claro
    }

    private void ocultarCarta(int i) {
        botones[i].setText("?");
        botones[i].setBackgroundColor(0xFF2E7D32); // Vuelve al verde original
    }

    private void actualizarMarcadores() {
        tvIntentos.setText("Intentos: " + juego.getIntentos());
        tvPares.setText("Pares: " + juego.getPares() + " / 8");
    }

    private void verificarFinDelJuego() {
        if (juego.juegoTerminado()) {
            Toast.makeText(this, "¡Felicidades! Juego completado en " + juego.getIntentos() + " intentos.", Toast.LENGTH_LONG).show();
            // Aquí podrías agregar lógica extra, como guardar el puntaje.
        }
    }
}