package espol.poo.sistemabienestarestudiantil.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
        // Usa tu diseño 'item_suenio.xml'
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suenio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RegistrarHorasDeSuenio item = lista.get(position);

        // 1. Poner Fecha y Horas
        holder.tvFecha.setText(item.getFechaRegistro().toString());
        holder.tvHoras.setText(String.format(Locale.US, "%.1f h", item.getDuracionHoras()));

        // 2. Calcular Progreso (Meta: 8 horas = 100%)
        // Tu XML tiene max=120, lo ajustamos para que se vea bien visualmente
        holder.progressBar.setMax(100);
        int porcentaje = (int) ((item.getDuracionHoras() / 8.0) * 100);
        if (porcentaje > 100) porcentaje = 100;
        holder.progressBar.setProgress(porcentaje);

        // 3. Colores (Rúbrica visual)
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
            // CONECTAMOS CON TUS IDs DEL XML
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvHoras = itemView.findViewById(R.id.tvHoras);
            progressBar = itemView.findViewById(R.id.progressBarSuenio);
        }
    }
}