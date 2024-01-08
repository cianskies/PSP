package director;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class procesoHormiga {
	private Posicion posicion;
	private BufferedReader brH;
	private BufferedWriter bwH;
	private int direccion;
	private String regla;
	public procesoHormiga(Posicion posicion, BufferedReader brH,BufferedWriter bwH, int direccion, String regla) {
		this.posicion=posicion;
		this.brH=brH;
		this.bwH=bwH;
		this.direccion=direccion;
		this.regla=regla;
		
	}
	public String getRegla() {
		return regla;
	}
	public Posicion getPosicion() {
		return posicion;
	}
	public Posicion setPosicion(Posicion posicion) {
		this.posicion=posicion;
		return this.posicion;
	}
	public  BufferedReader getBrH() {
		return brH;
	}
	public BufferedWriter getBwH() {
		return bwH;
	}	public int getDireccion() {
		return direccion;
	}

}
