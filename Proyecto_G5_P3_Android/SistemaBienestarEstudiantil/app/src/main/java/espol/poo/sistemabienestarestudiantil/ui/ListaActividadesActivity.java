package espol.poo.sistemabienestarestudiantil.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button; // Agregado para el botón

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadPersonal;

public class ListaActividadesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ActividadAdapter adapter;
    private List<Actividad> listaCompleta; // Referencia a la BD
    private List<Actividad> listaFiltrada; // Lista visual

    // 1. CAMBIO IMPORTANTE:
    // Declaramos los Spinners aquí arriba para usarlos en onResume
    private Spinner spinnerFiltro;
    private Spinner spinnerOrden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades);

        // Carga inicial de datos
        listaCompleta = AppRepository.getInstance(this).getListaActividades();
        listaFiltrada = new ArrayList<>(listaCompleta);

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerActividades);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ActividadAdapter(listaFiltrada, this);
        recyclerView.setAdapter(adapter);

        // Configurar Botón Agregar
        Button btnAgregar = findViewById(R.id.btnAgregarActividad);
        btnAgregar.setOnClickListener(v -> {
            // Abre el formulario (que ya unificamos en uno solo)
            Intent intent = new Intent(ListaActividadesActivity.this, FormularioActividadActivity.class);
            startActivity(intent);
        });

        // Configurar Filtros
        setupFiltrosYOrden();
    }

    private void setupFiltrosYOrden() {
        // Asignamos las vistas a las variables globales
        spinnerFiltro = findViewById(R.id.spinnerFiltro);
        spinnerOrden = findViewById(R.id.spinnerOrden);

        // --- Configurar opciones del Filtro ---
        String[] opcionesFiltro = {"Todos", "Académico", "Personal"};
        ArrayAdapter<String> adapterFiltro = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesFiltro);
        adapterFiltro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltro.setAdapter(adapterFiltro);

        // --- Configurar opciones del Orden ---
        String[] opcionesOrden = {"Sin orden", "Nombre (A-Z)", "Vencimiento (Desc)", "Avance"};
        ArrayAdapter<String> adapterOrden = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesOrden);
        adapterOrden.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrden.setAdapter(adapterOrden);

        // --- Listeners ---
        spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aplicarFiltros(position, spinnerOrden.getSelectedItemPosition());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerOrden.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aplicarFiltros(spinnerFiltro.getSelectedItemPosition(), position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // 2. CAMBIO IMPORTANTE: onResume RECARGADO
    @Override
    protected void onResume() {
        super.onResume();

        // A) Volvemos a traer la lista del Repositorio (por si agregaste algo nuevo)
        listaCompleta = AppRepository.getInstance(this).getListaActividades();

        // B) Volvemos a aplicar el filtro actual automáticamente
        if (spinnerFiltro != null && spinnerOrden != null) {
            aplicarFiltros(spinnerFiltro.getSelectedItemPosition(), spinnerOrden.getSelectedItemPosition());
        } else {
            // Si por alguna razón los spinners no están listos, refrescamos a lo bruto
            adapter.notifyDataSetChanged();
        }
    }

    private void aplicarFiltros(int indiceFiltro, int indiceOrden) {
        listaFiltrada.clear();

        // 1. FILTRADO
        if (indiceFiltro == 0) { // Todos
            listaFiltrada.addAll(listaCompleta);
        } else if (indiceFiltro == 1) { // Académico
            for (Actividad a : listaCompleta) {
                if (a instanceof ActividadAcademica) listaFiltrada.add(a);
            }
        } else if (indiceFiltro == 2) { // Personal
            for (Actividad a : listaCompleta) {
                if (a instanceof ActividadPersonal) listaFiltrada.add(a);
            }
        }

        // 2. ORDENAMIENTO
        switch (indiceOrden) {
            case 1: // Nombre (A-Z)
                Collections.sort(listaFiltrada, (a1, a2) -> a1.getNombre().compareToIgnoreCase(a2.getNombre()));
                break;
            case 2: // Vencimiento (Comparación de Strings de fecha puede fallar si no es YYYY-MM-DD, pero servirá por ahora)
                Collections.sort(listaFiltrada, (a1, a2) -> a2.getFechaVencimiento().compareTo(a1.getFechaVencimiento()));
                break;
            case 3: // Avance
                Collections.sort(listaFiltrada, Comparator.comparingInt(Actividad::getAvance));
                break;
        }

        // 3. ACTUALIZAR VISTA
        adapter.notifyDataSetChanged();
    }
}