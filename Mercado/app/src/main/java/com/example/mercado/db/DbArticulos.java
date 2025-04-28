package com.example.mercado.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.mercado.Entidades.Articulos;
import com.example.mercado.Utilidades.Utilidades;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class DbArticulos extends DbHelper{

    Context context;


    public DbArticulos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarArticulo(String cod_barra, String nombre, String precio, byte[] imagen, String estado, String dependencia){

        long id = 0;

       try {
           DbHelper dbHelper = new DbHelper(context);
           SQLiteDatabase db = dbHelper.getWritableDatabase();

           ContentValues values = new ContentValues();
           values.put(Utilidades.C_COD_BARRA,cod_barra);
           values.put(Utilidades.C_NOMBRE, nombre);
           values.put(Utilidades.C_PRECIO, precio);
           values.put(Utilidades.C_IMAGEN, imagen);
           values.put(Utilidades.C_ESTADO, estado);
           values.put(Utilidades.C_DEPENDECIA, dependencia);

           id = db.insert(Utilidades.TABLA_PRODUCTO, null,values);
       }catch (Exception ex){
           ex.toString();
       }


        return id;
    }

    public ArrayList<Articulos> mostrarArticulos(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Articulos> listaProducto = new ArrayList<>();
        Articulos articulos;
        Cursor cursorArticulos;

        cursorArticulos = db.rawQuery("SELECT nombre,precio,id,imagen FROM " +Utilidades.TABLA_PRODUCTO+" WHERE estado = 1", null);

        if (cursorArticulos.moveToFirst()){
            do {
                articulos = new Articulos();
                articulos.setNombre(cursorArticulos.getString(0));
                articulos.setPrecio(cursorArticulos.getString(1));
                articulos.setId(cursorArticulos.getInt(2));
                articulos.setImagen(cursorArticulos.getBlob(3));
                //String nombre = cursorArticulos.getString(0);
               // String precio = cursorArticulos.getString(1);
                listaProducto.add(articulos);

               // listaProducto.add(new Articulos(nombre,precio));
            } while (cursorArticulos.moveToNext());
        }
        cursorArticulos.close();

        return listaProducto;
    }

    public ArrayList<Articulos> mostrarArticulosDesc(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Articulos> listaProductoDesc = new ArrayList<>();
        Articulos articulosDesc;
        Cursor cursorArticulosDesc;

        cursorArticulosDesc = db.rawQuery("SELECT nombre,precio,id,imagen FROM " +Utilidades.TABLA_PRODUCTO+" WHERE estado = 0", null);

        if (cursorArticulosDesc.moveToFirst()){
            do {
                articulosDesc = new Articulos();
                articulosDesc.setNombre(cursorArticulosDesc.getString(0));
                articulosDesc.setPrecio(cursorArticulosDesc.getString(1));
                articulosDesc.setId(cursorArticulosDesc.getInt(2));
                articulosDesc.setImagen(cursorArticulosDesc.getBlob(3));

                listaProductoDesc.add(articulosDesc);

            }while (cursorArticulosDesc.moveToNext());
        }
        cursorArticulosDesc.close();

        return listaProductoDesc;
    }

    public Articulos VerArticulos(int id){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        Articulos articulos = null;
        Cursor cursorArticulos = null;

        cursorArticulos = db.rawQuery("SELECT nombre,precio,imagen,cod_barra,id FROM " +Utilidades.TABLA_PRODUCTO+ " WHERE id =" + id + " LIMIT 1", null);

        if (cursorArticulos.moveToFirst()){
                articulos = new Articulos();

                articulos.setNombre(cursorArticulos.getString(0));
                articulos.setPrecio(cursorArticulos.getString(1));
                articulos.setImagen(cursorArticulos.getBlob(2));
                articulos.setCod_barra(cursorArticulos.getString(3));
                articulos.setId(cursorArticulos.getInt(4));

        }

        cursorArticulos.close();

        return articulos;
    }


    public long EditarArticulo(String cod_barra, String nombre, String precio, byte[] imagen, String estado, int id) {

        String cod = String.valueOf(id);

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utilidades.C_COD_BARRA,cod_barra);
        contentValues.put(Utilidades.C_NOMBRE,nombre);
        contentValues.put(Utilidades.C_PRECIO,precio);
        contentValues.put(Utilidades.C_IMAGEN,imagen);
        contentValues.put(Utilidades.C_ESTADO,estado);
        db.update(Utilidades.TABLA_PRODUCTO, contentValues, "id = ?",new String[]{ cod });

        return 1;
    }


    public long DesactivarProducto(int estado, int id){
        String cod = String.valueOf(id);

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utilidades.C_ESTADO,estado);

        db.update(Utilidades.TABLA_PRODUCTO,contentValues, "id = ?",new String[]{ cod });

        return 1;
    }

    public long eliminarProducto(int id){
        long correcto = 0;
        Cursor relacionTabla = null;
        int dependencia =0;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        relacionTabla = db.rawQuery("SELECT dependencia FROM " +Utilidades.TABLA_PRODUCTO+ " WHERE id =" + id + " LIMIT 1", null);

        if (relacionTabla.moveToFirst()){
            dependencia = relacionTabla.getInt(0);
        }
        relacionTabla.close();

        if (dependencia == 0){
            db.execSQL("DELETE FROM " + Utilidades.TABLA_PRODUCTO + " WHERE  id = " + id + "");
            correcto = 1;
        }else {
            correcto = 0;
        }

        return correcto;
    }

    public int desabilitarBorrar(int id){
        int desabilitado=0;
        Cursor estadoProducto = null;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        estadoProducto = db.rawQuery("SELECT estado FROM " +Utilidades.TABLA_PRODUCTO+ " WHERE id =" + id + " LIMIT 1", null);

        if (estadoProducto.moveToFirst()){
            desabilitado = estadoProducto.getInt(0);
        }
        estadoProducto.close();

        return desabilitado;
    }

    public  Articulos buscarCodBarra(String codBarra){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Articulos articulos = null;
        Cursor cursorProducto = null;

        cursorProducto = db.rawQuery("SELECT nombre,precio,id FROM " +Utilidades.TABLA_PRODUCTO+ " WHERE cod_barra =" +codBarra+ " LIMIT 1", null);

        if (cursorProducto.moveToFirst()){
            articulos = new Articulos();

            articulos.setNombre(cursorProducto.getString(0));
            articulos.setPrecio(cursorProducto.getString(1));
            articulos.setId(cursorProducto.getInt(2));
        }
        cursorProducto.close();

        return articulos;
    }

    public Articulos nombreProductoVal(String nombrePro){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int validacion;
        Cursor valProd = null;
        Articulos articulos = null;
        valProd = db.rawQuery("SELECT nombre,precio,id  FROM " +Utilidades.TABLA_PRODUCTO+ " WHERE nombre ='" +nombrePro+ "'",null);

        if (valProd.moveToFirst()){
            articulos = new Articulos();

            articulos.setNombre(valProd.getString(0));
            articulos.setPrecio(valProd.getString(1));
            articulos.setId(valProd.getInt(2));
        }
        valProd.close();

        return articulos;
    }
}
