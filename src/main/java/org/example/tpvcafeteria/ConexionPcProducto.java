package org.example.tpvcafeteria;

import org.example.tpvcafeteria.modelo.ProductoC;
import org.example.tpvcafeteria.modelo.ProductosComanda;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ConexionPcProducto implements Runnable {
    String ip;
    ProductoComanda pc;
    String idComanda;
    ProductosComanda lista;
    public ConexionPcProducto(String ip, ProductoComanda pc, String idComanda) {
        this.ip = ip;
        this.pc = pc;
        this.idComanda = idComanda;
    }
    public void run(){
        try {
            Socket s;


            while (true){
                s  = new Socket(ip, 6000);
                ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream dis = new ObjectInputStream(s.getInputStream());


                lista = new ProductosComanda();

                dos.writeObject(idComanda);
                ArrayList<Object[]> prueba = (ArrayList<Object[]>) dis.readObject();
                dos.close();
                dis.close();
                s.close();

                for (Object[] com : prueba) {
                    ProductoC c = new ProductoC();
                    for (int i = 0; i < com.length; i++){
                        StringTokenizer st = new StringTokenizer(com[i].toString(), ";");
                        while (st.hasMoreTokens()){
                            String token = st.nextToken();
                            switch (token){
                                case "id": c.setId(st.nextToken());
                                    break;
                                case "nomprod": c.setNom(st.nextToken());
                                    break;
                                case "info": c.setInfo(st.nextToken());
                                    break;
                            }
                        }
                    }

                    lista.insertar(c);
                }

                pc.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        pc.RellenarListaProductos(lista);
                    }
                });
                Thread.sleep(5000);
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
