package com.example.mercado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mercado.Adaptadores.AdaptadorDevoluciones;
import com.example.mercado.db.DbVenta;

public class ListarDevoluciones extends AppCompatActivity {

    RecyclerView listaDevolucion;
    AdaptadorDevoluciones adapter;
    Toolbar toolbarDevoluciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_devoluciones);

        listaDevolucion = findViewById(R.id.listaDevolucion);
        toolbarDevoluciones = findViewById(R.id.toolbarDevoluciones);

        setSupportActionBar(toolbarDevoluciones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Devoluciones");


        Listar();
    }

    public void Listar(){
        DbVenta dbVenta = new DbVenta(ListarDevoluciones.this);

        listaDevolucion.setLayoutManager(new GridLayoutManager(ListarDevoluciones.this,1));
        adapter = new AdaptadorDevoluciones(dbVenta.mostrarDevolucion());

        listaDevolucion.setAdapter(adapter);
    }
}