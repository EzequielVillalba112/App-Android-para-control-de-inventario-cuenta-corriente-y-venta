package com.example.mercado;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mercado.Actividades.EditarProducto;
import com.example.mercado.Adaptadores.ListaArticulosAdapter;
import com.example.mercado.Entidades.Articulos;
import com.example.mercado.db.DbArticulos;

import java.util.ArrayList;


public class ListarProductos extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView listaArticulo;
    ArrayList<Articulos> listaAraryProducto;
    ArrayList<Articulos> listaArrayProductoDesc;
    RadioButton rdbDesactivado, rdbActivado;
    Button btnListar;
    SearchView txtBuscar;
    ListaArticulosAdapter adapter;
    ImageButton btnVolvelProdu;

    private Toolbar toolbarListPro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_productos);

        listaArticulo = findViewById(R.id.listaArticulo);
        rdbDesactivado = findViewById(R.id.rdbDesactivado);
        rdbActivado = findViewById(R.id.rdbActivado);
        btnListar = findViewById(R.id.btnListar);
        txtBuscar = findViewById(R.id.txtBuscar);
        toolbarListPro = findViewById(R.id.toolbarListPro);

        setSupportActionBar(toolbarListPro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Lista de Productos");


        txtBuscar.setOnQueryTextListener(this);

        Listar();


        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdbDesactivado.isChecked()==true){

                    DbArticulos dbArticulos = new DbArticulos(ListarProductos.this);

                    listaAraryProducto = new ArrayList<>();

                    listaArticulo.setLayoutManager(new LinearLayoutManager(ListarProductos.this));
                    adapter= new ListaArticulosAdapter(dbArticulos.mostrarArticulosDesc());
                    listaArticulo.setAdapter(adapter);

                    Toast.makeText(ListarProductos.this, "Productos Desactivados",Toast.LENGTH_SHORT).show();



                }else if (rdbActivado.isChecked()==true){
                    DbArticulos dbArticulos = new DbArticulos(ListarProductos.this);

                    listaArrayProductoDesc = new ArrayList<>();

                    listaArticulo.setLayoutManager(new LinearLayoutManager(ListarProductos.this));
                    adapter = new ListaArticulosAdapter(dbArticulos.mostrarArticulos());
                    listaArticulo.setAdapter(adapter);


                    Toast.makeText(ListarProductos.this, "Productos Activados",Toast.LENGTH_SHORT).show();


                }
            }
        });
    }

    public void Listar(){
            DbArticulos dbArticulos = new DbArticulos(ListarProductos.this);
            listaArrayProductoDesc = new ArrayList<>();
            listaArticulo.setLayoutManager(new LinearLayoutManager(ListarProductos.this));
            adapter = new ListaArticulosAdapter(dbArticulos.mostrarArticulos());
            listaArticulo.setAdapter(adapter);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }

    public void onBackPressed(){

    }

}