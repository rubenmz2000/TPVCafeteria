package org.example.tpvcafeteria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.example.tpvcafeteria.modelo.ProductosComanda;

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ViewHolderProducto> {
    ProductosComanda listProductos;

    public AdapterProductos(ProductosComanda listProductos){
        this.listProductos = listProductos;
    }

    @NonNull
    @Override
    public AdapterProductos.ViewHolderProducto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolderProducto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductos.ViewHolderProducto holder, int position) {
        holder.idProd.setText(listProductos.obtener(position).getId());
        holder.nomProd.setText(listProductos.obtener(position).getNom());
        holder.info.setText(listProductos.obtener(position).getInfo());
    }

    @Override
    public int getItemCount() {
        return listProductos.longitud();
    }

    public class ViewHolderProducto extends RecyclerView.ViewHolder {
        TextView idProd;
        TextView nomProd;
        TextView info;
        public ViewHolderProducto(@NonNull View itemView) {
            super(itemView);
            idProd = itemView.findViewById(R.id.idProd);
            nomProd = itemView.findViewById(R.id.nomprod);
            info = itemView.findViewById(R.id.info);
        }
    }
}
