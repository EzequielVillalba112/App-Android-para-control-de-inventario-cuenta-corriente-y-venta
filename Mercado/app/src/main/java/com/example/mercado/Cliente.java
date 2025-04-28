package com.example.mercado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mercado.Actividades.AgregarCliente;

public class Cliente extends AppCompatActivity {

    Toolbar toolbarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);


        toolbarCliente = findViewById(R.id.toolbarCliente);

        setSupportActionBar(toolbarCliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Clientes CC");

    }

    public void AgregarCliente(View view){
        Intent AgregarCliente= new Intent(this, AgregarCliente.class);
        startActivity(AgregarCliente);
    }
    public void ListarCliente(View view){
        Intent ListarCliente= new Intent(this, ListarCliente.class);
        startActivity(ListarCliente);
    }

}