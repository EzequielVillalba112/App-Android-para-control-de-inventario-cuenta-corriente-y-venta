package com.example.mercado.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercado.Entidades.Articulos;
import com.example.mercado.R;
import com.example.mercado.VerProducto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaArticulosAdapter extends RecyclerView.Adapter<ListaArticulosAdapter.ArticulosViewHolder> {

    ArrayList<Articulos> listaProducto;
    ArrayList<Articulos> listaProductoBusqueda;
    Articulos art = new Articulos();

    public ListaArticulosAdapter(ArrayList<Articulos> listaProducto){
        this.listaProducto = listaProducto;
        listaProductoBusqueda = new ArrayList<>();
        listaProductoBusqueda.addAll(listaProducto);
    }

    @NonNull
    @Override
    public ArticulosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_producto, null, false);

      return new ArticulosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticulosViewHolder holder, int position) {

        holder.viewNombre.setText(listaProducto.get(position).getNombre());
        holder.viewPrecio.setText(listaProducto.get(position).getPrecio());

        Articulos producto = listaProducto.get(position);

        byte[] imgProducto = producto.getImagen();

        Bitmap bitmap = BitmapFactory.decodeByteArray(imgProducto, 0,imgProducto.length);
        holder.imagPro.setImageBitmap(bitmap);

    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();


        if (longitud == 0){
            listaProducto.clear();
            listaProducto.addAll(listaProductoBusqueda);
        }else{
           if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
               List<Articulos> coleccion = listaProducto.stream()
                       .filter(i ->i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                       .collect(Collectors.toList());

               listaProducto.clear();
               listaProducto.addAll(coleccion);


           }else {
               for (Articulos c:listaProducto){
                   if(c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaProducto.add(c);
                   }
               }
           }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaProducto.size();
    }

    public class ArticulosViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewPrecio;
        Button btnDetallesPro;
        ImageView imagPro;

        public ArticulosViewHolder(@NonNull View itemView) {
            super(itemView);

            imagPro = itemView.findViewById(R.id.imagPro);
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewPrecio = itemView.findViewById(R.id.viewPrecioPro);
            btnDetallesPro = itemView.findViewById(R.id.btnDetallesPro);
            //imageViewProducto = itemView.findViewById(R.id.imageViewProducto);

            btnDetallesPro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent= new Intent(context, VerProducto.class);
                    intent.putExtra("id", listaProducto.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });

        }
    }
}
