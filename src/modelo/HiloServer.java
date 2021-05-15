/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.*;

public class HiloServer implements Runnable {
    @Override
    public void run(){
        try {
            ServerSocket servidor = new ServerSocket(6000);
            Socket cliente = null;
            System.out.println("SERVIDOR INICIADO");
            
            while (true){
                cliente = servidor.accept();
                
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
                
                String solicitud = entrada.readObject().toString();
                System.out.println(solicitud);
                if (solicitud.equals("estas?")){
                    salida.writeObject("si");
                }
                else if (solicitud.equals("comanda")){
                    try{
                        ArrayList<Object[]> s = new ArrayList();
                        Connection cn = Conexion.conectar();
                        PreparedStatement obtenerComandas = cn.prepareStatement("SELECT * FROM comanda WHERE activa = true");
                        ResultSet rs = obtenerComandas.executeQuery();
                        while (rs.next()){
                            s.add(new Object[]{"id;" + rs.getString("id"), "mesa;" + rs.getString("idMesa"), "empleado;" + rs.getString("empleado"), "hora;" + rs.getString("hora")});
                        }
                        salida.writeObject(s);
                        cn.close();
                    }catch (SQLException e){
                        
                    }
                    
                }
                else if (solicitud.equals("historial")){
                    try{
                        ArrayList<Object[]> s = new ArrayList();
                        Connection cn = Conexion.conectar();
                        PreparedStatement obtenerComandas = cn.prepareStatement("SELECT * FROM comanda WHERE activa = false");
                        ResultSet rs = obtenerComandas.executeQuery();
                        while (rs.next()){
                            s.add(new Object[]{"id;" + rs.getString("id"), "mesa;" + rs.getString("idMesa"), "empleado;" + rs.getString("empleado"), "hora;" + rs.getString("hora")});
                        }
                        salida.writeObject(s);
                        cn.close();
                    }catch (SQLException e){
                        
                    }
                }
                else{
                    try{
                        ArrayList<Object[]> s = new ArrayList();
                        int id = Integer.parseInt(solicitud);
                        
                        Connection cn = Conexion.conectar();
                        PreparedStatement ps = cn.prepareStatement("SELECT * FROM productoscomanda WHERE idcom = ?");
                        ps.setInt(1, id);
                        ResultSet rs = ps.executeQuery();
                        
                        while (rs.next()){
                            s.add(new Object[]{"id;" + rs.getString("idprod"), "nomprod;" + rs.getString("nomprod"), "info;"+ "informacion"});
                        }
                        salida.writeObject(s);
                        cn.close();
                    }catch(SQLException e){
                        
                    }
                }
                entrada.close();
                salida.close();
                cliente.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionMovil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
