package encriptador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Encriptador {
	
	private int numeroEncriptadores;
	private Thread[] encriptadores;
	private String clave;
	String texto;
	ArrayList<String> textoProcesado;
	private DatosEncriptador datos;
	
	public Encriptador(String texto, int numeroEncriptadores, String clave) {
		datos=new DatosEncriptador(texto,numeroEncriptadores);
		this.clave=clave;
		this.numeroEncriptadores=numeroEncriptadores;
		encriptadores=new Thread[numeroEncriptadores];
		
		
	}
	
	public void EncriptarODesencriptarTexto(boolean encriptar) {
		datos.reiniciarContador();
		String nombreArchivo="encriptado.txt";
		if(!encriptar) {
			nombreArchivo="desencriptado.txt";
		}
		indicarEncriptadores(encriptar);
		textoProcesado=datos.mostrartexto();
		guardarEnArchivo(nombreArchivo);
	}

	private void indicarEncriptadores(boolean encriptar) {
		for(int i=0;i<numeroEncriptadores;++i) {
			encriptadores[i]=new Thread(new HiloEncriptador(i,encriptar,clave,datos));
			encriptadores[i].start();
		}
		
		for(int i=0;i<numeroEncriptadores;++i) {
			try {
				encriptadores[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	}
	public void setTextoDeFicheroTxT(String ruta) {
		StringBuilder texto=new StringBuilder();
		try {
			File archivo= new File(ruta);
			String linea;
			BufferedReader br=new BufferedReader(new FileReader(archivo));
			while((linea=br.readLine())!=null) {
				texto.append(linea);
				
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		datos.setTexto(texto.toString());
	}
	private void guardarEnArchivo(String nombreArchivo) {
		File archivo=new File(nombreArchivo);
		try {
			archivo.createNewFile();
			FileWriter fr=new FileWriter(archivo);
			for (String palabra : textoProcesado) {
			    fr.write(palabra);
			}
			fr.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getTexto() {
		return textoProcesado.toString().replaceAll("[\\[\\]]", "").replaceAll(",", "").replaceAll(" ", "");
	}
}
