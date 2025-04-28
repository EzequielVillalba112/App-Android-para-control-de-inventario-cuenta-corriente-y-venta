package com.example.mercado.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercado.R;
import com.example.mercado.VistaVender.Utilidades.Devolucion;

import java.util.ArrayList;

public class AdaptadorDevoluciones  extends RecyclerView.Adapter<AdaptadorDevoluciones.DevolucionViewHolder> {

    ArrayList<Devolucion> listaDevolucion;

    public AdaptadorDevoluciones(ArrayList<Devolucion> listaDevolucion) {
        this.listaDevolucion = listaDevolucion;
    }

    @NonNull
    @Override
    public AdaptadorDevoluciones.DevolucionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_devolucion,null, false);

        return new DevolucionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDevoluciones.DevolucionViewHolder holder, int position) {
            holder.txtFechaDevo.setText(listaDevolucion.get(position).getFecha());
            holder.txtTotalDevo.setText(listaDevolucion.get(position).getTotal_pro());
            holder.txtNomPro.setText(listaDevolucion.get(position).getNom_pro());
            holder.txtCantidad.setText(listaDevolucion.get(position).getCantidad_pro());
    }

    @Override
    public int getItemCount() {
        return listaDevolucion.size();
    }

    public class DevolucionViewHolder extends RecyclerView.ViewHolder {
        TextView txtFechaDevo,txtTotalDevo,txtNomPro,txtCantidad;


        public DevolucionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFechaDevo = itemView.findViewById(R.id.txtFechaDevo);
            txtTotalDevo = itemView.findViewById(R.id.txtTotalDevo);
            txtNomPro = itemView.findViewById(R.id.txtNomPro);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
        }
    }
}
