package com.example.mercado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.mercado.Adaptadores.AdapterListaVentas;
import com.example.mercado.db.DbVenta;

import java.util.function.ToLongBiFunction;

public class ListarVentas extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView listaVentas;
    AdapterListaVentas adapter;
    SearchView txtBuscarVenta;
    Toolbar toolbarRegistrVentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_ventas);

        listaVentas = findViewById(R.id.listaVentas);
        txtBuscarVenta = findViewById(R.id.txtBuscarVenta);

        toolbarRegistrVentas = findViewById(R.id.toolbarRegistrVentas);

        setSupportActionBar(toolbarRegistrVentas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Registro de Ventas");


        txtBuscarVenta.setOnQueryTextListener(this);
        Listar();

    }

    public void Listar(){

        DbVenta dbVenta = new DbVenta(ListarVentas.this);

        listaVentas.setLayoutManager(new GridLayoutManager(ListarVentas.this,1));
        adapter = new AdapterListaVentas(dbVenta.mostrarVentas());

        listaVentas.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }
}