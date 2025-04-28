package com.example.mercado.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.mercado.Entidades.Clientes;
import com.example.mercado.Entidades.Usuario;
import com.example.mercado.Utilidades.Utilidades;

import java.util.ArrayList;

public class DbUsuario extends DbHelper{

    Context context;

    public DbUsuario(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarUsuario(String nom_user,  String pass){
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues user = new ContentValues();
            user.put(Utilidades.C_NOMBRE_USER, nom_user);
            user.put(Utilidades.C_PASSWORD, pass);

            id = db.insert(Utilidades.TABLA_USUARIO, null, user);

        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public ArrayList<Usuario> mostrarUsuario(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Usuario> listarUsuario = new ArrayList<>();
        Usuario usuario;
        Cursor cursorUser;

        cursorUser = db.rawQuery("SELECT id_usuario,nombre_user,password FROM " +Utilidades.TABLA_USUARIO+ "",null);

        if (cursorUser.moveToFirst()){
            do {
                usuario = new Usuario();
                usuario.setId_user(cursorUser.getString(0));
                usuario.setNombre_user(cursorUser.getString(1));
                usuario.setPassword(cursorUser.getString(2));

                listarUsuario.add(usuario);
            }while (cursorUser.moveToNext());
        }
        cursorUser.close();

        return listarUsuario;
    }

    public Usuario verUsuario(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Usuario usuario = null;
        Cursor cursorUser = null;

        cursorUser = db.rawQuery("SELECT id_usuario,nombre_user,password FROM " +Utilidades.TABLA_USUARIO+" WHERE id_usuario = "+id+ " LIMIT 1",null);

        if (cursorUser.moveToFirst()){
            usuario = new Usuario();
            usuario.setId_user(cursorUser.getString(0));
            usuario.setNombre_user(cursorUser.getString(1));
            usuario.setPassword(cursorUser.getString(2));

        }
        cursorUser.close();

        return usuario;
    }

    public long elimarUsuario(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("DELETE FROM "+Utilidades.TABLA_USUARIO+ " WHERE id_usuario = "+id+"");


        return 1;
    }

    public long editarUsuario(String nom_User, String pass, int id){
        String idUser = String.valueOf(id);


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues editarUser = new ContentValues();
        editarUser.put(Utilidades.C_NOMBRE_USER, nom_User);
        editarUser.put(Utilidades.C_PASSWORD, pass);

        db.update(Utilidades.TABLA_USUARIO,editarUser, "id_usuario = ?", new String[]{idUser});

        return 1;
    }

    public void insertarAdmin(){
        String admin = "admin";

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor bsAdmin = null;
        String where = "nombre_user = ?";
        bsAdmin = db.query(Utilidades.TABLA_USUARIO,null,where,new String[]{admin},null,null,null);

        if(bsAdmin.getCount() <1){
            ContentValues values = new ContentValues();
            values.put(Utilidades.C_NOMBRE_USER, admin);
            values.put(Utilidades.C_PASSWORD, admin);

            db.insert(Utilidades.TABLA_USUARIO,null,values);
        }
    }

    public Usuario login(String nombre, String pass){
        Usuario usuario;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String where = "nombre_user = ? AND password = ?";
        Cursor cursor = db.query(Utilidades.TABLA_USUARIO,null,where, new String[]{nombre,pass},null,null,null);

        if (cursor.getCount() < 1){
            return null;
        }else {
            cursor.moveToFirst();
            usuario = new Usuario();
            usuario.setId_user(cursor.getString(cursor.getColumnIndexOrThrow("id_usuario")));
            usuario.setNombre_user(cursor.getString(cursor.getColumnIndexOrThrow("nombre_user")));
            usuario.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));

            return usuario;
        }
    }
}
