package org.example.tpvcafeteria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.example.tpvcafeteria.modelo.ProductosComanda;

public class ProductoComanda extends AppCompatActivity {
    Activity activity;
    RecyclerView recycler;
    ProductosComanda productosComanda;
    Button volver;
    AdapterProductos adapter;
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_comanda);
        activity = this;
        volver = findViewById(R.id.bVolver);
        recycler = findViewById(R.id.recyclerProductos);

        recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        String ip = getIntent().getExtras().getString("ip");
        String id = getIntent().getExtras().getString("id");
        t = new Thread(new ConexionPcProducto(ip, this, id));
        t.start();

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Salir();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        t.interrupt();
    }

    public void RellenarListaProductos(ProductosComanda pc){
        productosComanda = pc;
        adapter = new AdapterProductos(pc);
        recycler.setAdapter(adapter);
    }
    public void Salir(){
        t.interrupt();
        this.finish();
    }
}