package com.example.tema9;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorTotal extends RecyclerView.Adapter<AdaptadorTotal.AlimentoViewHolder> {

    ArrayList<AlimentosTotal> listaalimentos;

    public AdaptadorTotal(ArrayList<AlimentosTotal> listaalimentos){
        this.listaalimentos = listaalimentos;
    }

    @NonNull
    @Override
    public AlimentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_total, null, false);
        return new AlimentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorTotal.AlimentoViewHolder holder, int position) {
        holder.tvnombre.setText(listaalimentos.get(position).getNombret());
        holder.tvdes.setText(listaalimentos.get(position).getDescripciont());
        holder.tvprecio.setText(listaalimentos.get(position).getPreciot());

    }

    @Override
    public int getItemCount() {
        return listaalimentos.size();
    }

    public class AlimentoViewHolder extends RecyclerView.ViewHolder {

        TextView tvnombre, tvdes, tvprecio;

        public AlimentoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvnombre = itemView.findViewById(R.id.totalnombre);
            tvdes = itemView.findViewById(R.id.totaldes);
            tvprecio = itemView.findViewById(R.id.totalprecio);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    //Toast.makeText(context, "Producto: " + tvnombre.getText().toString(), Toast.LENGTH_LONG).show();
                    Intent in = new Intent(context, Total.class);
                    in.putExtra("ID", listaalimentos.get(getAdapterPosition()).getId());
                    context.startActivity(in);
                }
            });*/
        }
    }
}
