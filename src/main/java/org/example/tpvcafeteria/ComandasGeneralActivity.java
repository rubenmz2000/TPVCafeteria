package org.example.tpvcafeteria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.example.tpvcafeteria.modelo.Comanda;
import org.example.tpvcafeteria.modelo.ListComanda;

import java.util.ArrayList;
import java.util.List;

public class ComandasGeneralActivity extends AppCompatActivity {
    ListComanda listComandas;
    Button bSalir;
    Button bHistorial;
    RecyclerView recycler;
    Activity activity;
    ComandasGeneralActivity cga;
    String ip;
    Thread c;
    Thread h;
    AdapterComandas adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comandas_general);
        activity = this;
        cga = this;
        bSalir = findViewById(R.id.btSalir);
        bHistorial = findViewById(R.id.btHistoral);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));


        listComandas = new ListComanda();


        ip = getIntent().getExtras().getString("ip");
        c = new Thread(new ConexionPc(ip, this, "comanda"));

        c.start();
        
        bSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        bHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bHistorial.getText().toString().toLowerCase().equals("historial")){
                    bHistorial.setText("comandas");
                    c.interrupt();

                    h = new Thread(new ConexionPc(ip, cga, "historial"));
                    h.start();
                }else{
                    bHistorial.setText("historial");
                    h.interrupt();

                    c = new Thread(new ConexionPc(ip, cga, "comanda"));
                    c.start();
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        c.interrupt();
    }

    public void LanzarHistorial(){
        Intent intent = new Intent();
        startActivity(intent);
    }
    public void RellenarListaComandas(ListComanda lista){
        listComandas = lista;
        adapter = new AdapterComandas(listComandas, this.getApplicationContext(), ip);
        recycler.setAdapter(adapter);
    }
}