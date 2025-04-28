package com.example.mercado.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercado.Entidades.Usuario;
import com.example.mercado.R;
import com.example.mercado.Usuario.VerUsuario;

import java.util.ArrayList;

public class AdatadorUsuario extends RecyclerView.Adapter<AdatadorUsuario.UsuarioViewHolder> {
    ArrayList<Usuario> listaUsuario;

    public AdatadorUsuario(ArrayList<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    @NonNull
    @Override
    public AdatadorUsuario.UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_usuario,null, false);

       return new AdatadorUsuario.UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdatadorUsuario.UsuarioViewHolder holder, int position) {
        holder.viewNombreUsuario.setText(listaUsuario.get(position).getNombre_user());
        holder.txtPassUser.setText(listaUsuario.get(position).getPassword());
    }

    @Override
    public int getItemCount() {
        return listaUsuario.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombreUsuario;
        EditText txtPassUser;
        Button btnDetalleUsuario;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombreUsuario = itemView.findViewById(R.id.viewNombreUsuario);
            txtPassUser = itemView.findViewById(R.id.txtPassUser);
            btnDetalleUsuario = itemView.findViewById(R.id.btnDetalleUsuario);

            btnDetalleUsuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String idUsuario = String.valueOf(listaUsuario.get(getAdapterPosition()).getId_user());

                    Context context = v.getContext();
                    Intent intent = new Intent(context, VerUsuario.class);
                    intent.putExtra("idUsuario", idUsuario);
                    context.startActivity(intent);
                }
            });
        }
    }
}
