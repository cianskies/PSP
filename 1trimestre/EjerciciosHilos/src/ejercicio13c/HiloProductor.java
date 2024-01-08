package ejercicio13c;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HiloProductor implements Runnable {

	private String[] palabras;
	private Datos datos;
	
	public HiloProductor(Datos datos, String rutaArchivo) {
		this.datos=datos;
		leerArchivo(rutaArchivo);
	}
	
	@Override
	public void run() {
		//En un bucle, ha de ir llamando a la función
		//producir() de Datos
		//Cuando se quede sin palabras, en lugr de producir
		//tiene que invocar alguna otra función para que
		//a los consumidores les llegue que no hay más palabras
		for (int i=0;i<palabras.length;++i) {
			datos.producir(palabras[i]);
		}
		datos.finalizarConsumidores();
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

}
