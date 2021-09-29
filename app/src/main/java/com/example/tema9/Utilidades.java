package com.example.tema9;

import android.net.Uri;

public class Utilidades {

    public static final String ID ="id";
    public static final String Tabla ="alimentos";
    public static final String Nombre ="nombre";
    public static final String Descripcion ="descripcion";
    public static final String Precio ="precio";
    public static final String Imagen ="imagen";

    public static final String Tablatotal ="total";
    public static final String IDt ="id";
    public static final String Nombret ="nombret";
    public static final String Descripciont ="descripciont";
    public static final String Preciot ="preciot";

    public static final String crear = "Create table "+Tabla+"("+ID+" Integer primary key autoincrement, "+ Nombre +" text not null, "+ Descripcion +" text not null, "+ Precio +" text not null, "+ Imagen +" text not null)";
    public static final String creart = "Create table "+Tablatotal+"("+IDt+" Integer primary key autoincrement, "+ Nombret +" text not null, "+ Descripciont +" text not null, "+ Preciot +" text not null)";
}
