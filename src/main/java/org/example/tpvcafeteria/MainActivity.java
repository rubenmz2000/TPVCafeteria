package org.example.tpvcafeteria;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText et;
    Button bt;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.et);
        bt = findViewById(R.id.bt);

        activity = this;

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip;


                ip = et.getText().toString();

                if (ip.equals("")) {
                    Toast.makeText(MainActivity.this, "Introduce una IP", Toast.LENGTH_SHORT).show();
                }
                else{
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ComprobarIp(ip);
                        }
                    });
                    t.start();
                }
            }
        });
    }


    public void ComprobarIp(String ip){
        try {
            Socket s = new Socket(ip, 6000);

            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            oos.writeObject("estas?");
            String respuesta = ois.readObject().toString();
            s.close();
            if (respuesta.equals("si")) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LanzarComandasGeneral(ip);
                    }
                });
            } else {
                activity.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "La ip es incorrecta o no está disponible", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } catch (IOException | ClassNotFoundException e) {
            activity.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "La ip es incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void LanzarComandasGeneral(String ip){
        Intent intent = new Intent(this, ComandasGeneralActivity.class);
        intent.putExtra("ip", ip);
        startActivity(intent);
    }
}