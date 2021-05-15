/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import vista.MainView;
import vista.GestionWindow;
import modelo.Usuario;
import modelo.Conexion;
import java.sql.*;
import java.time.Instant;
import javax.swing.JOptionPane;
import modelo.Categoria;
import modelo.HiloServer;
import modelo.ListCat;
import modelo.ListaProd;
import modelo.ListaUsu;
import modelo.Producto;
import modelo.ProductoC;
import modelo.ProductosComanda;

public class Controlador { 
    MainView mv;
    GestionWindow gw;
    public ListCat categorias = new ListCat();
    public int cantidadMesas;
    public ListaProd productos = new ListaProd();
    
    public int idMesa;
    HiloServer hs;
    Thread t;
    
    public Controlador(){
        hs = new HiloServer();
        t = new Thread(hs);
    }
    
    public void LanzarMainView(){
        mv = new MainView(this);
        mv.setVisible(true);
    }
    
    public void LanzarGestionWindow(){
        try{
            Connection cn = Conexion.conectar();
            int contador = 0;
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM mesa");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                contador++;
            }
            
            t.start();
            cantidadMesas = contador;
            
        }catch(SQLException e){
            
        }
        mv.dispose();
        gw = new GestionWindow(this);
        gw.setVisible(true);
    }
    
    public void IniciarSesion(Usuario U) throws SQLException{
        if (U.getUsuario().equals("admin") && U.getPassword().equals("admin")) {
            LanzarGestionWindow();
        }
        else {
            Connection cn = Conexion.conectar();

            PreparedStatement ps = cn.prepareStatement("SELECT usuario, password FROM usuario WHERE usuario = ? AND password = ?");
            ps.setString(1, U.getUsuario());
            ps.setInt(2, U.getPassword().hashCode());
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                LanzarGestionWindow();
            else
                JOptionPane.showMessageDialog(null, "No se ha podido iniciar sesión");
        }
    }

    public ProductosComanda ComprobarComanda() throws SQLException {
        Connection cn = Conexion.conectar();
        
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM comanda WHERE idmesa = ? AND activa = true");
        ps.setInt(1, idMesa);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next())
            return CargarComanda();
        else{
            gw.idCom = -1;
            gw.empleado = "";
            return new ProductosComanda();
        }
    }
    
    public void CrearComanda(String empleado) throws SQLException {
        Timestamp fechaTs = Timestamp.from(Instant.now());
        Connection cn = Conexion.conectar();
        PreparedStatement ps = cn.prepareStatement("INSERT INTO comanda (idmesa, preciototal, pagado, activa, empleado, hora) VALUES (?, ?, ?, ?, ?, ?)");
        ps.setInt(1, idMesa);
        ps.setInt(2, 0);
        ps.setBoolean(3, false);
        ps.setBoolean(4, true);
        ps.setString(5, empleado);
        ps.setString(6, fechaTs.toString());
        ps.executeUpdate();
        ComprobarComanda();
    }
    
    public ProductosComanda CargarComanda() throws SQLException {
        Connection cn = Conexion.conectar();
        ProductosComanda pc = new ProductosComanda();
        PreparedStatement psId = cn.prepareStatement("SELECT id, empleado FROM comanda WHERE idmesa = ? AND activa = true");
        psId.setInt(1, idMesa);
        ResultSet rs1 = psId.executeQuery();
        rs1.next();
        int id = rs1.getInt("id");
        String empleado = rs1.getString("empleado");
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM productoscomanda WHERE idcom = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            pc.insertar(new ProductoC(rs.getString("idprod"), rs.getString("nomprod"), rs.getDouble("precio")));
        }
        
        gw.empleado = empleado;
        gw.idCom = id;
        return pc;
    }
    
    public void CrearMesas(int mesa)throws SQLException {
        Connection cn = Conexion.conectar();
        PreparedStatement ps2 = cn.prepareStatement("TRUNCATE TABLE mesa CASCADE");
        ps2.executeUpdate();
        
        for (int i = 1; i <= mesa; i++){
            PreparedStatement ps = cn.prepareStatement("INSERT INTO mesa(id) VALUES(?)");
            ps.setInt(1, i);
            ps.executeUpdate();
        }
        cantidadMesas = mesa;
    }

    public ListCat InsertarCategoria(Categoria cat) throws SQLException {
        Connection cn = Conexion.conectar();
        PreparedStatement ps = cn.prepareStatement("INSERT INTO categoria(id, nombre) VALUES(?,?)");
        ps.setInt(1, Integer.parseInt(cat.getCod()));
        ps.setString(2, cat.getNom());
        ps.executeUpdate();
        categorias.insertar(cat);
        return categorias;
    }
    
    public ListCat EliminarCategoria(Categoria cat) throws SQLException{
        Connection cn = Conexion.conectar();
        PreparedStatement ps = cn.prepareStatement("DELETE FROM categoria WHERE nombre = ?");
        ps.setString(1, cat.getNom());
        ps.executeUpdate();
        categorias.eliminar(cat);
        return categorias;
    }
    
    public ListCat ActualizarCategorias()throws SQLException {
        categorias = new ListCat();
        Connection cn = Conexion.conectar();
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM categoria");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()){
            categorias.insertar(new Categoria(rs.getString("id"), rs.getString("nombre")));
        }
        return categorias;
    }
    
    public ListaProd InsertarProducto(Producto p)throws SQLException{
        
        Connection cn = Conexion.conectar();
        PreparedStatement ps = cn.prepareStatement("INSERT INTO producto(id, nombre, categoria, precio, info) VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, Integer.parseInt(p.getId()));
        ps.setString(2, p.getNombre());
        ps.setString(3, p.getCategoria());
        ps.setDouble(4, p.getPrecio());
        ps.setString(5, p.getInfo());
        ps.executeUpdate();
        return ActualizarProductos();
    }
    
    public ListaProd BorrarProducto(Producto p) throws SQLException {
        Connection cn = Conexion.conectar();
        PreparedStatement ps = cn.prepareStatement("DELETE FROM producto WHERE id = ?");
        ps.setInt(1, Integer.parseInt(p.getId()));
        ps.executeUpdate();
        return ActualizarProductos();
    }
    
    public ListaProd ActualizarProductos() throws SQLException{
        productos = new ListaProd();
        Connection cn = Conexion.conectar();
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM producto");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()){
            productos.insertar(new Producto(rs.getString("id"), rs.getString("nombre"), rs.getString("categoria"), rs.getDouble("precio"), rs.getString("info")));
        }
        return productos;
    }
    
    public ListaUsu ActualizarUsuario() throws SQLException {
        ListaUsu lu = new ListaUsu();
        Connection cn = Conexion.conectar();
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM usuario");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()){
            lu.insertar(new Usuario(rs.getString("usuario"), rs.getString("nombre"), rs.getString("password")));
        }
        return lu;
    }
    
    public ListaUsu InsertarUsuario(Usuario u) throws SQLException {
        ListaUsu lu = new ListaUsu();
        Connection cn = Conexion.conectar();
        PreparedStatement ps = cn.prepareStatement("INSERT INTO usuario (usuario, password, nombre) VALUES (?, ?, ?)");
        ps.setString(1, u.getUsuario());
        ps.setInt(2, u.getPassword().hashCode());
        ps.setString(3, u.getNombre());
        ps.executeUpdate();
        
        
        PreparedStatement ps2 = cn.prepareStatement("SELECT * FROM usuario");
        ResultSet rs = ps2.executeQuery();
        
        while (rs.next()){
            lu.insertar(new Usuario(rs.getString("usuario"), rs.getString("nombre"), rs.getString("password")));
        }
        
        return lu;
    }
    
    public ListaUsu BorrarUsuario(Usuario u) throws SQLException {
        ListaUsu lu = new ListaUsu();
        Connection cn = Conexion.conectar();
        
        PreparedStatement ps = cn.prepareStatement("DELETE FROM usuario WHERE usuario = ?");
        ps.setString(1, u.getUsuario());
        ps.executeUpdate();
        
        
        PreparedStatement ps2 = cn.prepareStatement("SELECT * FROM usuario");
        ResultSet rs = ps2.executeQuery();
        
        while (rs.next()){
            lu.insertar(new Usuario(rs.getString("usuario"), rs.getString("nombre"), rs.getString("password")));
        }
        
        return lu;
    }
    
    public ProductosComanda InsertarEnComanda(Producto p) throws SQLException {
        ProductosComanda pc = new ProductosComanda();
        Connection cn = Conexion.conectar();
        PreparedStatement obtId = cn.prepareStatement("SELECT id FROM comanda WHERE activa = true AND idmesa = ?");
        obtId.setInt(1, idMesa);
        ResultSet rs1 = obtId.executeQuery();
        rs1.next();
        int idCom = rs1.getInt("id");
        PreparedStatement ps = cn.prepareStatement("INSERT INTO productoscomanda(idcom, idprod, nomprod) VALUES (?, ?, ?)");
        ps.setInt(1, idCom);
        ps.setInt(2, Integer.parseInt(p.getId()));
        ps.setString(3, p.getNombre());
        ps.executeUpdate();
        
        PreparedStatement upC = cn.prepareStatement("UPDATE comanda SET preciototal = preciototal + ? WHERE id = ?");
        upC.setDouble(1, p.getPrecio());
        upC.setInt(1, idCom);
        upC.executeUpdate();
        
        PreparedStatement list = cn.prepareStatement("SELECT * FROM productoscomanda WHERE idcom = ?");
        list.setInt(1, idCom);
        ResultSet rs = list.executeQuery();
        while (rs.next()){
            pc.insertar(new ProductoC(rs.getString("idprod"), rs.getString("nomprod"), rs.getDouble("precio")));
        }
        return pc;
    }
    
    public ProductosComanda eliminarDeComanda(ProductoC p) throws SQLException {
        Connection cn = Conexion.conectar();
        PreparedStatement elim = cn.prepareStatement("DELETE FROM productoscomanda WHERE idprod = ? AND idprod = ?");
        elim.setInt(1, Integer.parseInt(p.getId()));
        elim.setInt(2, gw.idCom);
        elim.executeUpdate();
        
        return CargarComanda();
    }
    
    public void Pagar(int idComanda) throws SQLException {
        Connection cn = Conexion.conectar();
        PreparedStatement ps = cn.prepareStatement("UPDATE comanda SET pagado = true, activa = false WHERE id = ?");
        ps.setInt(1, idComanda);
        ps.executeUpdate();
        PreparedStatement ps2 = cn.prepareStatement("SELECT preciototal FROM comanda WHERE id = ?");
        ps2.setInt(1, idComanda);
        ResultSet rs = ps2.executeQuery();
        while (rs.next()){
            JOptionPane.showMessageDialog(null, "Precio total: " + rs.getInt("preciototal"));
        }
    }
}
