package com.example.mercado.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercado.R;
import com.example.mercado.VistaVender.Utilidades.ProductoVenta;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AdaptadorCarroCompra extends RecyclerView.Adapter<AdaptadorCarroCompra.CarroCompra> {

    Context context;
    TextView txtTotal;
    Button btnProductoVolver;
    List<ProductoVenta> carroCompra;
    ImageButton btnAgregarClienrte;
    int idCliente12;

    public AdaptadorCarroCompra(Context context, List<ProductoVenta> carroCompra, TextView txtTotal, Button btnProductoVolver) {
        this.context = context;
        this.carroCompra = carroCompra;
        this.txtTotal = txtTotal;
        this.btnProductoVolver = btnProductoVolver;
    }

    @NonNull
    @Override
    public AdaptadorCarroCompra.CarroCompra onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carro_compra,null,false);

       return new AdaptadorCarroCompra.CarroCompra(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarroCompra holder, int i) {
        holder.tvProductoVenta.setText(carroCompra.get(i).getNombreProd());
        holder.txtPrecioVenta.setText(carroCompra.get(i).getPrecio());
        holder.txtCantidadVenta.setText(carroCompra.get(i).getCantidad());
        holder.txtTotalProducto.setText(carroCompra.get(i).getTotal());

        funcionTotal();


        holder.btnEliminarDeCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carroCompra.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

                funcionTotal();
            }
        });


        btnProductoVolver.setOnClickListener((v)-> {
            if(carroCompra != null && !carroCompra.isEmpty()){
                Intent intent = new Intent(context, com.example.mercado.VistaVender.ProductoVender.class);
                intent.putExtra("carritoProducto",(Serializable) carroCompra);
                intent.putExtra("idCliente",String.valueOf(idCliente12));
                intent.putExtra("val", 1);
                context.startActivity(intent);
            }else {
                Intent intent = new Intent(context, com.example.mercado.VistaVender.ProductoVender.class);
                intent.putExtra("carritoProducto",(Serializable) carroCompra);
                intent.putExtra("idCliente",String.valueOf(idCliente12));
                intent.putExtra("val", 0);
                context.startActivity(intent);
            }

        });
    }


    @Override
    public int getItemCount() {
        return carroCompra.size();
    }

    public class CarroCompra extends RecyclerView.ViewHolder{
        
        TextView tvProductoVenta,txtCantidadVenta,txtTotalProducto,txtPrecioVenta,txtTotal;
        ImageButton btnEliminarDeCarro,btnRemove;
        Button btnProductoVolver;

        public CarroCompra(@NonNull View itemView) {
            super(itemView);
            tvProductoVenta = itemView.findViewById(R.id.tvProductoVenta);
            txtCantidadVenta = itemView.findViewById(R.id.txtCantidadVenta);
            txtPrecioVenta = itemView.findViewById(R.id.txtPrecioVenta);
            txtTotalProducto = itemView.findViewById(R.id.txtTotalProducto);
            btnEliminarDeCarro =  itemView.findViewById(R.id.btnEliminarDeCarro);
            btnProductoVolver = itemView.findViewById(R.id.btnProductoVolver);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }


    }

    public void funcionTotal(){

        AtomicInteger totalProd = new AtomicInteger();

        carroCompra.forEach((carroCompra)->{
            int tot = Integer.parseInt(carroCompra.getTotal());
           totalProd.set(totalProd.get() + tot);
        });

        txtTotal.setText(Integer.toString(totalProd.get()));

    }

    public  void idClient(int id){
        idCliente12 = id;
    }

}
