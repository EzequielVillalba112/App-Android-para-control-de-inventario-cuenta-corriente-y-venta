package com.example.mercado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercado.Entidades.Usuario;
import com.example.mercado.db.DbCliente;
import com.example.mercado.db.DbHelper;
import com.example.mercado.db.DbUsuario;

public class Login extends AppCompatActivity {

    EditText txtNombreUser,txtPassword;
    Button btnIngresar;
    DbUsuario dbUsuario;
    Usuario usuario;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbUsuario = new DbUsuario(Login.this);
        usuario = new Usuario();

        preferences = getSharedPreferences("usuario", MODE_PRIVATE);

        txtNombreUser = findViewById(R.id.txtNombreUser);
        txtPassword = findViewById(R.id.txtPassword);
        btnIngresar = findViewById(R.id.btnIngresar);

        validarSecio();

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbUsuario.insertarAdmin();
                String nombre = txtNombreUser.getText().toString();
                String pass = txtPassword.getText().toString();
                boolean validar = validarCampos(nombre,pass);
                if (validar){

                    DbHelper dbHelper = new DbHelper(Login.this);
                    dbHelper.getWritableDatabase();
                    usuario = dbUsuario.login(nombre, pass);

                    if (usuario != null){

                        Toast.makeText(Login.this,"Usuario Correcto", Toast.LENGTH_LONG).show();

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("usuario_id",usuario.getId_user());
                        editor.putString("usuario_nom", usuario.getNombre_user());
                        editor.commit();

                        Intent main = new Intent(Login.this, MainActivity.class);
                        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(main);
                    }else {
                        Toast.makeText(Login.this,"Usuario Incorrecto", Toast.LENGTH_LONG).show();

                        txtNombreUser.setText("");
                        txtPassword.setText("");
                    }
                }
            }
        });

    }

    private void validarSecio(){
        String usuario_nom = preferences.getString("usuario_nom", null);

        if (usuario_nom != null){
            Intent main = new Intent(Login.this, MainActivity.class);
            main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(main);
        }

    }

    public boolean validarCampos(String nombre, String pass){
        if(nombre.isEmpty() || pass.isEmpty()){
            Toast.makeText(Login.this, "Ingrese usuario y contraseña", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }
}