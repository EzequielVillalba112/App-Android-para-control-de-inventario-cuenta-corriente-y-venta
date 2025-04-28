package com.example.mercado;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mercado.Adaptadores.ListarClienteAdapter;
import com.example.mercado.Entidades.Clientes;
import com.example.mercado.db.DbCliente;

import java.util.ArrayList;

public class ListarCliente extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView listaCliente;
    ArrayList<Clientes> listaArrayCliente;
    ArrayList<Clientes> listaArrayClienteDesc;
    RadioButton rdbDesactivado, rdbActivado;
    Button btnListar;
    SearchView txtBuscar;
    ListarClienteAdapter adapter;

    Toolbar toolbarListClien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cliente);

        listaCliente = findViewById(R.id.listaClienteCC);
        rdbDesactivado = findViewById(R.id.rdbDesactivado);
        rdbActivado = findViewById(R.id.rdbActivado);
        btnListar = findViewById(R.id.btnListar);
        txtBuscar = findViewById(R.id.txtBuscar);
        toolbarListClien = findViewById(R.id.toolbarListClien);

        setSupportActionBar(toolbarListClien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Lista Cliente CC");


        txtBuscar.setOnQueryTextListener(this);

        Listar();

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdbDesactivado.isChecked()==true){
                    DbCliente dbCliente = new DbCliente(ListarCliente.this);

                    listaArrayClienteDesc = new ArrayList<>();

                    listaCliente.setLayoutManager(new LinearLayoutManager(ListarCliente.this));
                    adapter = new ListarClienteAdapter(dbCliente.mostrarClienteDesc());
                    listaCliente.setAdapter(adapter);

                    Toast.makeText(ListarCliente.this, "Cliente Desactivados",Toast.LENGTH_SHORT).show();
                }else if(rdbActivado.isChecked()==true){
                    DbCliente dbCliente = new DbCliente(ListarCliente.this);

                    listaArrayCliente = new ArrayList<>();

                    listaCliente.setLayoutManager(new LinearLayoutManager(ListarCliente.this));
                    adapter = new ListarClienteAdapter(dbCliente.mostrarCliente());
                    listaCliente.setAdapter(adapter);

                    Toast.makeText(ListarCliente.this, "Clientes Activados",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Listar(){
        DbCliente dbCliente = new DbCliente(ListarCliente.this);

        listaArrayCliente = new ArrayList<>();

        listaCliente.setLayoutManager(new LinearLayoutManager(ListarCliente.this));
        adapter = new ListarClienteAdapter(dbCliente.mostrarCliente());
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

    public void onBackPressed(){

    }
}