package com.example.mercado.VistaVender;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercado.Adaptadores.AdaptadorProductoVenta;
import com.example.mercado.Entidades.Articulos;
import com.example.mercado.MainActivity;
import com.example.mercado.R;
import com.example.mercado.VistaVender.Utilidades.ProductoVenta;
import com.example.mercado.db.DbArticulos;
import com.example.mercado.db.DbVenta;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class ProductoVender extends AppCompatActivity implements SearchView.OnQueryTextListener{

    Button btnCarrito;
    RecyclerView recicleProducto;
    AdaptadorProductoVenta adaptador;
    String cantidad;
    int total = 0;
    int totalProducto=0;
    int val,idProd;
    List<ProductoVenta> carroCompra = new ArrayList<>();
    List<ProductoVenta> carroCompra1 = new ArrayList<>();

    TextView txtCodBarraVenta, txtCantidadProduc, txtPrecioArticulo;
    ImageButton btnCodBarraVenta, btnAgregarCarrito;
    String cod_barra;
    SearchView txtBuscarProduxtoVenta;

    Articulos articulos;

    Toolbar toolBarProductoVender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_vender);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        btnCarrito = findViewById(R.id.btnCarritoProducto);
        recicleProducto = findViewById(R.id.recicleProducto);
        txtCodBarraVenta = findViewById(R.id.txtCodBarraVenta);
        txtCantidadProduc = findViewById(R.id.txtCantidadProduc);
        txtPrecioArticulo = findViewById(R.id.txtPrecioArticulo);
        btnCodBarraVenta = findViewById(R.id.btnCodBarraVenta);
        btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito);
        txtBuscarProduxtoVenta = findViewById(R.id.txtBuscarProduxtoVenta);

        toolBarProductoVender = findViewById(R.id.toolBarProductoVender);

        setSupportActionBar(toolBarProductoVender);
        setTitle("Producto Vender");

        txtBuscarProduxtoVenta.setOnQueryTextListener(this);

        txtCodBarraVenta.setInputType(InputType.TYPE_NULL);

        carroCompra1 = (List<ProductoVenta>) getIntent().getSerializableExtra("carritoProducto");

        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                val = Integer.parseInt("0");
            }else {
                val = extras.getInt("val");
            }
        }else{
            val = (int) savedInstanceState.getSerializable("val");
        }

        //Recibe id de cliente carrito
        String idcli = getIntent().getStringExtra("idCliente");

        btnCodBarraVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });

        btnAgregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtCodBarraVenta.getText().toString().isEmpty()||txtCantidadProduc.getText().toString().isEmpty()||txtPrecioArticulo.getText().toString().isEmpty()){
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(ProductoVender.this);
                    dialogo1.setTitle("ADVERTENCIA");
                    dialogo1.setMessage("Escanee un articulo primero");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {

                        }
                    });
                    dialogo1.show();

                }else {
                    String cantidad,precio;

                    cantidad = txtCantidadProduc.getText().toString();
                    precio = txtPrecioArticulo.getText().toString();

                    totalProducto = (Integer.parseInt(precio)*Integer.parseInt(cantidad));

                    if(carroCompra1 != null && !carroCompra1.isEmpty()){
                        carroCompra1.add(new ProductoVenta(txtCodBarraVenta.getText().toString(),txtPrecioArticulo.getText().toString(),txtCantidadProduc.getText().toString(),String.valueOf(totalProducto),idProd));

                        Toast.makeText(ProductoVender.this,"AGREGADO A CARRITO",Toast.LENGTH_SHORT).show();

                    }else if (carroCompra != null && !carroCompra.isEmpty()){
                        carroCompra.add(new ProductoVenta(txtCodBarraVenta.getText().toString(),txtPrecioArticulo.getText().toString(),txtCantidadProduc.getText().toString(),String.valueOf(totalProducto),idProd));

                        Toast.makeText(ProductoVender.this,"AGREGADO A CARRITO",Toast.LENGTH_SHORT).show();

                    }else {
                        carroCompra.add(0,new ProductoVenta(txtCodBarraVenta.getText().toString(),txtPrecioArticulo.getText().toString(),txtCantidadProduc.getText().toString(),String.valueOf(totalProducto),idProd));

                        Toast.makeText(ProductoVender.this,"AGREGADO A CARRITO",Toast.LENGTH_SHORT).show();

                    }

                    vaciar();
                }
            }
        });

        recicleProducto.setLayoutManager(new GridLayoutManager(ProductoVender.this,1));

        DbVenta dbVenta = new DbVenta(ProductoVender.this);


        adaptador = new AdaptadorProductoVenta(ProductoVender.this, btnCarrito,cantidad,(dbVenta.mostrarArticulos()),carroCompra,carroCompra1,val,total,idcli);
        recicleProducto.setAdapter(adaptador);
    }

    public void escanear(){
        IntentIntegrator integrador = new IntentIntegrator(ProductoVender.this);
        integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrador.setPrompt("ESCANEAR CODIGO DE BARRA");
        integrador.setCameraId(0);
        integrador.setBeepEnabled(true);
        integrador.setBarcodeImageEnabled(false);
        integrador.initiateScan();
    }

    public void vaciar(){
        txtCodBarraVenta.setText("");
        txtCantidadProduc.setText("");
        txtPrecioArticulo.setText("");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(ProductoVender.this, "Cancelaste el escaneo", Toast.LENGTH_SHORT).show();
            } else {
                cod_barra = result.getContents();

                DbArticulos dbArticulos = new DbArticulos(ProductoVender.this);
                articulos = dbArticulos.buscarCodBarra(cod_barra);

                if (articulos != null){
                    txtCodBarraVenta.setText(articulos.getNombre());
                    txtPrecioArticulo.setText(articulos.getPrecio());
                    txtCantidadProduc.setText("1");
                    idProd = articulos.getId();
                }else{
                    Toast.makeText(ProductoVender.this, "ESTE COD.BARRA NO ESTA REGISTRADO", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        int cotrl = 0;
        if(carroCompra1 != null && !carroCompra1.isEmpty()){
            new AlertDialog.Builder(this)
                    .setTitle("¿Salir?")
                    .setMessage("¿Estas seguro que deseas salir?, se borrara todos los productos del carrito")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent inicio = new Intent(ProductoVender.this, MainActivity.class);
                            startActivity(inicio);
                            finish();
                        }
                    })
                    .create().show();
            cotrl = 1;
        }else if (cotrl == 0){
            Intent inicio = new Intent(this, MainActivity.class);
            startActivity(inicio);
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adaptador.filtrado(newText);
        return false;
    }
}