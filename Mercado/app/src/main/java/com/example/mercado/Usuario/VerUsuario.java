package com.example.mercado.Usuario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mercado.Entidades.Usuario;
import com.example.mercado.R;
import com.example.mercado.VerCliente;
import com.example.mercado.db.DbUsuario;

public class VerUsuario extends AppCompatActivity {

    EditText txtNomUserVer,txtPassVerUser;

    Button btnGuardaruserVer,btnEditarUser,btnEliminar;

    String id;
    Toolbar toolbarVerUsuario;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_usuario);

        toolbarVerUsuario = findViewById(R.id.toolbarVerUsuario);
        txtNomUserVer = findViewById(R.id.txtNomUserVer);
        txtPassVerUser = findViewById(R.id.txtPassVerUser);
        btnGuardaruserVer = findViewById(R.id.btnGuardaruserVer);
        btnEditarUser = findViewById(R.id.btnEditarUser);
        btnEliminar = findViewById(R.id.btnEliminar);

        setSupportActionBar(toolbarVerUsuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detalle Usuario");

        id = getIntent().getStringExtra("idUsuario");

        ver();

        btnEditarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerUsuario.this, EditarUsuario.class);
                intent.putExtra("idUsuario", id);
                startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elimar();
            }
        });
    }

    public void ver(){
        DbUsuario dbUsuario = new DbUsuario(VerUsuario.this);
        usuario = dbUsuario.verUsuario(Integer.parseInt(id));

        if (usuario != null){
            txtNomUserVer.setText(usuario.getNombre_user());
            txtPassVerUser.setText(usuario.getPassword());

            btnGuardaruserVer.setVisibility(View.INVISIBLE);

            txtNomUserVer.setInputType(InputType.TYPE_NULL);
            txtPassVerUser.setInputType(InputType.TYPE_NULL);
        }
    }

    public void elimar() {
        AlertDialog dialog = new AlertDialog
                .Builder(VerUsuario.this)
                .setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DbUsuario dbUsuario = new DbUsuario(VerUsuario.this);
                        long user = dbUsuario.elimarUsuario(Integer.parseInt(id));

                        if (user == 1) {
                            Toast.makeText(VerUsuario.this, "CLIENTE ELIMINADO", Toast.LENGTH_SHORT).show();
                            verUser();
                            limpiar();
                        }
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setTitle("CONFIRMAR")
                .setMessage("¿ELIMINAR USUARIO?")
                .create();
        dialog.show();

    }

    public void verUser(){
        Intent intent = new Intent(this, ListarUsuarios.class);
        intent.putExtra("idUsuario", id);
        startActivity(intent);
    }
    public void limpiar(){
        txtNomUserVer.setText("");
        txtPassVerUser.setText("");
    }

    public void onBackPressed(){

    }

}