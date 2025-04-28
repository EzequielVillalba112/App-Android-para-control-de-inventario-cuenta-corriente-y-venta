package com.example.mercado.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercado.R;
import com.example.mercado.VistaVender.Utilidades.VentaCAB;

import java.util.ArrayList;

public class AdaptadorDetalleVenta extends RecyclerView.Adapter<AdaptadorDetalleVenta.DetalleViewHolder> {
    ArrayList<VentaCAB> listaDetalle;

    public AdaptadorDetalleVenta(ArrayList<VentaCAB> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    @NonNull
    @Override
    public AdaptadorDetalleVenta.DetalleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detalle_venta, null);

        return new DetalleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDetalleVenta.DetalleViewHolder holder, int position) {

        holder.tvCantidad.setText(listaDetalle.get(position).getCantidad());
        holder.tvIdProducto.setText(listaDetalle.get(position).getNombre());
        holder.tvPrecio.setText(listaDetalle.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return listaDetalle.size();
    }

    public class DetalleViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdProducto,tvCantidad,tvPrecio;



        public DetalleViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdProducto = itemView.findViewById(R.id.tvIdProducto);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);

        }
    }
}
