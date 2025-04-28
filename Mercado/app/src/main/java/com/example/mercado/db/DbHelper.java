package com.example.mercado.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mercado.Utilidades.Utilidades;

public class DbHelper extends SQLiteOpenHelper {

    DbUsuario dbUsuario;

    //VERSION ACTUAL 10
    private  static final int DATABASE_VERSION = 11;
    private  static final String DATABASE_NOMBRE = "Rojo.db";

    public DbHelper(@Nullable Context context) {
        super(context,DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_PRODUCTO);
        db.execSQL(Utilidades.CREAR_TABLA_CLIENTE);
        db.execSQL(Utilidades.CREAR_TABLA_VENTAS);
        db.execSQL(Utilidades.CREAR_TABLA_DETALLE_VENTA);
        db.execSQL(Utilidades.CREAR_TABLA_CUENTA_CORRIENTE);
        db.execSQL(Utilidades.CREAR_TABLA_DEVOLUCION);
        db.execSQL(Utilidades.CREAR_TABLA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_PRODUCTO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_CLIENTE);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_VENTA);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_DETALLE_VENTAS);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_CUENTA_CORRIENTE);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_DEVOLUCION);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_USUARIO);
        onCreate(db);

    }


}
