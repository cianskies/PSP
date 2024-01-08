package ejercicio13a;

import java.util.ArrayList;

public class HiloEjercicio13a implements Runnable {

	private Datos datos;
	private String palabraABuscar;
	
	
	public HiloEjercicio13a(Datos datos,String palabraABuscar) {
		this.datos=datos;
		this.palabraABuscar=palabraABuscar.toLowerCase();
	}
	
	@Override
	public void run() {
		//Obtenemos de datos el trozo que tenemos que analizar
		String texto = datos.getTrozo();
		texto=texto.toLowerCase();
		//Lo partimos en palabras
		ArrayList<String> coincidencias=new ArrayList<String>();
		String[] palabras = texto.split(" ");
		for(int i=0;i<palabras.length;++i) {
			if (palabras[i].contains(palabraABuscar)) {
				coincidencias.add(palabras[i]);
			}
		}
		datos.anhadirResultados(coincidencias);
	}

}
