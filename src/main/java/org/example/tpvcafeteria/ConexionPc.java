package org.example.tpvcafeteria;

import android.widget.ListAdapter;

import org.example.tpvcafeteria.modelo.Comanda;
import org.example.tpvcafeteria.modelo.ListComanda;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ConexionPc implements Runnable {
    String ip;
    ComandasGeneralActivity cga;
    ListComanda lista;
    String parameter;
    public ConexionPc(String ip, ComandasGeneralActivity cga, String parameter) {
        this.ip = ip;
        this.cga = cga;
        this.parameter = parameter;
    }
    public void run(){
        try {
            Socket s;


            while (true){
                s  = new Socket(ip, 6000);
                ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream dis = new ObjectInputStream(s.getInputStream());

                ArrayList<Object[]> prueba;
                        lista = new ListComanda();
                if (parameter.equals("comanda")) {
                    dos.writeObject("comanda");
                    prueba = (ArrayList<Object[]>) dis.readObject();
                }
                else{
                    dos.writeObject("historial");
                    prueba = (ArrayList<Object[]>) dis.readObject();
                }
                dos.close();
                dis.close();
                s.close();

                for (Object[] com : prueba) {
                    Comanda c = new Comanda();
                    for (int i = 0; i < com.length; i++){
                        StringTokenizer st = new StringTokenizer(com[i].toString(), ";");
                        while (st.hasMoreTokens()){
                            String token = st.nextToken();
                            switch (token){
                                case "id": c.setId(st.nextToken());
                                    break;
                                case "mesa": c.setIdMesa(st.nextToken());
                                    break;
                                case "empleado": c.setEmpleado(st.nextToken());
                                    break;
                                case "hora": c.setHora(st.nextToken());
                                    break;
                            }
                        }
                    }

                    lista.insertar(c);
                }

                cga.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        cga.RellenarListaComandas(lista);
                    }
                });
                Thread.sleep(5000);
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
