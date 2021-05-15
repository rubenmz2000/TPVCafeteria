package org.example.tpvcafeteria;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.example.tpvcafeteria.modelo.Comanda;
import org.example.tpvcafeteria.modelo.ListComanda;

import java.util.ArrayList;

public class AdapterComandas extends RecyclerView.Adapter<AdapterComandas.ViewHolderComanda> {
    ListComanda listComandas;
    Context context;
    String ip;
    public AdapterComandas(ListComanda listComandas, Context context, String ip){
        this.listComandas = listComandas;
        this.context = context;
        this.ip = ip;
    }

    @NonNull
    @Override
    public ViewHolderComanda onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comanda, parent, false);

        return new ViewHolderComanda(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderComanda holder, int position) {
        holder.idCom.setText(listComandas.obtener(position).getId());
        holder.empleado.setText(listComandas.obtener(position).getEmpleado());
        holder.mesa.setText(listComandas.obtener(position).getIdMesa());
        holder.hora.setText(listComandas.obtener(position).getHora());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = holder.idCom.getText().toString();
                Intent intent = new Intent(context, ProductoComanda.class);
                intent.putExtra("id", id);
                intent.putExtra("ip", ip);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listComandas.longitud();
    }

    public class ViewHolderComanda extends RecyclerView.ViewHolder  {
        TextView idCom;
        TextView empleado;
        TextView mesa;
        TextView hora;
        CardView card;

        public ViewHolderComanda(@NonNull View itemView) {
            super(itemView);
            idCom = itemView.findViewById(R.id.idComanda);
            empleado = itemView.findViewById(R.id.empleado);
            mesa = itemView.findViewById(R.id.mesa);
            hora = itemView.findViewById(R.id.hora);
            card = itemView.findViewById(R.id.comanda);

        }
    }
}
