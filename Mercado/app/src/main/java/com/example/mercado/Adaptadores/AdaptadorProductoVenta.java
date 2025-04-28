package com.example.mercado.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercado.MainActivity;
import com.example.mercado.R;
import com.example.mercado.VistaVender.CarroCompra;
import com.example.mercado.VistaVender.ProductoVender;
import com.example.mercado.VistaVender.Utilidades.ProductoVenta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdaptadorProductoVenta extends RecyclerView.Adapter<AdaptadorProductoVenta.ProductosViewHolder> {

    Context context;
    Button btnCarrito;

    List<ProductoVenta> listaProductos;
    List<ProductoVenta> carroCompra;
    List<ProductoVenta> carroCompra1;

    List<ProductoVenta> listaProductosBusqueda;

    int total ;
    String cantidad1,precio,nombre;
    int tot;
    int val;
    String idCliente12;

    public AdaptadorProductoVenta(Context context, Button btnCarrito,String cantidad, List<ProductoVenta> listaProductos,
                                  List<ProductoVenta> carroCompra,List<ProductoVenta> carroCompra1,
                                  int val,int total, String idCliente) {
        this.context = context;
        this.btnCarrito = btnCarrito;
        this.cantidad1 = cantidad;
        this.listaProductos = listaProductos;
        this.carroCompra = carroCompra;
        this.carroCompra1 = carroCompra1;
        this.total = total;
        this.val = val;
        this.idCliente12 = idCliente;
        listaProductosBusqueda = new ArrayList<>();
        listaProductosBusqueda.addAll(listaProductos);
    }

    @NonNull
    @Override
    public AdaptadorProductoVenta.ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prod_venta,null,false);

       return new AdaptadorProductoVenta.ProductosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorProductoVenta.ProductosViewHolder holder, @SuppressLint("RecyclerView") int i) {
        holder.viewNombrePro.setText(listaProductos.get(i).getNombreProd());
        holder.txtPreciArticulo.setText(listaProductos.get(i).getPrecio());

        btnCarrito.setOnClickListener((v)-> {

            if(carroCompra != null && !carroCompra.isEmpty()){
                if (val !=1){
                    Intent intent = new Intent(context, CarroCompra.class);
                    intent.putExtra("CarroCompra",(Serializable) carroCompra);
                    intent.putExtra("idClienteRec",idCliente12);
                    context.startActivity(intent);
                }
            }else if (val == 1) {
                Intent intent = new Intent(context, CarroCompra.class);
                intent.putExtra("CarroCompra",(Serializable) carroCompra1);
                intent.putExtra("idClienteRec",idCliente12);
                context.startActivity(intent);
            }else {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setTitle("ADVERTENCIA");
                dialogo1.setMessage("Agrege un articulo antes de ir al carrito.");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();
            }
        });

    }

    public void filtrado(String txtBuscarProduxtoVenta){
        int longitud = txtBuscarProduxtoVenta.length();

        if (longitud == 0){
            listaProductos.clear();
            listaProductos.addAll(listaProductosBusqueda);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
                List<ProductoVenta> coleccion = listaProductos.stream()
                        .filter(i -> i.getNombreProd().toLowerCase().contains(txtBuscarProduxtoVenta.toLowerCase()))
                        .collect(Collectors.toList());

                listaProductos.clear();
                listaProductos.addAll(coleccion);

            }else {
                for (ProductoVenta c:listaProductos){
                    if (c.getNombreProd().toLowerCase().contains(txtBuscarProduxtoVenta.toLowerCase())){
                        listaProductos.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ProductosViewHolder  extends RecyclerView.ViewHolder{

        TextView viewNombrePro,txtPreciArticulo,txtCantidadArt;
        Button btnAgregarPro;
        ImageButton btnAgregarCarrito;

        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCantidadArt = itemView.findViewById(R.id.txtCantidadArt);
            viewNombrePro = itemView.findViewById(R.id.viewNombrePro);
            txtPreciArticulo = itemView.findViewById(R.id.txtPreciArticulo);
            btnAgregarPro = itemView.findViewById(R.id.btnAgregarPro);
            btnAgregarCarrito = itemView.findViewById(R.id.btnAgregarCarrito);

            btnAgregarPro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nombre = viewNombrePro.getText().toString();
                    cantidad1 = txtCantidadArt.getText().toString();
                    precio = txtPreciArticulo.getText().toString();
                    int idPro = listaProductos.get(getAdapterPosition()).getIdProducto();


                    tot =Integer.parseInt(cantidad1)*Integer.parseInt(precio);

                    if (val == 1){
                        carroCompra1.add(new ProductoVenta(nombre,precio,cantidad1,Integer.toString(tot),idPro));
                    }else if (carroCompra.isEmpty()){
                        carroCompra.add(new ProductoVenta(nombre,precio,cantidad1,Integer.toString(tot),idPro));
                    }else {
                        carroCompra.add(new ProductoVenta(nombre,precio,cantidad1,Integer.toString(tot),idPro));
                    }

                    //Toast.makeText(context,"Total: "+tot,Toast.LENGTH_LONG).show();
                    Toast.makeText(itemView.getContext(),"AGREGADO A CARRITO",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


}
