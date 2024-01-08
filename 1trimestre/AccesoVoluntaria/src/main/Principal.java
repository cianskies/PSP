package main;

import java.util.List;
import java.util.ArrayList;

public class Principal {
	
	public static void main(String[] args) {
		DBManager manager=new DBManager();
		
		Producto patata=new Producto("kkk","fo",(float)10.99);
		manager.insertarProducto(patata);
		manager.eliminarProducto(new Producto("Manzana","Fruta",(float)10.99));
		
		List<Producto> productos=manager.listarProductos();
		//productos=manager.filtrarProductosPorNombre("manzana");
		//productos=manager.filtrarProductosPorNombreSeccionYPrecio("manzana","fruta",(float)10.99);
		

		for(Producto p:productos) {
			System.out.println(p.toString());
		}
	}
}
