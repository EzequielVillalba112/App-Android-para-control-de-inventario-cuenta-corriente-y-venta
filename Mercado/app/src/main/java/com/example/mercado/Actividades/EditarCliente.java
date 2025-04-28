package com.example.mercado.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mercado.Entidades.Clientes;
import com.example.mercado.ListarCliente;
import com.example.mercado.R;
import com.example.mercado.VerCliente;
import com.example.mercado.db.DbCliente;

public class EditarCliente extends AppCompatActivity {

    EditText txtNombreCliente, txtApellidoCliente, txtLimiteCliente;
    Button btnGuardar, btnEditar, btnBorrar;

    Clientes clientes;
    int id = 0;

    Toolbar toolbarVerCliente;

    private ActionBar actionBar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cliente);

        txtNombreCliente = findViewById(R.id.txtNombreCliente);
        txtApellidoCliente = findViewById(R.id.txtApellidoCliente);
        txtLimiteCliente = findViewById(R.id.txtLimiteCliente);
        toolbarVerCliente = findViewById(R.id.toolbarVerCliente);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.btnEditar);
        btnBorrar = findViewById(R.id.btnBorrar);

        btnEditar.setVisibility(View.INVISIBLE);
        btnBorrar.setVisibility(View.INVISIBLE);

        setSupportActionBar(toolbarVerCliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Editar Cliente");

        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                id = Integer.parseInt(null);
            }else{
                id = extras.getInt("id");
            }
        }else {
            id = (int) savedInstanceState.getSerializable("id");
        }

        DbCliente dbCliente = new DbCliente(EditarCliente.this);
        clientes = dbCliente.VerClientes(id);

        if (clientes != null){
            txtNombreCliente.setText(clientes.getNombre_cliente());
            txtApellidoCliente.setText(clientes.getApellido_cliente());
            txtLimiteCliente.setText(clientes.getLimite());

        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtNombreCliente.getText().toString().equals("") && !txtApellidoCliente.getText().toString().equals("")){
                    DbCliente dbCliente = new DbCliente(EditarCliente.this);
                    long clie = dbCliente.EditarCliente(txtNombreCliente.getText().toString(), txtApellidoCliente.getText().toString(),txtLimiteCliente.getText().toString(),"1",id);

                    if(clie == 1){
                        Toast.makeText(EditarCliente.this, "CLIENTE MODIFICADO",Toast.LENGTH_LONG).show();
                        verCliente();
                        limpiar();
                    }else{
                        Toast.makeText(EditarCliente.this, "ERROR AL MODIFICAR REGISTRO",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(EditarCliente.this, "DEBE LLENAR LOS CAPOS NOMBRE Y APELLIDO",Toast.LENGTH_LONG).show();
                }
                }
        });
    }

    private void limpiar(){
        txtNombreCliente.setText("");
        txtApellidoCliente.setText("");
        txtLimiteCliente.setText("");
    }

    private void verCliente(){
        Intent intent = new Intent(this, ListarCliente.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}

