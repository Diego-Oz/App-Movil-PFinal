package com.example.tema9;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BDAlimentos extends BDatos{

    Context context;
    /*public static final int ORDER = 100;

    public static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(Utilidades.CONTENT_AUTHORITY, Utilidades.Tabla, ORDER);
    }*/

    public BDAlimentos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertar(String nombre, String descripcion, String precio, String imagen){
        long id = 0;

        try {
            BDatos bDatos = new BDatos(context);
            SQLiteDatabase db = bDatos.getWritableDatabase();

            ContentValues val = new ContentValues();
            val.put("nombre", nombre);
            val.put("descripcion", descripcion);
            val.put("precio", precio);
            val.put("imagen", imagen);

            id = db.insert(Utilidades.Tabla, null, val);
        }
        catch (Exception exception){
            exception.toString();
        }

        return id;
    }

    public boolean editar(int id, String nombre, String descripcion, String precio, String imagen){

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
    }

    public ArrayList<Alimentos> mostrar(){
        BDatos bDatos = new BDatos(context);
        SQLiteDatabase db = bDatos.getWritableDatabase();

        ArrayList<Alimentos> lista = new ArrayList<>();
        Alimentos ali = null;
        Cursor cursor = null;

        cursor = db.rawQuery("Select * from "+ Utilidades.Tabla, null);

        if(cursor.moveToFirst()){
            do {
                ali = new Alimentos();
                ali.setId(cursor.getInt(0));
                ali.setNombre(cursor.getString(1));
                ali.setDescripcion(cursor.getString(2));
                ali.setPrecio(cursor.getString(3));
                ali.setImagen(cursor.getString(4));

                lista.add(ali);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    public Alimentos modificar(int id){
        BDatos bDatos = new BDatos(context);
        SQLiteDatabase db = bDatos.getWritableDatabase();

        Alimentos ali = null;
        Cursor cursor = null;

        cursor = db.rawQuery("Select * from " + Utilidades.Tabla + " Where id = " + id + " LIMIT 1", null);

        if(cursor.moveToFirst()){
            ali = new Alimentos();
            ali.setId(cursor.getInt(0));
            ali.setNombre(cursor.getString(1));
            ali.setDescripcion(cursor.getString(2));
            ali.setPrecio(cursor.getString(3));
            ali.setImagen(cursor.getString(4));

        }
        cursor.close();
        return ali;
    }

    public boolean eliminar(int id) {

        boolean ed = false;

        BDatos bDatos = new BDatos(context);
        SQLiteDatabase db = bDatos.getWritableDatabase();

        try {
            db.execSQL("Delete From " + Utilidades.Tabla + " WHERE id = '" + id + "'");
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
