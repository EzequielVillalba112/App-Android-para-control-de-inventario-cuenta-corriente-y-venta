package com.example.mercado.Usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mercado.R;
import com.example.mercado.db.DbUsuario;

public class AgregarUsuario extends AppCompatActivity {

    EditText txtNomUser,txtPass;
    Button btnGuardaruser;

    Toolbar toolbarAddUsuario;

    long usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);

        txtNomUser = findViewById(R.id.txtNomUser);
        txtPass = findViewById(R.id.txtPass);
        btnGuardaruser = findViewById(R.id.btnGuardaruser);
        toolbarAddUsuario = findViewById(R.id.toolbarAddUsuario);

        setSupportActionBar(toolbarAddUsuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Agregar Usuario");

        btnGuardaruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNomUser.getText().toString().isEmpty() || txtPass.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "LLENE LOS CAPOS NOMBRE Y CONTRASEÑA", Toast.LENGTH_LONG).show();
                }else {
                    DbUsuario dbUsuario = new DbUsuario(AgregarUsuario.this);

                    usuario = dbUsuario.insertarUsuario(txtNomUser.getText().toString(), txtPass.getText().toString());
                    if (usuario>1){
                        Toast.makeText(AgregarUsuario.this, "USUARIO GUARDADO", Toast.LENGTH_LONG).show();
                        limpiar();
                    }else{
                        Toast.makeText(AgregarUsuario.this, "ERROR AL GUARDAR USUARIO", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void limpiar(){
        txtNomUser.setText("");
        txtPass.setText("");
    }
    public void onBackPressed(){

    }

}