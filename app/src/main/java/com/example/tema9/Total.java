package com.example.tema9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class Total extends AppCompatActivity {

    RecyclerView listtotal;
    Button limpiarlistado, comprar;
    ArrayList<AlimentosTotal> listaali;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        listtotal = (RecyclerView)findViewById(R.id.listtotal);
        limpiarlistado = (Button)findViewById(R.id.limpiarlistado);
        comprar = (Button)findViewById(R.id.comprar);
        dialog = new Dialog(this);

        listtotal.setLayoutManager(new LinearLayoutManager(this));

        BDAlimentosTotal bdAlimentos = new BDAlimentosTotal(Total.this);

        listaali =new ArrayList<>();

        AdaptadorTotal adap = new AdaptadorTotal(bdAlimentos.mostrar());
        listtotal.setAdapter(adap);

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComprarDialog();

            }
        });

        limpiarlistado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelarDialog();
            }
        });

    }

    private void ComprarDialog(){
        dialog.setContentView(R.layout.comprado);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        Button ok = dialog.findViewById(R.id.btnOk);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDAlimentosTotal bdAlimentos = new BDAlimentosTotal(Total.this);
                bdAlimentos.eliminar();
                dialog.dismiss();
                sinnada();
            }
        });
        dialog.show();
    }

    private void CancelarDialog(){
        dialog.setContentView(R.layout.cancelando);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView ivClosec = dialog.findViewById(R.id.ivCloseC);
        Button okc = dialog.findViewById(R.id.btnOkC);

        ivClosec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        okc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDAlimentosTotal bdAlimentos = new BDAlimentosTotal(Total.this);
                bdAlimentos.eliminar();
                dialog.dismiss();
                sinnada();
            }
        });
        dialog.show();
    }

    private void sinnada(){
        Intent intent = new Intent(this, MainCarrito.class);
        startActivity(intent);
    }
}