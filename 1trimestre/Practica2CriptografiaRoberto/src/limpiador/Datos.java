package limpiador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Datos {
	
	private String ruta;
	private String textoEnBruto;
	private int numeroLimpiadores;
	private int[] limitesDeCadaTrozo;
	StringBuilder textoLimpio;
	private String[] trozos;
	
	public Datos(String ruta, int numeroLimpiadores) {
		this.ruta=ruta;
		this.numeroLimpiadores=numeroLimpiadores;
		textoEnBruto=recibirTextoDeArchivo();
		limitesDeCadaTrozo=determinarLimitesDeCadaTrozo();
		textoLimpio=new StringBuilder();
		trozos=new String[numeroLimpiadores];
		
	}
	
	private String recibirTextoDeArchivo() {
		StringBuilder texto=new StringBuilder();
		try {
			File archivo= new File(ruta);
			String linea;
			BufferedReader br=new BufferedReader(new FileReader(archivo));
			while((linea=br.readLine())!=null) {
				texto.append(linea);
				texto.append("\n");
				
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texto.toString();
	}
	
	private int[] determinarLimitesDeCadaTrozo() {
		
		int[] limites=new int[numeroLimpiadores+1];
		limites[0]=0;
		limites[numeroLimpiadores]=textoEnBruto.length();
		int posicion;
		int longitudDeCadaTrozo=textoEnBruto.length()/numeroLimpiadores;
		for(int i=1;i<numeroLimpiadores;++i) {
			posicion=longitudDeCadaTrozo*i;
			while(textoEnBruto.charAt(posicion)!=' ' 
					&& textoEnBruto.charAt(posicion)!='\n') {
				++posicion;
			}
			limites[i]=posicion;
		}
		return limites;
	}
	
	//metodos que usan los limpiadores
	public synchronized String recibirTrozoDeTexto(int id) {
		String trozo=textoEnBruto.substring(limitesDeCadaTrozo[id], limitesDeCadaTrozo[id+1]);
		return trozo;
	}
	public synchronized void darTrozo(String trozo,int id) {
		trozos[id]=trozo;
	}
	//metodos que usa la clase principal
	public String recibirTextoLimpio() {
		for(int i=0; i<numeroLimpiadores;++i) {
			textoLimpio.append(trozos[i]);
		}
		return textoLimpio.toString();
	}
	
}
