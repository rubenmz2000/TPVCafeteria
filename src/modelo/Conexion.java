/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.sql.*;

public class Conexion {
public static Connection conectar() {
		Connection conexion = null;
		 
		try {
		  Class.forName("org.postgresql.Driver").newInstance();
		  conexion = DriverManager.getConnection(
		    "jdbc:postgresql://localhost:5432/cafeteria",
		    "postgres", "postgres");
		} catch (ClassNotFoundException cnfe) {
		  cnfe.printStackTrace();
		} catch (SQLException sqle) {
		  sqle.printStackTrace();
		} catch (InstantiationException ie) {
		  ie.printStackTrace();
		} catch (IllegalAccessException iae) {
		  iae.printStackTrace();
		}
		
		return conexion;
	}
}

