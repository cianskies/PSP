package main;

public class Producto {
	
	private int id;
	private String nombre;
	private String seccion;
	private float precio;
	
	public Producto(int id, String nombre, String seccion, float precio) {
		this.id=id;
		this.nombre=nombre;
		this.seccion=seccion;
		this.precio=precio;
	}
	public Producto(String nombre, String seccion, float precio) {
		this.nombre=nombre;
		this.seccion=seccion;
		this.precio=precio;
	}
	
	public String getNombre() {
		return nombre;
	}
	public String getSeccion() {
		return seccion;
	}
	public float getPrecio() {
		return precio;
	}
	
	@Override
	public String toString() {
		return id+" "+nombre+", "+seccion+", "+precio;
	}

}
