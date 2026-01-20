package espol.poo.sistemabienestarestudiantil.ui.suenio;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.time.format.DateTimeFormatter; // Importante
import java.util.List;
import java.util.Locale;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.modelo.suenio.RegistrarHorasDeSuenio;

public class SuenioAdapter extends RecyclerView.Adapter<SuenioAdapter.ViewHolder> {

    private List<RegistrarHorasDeSuenio> lista;

    public SuenioAdapter(List<RegistrarHorasDeSuenio> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suenio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RegistrarHorasDeSuenio item = lista.get(position);

        // --- CAMBIO AQUÍ: Formatear la fecha para incluir el día ---
        // Ejemplo resultado: "Lun 18/01" o "Monday 2026-01-18" según prefieras.
        // Aquí usaremos formato: "Lun 18/Ene" para que se vea elegante.

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd/MM", new Locale("es", "ES"));
        String fechaFormateada = item.getFechaRegistro().format(formatter);

        // Poner la primera letra en mayúscula (ej: "lun" -> "Lun")
        fechaFormateada = fechaFormateada.substring(0, 1).toUpperCase() + fechaFormateada.substring(1);

        holder.tvFecha.setText(fechaFormateada);
        // -----------------------------------------------------------

        holder.tvHoras.setText(String.format(Locale.US, "%.1f h", item.getDuracionHoras()));

        // Barra de progreso
        holder.progressBar.setMax(100);
        int porcentaje = (int) ((item.getDuracionHoras() / 8.0) * 100);
        if (porcentaje > 100) porcentaje = 100;
        holder.progressBar.setProgress(porcentaje);

        // Colores
        if (item.getDuracionHoras() >= 7.5) {
            holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50"))); // Verde
        } else if (item.getDuracionHoras() >= 5.0) {
            holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FF9800"))); // Naranja
        } else {
            holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#F44336"))); // Rojo
        }
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvHoras;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvHoras = itemView.findViewById(R.id.tvHoras);
            progressBar = itemView.findViewById(R.id.progressBarSuenio);
        }
    }
}