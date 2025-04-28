package com.example.mercado.Actividades;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mercado.Entidades.Clientes;
import com.example.mercado.R;
import com.example.mercado.db.DbCliente;

public class AgregarCliente extends AppCompatActivity {

    EditText txtNombreCliente, txtApellidoCliente, txtLimiteCliente;
    Button btnGuardar;

    Toolbar toolbarClienteADD;

    Clientes clientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);

        txtNombreCliente = findViewById(R.id.txtNombreCliente);
        txtApellidoCliente = findViewById(R.id.txtApellidoCliente);
        txtLimiteCliente = findViewById(R.id.txtLimiteCliente);
        btnGuardar = findViewById(R.id.btnGuardar);
        toolbarClienteADD = findViewById(R.id.toolbarClienteADD);

        txtNombreCliente.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        txtApellidoCliente.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        setSupportActionBar(toolbarClienteADD);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Agregar Cliente CC");

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNombreCliente.getText().toString().isEmpty()||txtApellidoCliente.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "LLENE LOS CAPOS NOMBRE Y APELLIDO", Toast.LENGTH_LONG).show();
                }else {
                    DbCliente dbCliente = new DbCliente(AgregarCliente.this);

                    clientes = dbCliente.validarCliente(txtNombreCliente.getText().toString(),txtApellidoCliente.getText().toString());
                    if (clientes != null){
                        Toast.makeText(AgregarCliente.this, "Este Cliente ya existe", Toast.LENGTH_LONG).show();
                        limpiar();
                    }else {
                        long id = dbCliente.insertarCliente(txtNombreCliente.getText().toString(), txtApellidoCliente.getText().toString(), txtLimiteCliente.getText().toString(),"0","1");

                        if(id > 0){
                            Toast.makeText(AgregarCliente.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                            limpiar();
                        }else{
                            Toast.makeText(AgregarCliente.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        });
    }

    private void limpiar(){
        txtNombreCliente.setText("");
        txtApellidoCliente.setText("");
        txtLimiteCliente.setText("");
    }
}