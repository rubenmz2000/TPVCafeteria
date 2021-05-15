/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexionMovil implements Runnable {
    @Override
    public void run(){
        try {
            ServerSocket servidor = new ServerSocket(6000);
            Socket cliente = null;
            
            while (true){
                cliente = servidor.accept();
                
                ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
                salida.flush();
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                
                String solicitud = entrada.readObject().toString();
                
                if (solicitud.equals("estas?")){
                    salida.writeObject("si");
                }
                /*if (solicitud.equals("Comandas")){
                    salida.writeObject(this);
                }*/
                cliente.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionMovil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
