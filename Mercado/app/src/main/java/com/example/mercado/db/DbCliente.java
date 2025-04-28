package com.example.mercado.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.mercado.Cliente;
import com.example.mercado.Entidades.Clientes;
import com.example.mercado.Utilidades.Utilidades;

import java.util.ArrayList;

public class DbCliente extends DbHelper{

    Context context;

    public DbCliente(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarCliente(String nom_cliente, String apellido_cliente, String limite, String dependencia_cliente, String estado){
       long id = 0;

       try {

           DbHelper dbHelper = new DbHelper(context);
           SQLiteDatabase db = dbHelper.getWritableDatabase();

           ContentValues cliente = new ContentValues();
           cliente.put(Utilidades.C_NOMBRE_CLIENTE,nom_cliente);
           cliente.put(Utilidades.C_APELLIDO_CLIENTE,apellido_cliente);
           cliente.put(Utilidades.C_DEPENDECIA_CLIENTE,dependencia_cliente);
           cliente.put(Utilidades.C_LIMITE,limite);
           cliente.put(Utilidades.C_ESTADO_CLIENTE,estado);

           id= db.insert(Utilidades.TABLA_CLIENTE, null,cliente);
       }catch (Exception ex){
           ex.toString();
       }
       return id;
    }

    public ArrayList<Clientes> mostrarCliente(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Clientes> listaCliente = new ArrayList<>();
        Clientes cliente;
        Cursor cursorCliente;

        cursorCliente = db.rawQuery("SELECT id_cliente,nom_cliente,apellido_cliente,limite FROM " +Utilidades.TABLA_CLIENTE+" WHERE estado_cliente = 1", null);

        if (cursorCliente.moveToFirst()){
            do {
                cliente = new Clientes();
                cliente.setId_cliente(cursorCliente.getInt(0));
                cliente.setNombre_cliente(cursorCliente.getString(1));
                cliente.setApellido_cliente(cursorCliente.getString(2));
                cliente.setLimite(cursorCliente.getString(3));

                listaCliente.add(cliente);
            }while (cursorCliente.moveToNext());
        }
        cursorCliente.close();

        return listaCliente;
    }

    public ArrayList<Clientes> mostrarTodosLosClientes(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Clientes> listaCliente = new ArrayList<>();
        Clientes cliente;
        Cursor cursorCliente;

        cursorCliente = db.rawQuery("SELECT id_cliente,nom_cliente,apellido_cliente,limite FROM " +Utilidades.TABLA_CLIENTE+"", null);

        if (cursorCliente.moveToFirst()){
            do {
                cliente = new Clientes();
                cliente.setId_cliente(cursorCliente.getInt(0));
                cliente.setNombre_cliente(cursorCliente.getString(1));
                cliente.setApellido_cliente(cursorCliente.getString(2));
                cliente.setLimite(cursorCliente.getString(3));

                listaCliente.add(cliente);
            }while (cursorCliente.moveToNext());
        }
        cursorCliente.close();

        return listaCliente;
    }


    public ArrayList<Clientes> mostrarClienteDesc(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Clientes> listaClienteDesc = new ArrayList<>();
        Clientes clientesDesc;
        Cursor cursorClientesDesc;

        cursorClientesDesc = db.rawQuery("SELECT id_cliente,nom_cliente,apellido_cliente,limite FROM " +Utilidades.TABLA_CLIENTE+" WHERE estado_cliente = 0",null);

        if (cursorClientesDesc.moveToFirst()){
            do {
                clientesDesc = new Clientes();
                clientesDesc.setId_cliente(cursorClientesDesc.getInt(0));
                clientesDesc.setNombre_cliente(cursorClientesDesc.getString(1));
                clientesDesc.setApellido_cliente(cursorClientesDesc.getString(2));
                clientesDesc.setLimite(cursorClientesDesc.getString(3));

                listaClienteDesc.add(clientesDesc);
            }while (cursorClientesDesc.moveToNext());
        }
        cursorClientesDesc.close();

        return listaClienteDesc;
    }


    public Clientes VerClientes(int id){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Clientes clientes = null;
        Cursor cursorCliente = null;

        cursorCliente = db.rawQuery("SELECT nom_cliente,apellido_cliente,limite,id_cliente FROM " +Utilidades.TABLA_CLIENTE+ " WHERE id_cliente =" + id +" LIMIT 1",null);

        if (cursorCliente.moveToFirst()){
            clientes = new Clientes();

            clientes.setNombre_cliente(cursorCliente.getString(0));
            clientes.setApellido_cliente(cursorCliente.getString(1));
            clientes.setLimite(cursorCliente.getString(2));
            clientes.setId_cliente(cursorCliente.getInt(3));

        }
        cursorCliente.close();

        return clientes;
    }

    public long EditarCliente(String nom_cliente, String apellido_cliente, String limite, String estado, int id){
        String cod = String.valueOf(id);

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues editarCliente = new ContentValues();
        editarCliente.put(Utilidades.C_NOMBRE_CLIENTE,nom_cliente);
        editarCliente.put(Utilidades.C_APELLIDO_CLIENTE,apellido_cliente);
        editarCliente.put(Utilidades.C_LIMITE,limite);
        editarCliente.put(Utilidades.C_ESTADO_CLIENTE,estado);
        db.update(Utilidades.TABLA_CLIENTE, editarCliente,"id_cliente = ?",new String[]{ cod });

        return 1;
    }

    public long DesactivarCliente(int estado_cliente, int id){
        String cod = String.valueOf(id);

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utilidades.C_ESTADO_CLIENTE,estado_cliente);

        db.update(Utilidades.TABLA_CLIENTE,contentValues, "id_cliente = ?", new String[]{ cod });

        return 1;
    }

    public long EliminarCliente(int id){
        long correcto = 0;
        Cursor relacionTabla = null;
        int dependencia = 0;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        relacionTabla = db.rawQuery("SELECT dependencia_cliente FROM " +Utilidades.TABLA_CLIENTE+ " WHERE id_cliente =" +id+ " LIMIT 1",null);

        if (relacionTabla.moveToFirst()){
            dependencia = relacionTabla.getInt(0);
        }
        relacionTabla.close();

        if (dependencia == 0){
            db.execSQL("DELETE FROM " +Utilidades.TABLA_CLIENTE+ " WHERE id_cliente =" +id+ "");
            correcto = 1;
        }else{
            correcto = 0;
        }

        return correcto;
    }

    public int DesabilitarBorrar(int id){
        int desabilitar = 0;
        Cursor estadoCliente = null;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        estadoCliente = db.rawQuery("SELECT estado_cliente FROM " +Utilidades.TABLA_CLIENTE+ " WHERE id_cliente =" +id+ " LIMIT 1",null);

        if (estadoCliente.moveToFirst()){
            desabilitar = estadoCliente.getInt(0);
        }
        estadoCliente.close();

        return desabilitar;
    }

    public Clientes validarCliente(String nombre, String apellido){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor valCliente = null;
        Clientes clientes = null;

        valCliente = db.rawQuery("SELECT * FROM " +Utilidades.TABLA_CLIENTE+ " WHERE nom_cliente ='" +nombre+ "' AND apellido_cliente = '" +apellido+ "'",null);

        if (valCliente.moveToFirst()){
            clientes = new Clientes();
            clientes.setId_cliente(valCliente.getColumnIndexOrThrow("id_cliente"));
        }
        valCliente.close();

        return clientes;
    }
}
