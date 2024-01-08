package encriptador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DatosEncriptador {
	private String texto;
	ArrayList<String> textoProcesado;
	private int posicionTexto;
	private int contadorRecibir;
	private int contadorAnhadir;
	private int numeroEncriptadores;
	
	
	public DatosEncriptador(String texto, int numeroEncriptadores) {
		this.texto=texto;
		this.numeroEncriptadores=numeroEncriptadores-1;
	}
	public void reiniciarContador() {
		textoProcesado=new ArrayList<String>();
		posicionTexto=0;
		contadorRecibir=0;
		contadorAnhadir=0;
	}
	public synchronized String recibirPalabra(int longitud, int id) throws TextoVacioException {
		while(contadorRecibir!=id) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String palabra;
		if(posicionTexto>texto.length()) {
			++contadorRecibir;
			if(contadorRecibir>numeroEncriptadores) {
				contadorRecibir=0;
			}
			notifyAll();
			throw new TextoVacioException();
			
		}
		if(posicionTexto+longitud>=texto.length()) {
			palabra= texto.substring(posicionTexto, texto.length());
			
		}else {
			palabra= texto.substring(posicionTexto, posicionTexto+longitud);
		}
		posicionTexto+=longitud;
		++contadorRecibir;
		if(contadorRecibir>numeroEncriptadores) {
			contadorRecibir=0;
		}
		notifyAll();
		return palabra;
	}
	public synchronized void anhadirPalabra(String palabra,int id) {
		while(contadorAnhadir!=id) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			textoProcesado.add(palabra);
		++contadorAnhadir;
		if(contadorAnhadir>numeroEncriptadores) {
			contadorAnhadir=0;
		}
		notifyAll();
	}
	public ArrayList<String> mostrartexto() {
		return textoProcesado;
	}
	public void setTexto(String texto) {
		this.texto=texto;
	}
}
