package com.example.mercado.Usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mercado.R;

public class Usuario extends AppCompatActivity {

    Toolbar toolbarUsuarioP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        toolbarUsuarioP = findViewById(R.id.toolbarUsuarioP);

        setSupportActionBar(toolbarUsuarioP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Usuarios");

    }

    public void AddUser(View view){
        Intent add = new Intent(this, AgregarUsuario.class);
        startActivity(add);
    }

    public void ListaUser(View view){
        Intent lisUser = new Intent(this, ListarUsuarios.class);
        startActivity(lisUser);
    }
}