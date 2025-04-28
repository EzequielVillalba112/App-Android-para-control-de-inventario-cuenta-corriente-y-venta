package com.example.mercado.Usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mercado.Adaptadores.AdatadorUsuario;
import com.example.mercado.ListarCliente;
import com.example.mercado.R;
import com.example.mercado.db.DbUsuario;

public class ListarUsuarios extends AppCompatActivity {

    RecyclerView listaUsuario;

    Toolbar toolbarListadeUsuarios;

    AdatadorUsuario adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuarios);

        listaUsuario = findViewById(R.id.listaUsuario);
        toolbarListadeUsuarios = findViewById(R.id.toolbarListadeUsuarios);

        setSupportActionBar(toolbarListadeUsuarios);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Lista Usuarios");

        listarUsuarios();
    }

    public void listarUsuarios(){
        DbUsuario dbUsuario = new DbUsuario(ListarUsuarios.this);

        listaUsuario.setLayoutManager(new LinearLayoutManager(ListarUsuarios.this));
        adapter = new AdatadorUsuario(dbUsuario.mostrarUsuario());
        listaUsuario.setAdapter(adapter);
    }

    public void onBackPressed(){

    }

}