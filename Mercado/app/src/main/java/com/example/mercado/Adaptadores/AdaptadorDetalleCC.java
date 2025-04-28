package com.example.mercado.Adaptadores;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercado.R;
import com.example.mercado.VistaVender.Utilidades.CuentaCorrienteList;
import com.example.mercado.db.DbVenta;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AdaptadorDetalleCC extends RecyclerView.Adapter<AdaptadorDetalleCC.CCDetalleViewHolder> {

    ArrayList<CuentaCorrienteList> listDetalleClient;
    TextView txtTotalCuentaCorriente;
    Context context;


    public AdaptadorDetalleCC(ArrayList<CuentaCorrienteList> listDetalleClient, TextView txtTotalCuentaCorriente,Context context) {
        this.listDetalleClient = listDetalleClient;
        this.txtTotalCuentaCorriente = txtTotalCuentaCorriente;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptadorDetalleCC.CCDetalleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuenta_corriente,null);

        return new CCDetalleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDetalleCC.CCDetalleViewHolder holder, int position) {

        holder.txtFechaCC.setText(listDetalleClient.get(position).getFecha());
        holder.txtNomProdCC.setText(listDetalleClient.get(position).getNomPro());
        holder.txtCantidadCC.setText(listDetalleClient.get(position).getCantidad());
        holder.txtTotalCC.setText(listDetalleClient.get(position).getTotal());

        funcionTotal();
    }

    @Override
    public int getItemCount() {
        return listDetalleClient.size();
    }

    public class CCDetalleViewHolder extends RecyclerView.ViewHolder {

        TextView txtFechaCC,txtNomProdCC,txtCantidadCC,txtTotalCC,txtTotalCuentaCorriente;
        ImageButton btnEliminarPro;

        public CCDetalleViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFechaCC = itemView.findViewById(R.id.txtFechaCC);
            txtNomProdCC = itemView.findViewById(R.id.txtNomProdCC);
            txtCantidadCC = itemView.findViewById(R.id.txtCantidadCC);
            txtTotalCC = itemView.findViewById(R.id.txtTotalCC);
            txtTotalCuentaCorriente = itemView.findViewById(R.id.txtTotalCuentaCorriente);
            btnEliminarPro = itemView.findViewById(R.id.btnEliminarPro);

            btnEliminarPro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = listDetalleClient.get(getAdapterPosition()).getId_cuenta_cc();

                    AlertDialog.Builder builder =  new AlertDialog.Builder(context);
                    builder.setTitle("ELIMINA")
                            .setMessage("DESEA ELIMINAR ESTE ARTICULO?")
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DbVenta dbVenta = new DbVenta(context);
                                    long art = dbVenta.eliminarProdCC(id);

                                    if(art == 1){
                                        listDetalleClient.remove(getAdapterPosition());
                                        notifyItemChanged(getAdapterPosition());

                                        Toast.makeText(itemView.getContext(), "PRODUCTO ELIMINADO",Toast.LENGTH_LONG).show();
                                        funcionTotal();
                                    }else {
                                        Toast.makeText(itemView.getContext(), "ERRO AL ELIMINAR",Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int which) {
                                    Toast.makeText(itemView.getContext(), "NO SE ELIMINO EL PRODUCTO",Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            }).create().show();
                }
            });
        }
    }

    public void funcionTotal(){
        AtomicInteger totalProd = new AtomicInteger();

        listDetalleClient.forEach((carroCompra)->{
            int tot = Integer.parseInt(carroCompra.getTotal());
            totalProd.set(totalProd.get() + tot);
        });

        txtTotalCuentaCorriente.setText(Integer.toString(totalProd.get()));
    }
}
