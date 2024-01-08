package limpiador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Limpiador {
	//Esta función limpia un archivo haciendo uso de hilos
	public static void limpiarArchivo(String rutaArchivo,int nHilos) throws IOException {
		if(!determinarSiNecesitaMasDeUnHiloEnFuncionDeTamanhoArchivo(rutaArchivo)) {
			//nHilos es 1 porque el archivo es menor que un Mega
			nHilos=1;
		}		
		ejecutarHilos(nHilos, rutaArchivo);		
	}
	//Esta función determina si se necesita más de un hilo, si supera 1 Mbyte se crea
	//más de un hilo
	private static boolean determinarSiNecesitaMasDeUnHiloEnFuncionDeTamanhoArchivo(String ruta) {
		//1024*1024
		long tamanhoMegaByte = 1048576;
		File archivo = new File(ruta);
		boolean masHilos = false;
		long tamanho = archivo.length();
		if(tamanho/tamanhoMegaByte >= 1) {
			//supera un Mbyte por lo tanto es true
			masHilos = true;
		}
		return masHilos;
	}
	//Esta función ejecuta los hilos
	private static void ejecutarHilos(int nHilos, String rutaArchivo) throws IOException {
		Datos datos = new Datos(rutaArchivo,nHilos);
		Thread[] hilos = new Thread[nHilos];
		for(int i=0;i<hilos.length;++i) {
			//Se declaran e inician
			hilos[i] = new Thread(new HiloLimpiador(datos,i));
			hilos[i].start();
		}
		for(int i=0;i<hilos.length;++i) {
			try {
				//Se hace join para que el programa espere hasta que se ejecuten todos
				hilos[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		escribirTextoLimpioEnArchivo(datos.getTextoLimpio());
	}
	//Esta función escribe el texto limpio en un archivo
	private static void escribirTextoLimpioEnArchivo(String textoLimpio) throws IOException {
		File archivo = new File("limpio.txt");		
		BufferedWriter escritorArchivo = new BufferedWriter(new FileWriter(archivo));
		escritorArchivo.write(textoLimpio);
		escritorArchivo.flush();
		escritorArchivo.close();		
	}
}
