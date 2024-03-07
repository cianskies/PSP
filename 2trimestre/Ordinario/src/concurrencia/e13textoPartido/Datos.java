package concurrencia.e13textoPartido;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Datos {
	private int numTrozos;
	private BufferedReader br;
	private int[][] limitesDeCadaTrozo;
	private int[][] trozoqueCogeCadaHilo;
	private int longitudTrozo;
	private String texto;
	private String[] textoToUpper;
	private int hiloActualRecogiendo;
	
	public Datos(String ruta,int numTrozos) {
		this.numTrozos=numTrozos;
		Path path=Paths.get(ruta);
		try {
			br=Files.newBufferedReader(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		leerTexto();
		determinarLongitudDeTrozo();
		repartirTexto();
		hiloActualRecogiendo=0;
		trozoqueCogeCadaHilo=new int[numTrozos][2];
		textoToUpper=new String[numTrozos];
		
	}
	
	private void repartirTexto() {
		limitesDeCadaTrozo=new int[numTrozos][2];
		System.out.println(limitesDeCadaTrozo.length);
		int posicionEnTexto=0;
		for(int i=0;i<limitesDeCadaTrozo.length;++i) {
			limitesDeCadaTrozo[i][0]=posicionEnTexto;
			if(posicionEnTexto+longitudTrozo>texto.length()) {
				limitesDeCadaTrozo[i][1]=texto.length();
			}else if(i+1==limitesDeCadaTrozo.length) {
				limitesDeCadaTrozo[i][1]=texto.length();
				
			}else {
				int nuevaPosicion=posicionEnTexto+longitudTrozo-1;
				limitesDeCadaTrozo[i][1]=nuevaPosicion;
				posicionEnTexto=nuevaPosicion;
			}
			System.out.println("Fragmento numero "+i+": desde "+limitesDeCadaTrozo[i][0]+" hasta "+limitesDeCadaTrozo[i][1]);
		}
	}
	private void leerTexto() {
		StringBuilder sb=new StringBuilder();
		String linea;
		try {
			while( (linea=br.readLine())!=null) {
				sb.append(linea);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		texto=sb.toString();
		
	}
	private void determinarLongitudDeTrozo() {
		int numeroCaracteres=texto.length();
		longitudTrozo=numeroCaracteres/numTrozos;
		System.out.println(numeroCaracteres);
		System.out.println(longitudTrozo);
	}
	public synchronized String recogerTrozo(int id) {
		String trozo=texto.substring(limitesDeCadaTrozo[hiloActualRecogiendo][0],limitesDeCadaTrozo[hiloActualRecogiendo][1]);
		trozoqueCogeCadaHilo[hiloActualRecogiendo][0]=hiloActualRecogiendo;
		trozoqueCogeCadaHilo[hiloActualRecogiendo][1]=id;
		System.out.println("Le toca a "+trozoqueCogeCadaHilo[hiloActualRecogiendo][0]+ " el trozo "+trozoqueCogeCadaHilo[hiloActualRecogiendo][1]);
		++hiloActualRecogiendo;
		return trozo;
		
		
	}
	public synchronized void appendTrozo(int id, String trozo) {
		int posicionTexto=0;
		for(int i=1;i<numTrozos;++i) {
			if(trozoqueCogeCadaHilo[i][1]==id) {
				posicionTexto=trozoqueCogeCadaHilo[i][0];

			}
		}
		textoToUpper[posicionTexto]=trozo;
	}
	public void imprimirTXT() {
		for(int i=0;i<textoToUpper.length;++i) {
			System.out.println(textoToUpper[i]);
		}
	}
}
