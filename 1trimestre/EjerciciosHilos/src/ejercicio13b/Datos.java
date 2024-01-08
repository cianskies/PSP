package ejercicio13b;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Datos {
	private String[] palabras;
	private ArrayList<String> coincidencias;
	private int contadorPalabras;
	
	public Datos(String rutaArchivo) {
		coincidencias=new ArrayList<String>();
		leerArchivo(rutaArchivo);
		contadorPalabras=0;
	}
	
	private void leerArchivo(String ruta) {
		try {
			String textoCompleto = new String(Files.readAllBytes(Paths.get(ruta)));
			palabras=textoCompleto.split("[ \n]");		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized String getPalabra() throws NoMasPalabrasException{
		if (contadorPalabras>=palabras.length) {
			throw new NoMasPalabrasException();
		}
		return palabras[contadorPalabras++];
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
