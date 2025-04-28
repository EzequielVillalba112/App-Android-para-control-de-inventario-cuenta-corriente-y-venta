package com.example.mercado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mercado.Adaptadores.AdaptadorDetalleVenta;
import com.example.mercado.db.DbVenta;

public class DetalleVenta extends AppCompatActivity {

    RecyclerView detalleVenta;
    AdaptadorDetalleVenta adapter;

    Toolbar toolbarDetalleVenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_venta);

        detalleVenta = findViewById(R.id.detalleVenta);
        toolbarDetalleVenta = findViewById(R.id.toolbarDetalleVenta);

        setSupportActionBar(toolbarDetalleVenta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detalle de la veta");


        String id = getIntent().getStringExtra("idVenta");

        DbVenta dbVenta = new DbVenta(DetalleVenta.this);

        detalleVenta.setLayoutManager(new GridLayoutManager(DetalleVenta.this,1));
        adapter = new AdaptadorDetalleVenta(dbVenta.detalleVentas(Integer.parseInt(id)));

        detalleVenta.setAdapter(adapter);

    }


}