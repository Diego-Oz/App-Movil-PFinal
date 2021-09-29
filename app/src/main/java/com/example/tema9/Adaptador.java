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
import androidx.core.net.UriCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptador extends RecyclerView.Adapter<Adaptador.AlimentoViewHolder> {

    ArrayList<Alimentos> listaalimentos;

    public Adaptador(ArrayList<Alimentos> listaalimentos){
        this.listaalimentos = listaalimentos;
    }

    @NonNull
    @Override
    public AlimentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_contenido, null, false);
        return new AlimentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.AlimentoViewHolder holder, int position) {
        holder.tvnombre.setText(listaalimentos.get(position).getNombre());
        //holder.tvdes.setText(listaalimentos.get(position).getDescripcion());
        holder.tvprecio.setText(listaalimentos.get(position).getPrecio());
        holder.img.setImageURI(Uri.parse(listaalimentos.get(position).getImagen()));

    }

    @Override
    public int getItemCount() {
        return listaalimentos.size();
    }

    public class AlimentoViewHolder extends RecyclerView.ViewHolder {

        TextView tvnombre, tvdes, tvprecio;
        CircleImageView img;

        public AlimentoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvnombre = itemView.findViewById(R.id.listanombre);
            //tvdes = itemView.findViewById(R.id.listades);
            tvprecio = itemView.findViewById(R.id.listaprecio);
            img = itemView.findViewById(R.id.listaimagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Toast.makeText(context, "Producto: " + tvnombre.getText().toString(), Toast.LENGTH_LONG).show();
                    Intent in = new Intent(context, Modificar.class);
                    in.putExtra("ID", listaalimentos.get(getAdapterPosition()).getId());
                    context.startActivity(in);
                }
            });
        }
    }
}
