package com.example.mercado;

import androidx.appcompat.app.ActionBar;
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

import com.example.mercado.Actividades.EditarCliente;
import com.example.mercado.Entidades.Clientes;
import com.example.mercado.db.DbCliente;

public class VerCliente extends AppCompatActivity {

    EditText txtNombreCliente, txtApellidoCliente, txtLimiteCliente;
    Button btnGuardar, btnEditar, btnBorrar;

    Clientes clientes;
    int id = 0;

    Toolbar toolbarVerCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cliente);

        toolbarVerCliente = findViewById(R.id.toolbarVerCliente);

        txtNombreCliente = findViewById(R.id.txtNombreCliente);
        txtApellidoCliente = findViewById(R.id.txtApellidoCliente);
        txtLimiteCliente = findViewById(R.id.txtLimiteCliente);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.btnEditar);
        btnBorrar = findViewById(R.id.btnBorrar);

        setSupportActionBar(toolbarVerCliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detalle Cliente");

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

        DbCliente dbCliente = new DbCliente(VerCliente.this);
        clientes = dbCliente.VerClientes(id);

        int desactivar = dbCliente.DesabilitarBorrar(id);

        if (desactivar == 0){
            btnBorrar.setVisibility(View.INVISIBLE);
        }else {
            btnBorrar.setVisibility(View.VISIBLE);
        }

        if (clientes != null){
            txtNombreCliente.setText(clientes.getNombre_cliente());
            txtApellidoCliente.setText(clientes.getApellido_cliente());
            txtLimiteCliente.setText(clientes.getLimite());
            //bloquear boton
            btnGuardar.setVisibility(View.INVISIBLE);
            //bloquear texto
            txtNombreCliente.setInputType(InputType.TYPE_NULL);
            txtApellidoCliente.setInputType(InputType.TYPE_NULL);
            txtLimiteCliente.setInputType(InputType.TYPE_NULL);
        }

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerCliente.this, EditarCliente.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialogo = new AlertDialog
                        .Builder(VerCliente.this)
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DbCliente dbCliente = new DbCliente(VerCliente.this);
                                long art = dbCliente.EliminarCliente(id);

                                if (art == 1){
                                    Toast.makeText(VerCliente.this, "CLIENTE ELIMINADO",Toast.LENGTH_LONG).show();
                                    verCliente();
                                    limpiar();
                                }else{
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VerCliente.this);

                                    alertDialogBuilder.setTitle("ERROR AL ELIMINAR EL CLIENTE");


                                    alertDialogBuilder
                                            .setMessage("Otro sector de la aplicacion esta usando estos datos ¿Desea solo deshabilitarlo?")
                                            .setCancelable(false)
                                            .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,int which) {
                                                    DbCliente dbCliente = new DbCliente(VerCliente.this);
                                                    long desc = dbCliente.DesactivarCliente(0,id);

                                                    if (desc == 1){
                                                        Toast.makeText(VerCliente.this, "CLIENTE DESACTIVADO",Toast.LENGTH_LONG).show();
                                                        verCliente();
                                                        limpiar();
                                                    }
                                                }
                                            })
                                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,int which) {
                                                    Toast.makeText(VerCliente.this, "NO SE DASACTIVO EL CLIENTE",Toast.LENGTH_LONG).show();
                                                    dialog.cancel();
                                                }
                                            }).create().show();
                                }
                                // Hicieron click en el botón positivo, así que la acción está confirmada
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Hicieron click en el botón negativo, no confirmaron
                                // Simplemente descartamos el diálogo
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar") // El título
                        .setMessage("¿Eliminar Cliente?") // El mensaje
                        .create();// No olvides llamar a Create
                dialogo.show();
            }
        });

    }

    public void onBackPressed(){

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