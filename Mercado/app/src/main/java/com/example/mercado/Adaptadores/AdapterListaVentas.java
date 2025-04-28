package com.example.mercado.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercado.DetalleVenta;
import com.example.mercado.Entidades.Articulos;
import com.example.mercado.R;
import com.example.mercado.VerProducto;
import com.example.mercado.VistaVender.Utilidades.VentaCAB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterListaVentas extends RecyclerView.Adapter<AdapterListaVentas.VentasViewHolder> {

    ArrayList<VentaCAB> listaVenta;
    ArrayList<VentaCAB> listaVentaBusqueda;

    public AdapterListaVentas(ArrayList<VentaCAB> listaVenta) {
        this.listaVenta = listaVenta;
        listaVentaBusqueda = new ArrayList<>();
        listaVentaBusqueda.addAll(listaVenta);
    }

    @NonNull
    @Override
    public AdapterListaVentas.VentasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_venta,null);

        return new VentasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListaVentas.VentasViewHolder holder, int position) {

        holder.txtIDfactura.setText(listaVenta.get(position).getId_venta());
        holder.txtFechaLista.setText(listaVenta.get(position).getFecha());
        holder.txtTotalLista.setText(listaVenta.get(position).getTotal());

    }

    public void filtrado(String txtBuscarVenta){
        int longitud = txtBuscarVenta.length();

        if (longitud == 0){
            listaVenta.clear();
            listaVenta.addAll(listaVentaBusqueda);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
                List<VentaCAB> coleccion = listaVenta.stream()
                        .filter(i ->i.getFecha().toLowerCase().contains(txtBuscarVenta.toLowerCase()))
                        .collect(Collectors.toList());

                listaVenta.clear();
                listaVenta.addAll(coleccion);

            }else {
                for (VentaCAB c:listaVenta){
                    if(c.getFecha().toLowerCase().contains(txtBuscarVenta.toLowerCase())){
                        listaVenta.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return listaVenta.size();
    }

    public class VentasViewHolder extends RecyclerView.ViewHolder {

        TextView txtIDfactura,txtFechaLista,txtTotalLista;
        Button btnDetalleList;

        public VentasViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIDfactura = itemView.findViewById(R.id.txtIDfactura);
            txtFechaLista = itemView.findViewById(R.id.txtFechaLista);
            txtTotalLista = itemView.findViewById(R.id.txtTotalLista);
            btnDetalleList = itemView.findViewById(R.id.btnDetalleList);

            btnDetalleList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String idVenta = listaVenta.get(getAdapterPosition()).getId_venta();

                    Context context = v.getContext();
                    Intent intent= new Intent(context, DetalleVenta.class);
                    intent.putExtra("idVenta", idVenta);
                    context.startActivity(intent);
                }
            });
        }
    }
}
