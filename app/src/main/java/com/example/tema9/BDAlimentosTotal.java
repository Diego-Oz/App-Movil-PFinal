package com.example.tema9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BDAlimentosTotal extends BDatos{

    Context context;
    /*public static final int ORDER = 100;

    public static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(Utilidades.CONTENT_AUTHORITY, Utilidades.Tabla, ORDER);
    }*/

    public BDAlimentosTotal(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertar(String nombret, String descripciont, String preciot){
        long id = 0;

        try {
            BDatos bDatos = new BDatos(context);
            SQLiteDatabase db = bDatos.getWritableDatabase();

            ContentValues val = new ContentValues();
            val.put("nombret", nombret);
            val.put("descripciont", descripciont);
            val.put("preciot", preciot);

            id = db.insert(Utilidades.Tablatotal, null, val);
        }
        catch (Exception exception){
            exception.toString();
        }

        return id;
    }

    /*public boolean editar(int id, String nombre, String descripcion, String precio, String imagen){

        boolean ed = false;

        BDatos bDatos = new BDatos(context);
        SQLiteDatabase db = bDatos.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + Utilidades.Tabla + " SET nombre = '" + nombre + "', descripcion = '" + descripcion + "', precio = '" + precio + "', imagen = '" + imagen + "' WHERE id='" + id +"' ");
            ed = true;
        }
        catch (Exception exception){
            exception.toString();
            ed = false;
        } finally {
            db.close();
        }

        return ed;
    }*/

    public ArrayList<AlimentosTotal> mostrar(){
        BDatos bDatos = new BDatos(context);
        SQLiteDatabase db = bDatos.getWritableDatabase();

        ArrayList<AlimentosTotal> lista = new ArrayList<>();
        AlimentosTotal ali = null;
        Cursor cursor = null;

        cursor = db.rawQuery("Select * from "+ Utilidades.Tablatotal, null);

        if(cursor.moveToFirst()){
            do {
                ali = new AlimentosTotal();
                ali.setId(cursor.getInt(0));
                ali.setNombret(cursor.getString(1));
                ali.setDescripciont(cursor.getString(2));
                ali.setPreciot(cursor.getString(3));

                lista.add(ali);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    public AlimentosTotal modificar(int id){
        BDatos bDatos = new BDatos(context);
        SQLiteDatabase db = bDatos.getWritableDatabase();

        AlimentosTotal ali = null;
        Cursor cursor = null;

        cursor = db.rawQuery("Select * from " + Utilidades.Tablatotal + " Where id = " + id + " LIMIT 1", null);

        if(cursor.moveToFirst()){
            ali = new AlimentosTotal();
            ali.setId(cursor.getInt(0));
            ali.setNombret(cursor.getString(1));
            ali.setDescripciont(cursor.getString(2));
            ali.setPreciot(cursor.getString(3));

        }
        cursor.close();
        return ali;
    }

    public boolean eliminar() {

        boolean ed = false;

        BDatos bDatos = new BDatos(context);
        SQLiteDatabase db = bDatos.getWritableDatabase();

        try {
            db.execSQL("Delete From " + Utilidades.Tablatotal + "");
            ed = true;
        } catch (Exception exception) {
            exception.toString();
            ed = false;
        } finally {
            db.close();
        }

        return ed;
    }
}
