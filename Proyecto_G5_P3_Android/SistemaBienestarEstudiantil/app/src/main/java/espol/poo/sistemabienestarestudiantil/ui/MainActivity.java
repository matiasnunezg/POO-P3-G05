package espol.poo.sistemabienestarestudiantil.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import espol.poo.sistemabienestarestudiantil.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnActividades).setOnClickListener(v ->
                startActivity(new Intent(this, ActividadesActivity.class)));

        findViewById(R.id.btnEnfoque).setOnClickListener(v ->
                startActivity(new Intent(this, EnfoqueActivity.class)));

        findViewById(R.id.btnHidratacion).setOnClickListener(v ->
                startActivity(new Intent(this, HidratacionActivity.class)));

        findViewById(R.id.btnSuenio).setOnClickListener(v ->
                startActivity(new Intent(this, SuenioActivity.class)));

        findViewById(R.id.btnSostenibilidad).setOnClickListener(v ->
                startActivity(new Intent(this, SostenibilidadActivity.class)));

        findViewById(R.id.btnJuego).setOnClickListener(v ->
                startActivity(new Intent(this, JuegoMemoriaActivity.class)));
    }
}
