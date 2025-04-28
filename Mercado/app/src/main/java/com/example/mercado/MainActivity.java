package com.example.mercado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercado.Usuario.Usuario;
import com.example.mercado.VistaVender.ProductoVender;
import com.example.mercado.db.DbHelper;

public class MainActivity extends AppCompatActivity {

    private ActionBar actionBar;

    private Toolbar toolbar;
    TextView usuario;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        String user = preferences.getString("usuario_nom", null);

        setSupportActionBar(toolbar);

        if (user != null){
            usuario.setText(user);
        }

    }

    private void  init(){
        usuario = findViewById(R.id.usuario);
        preferences = getSharedPreferences("usuario", MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar);
    }

    private void cerrarSecion(){
        preferences.edit().clear().apply();
        Intent login = new Intent(MainActivity.this, Login.class);
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(login);
    }

    public void Articulos(View view){
        Intent Articulos = new Intent(this, Producto.class);
        startActivity(Articulos);

    }

    public void Clientes(View view){
        Intent Clientes = new Intent(this, Cliente.class);
        startActivity(Clientes);
    }
    public  void Vender(View view){
        Intent Vender = new Intent(this, ProductoVender.class);
        startActivity(Vender);
    }

    public void Registro(View view){
        Intent listVentas = new Intent(this,Registros.class);
        startActivity(listVentas);
    }

    public void Usuario(View view){
        Intent user = new Intent(this, Usuario.class);
        startActivity(user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.ItemSalirAPP){
           finish();
           System.exit(0);
        }else if(id == R.id.itemCerrarSecion){
            cerrarSecion();
        }

        return super.onOptionsItemSelected(item);
    }
}