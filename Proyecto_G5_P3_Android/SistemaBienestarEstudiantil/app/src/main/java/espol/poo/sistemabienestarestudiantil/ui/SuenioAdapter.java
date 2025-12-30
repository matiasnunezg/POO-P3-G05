//package espol.poo.sistemabienestarestudiantil.ui;
//
//import android.content.res.ColorStateList;
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.Locale;
//
//import espol.poo.sistemabienestarestudiantil.databinding.ItemSuenioBinding;
//import espol.poo.sistemabienestarestudiantil.modelo.suenio.RegistrarHorasDeSuenio;
//
//public class SuenioAdapter extends RecyclerView.Adapter<SuenioAdapter.ViewHolder> {
//
//    private List<RegistrarHorasDeSuenio> lista;
//
//    public SuenioAdapter(List<RegistrarHorasDeSuenio> lista) {
//        this.lista = lista;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ItemSuenioBinding binding = ItemSuenioBinding.inflate(
//                LayoutInflater.from(parent.getContext()), parent, false);
//        return new ViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        RegistrarHorasDeSuenio item = lista.get(position);
//
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEE d/MM", new Locale("es", "ES"));
//        String fechaStr = item.getFechaRegistro().format(fmt);
//        // Capitalizar primera letra
//        fechaStr = fechaStr.substring(0, 1).toUpperCase() + fechaStr.substring(1);
//
//        holder.binding.tvFecha.setText(fechaStr);
//        holder.binding.tvHoras.setText(String.format(Locale.US, "%.1f h", item.getDuracionHoras()));
//
//        int progreso = (int) (item.getDuracionHoras() * 10);
//        holder.binding.progressBarSuenio.setProgress(progreso);
//
//        // Colores según desempeño
//        int color;
//        if (item.getDuracionHoras() >= 8.0) color = Color.parseColor("#4CAF50"); // Verde
//        else if (item.getDuracionHoras() >= 6.0) color = Color.parseColor("#2196F3"); // Azul
//        else color = Color.parseColor("#F44336"); // Rojo
//
//        holder.binding.progressBarSuenio.setProgressTintList(ColorStateList.valueOf(color));
//    }
//
//    @Override
//    public int getItemCount() { return lista.size(); }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        ItemSuenioBinding binding;
//        public ViewHolder(ItemSuenioBinding b) {
//            super(b.getRoot());
//            binding = b;
//        }
//    }
//}
