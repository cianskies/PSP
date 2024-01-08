package ejercicio13a;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Datos {

	private String textoCompleto;
	private String[] trozos;
	private ArrayList<String> coincidencias;
	private int[] limites;
	private int nHilos;
	private int contadorHilos;
	
	public Datos(String rutaArchivo, int nHilos) {
		this.nHilos=nHilos;
		coincidencias=new ArrayList<String>();
		leerArchivo(rutaArchivo);
		determinarLimitesDeCadaTrozo();
		contadorHilos=0;
	}
	
	private void leerArchivo(String ruta) {
		try {
			textoCompleto = new String(Files.readAllBytes(Paths.get(ruta)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void determinarLimitesDeCadaTrozo() {
		//Primero hacemos el troceado bruto
		limites = new int[nHilos+1];
		limites[0]=0;
		limites[nHilos]=textoCompleto.length();
		int longitudDeCadaTrozo=textoCompleto.length()/nHilos;
		int posicion;
		for (int i=1;i<nHilos;++i) {
			posicion=longitudDeCadaTrozo*i;
			while(textoCompleto.charAt(posicion)!=' ' 
					&& textoCompleto.charAt(posicion)!='\n') {
				++posicion;
			}
			limites[i]=posicion;
		}
		
	}
	
	public synchronized String getTrozo() {
		String trozo = textoCompleto.substring(limites[contadorHilos], limites[contadorHilos+1]);
		++contadorHilos;
		return trozo;
	}
	
	public synchronized void anhadirResultados(ArrayList<String> coincidenciasParciales) {
		coincidencias.addAll(coincidenciasParciales);
	}
	
	public void mostrarResultados() {
		for(String coincidencia:coincidencias) {
			System.out.println(coincidencia);
		}
		System.out.println("En total: "+coincidencias.size()+" coincidencias");
	}
	
	
}
