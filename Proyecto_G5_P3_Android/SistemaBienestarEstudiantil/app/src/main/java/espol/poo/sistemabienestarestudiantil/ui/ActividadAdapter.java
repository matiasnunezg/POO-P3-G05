package espol.poo.sistemabienestarestudiantil.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.data.AppRepository;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.Actividad;
import espol.poo.sistemabienestarestudiantil.modelo.actividades.ActividadAcademica; // ¡IMPORTANTE PARA DETECTAR EL TIPO!

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.ActividadViewHolder> {

    private List<Actividad> listaActividades;
    private Context context;

    public ActividadAdapter(List<Actividad> lista, Context context) {
        this.listaActividades = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actividad, parent, false);
        return new ActividadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadViewHolder holder, int position) {
        Actividad actividad = listaActividades.get(position);

        // --- 1. ASIGNAR TEXTOS ---
        holder.txtId.setText("ID: " + actividad.getId());
        holder.txtNombre.setText("Nombre: " + actividad.getNombre());
        holder.txtFecha.setText("Vence: " + actividad.getFechaVencimiento());
        holder.txtPrioridad.setText("Prioridad: " + actividad.getPrioridad().toString());
        holder.txtAvance.setText("Avance: " + actividad.getAvance() + "%");

        // Mostrar el TIPO solo si existe el dato
        if (holder.txtTipo != null && actividad.getTipoActividad() != null) {
            holder.txtTipo.setText("Tipo: " + actividad.getTipoActividad());
        }

        // --- 2. LÓGICA VISUAL (OCULTAR/MOSTRAR BOTONES) ---
        // Aquí preguntamos: ¿Es Académica?
        if (actividad instanceof ActividadAcademica) {
            // SÍ ES ACADÉMICA: Mostramos los botones (VISIBLE)
            holder.btnPomodoro.setVisibility(View.VISIBLE);
            holder.btnDeepWork.setVisibility(View.VISIBLE);

            // Configuramos sus clics
            holder.btnPomodoro.setOnClickListener(v -> {
                Intent intent = new Intent(context, PomodoroActivity.class);
                intent.putExtra("ID_EXTRA", actividad.getId());
                intent.putExtra("nombre_tarea", actividad.getNombre());
                context.startActivity(intent);
            });

            holder.btnDeepWork.setOnClickListener(v -> {
                Intent intent = new Intent(context, DeepWorkActivity.class);
                intent.putExtra("ID_EXTRA", actividad.getId());
                intent.putExtra("nombre_tarea", actividad.getNombre());
                context.startActivity(intent);
            });

        } else {
            // NO ES ACADÉMICA (Es Personal): Los ocultamos (GONE)
            // 'GONE' hace que desaparezcan y no ocupen espacio en la pantalla
            holder.btnPomodoro.setVisibility(View.GONE);
            holder.btnDeepWork.setVisibility(View.GONE);
        }

        // --- 3. BOTONES COMUNES ---

        // Botón Detalles
        holder.btnDetalles.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleActividadActivity.class);
            intent.putExtra("ID_EXTRA", actividad.getId());
            intent.putExtra("NOMBRE_EXTRA", actividad.getNombre());
            intent.putExtra("FECHA_EXTRA", String.valueOf(actividad.getFechaVencimiento()));
            intent.putExtra("PRIORIDAD_EXTRA", actividad.getPrioridad().toString());
            intent.putExtra("AVANCE_EXTRA", actividad.getAvance());

            if (actividad.getTipoActividad() != null) {
                intent.putExtra("TIPO_EXTRA", actividad.getTipoActividad().toString());
            }
            context.startActivity(intent);
        });

        // Botón Avance
        holder.btnAvance.setOnClickListener(v -> {
            Intent intent = new Intent(context, RegistrarAvanceActivity.class);
            // Pasamos los datos para llenar los campos grises
            intent.putExtra("ID_EXTRA", actividad.getId());
            intent.putExtra("NOMBRE_EXTRA", actividad.getNombre());
            intent.putExtra("AVANCE_EXTRA", actividad.getAvance());

            context.startActivity(intent);
        });

        // Botón Eliminar (Con actualización visual inmediata)
        holder.btnEliminar.setOnClickListener(v -> {
            // 1. Crear el diálogo
            androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setMessage("¿Está seguro que desea eliminar la actividad " + actividad.getNombre() + "?")
                    .setPositiveButton("Sí", (d, which) -> {
                        // --- LÓGICA DE ELIMINACIÓN (Solo si dice SÍ) ---

                        // A. Borrar del Repositorio (Base de Datos)
                        AppRepository.getInstance().getListaActividades().remove(actividad);

                        // B. Borrar de la lista visual (RecyclerView)
                        int currentPos = holder.getAdapterPosition();
                        if (currentPos != RecyclerView.NO_POSITION) {
                            listaActividades.remove(currentPos);
                            notifyItemRemoved(currentPos);
                            notifyItemRangeChanged(currentPos, listaActividades.size());
                        }

                        Toast.makeText(context, "Actividad eliminada", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", (d, which) -> d.dismiss())
                    .create();

            // 2. Mostrar el diálogo
            dialog.show();

            // 3. CAMBIAR COLORES DE LOS BOTONES (Verde y Rojo)
            // Esto debe ir SIEMPRE después del dialog.show()
            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));

            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        });
    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }

    // --- CLASE VIEWHOLDER (Optimizada) ---
    public static class ActividadViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtNombre, txtFecha, txtPrioridad, txtAvance, txtTipo;
        Button btnDetalles, btnAvance, btnEliminar;
        Button btnPomodoro, btnDeepWork; // Agregados aquí para mejor rendimiento

        public ActividadViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId = itemView.findViewById(R.id.txtId);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtPrioridad = itemView.findViewById(R.id.txtPrioridad);
            txtAvance = itemView.findViewById(R.id.txtAvance);
            txtTipo = itemView.findViewById(R.id.txtTipo);

            btnDetalles = itemView.findViewById(R.id.btnDetalles);
            btnAvance = itemView.findViewById(R.id.btnAvance);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);

            // Enlazamos los botones de estudio una sola vez aquí
            btnPomodoro = itemView.findViewById(R.id.btnPomodoro);
            btnDeepWork = itemView.findViewById(R.id.btnDeepWork);
        }
    }
}