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

public class AdaptadorPrincipal extends RecyclerView.Adapter<AdaptadorPrincipal.AlimentoViewHolder> {

    ArrayList<Alimentos> listaalimentos;

    public AdaptadorPrincipal(ArrayList<Alimentos> listaalimentos){
        this.listaalimentos = listaalimentos;
    }

    @NonNull
    @Override
    public AlimentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_principal, null, false);
        return new AlimentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPrincipal.AlimentoViewHolder holder, int position) {
        holder.tvnom.setText(listaalimentos.get(position).getNombre());
        holder.tvdesc.setText(listaalimentos.get(position).getDescripcion());
        holder.tvpre.setText(listaalimentos.get(position).getPrecio());
        holder.imagen.setImageURI(Uri.parse(listaalimentos.get(position).getImagen()));

    }

    @Override
    public int getItemCount() {
        return listaalimentos.size();
    }

    public class AlimentoViewHolder extends RecyclerView.ViewHolder {

        TextView tvnom, tvdesc, tvpre;
        ImageView imagen;

        public AlimentoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvnom = itemView.findViewById(R.id.prinnombre);
            tvdesc = itemView.findViewById(R.id.prindes);
            tvpre = itemView.findViewById(R.id.prinprecio);
            imagen = itemView.findViewById(R.id.prinfoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    //Toast.makeText(context, "Producto: " + tvnom.getText().toString(), Toast.LENGTH_LONG).show();
                    Intent in = new Intent(context, InfoActivity.class);
                    in.putExtra("ID", listaalimentos.get(getAdapterPosition()).getId());
                    context.startActivity(in);
                }
            });
        }
    }
}
