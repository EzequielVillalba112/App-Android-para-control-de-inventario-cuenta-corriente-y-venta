package com.example.mercado.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mercado.Actividades.EditarCliente;
import com.example.mercado.Entidades.Usuario;
import com.example.mercado.R;
import com.example.mercado.db.DbUsuario;

public class EditarUsuario extends AppCompatActivity {

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
        setTitle("Editar Usuario");

        id = getIntent().getStringExtra("idUsuario");

        ver();
        btnGuardaruserVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarUser();
            }
        });
    }

    public void ver(){
        DbUsuario dbUsuario = new DbUsuario(EditarUsuario.this);
        usuario = dbUsuario.verUsuario(Integer.parseInt(id));

        if (usuario != null){
            txtNomUserVer.setText(usuario.getNombre_user());
            txtPassVerUser.setText(usuario.getPassword());

            btnEditarUser.setVisibility(View.INVISIBLE);
            btnEliminar.setVisibility(View.INVISIBLE);

        }
    }

    public void editarUser(){
        if (!txtNomUserVer.getText().toString().equals("") && !txtPassVerUser.getText().toString().equals("")){
            DbUsuario dbUsuario = new DbUsuario(EditarUsuario.this);
            long user = dbUsuario.editarUsuario(txtNomUserVer.getText().toString(),txtPassVerUser.getText().toString(),Integer.parseInt(id));

            if (user == 1){
                Toast.makeText(EditarUsuario.this, "USUARIO MODIFICADO",Toast.LENGTH_SHORT).show();
                limpiar();
                verUser();
            }else {
                Toast.makeText(EditarUsuario.this, "ERROR AL MODIFICAR USUARIO",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(EditarUsuario.this, "DEBE LLENAR LOS CAPOS NOMBRE Y CONTRASEÑA",Toast.LENGTH_LONG).show();
        }
    }

    public void verUser(){
        Intent intent = new Intent(this, VerUsuario.class);
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
