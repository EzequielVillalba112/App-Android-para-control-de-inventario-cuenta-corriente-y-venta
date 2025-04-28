package com.example.mercado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.mercado.Adaptadores.AdaptadorClienteCC;
import com.example.mercado.Adaptadores.ListarClienteAdapter;
import com.example.mercado.db.DbCliente;

import java.util.ArrayList;

public class ListaCuentaCorriente extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView listaCliente;
    AdaptadorClienteCC adapter;
    SearchView txtBuscarCli;

    Toolbar toolbarCuentaCorriente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cuenta_corriente);

        listaCliente = findViewById(R.id.listaClienteCC);
        txtBuscarCli = findViewById(R.id.txtBuscarCli);
        toolbarCuentaCorriente = findViewById(R.id.toolbarCuentaCorriente);

        setSupportActionBar(toolbarCuentaCorriente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Lista de cliente Cuenta Corriente");


        txtBuscarCli.setOnQueryTextListener(this);
        Listar();
    }

    public void Listar(){
        DbCliente dbCliente = new DbCliente(ListaCuentaCorriente.this);

        listaCliente.setLayoutManager(new LinearLayoutManager(ListaCuentaCorriente.this));
        adapter = new AdaptadorClienteCC(dbCliente.mostrarTodosLosClientes());
        listaCliente.setAdapter(adapter);
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