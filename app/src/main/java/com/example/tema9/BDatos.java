package com.example.tema9;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDatos extends SQLiteOpenHelper {

    private static final int DVersion = 1;
    private static String BaseDatos = "final.db";

    public BDatos(@Nullable Context context) {
        super(context, BaseDatos, null, DVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(Utilidades.crear);
       db.execSQL(Utilidades.creart);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+Utilidades.Tabla);
        db.execSQL("Drop table if exists "+Utilidades.Tablatotal);
        onCreate(db);
    }
}