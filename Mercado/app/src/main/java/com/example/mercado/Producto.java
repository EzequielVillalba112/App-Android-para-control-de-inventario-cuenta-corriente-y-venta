package com.example.mercado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mercado.Actividades.AgregarProducto;
import com.example.mercado.db.DbHelper;

public class Producto extends AppCompatActivity {

    private Toolbar toolbarProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        toolbarProducto = findViewById(R.id.toolbarProducto);

        setSupportActionBar(toolbarProducto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Producto");

    }
    public void AgregarProducto(View view){
        Intent Articulos = new Intent(this, AgregarProducto.class);
        startActivity(Articulos);
    }

    public void ListarProducto(View view){
        Intent ListaArticulos = new Intent(this, ListarProductos.class);
        startActivity(ListaArticulos);
    }



}