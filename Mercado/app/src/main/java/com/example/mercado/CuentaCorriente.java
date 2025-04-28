package com.example.mercado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercado.Adaptadores.AdaptadorDetalleCC;
import com.example.mercado.db.DbVenta;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CuentaCorriente extends AppCompatActivity {

    RecyclerView lsitaCC;
    AdaptadorDetalleCC adapter;
    TextView txtTotalCuentaCorriente,txtEntrega,txtVuelto;
    Button btnCobrar;
    String id;

    Toolbar toolbarDetalleCC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_corriente);

        lsitaCC = findViewById(R.id.lsitaCC);
        txtTotalCuentaCorriente = findViewById(R.id.txtTotalCuentaCorriente);
        txtEntrega = findViewById(R.id.txtEntrega);
        txtVuelto = findViewById(R.id.txtVuelto);
        btnCobrar = findViewById(R.id.btnCobrar);
        toolbarDetalleCC = findViewById(R.id.toolbarDetalleCC);

        setSupportActionBar(toolbarDetalleCC);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Cuenta Corriente");



        id = getIntent().getStringExtra("idCliente");

        DbVenta dbVenta = new DbVenta(CuentaCorriente.this);
        lsitaCC.setLayoutManager(new GridLayoutManager(CuentaCorriente.this,1));
        adapter = new AdaptadorDetalleCC(dbVenta.detalleCC(Integer.parseInt(id)),txtTotalCuentaCorriente,CuentaCorriente.this);

        lsitaCC.setAdapter(adapter);

        Calcular();

        btnCobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calcular();
            }
        });
    }

    public int Calcular(){
        int total = 0,val1 = 0, val2 = 0;

        if (txtEntrega.getText().toString().isEmpty()||txtTotalCuentaCorriente.getText().toString().isEmpty()){

        }else {
            val1 = Integer.parseInt(txtTotalCuentaCorriente.getText().toString());
            val2 = Integer.parseInt(txtEntrega.getText().toString());

            total = val1 - val2;

            if (val1 > val2){
                int finalTotal = total;
                AlertDialog dialog = new AlertDialog
                        .Builder(CuentaCorriente.this)
                        .setPositiveButton("SALDAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                String fechaHora1 = simpleDateFormat.format(new Date());

                                DbVenta dbVenta = new DbVenta(CuentaCorriente.this);
                                dbVenta.pagarCC(Integer.parseInt(id));

                                long saldo = dbVenta.saldoCC(id,fechaHora1,String.valueOf(finalTotal));

                                if (saldo > 0){
                                    Toast.makeText(CuentaCorriente.this, "saldado",Toast.LENGTH_LONG).show();
                                }
                                Listar();
                                txtEntrega.setText("");
                            }
                        })

                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(CuentaCorriente.this, "NO SE SALDO LA CUENTA",Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        }).setTitle("CONFIRMAR")
                        .setMessage("¿Desea saldar esta cuenta con un saldo de : $"+String.valueOf(finalTotal)+"?")
                        .create();
                dialog.show();



            }else{
                total = val1 - val2;

                int finalTotal = total;
                AlertDialog dialog = new AlertDialog
                        .Builder(CuentaCorriente.this)
                        .setPositiveButton("SALDAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DbVenta dbVenta = new DbVenta(CuentaCorriente.this);
                                long paga = dbVenta.pagarCC(Integer.parseInt(id));

                                if (paga == 0){
                                    Toast.makeText(CuentaCorriente.this, "saldado",Toast.LENGTH_LONG).show();
                                }
                                Listar();
                                txtVuelto.setText(String.valueOf(finalTotal));
                                txtTotalCuentaCorriente.setText("");
                                txtEntrega.setText("");
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(CuentaCorriente.this, "NO SE SALDO LA CUENTA",Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        }).setTitle("CONFIRMAR")
                        .setMessage("¿Desea saldar esta cuenta?")
                        .create();
                dialog.show();
            }
        }
       return total;
    }

    public void Listar(){
        DbVenta dbVenta = new DbVenta(CuentaCorriente.this);
        lsitaCC.setLayoutManager(new GridLayoutManager(CuentaCorriente.this,1));
        adapter = new AdaptadorDetalleCC(dbVenta.detalleCC(Integer.parseInt(id)),txtTotalCuentaCorriente,CuentaCorriente.this);

        lsitaCC.setAdapter(adapter);
    }

}