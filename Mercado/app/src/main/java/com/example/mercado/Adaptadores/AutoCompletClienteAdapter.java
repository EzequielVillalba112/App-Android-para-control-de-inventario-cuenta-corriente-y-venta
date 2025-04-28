package com.example.mercado.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mercado.R;
import com.example.mercado.VistaVender.Utilidades.ClienteItem;

import java.util.ArrayList;
import java.util.List;

public class AutoCompletClienteAdapter extends ArrayAdapter<ClienteItem> {

    private  List<ClienteItem> clienteListFull;
    TextView txtIdClin;

    public AutoCompletClienteAdapter(@NonNull Context context, @NonNull List<ClienteItem> clienteList) {
        super(context, 0, clienteList);

        clienteListFull = new ArrayList<>(clienteList);
        this.txtIdClin =txtIdClin;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return  clienteFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_clien_autocom,parent, false
            );
        }

        TextView txtNombreCli = convertView.findViewById(R.id.txtNombreCli);
        TextView txtApelido = convertView.findViewById(R.id.txtApelido);
        ClienteItem clienteItem = getItem(position);

        if (clienteItem != null){
            txtNombreCli.setText(clienteItem.getNomClient());
            txtApelido.setText(clienteItem.getApeClient());
        }

        return convertView;
    }

    private Filter clienteFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ClienteItem> sugerencia = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                sugerencia.addAll(clienteListFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ClienteItem item : clienteListFull){
                    if (item.getNomClient().toLowerCase().contains(filterPattern) || item.getApeClient().toLowerCase().contains(filterPattern)){
                        sugerencia.add(item);
                    }
                }
            }
            results.values = sugerencia;
            results.count = sugerencia.size();

            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results ) {
            clear();
            addAll((List)results.values);
            notifyDataSetChanged();
        }


        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ClienteItem) resultValue).getNomClient();
        }
    };
}
