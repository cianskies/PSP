package limpiador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Datos {
	private String texto;
	private StringBuilder constructorTextoLimpio;
	private int[] limitesDelTexto;
	private int contadorHilosObtenerTexto;
	private int contadorHilosAnhadirTexto;
	//Datos "coopera con los hilos", les devuelve el texto que les corresponde
	//y almacena el trabajo de cada hilo, además de coordinarlos
	public Datos(String rutaArchivo,int nHilos) throws IOException {
		leerArchivo(rutaArchivo);
		determinarLimitesDeCadaTrozoDeTexto(nHilos);
		constructorTextoLimpio = new StringBuilder();
		this.contadorHilosObtenerTexto=0;
		this.contadorHilosAnhadirTexto=0;
	}
	//Esta función lee el contenido de un archivo de texto
	private void leerArchivo(String rutaArchivo) throws IOException {
		texto = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
	}
	//Esta función determina los límites de cada trozo dependiendo del número de hilos
	private void determinarLimitesDeCadaTrozoDeTexto(int nHilos) {
		limitesDelTexto = new int[nHilos+1];
		//Se establece la posicon incial y final del array en 0 y .length
		limitesDelTexto[0]=0;
		limitesDelTexto[nHilos] = texto.length();
		//Los limites intermedios del array es la division por el número del hilo
		int longitudTrozos = limitesDelTexto[nHilos]/nHilos;
		int posicion;
		for(int i=1;i<limitesDelTexto.length-1;++i) {			
			posicion = longitudTrozos*i;
			limitesDelTexto[i]=posicion;
		}
	}
	//Esta función sincronizada devuelve un trozo de texto a cada hilo
	public synchronized String getTrozoTexto(int idHilo) {
		//El while sirve para ordenar los hilos, sólo saldrán si les toca
		while(idHilo!=contadorHilosObtenerTexto) {
			try {
				//espera el hilo hasta que sea despertado
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//se crea un texto a partir del original cuya longitud será determinada por el hilo
		String trozoTexto =  texto.substring(limitesDelTexto[contadorHilosObtenerTexto]
				,limitesDelTexto[contadorHilosObtenerTexto+1]);
		++contadorHilosObtenerTexto;
		//Se despiertan a todos los hilos
		notifyAll();
		return trozoTexto;
	}
	//Esta función sincronizada añade un trozo limpio al StringBuilder ordenadamente por id
	public synchronized void anhadirTrozoLimpio(String trozoLimpio,int idHilo) {
		while(idHilo!=contadorHilosAnhadirTexto) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//Se añade el texto
		constructorTextoLimpio.append(trozoLimpio);		
		++contadorHilosAnhadirTexto;
		notifyAll();
	}
	//Esta función devuelve el texto limpio
	public String getTextoLimpio() {
		return constructorTextoLimpio.toString();
	}
	
}
