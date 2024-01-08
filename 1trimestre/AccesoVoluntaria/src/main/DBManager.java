package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
	private Connection conn;
	private Statement st;
	private ResultSet rs;
	private int id;
	private String nombre;
	private String seccion;
	private float precio;
	private ArrayList<Producto> productos;
	
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
			conn=DriverManager.getConnection("jdbc:mysql://localhost/productos", "root", "admin");
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
	 public List<Producto> listarProductos() {
		 productos=new ArrayList<Producto>();

		try {
			st = conn.createStatement();

			String secuencia="Select * from productos";
		
			rs = st.executeQuery(secuencia);
			 llenarLista();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return productos;
	 }
	
	 public List<Producto> filtrarProductosPorNombre(String nombreProducto) {
		 productos=new ArrayList<Producto>();

			try {
				st = conn.createStatement();

				String secuencia="Select * from productos where nombre='"+nombreProducto+"'";
			
				rs = st.executeQuery(secuencia);
				llenarLista();
		 
		 
		 
			}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			return productos;
			
	 }
	 
	 public List<Producto> filtrarProductosPorNombreSeccionYPrecio(String nombreProducto, String seccionProducto, float precioProducto){
		 productos=new ArrayList<Producto>();

			try {
				st = conn.createStatement();

				String secuencia="Select * from productos where nombre='"+nombreProducto+"' and seccion='"+seccionProducto+"' and precio="+precioProducto;
			
				rs = st.executeQuery(secuencia);
				llenarLista();
		 
		 
			}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			return productos;
	 }
	 
	 public void insertarProducto(Producto producto) {
		 //Instanciar secuencia con los parametros del producto que le damos
		 String secuencia="Insert into productos (nombre, seccion, precio) values ('"+producto.getNombre()+"', '"+producto.getSeccion()+"', "+producto.getPrecio()+")";
		 String secuenciaId="Select id from productos where nombre='"+producto.getNombre()+"'";
		 try {
			st = conn.createStatement();
			st.executeUpdate(secuencia);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("No se pudo insertar producto");
			e.printStackTrace();
		}
		//Consultamos el id del producto que hemos creado
		 try {
			st = conn.createStatement();
			rs = st.executeQuery(secuenciaId);
			rs.next();
			int idInsertado=rs.getInt(1);
			//Mostramos el id del producto que hemos insertado
		    System.out.println("El siguiente producto ha sido insertado: "+producto.getNombre()+" con id: "+idInsertado);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("No se pudo mostrar id..");
			e.printStackTrace();
		}
	 }
	 
	 public void eliminarProducto(Producto producto) {
		 String secuencia="Delete from productos where nombre='"+producto.getNombre()+"' and seccion='"+producto.getSeccion()+"' and precio="+producto.getPrecio();
		 try {
				st = conn.createStatement();
				st.executeUpdate(secuencia);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("No se pudo borrar producto");
				e.printStackTrace();
			}
	 }
	 
	 
	 private void llenarLista() throws SQLException {
			while(rs.next()){
				//almacenar los datos de cada producto
				 Producto producto = extraerProductoDeConsulta();
				 //añadir objeto producto a lista
				productos.add(producto);
				 
			 }
		}
	 private Producto extraerProductoDeConsulta() throws SQLException {
		 id=rs.getInt("id");
		 nombre=rs.getString("nombre");
		 seccion=rs.getString("seccion");
		 precio=rs.getFloat("precio");
		//Crear un objeto producto por cada elemento en la tabla productos
		Producto producto=new Producto(id,nombre,seccion,precio);
		return producto;
	 }
		 
	 
}