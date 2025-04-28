package com.example.mercado;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mercado.Actividades.EditarProducto;
import com.example.mercado.Entidades.Articulos;
import com.example.mercado.db.DbArticulos;

public class VerProducto extends AppCompatActivity {

    ImageButton btnScan;
    EditText txtCodBarra, txtNombreProd, txtPrecio;
    ImageView imgViewCambiarFoto;
    Button btnGuardar, btnEditar, btnBorrar;
    ImageButton btnVolvelListaPro;

    Articulos articulos;
    int id = 0;

    private Toolbar toolbarVerPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);


        toolbarVerPro = findViewById(R.id.toolbarVerPro);
        btnScan = findViewById(R.id.btnScanner);
        txtCodBarra = findViewById(R.id.txtCodBarra);
        txtNombreProd = findViewById(R.id.txtApellidoCliente);
        txtPrecio = findViewById(R.id.txtPrecio);
        imgViewCambiarFoto = findViewById(R.id.imgViewCambiarFoto);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditar = findViewById(R.id.btnEditar);
        btnBorrar = findViewById(R.id.btnBorrar);


        setSupportActionBar(toolbarVerPro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detalles Producto");

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

        DbArticulos dbArticulos = new DbArticulos(VerProducto.this);
        articulos = dbArticulos.VerArticulos(id);

        int desc = dbArticulos.desabilitarBorrar(id);

        if (desc == 0){
            btnBorrar.setVisibility(View.INVISIBLE);
        }else {
            btnBorrar.setVisibility(View.VISIBLE);
        }

        if (articulos != null){

            byte[] imgProductos = articulos.getImagen();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgProductos, 0,imgProductos.length);

            txtCodBarra.setText(articulos.getCod_barra());
            txtNombreProd.setText(articulos.getNombre());
            txtPrecio.setText(articulos.getPrecio());
            imgViewCambiarFoto.setImageBitmap(bitmap);
            //bloquear boton
            btnGuardar.setVisibility(View.INVISIBLE);
            btnScan.setVisibility(View.INVISIBLE);
            //bloquear texto
            txtCodBarra.setInputType(InputType.TYPE_NULL);
            txtNombreProd.setInputType(InputType.TYPE_NULL);
            txtPrecio.setInputType(InputType.TYPE_NULL);

        }

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerProducto.this, EditarProducto.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialogo = new AlertDialog
                        .Builder(VerProducto.this)
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DbArticulos dbArticulos = new DbArticulos(VerProducto.this);
                                long art = dbArticulos.eliminarProducto(id);

                                if (art == 1){
                                    Toast.makeText(VerProducto.this, "PRODUCTO ELIMINADO",Toast.LENGTH_LONG).show();
                                    Intent listaPro = new Intent(VerProducto.this, ListarProductos.class);
                                    startActivity(listaPro);
                                    limpiar();
                                }else{
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VerProducto.this);

                                    alertDialogBuilder.setTitle("ERROR AL ELIMINAR EL PRODUCTO");


                                    alertDialogBuilder
                                            .setMessage("Otro sector de la aplicacion esta usando estos datos ¿Desea solo deshabilitarlo?")
                                            .setCancelable(false)
                                            .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,int which) {
                                                    DbArticulos dbArticulos = new DbArticulos(VerProducto.this);
                                                    long desc = dbArticulos.DesactivarProducto(0,id);

                                                    if (desc == 1){
                                                        Toast.makeText(VerProducto.this, "PRODUCTO DESACTIVADO",Toast.LENGTH_LONG).show();
                                                        verProducto();
                                                        limpiar();
                                                    }
                                                }
                                            })
                                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,int which) {
                                                    Toast.makeText(VerProducto.this, "NO SE DASACTIVO EL PRODUCTO",Toast.LENGTH_LONG).show();
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
                        .setMessage("¿Desea elimiinar el producto?") // El mensaje
                        .create();// No olvides llamar a Create
                dialogo.show();
            }
        });



    }

    private void verProducto() {
        Intent intent = new Intent(this, ListarProductos.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    private void limpiar(){
        txtCodBarra.setText("");
        txtNombreProd.setText("");
        txtPrecio.setText("");
        imgViewCambiarFoto.setImageResource(R.drawable.camera);
    }

    public void onBackPressed(){

    }

}