package com.example.tema9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private static int Splash = 5000;
    Animation topa, bottoma;
    ImageView logo, d1, d2;
    TextView nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topa = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottoma = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo = (ImageView)findViewById(R.id.logo);
        nom = (TextView)findViewById(R.id.GoCake);
        d1 = (ImageView)findViewById(R.id.dona1);
        d2 = (ImageView)findViewById(R.id.dona2);

        d1.setAnimation(topa);
        logo.setAnimation(topa);
        nom.setAnimation(bottoma);
        d2.setAnimation(bottoma);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent siguiente = new Intent(MainActivity.this, MainCarrito.class);
                startActivity(siguiente);
                finish();
            }
        }, Splash);
    }
}