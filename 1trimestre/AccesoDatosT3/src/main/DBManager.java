package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DBManager {
	private Connection conn;
	Statement st;
	ResultSet rs;
	
	public DBManager() {
		conn=abrirConexion();
	}
	private Connection abrirConexion() {
		Connection conn= null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost/dep", "root", "admin");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	
	}
	 public void close() throws SQLException {
	        if (conn != null) {
	            conn.close();
	        }
	        conn = null;
	    }
	 public void modificarDepartamento(int num, String nuevoNombre) {
		 try{
	    		Statement s1 = conn.createStatement(); 
	    		s1.executeUpdate("update departamentos set dnombre ='"+nuevoNombre+"'  where dept_no="+num);
		 
		 }catch(Exception e) {
	    		e.printStackTrace();
	    	}
	 }
		 public void modificarLocalidad(int num, String nuevaLocalidad) {
			 try{
		    		Statement s1 = conn.createStatement(); 
		    		s1.executeUpdate("update departamentos set loc ='"+nuevaLocalidad+"'  where dept_no="+num);
			 
			 }catch(Exception e) {
		    		e.printStackTrace();
		    	}
	
	 }
}
