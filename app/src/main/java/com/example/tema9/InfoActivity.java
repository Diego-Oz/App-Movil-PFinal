package com.example.tema9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    ImageView infofoto;
    ImageButton menos, mas;
    TextView infocantidad, infonombre, infoprecio, infodes;
    Button agre;
    int cantidad;
    public Uri mCurrentCartUri;
    boolean hasAllRequiredValues = false;

    Alimentos ali;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        infofoto = (ImageView)findViewById(R.id.infofoto);
        menos = (ImageButton)findViewById(R.id.menos);
        mas = (ImageButton)findViewById(R.id.mas);
        infocantidad = (TextView)findViewById(R.id.cantidad);
        infonombre = (TextView)findViewById(R.id.infonombre);
        infoprecio = (TextView)findViewById(R.id.infoprecio);
        infodes = (TextView)findViewById(R.id.infodes);
        agre = (Button)findViewById(R.id.agrecarcar);

        agre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDAlimentosTotal dbali = new BDAlimentosTotal(InfoActivity.this);
                long id = dbali.insertar(infonombre.getText().toString(), infodes.getText().toString(), infoprecio.getText().toString());
                Toast.makeText(InfoActivity.this, "Agregado correctamente", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(InfoActivity.this, MainCarrito.class);
                startActivity(in);
            }
        });

        if(savedInstanceState == null){
            Bundle ext = getIntent().getExtras();

            if(ext == null){
                id = Integer.parseInt(null);
            }else {
                id = ext.getInt("ID");
            }
        }else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final BDAlimentos bdAlimentos = new BDAlimentos(InfoActivity.this);
        ali = bdAlimentos.modificar(id);

        if(ali != null){
            infonombre.setText(ali.getNombre());
            infodes.setText(ali.getDescripcion());
            infoprecio.setText(ali.getPrecio());
            infofoto.setImageURI(Uri.parse(ali.getImagen()));
        }

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int base = Integer.parseInt(ali.getPrecio());
                cantidad++;
                Cantidades();
                int p = base * cantidad;
                String pfinal = String.valueOf(p);
                infoprecio.setText(pfinal);
            }
        });

        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int base = Integer.parseInt(ali.getPrecio());
                if(cantidad == 0){
                    Toast.makeText(InfoActivity.this, "Invalido", Toast.LENGTH_SHORT).show();
                }else{
                    cantidad--;
                    Cantidades();
                    int p = base * cantidad;
                    String pfinal = String.valueOf(p);
                    infoprecio.setText(pfinal);
                }
            }
        });
    }


    private void Cantidades(){
        infocantidad.setText(String.valueOf(cantidad));
    }
}