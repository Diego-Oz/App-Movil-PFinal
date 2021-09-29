package com.example.tema9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainCarrito extends AppCompatActivity {

    RecyclerView listap;
    ArrayList<Alimentos> listaalim;
    FloatingActionButton btnc, btnadm, btninfo, btns;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_carrito);

        listap = (RecyclerView)findViewById(R.id.principal);
        btnc = (FloatingActionButton)findViewById(R.id.carrito);
        btnadm = (FloatingActionButton)findViewById(R.id.admin);
        btninfo = (FloatingActionButton)findViewById(R.id.informacion);
        btns = (FloatingActionButton)findViewById(R.id.out);
        dialog = new Dialog(this);

        listap.setLayoutManager(new LinearLayoutManager(this));

        BDAlimentos bdAlimentos = new BDAlimentos(MainCarrito.this);

        listaalim =new ArrayList<>();

        AdaptadorPrincipal adap = new AdaptadorPrincipal(bdAlimentos.mostrar());
        listap.setAdapter(adap);

        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(MainCarrito.this, Total.class);
                startActivity(sig);
            }
        });

        btnadm.setOnClickListener(new View.OnClickListener() {
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

        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.BaseDatos:
                Toast.makeText(MainCarrito.this, "Necesita Iniciar Sesión", Toast.LENGTH_LONG).show();

            case R.id.Nuevo:
                hola();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void login(){
        dialog.setContentView(R.layout.login);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        EditText user = dialog.findViewById(R.id.eduser);
        EditText contra = dialog.findViewById(R.id.edcontra);
        Button ingresar = dialog.findViewById(R.id.btningresar);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String admin = user.getText().toString();
                String contrase = contra.getText().toString();

                if(admin.equals("Diego") && contrase.equals("1234")){
                    //Toast.makeText(MainCarrito.this, "Ingresando...", Toast.LENGTH_LONG).show();
                    //dialog.dismiss();
                    Intent sig = new Intent(MainCarrito.this, MainAdmin.class);
                    startActivity(sig);
                }else{
                    Toast.makeText(MainCarrito.this, "Usuario o Contraseña Incorrecto", Toast.LENGTH_LONG).show();
                    user.setText("");
                    contra.setText("");
                }
            }
        });
        dialog.show();
    }

    private void contacto(){
        dialog.setContentView(R.layout.contacto);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void hola(){
        dialog.setContentView(R.layout.hola);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button okh = dialog.findViewById(R.id.btnOkhappy);

        okh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}