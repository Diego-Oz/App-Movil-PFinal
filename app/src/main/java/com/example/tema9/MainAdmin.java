package com.example.tema9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainAdmin extends AppCompatActivity {

    SQLiteDatabase db;
    RecyclerView lista;
    ArrayList<Alimentos> listaali;
    FloatingActionButton btng, btnce, btninfo;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        lista = (RecyclerView)findViewById(R.id.lista);
        btng = (FloatingActionButton)findViewById(R.id.agregar);
        btnce = (FloatingActionButton)findViewById(R.id.cerrarsesion);
        btninfo = (FloatingActionButton)findViewById(R.id.informacion);
        dialog = new Dialog(this);

        lista.setLayoutManager(new LinearLayoutManager(this));

        BDAlimentos bdAlimentos = new BDAlimentos(MainAdmin.this);

        listaali =new ArrayList<>();

        Adaptador adap = new Adaptador(bdAlimentos.mostrar());
        lista.setAdapter(adap);

        btng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(MainAdmin.this, CrearActivity.class);
                startActivity(sig);
            }
        });

        btnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacto();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.BaseDatos:

                BDatos con= new BDatos(MainAdmin.this);
                db = con.getWritableDatabase();
                if(db != null){
                    Toast.makeText(MainAdmin.this, "Base de Datos creada", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainAdmin.this, "Ha ocurrido un error", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.Nuevo:
                //Intent sig = new Intent(this, MainAdmin.class);
                //startActivity(sig);
                Toast.makeText(MainAdmin.this, "Se encuentra en la ventana de Administrador", Toast.LENGTH_LONG).show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void login(){
        dialog.setContentView(R.layout.cerrar_sesion);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView ivClose = dialog.findViewById(R.id.ivCloseC);
        Button cerrar = dialog.findViewById(R.id.btncerrar);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(MainAdmin.this, MainCarrito.class);
                startActivity(sig);
                finish();
            }
        });
        dialog.show();
    }

    private void contacto(){
        dialog.setContentView(R.layout.contacto);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}