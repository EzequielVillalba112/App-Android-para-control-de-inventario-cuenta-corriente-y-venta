package com.example.mercado.VistaVender;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercado.Adaptadores.AdaptadorCarroCompra;
import com.example.mercado.Adaptadores.AutoCompletClienteAdapter;
import com.example.mercado.Entidades.Clientes;
import com.example.mercado.R;
import com.example.mercado.Utilidades.Utilidades;
import com.example.mercado.VistaVender.Utilidades.ClienteItem;
import com.example.mercado.VistaVender.Utilidades.ProductoVenta;
import com.example.mercado.VistaVender.Utilidades.VentaCAB;
import com.example.mercado.db.DbCliente;
import com.example.mercado.db.DbHelper;
import com.example.mercado.db.DbVenta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class CarroCompra extends AppCompatActivity {

    RecyclerView reclirCarrito;
    List<ProductoVenta> carroCompra = new ArrayList<>();

    List<ClienteItem> listaCliente = new ArrayList<>();

    TextView txtTotal;
    TextView txtFecha;
    TextView txtPru, txtApe, txtLimit;
    Button btnProductoVolver, btnVender, btnDevolucion;
    ImageButton btnRemove;
    AdaptadorCarroCompra adaptador;
    AutoCompleteTextView txtAutoCompl;

    String nom, ape, lim;
    String fechaHora;

    int idCli;

    VentaCAB ventaCAB;
    Clientes clientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro_compra);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        reclirCarrito = findViewById(R.id.reclirCarrito);
        reclirCarrito.setLayoutManager(new GridLayoutManager(CarroCompra.this,1));
        btnProductoVolver = findViewById(R.id.btnProductoVolver);
        btnVender = findViewById(R.id.btnVender);
        txtPru = findViewById(R.id.txtPru);
        txtApe = findViewById(R.id.txtApe);
        txtLimit = findViewById(R.id.txtLimit);
        txtFecha = findViewById(R.id.txtFecha);
        txtAutoCompl = findViewById(R.id.txtAutoCompl);
        btnRemove = findViewById(R.id.btnRemove);
        btnDevolucion = findViewById(R.id.btnDevolucion);
        //Array resivido de ProductoVenta
        carroCompra = (List<ProductoVenta>) getIntent().getSerializableExtra("CarroCompra");

        //Fechas
        Date d = new Date();
        CharSequence s = DateFormat.format("d/MM/yyyy", d.getTime());
        txtFecha.setText(s);

        //Recibir cliente
        String id = getIntent().getStringExtra("idClienteRec");

        if (id != null){
            DbCliente dbCliente = new DbCliente(CarroCompra.this);
            clientes = dbCliente.VerClientes(Integer.parseInt(id));
        }

        if (clientes != null){
            txtPru.setText(clientes.getNombre_cliente());
            txtApe.setText(clientes.getApellido_cliente());
            txtLimit.setText(clientes.getLimite());
            idCli = clientes.getId_cliente();
        }

        //Borrar textFild cliente
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idCli > 0){
                    txtPru.setText("");
                    txtApe.setText("");
                    txtLimit.setText("");
                    txtAutoCompl.setText("");
                    idCli = 0;
                    adaptador.idClient(idCli);
                }else {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(CarroCompra.this);
                    dialogo1.setTitle("ADVERTENCIA");
                    dialogo1.setMessage("No hay cliente para eliminar");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {

                        }
                    });
                    dialogo1.show();
                }
            }
        });

        //Texto Predictivo
        buscadorAutoComp();

        AutoCompletClienteAdapter adapterCliente = new AutoCompletClienteAdapter(this,listaCliente);

        txtAutoCompl.setAdapter(adapterCliente);
        txtAutoCompl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idCli = 0;
                Object item = parent.getItemAtPosition(position);
                if (item instanceof  ClienteItem){
                    ClienteItem itn = (ClienteItem) item;
                     nom = itn.getNomClient();
                     ape = itn.getApeClient();
                     lim = itn.getLimiteClien();
                     idCli = Integer.parseInt(itn.getIdCliente());
                    txtPru.setText(nom);
                    txtApe.setText(ape);
                    txtLimit.setText(lim);

                    adaptador.idClient(idCli);
                }
            }
        });

        //Carrito List
        txtTotal = findViewById(R.id.txtTotal);
        adaptador = new AdaptadorCarroCompra(CarroCompra.this,carroCompra,txtTotal,btnProductoVolver);
        reclirCarrito.setAdapter(adaptador);
        adaptador.idClient(idCli);

        //Cargar datos a registro de venta
        btnVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valArray() == 1){
                    new AlertDialog.Builder(CarroCompra.this).setTitle("ERROR")
                            .setMessage("NO HAY PRODUCTOS EN EL CARRITO")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                }
                            })
                            .create().show();
                }else {
                    int limit = 0,total = 0;
                    if (!txtLimit.getText().toString().equals("")){
                        limit = Integer.parseInt(txtLimit.getText().toString());
                        total = Integer.parseInt(txtTotal.getText().toString());
                    }

                    if (total > limit){
                        new AlertDialog.Builder(CarroCompra.this).setTitle("ADVERTENCIA")
                                .setMessage("EL TOTAL ES MAYOR AL LIMITE CONSEDIDO. ¿DESEA PROCEDER CON LA VENTA?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        String id_venta="";

                                        //Total carrito para cabezera
                                        AtomicInteger totalProd = new AtomicInteger();
                                        carroCompra.forEach((carroCompra)->{
                                            int tot = Integer.parseInt(carroCompra.getTotal());
                                            totalProd.set(totalProd.get() + tot);
                                        });

                                        DbVenta dbVenta = new DbVenta(CarroCompra.this);

                                        if (idCli == 0){
                                            long valid = dbVenta.agregarVentaCab(String.valueOf(s),String.valueOf(totalProd));

                                            if (valid > 0){
                                                ventaCAB = dbVenta.ultimoidventa();
                                                id_venta = String.valueOf(ventaCAB.getId_venta());
                                            }

                                        }

                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                        fechaHora = simpleDateFormat.format(new Date());

                                        //RECORRE ARRAYLIST
                                        String finalId_venta = id_venta;
                                        carroCompra.forEach((carroCompra)->{

                                            if (idCli > 0){
                                                dbVenta.cuentaCorriente(String.valueOf(carroCompra.getIdProducto()), String.valueOf(idCli),carroCompra.getPrecio(),
                                                        carroCompra.getCantidad(),String.valueOf(totalProd),fechaHora);
                                            }else {
                                                dbVenta.detalleVenta(finalId_venta,String.valueOf(carroCompra.getIdProducto()),carroCompra.getPrecio(),carroCompra.getCantidad(),idCli);
                                            }

                                        });
                                        carroCompra.clear();
                                        adaptador.notifyDataSetChanged();
                                        reclirCarrito.setAdapter(adaptador);
                                        vaciar();
                                        Toast.makeText(CarroCompra.this, "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(CarroCompra.this, com.example.mercado.VistaVender.ProductoVender.class);
                                        CarroCompra.this.startActivity(intent);

                                    }
                                })
                                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create().show();
                    }else {

                        if (limit > 0){
                            DbVenta dbVenta = new DbVenta(CarroCompra.this);
                            int totalCarrito = Integer.parseInt(txtTotal.getText().toString());

                            limit = limit - totalCarrito;

                            dbVenta.limite(String.valueOf(limit), String.valueOf(idCli));

                        }

                        String id_venta="";

                        //Total carrito para cabezera
                        AtomicInteger totalProd = new AtomicInteger();
                        carroCompra.forEach((carroCompra)->{
                            int tot = Integer.parseInt(carroCompra.getTotal());
                            totalProd.set(totalProd.get() + tot);
                        });

                        DbVenta dbVenta = new DbVenta(CarroCompra.this);

                        if (idCli == 0){
                            long valid = dbVenta.agregarVentaCab(String.valueOf(s),String.valueOf(totalProd));

                            if (valid > 0){
                                ventaCAB = dbVenta.ultimoidventa();
                                id_venta = String.valueOf(ventaCAB.getId_venta());
                            }

                        }

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        fechaHora = simpleDateFormat.format(new Date());

                        String finalId_venta = id_venta;
                        carroCompra.forEach((carroCompra)->{

                            if (idCli > 0){
                                dbVenta.cuentaCorriente(String.valueOf(carroCompra.getIdProducto()), String.valueOf(idCli),carroCompra.getPrecio(),
                                        carroCompra.getCantidad(),String.valueOf(totalProd),fechaHora);
                            }else {
                                dbVenta.detalleVenta(finalId_venta,String.valueOf(carroCompra.getIdProducto()),carroCompra.getPrecio(),carroCompra.getCantidad(),idCli);
                            }

                        });
                        carroCompra.clear();
                        adaptador.notifyDataSetChanged();
                        reclirCarrito.setAdapter(adaptador);
                        vaciar();
                        Toast.makeText(CarroCompra.this, "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CarroCompra.this, com.example.mercado.VistaVender.ProductoVender.class);
                        CarroCompra.this.startActivity(intent);

                    }

                }
            }
        });

        //DEVOLUCION
        btnDevolucion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valArray() == 1){
                    new AlertDialog.Builder(CarroCompra.this).setTitle("ERROR")
                            .setMessage("NO HAY PRODUCTOS EN EL CARRITO")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                }
                            })
                            .create().show();
                }else {
                    new AlertDialog.Builder(CarroCompra.this)
                            .setTitle("¿DEVOLUCIÓN?")
                            .setMessage("¿Estas seguro que deseas realizar la devolución?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    DbVenta dbVenta = new DbVenta(CarroCompra.this);

                                    //Total carrito para cabezera
                                    AtomicInteger totalProd = new AtomicInteger();
                                    carroCompra.forEach((carroCompra)->{
                                        int tot = Integer.parseInt(carroCompra.getTotal());
                                        totalProd.set(totalProd.get() + tot);
                                    });

                                    if (idCli == 0){
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                        String fechaHora1 = simpleDateFormat.format(new Date());

                                        carroCompra.forEach((carroCompra)-> {
                                            dbVenta.devolucion(String.valueOf(carroCompra.getIdProducto()), String.valueOf(carroCompra.getCantidad()),
                                                    String.valueOf(carroCompra.getPrecio()), String.valueOf(totalProd), fechaHora1);
                                        });

                                        carroCompra.clear();
                                        adaptador.notifyDataSetChanged();
                                        reclirCarrito.setAdapter(adaptador);
                                        vaciar();
                                        Toast.makeText(CarroCompra.this, "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(CarroCompra.this, com.example.mercado.VistaVender.ProductoVender.class);
                                        CarroCompra.this.startActivity(intent);

                                    }else{
                                        new AlertDialog.Builder(CarroCompra.this).setTitle("ERROR")
                                                .setMessage("LA DEVOLUCIÓN DE CUENTA CORRIENTE NO SE REALIZA POR ESTE MEDIO")
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                    }
                                                })
                                                .create().show();
                                    }
                                }
                            })
                            .create().show();
                }
                }
        });
    }

    public void onBackPressed(){

    }

    private void buscadorAutoComp(){
        DbHelper dbHelper = new DbHelper(CarroCompra.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ClienteItem cliente;
        Cursor cursorCliente;

        cursorCliente = db.rawQuery("SELECT id_cliente,nom_cliente,apellido_cliente,limite FROM " + Utilidades.TABLA_CLIENTE+" WHERE estado_cliente = 1", null);

        if (cursorCliente.moveToFirst()){
            do {
                cliente = new ClienteItem();
                cliente.setIdCliente(cursorCliente.getString(0));
                cliente.setNomClient(cursorCliente.getString(1));
                cliente.setApeClient(cursorCliente.getString(2));
                cliente.setLimiteClien(cursorCliente.getString(3));

                listaCliente.add(cliente);
            }while (cursorCliente.moveToNext());
        }
        cursorCliente.close();

    }

    public void vaciar(){
       txtAutoCompl.setText("");
       txtPru.setText("");
       txtApe.setText("");
       txtLimit.setText("");
       txtTotal.setText("");
    }

    public int valArray(){
        int val = 0;
        val = Integer.parseInt(txtTotal.getText().toString());

        if ( val == 0){
            carroCompra.clear();
            val = 1;
        }
        return val;
    }
}