package com.example.mercado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Registros extends AppCompatActivity {

    Toolbar toolbarRegistros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        toolbarRegistros = findViewById(R.id.toolbarRegistros);

        setSupportActionBar(toolbarRegistros);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Registros");


    }

    public void ListaVentas(View view){
        Intent listVentas = new Intent(this,ListarVentas.class);
        startActivity(listVentas);
    }
    public void ListaCC(View view){
        Intent listacc = new Intent(this,ListaCuentaCorriente.class);
        startActivity(listacc);
    }

    public void LisaDev(View view){
        Intent listaDev = new Intent(this,ListarDevoluciones.class);
        startActivity(listaDev);
    }
}