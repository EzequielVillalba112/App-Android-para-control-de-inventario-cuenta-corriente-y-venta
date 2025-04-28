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

import com.example.mercado.Entidades.Clientes;
import com.example.mercado.R;
import com.example.mercado.VerCliente;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListarClienteAdapter extends RecyclerView.Adapter<ListarClienteAdapter.ClienteViewHolder> {

   ArrayList<Clientes> listaCliente;
   ArrayList<Clientes> listaClienteBusqueda;
   Clientes cliente = new Clientes();

   public ListarClienteAdapter(ArrayList<Clientes> listaCliente){
       this.listaCliente = listaCliente;
       listaClienteBusqueda = new ArrayList<>();
       listaClienteBusqueda.addAll(listaCliente);
   }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_cliente, null,false);

      return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {

       holder.viewNombre.setText(listaCliente.get(position).getNombre_cliente());
       holder.viewApellido.setText(listaCliente.get(position).getApellido_cliente());

    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();

        if(longitud == 0){
            listaCliente.clear();
            listaCliente.addAll(listaClienteBusqueda);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
                List<Clientes> coleccion = listaCliente.stream()
                        .filter(i ->i.getNombre_cliente().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());

                listaCliente.clear();
                listaCliente.addAll(coleccion);
            }else{
                for (Clientes c:listaCliente){
                    if(c.getNombre_cliente().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaCliente.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaCliente.size();
    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder {

       TextView viewNombre,viewApellido;
       Button btnDetalleCliente;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewApellido = itemView.findViewById(R.id.viewPrecioPro);
            btnDetalleCliente = itemView.findViewById(R.id.btnDetalleCliente);


            btnDetalleCliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent= new Intent(context, VerCliente.class);
                    intent.putExtra("id", listaCliente.get(getAdapterPosition()).getId_cliente());
                    context.startActivity(intent);
                }
            });


        }
    }
}
