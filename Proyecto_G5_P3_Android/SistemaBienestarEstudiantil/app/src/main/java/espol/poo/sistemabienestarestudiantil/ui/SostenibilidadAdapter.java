package espol.poo.sistemabienestarestudiantil.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import espol.poo.sistemabienestarestudiantil.R;
import espol.poo.sistemabienestarestudiantil.modelo.sostenibilidad.RegistroDiarioSostenible;

public class SostenibilidadAdapter extends RecyclerView.Adapter<SostenibilidadAdapter.ViewHolder> {

    private List<RegistroDiarioSostenible> lista;

    public SostenibilidadAdapter(List<RegistroDiarioSostenible> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historial_sostenible, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RegistroDiarioSostenible item = lista.get(position);

        // 1. Formatear Día de la semana (Ej: "Domingo")
        DateTimeFormatter fmtDia = DateTimeFormatter.ofPattern("EEEE", new Locale("es", "ES"));
        String dia = item.getFecha().format(fmtDia);
        // Poner primera letra mayúscula
        dia = dia.substring(0, 1).toUpperCase() + dia.substring(1);
        holder.tvDia.setText(dia);

        // 2. Formatear Fecha (Ej: "18/01/2026")
        DateTimeFormatter fmtFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        holder.tvFecha.setText(item.getFecha().format(fmtFecha));

        // 3. Cantidad de acciones
        int total = item.getAccionesSeleccionadasIds().size();
        holder.tvResumen.setText(total + " acciones");
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDia, tvFecha, tvResumen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDia = itemView.findViewById(R.id.tvDiaSemanaItem);
            tvFecha = itemView.findViewById(R.id.tvFechaItem);
            tvResumen = itemView.findViewById(R.id.tvResumenAcciones);
        }
    }
}