package ejercicio13c;

import java.util.ArrayList;


public class HiloConsumidor implements Runnable {
	private Datos datos;
	private String palabraABuscar;
	
	
	public HiloConsumidor(Datos datos,String palabraABuscar) {
		this.datos=datos;
		this.palabraABuscar=palabraABuscar.toLowerCase();
	}
	
	@Override
	public void run() {
		ArrayList<String> coincidencias=new ArrayList<String>();
		boolean salir = false;
		String palabra;
		while (!salir) {
			try {
				palabra = datos.consumir();
				if (palabra.contains(palabraABuscar)) {
					coincidencias.add(palabra);
				}
			} catch (NoMasPalabrasException e) {
				salir=true;
			}			
		}
		datos.anhadirResultados(coincidencias);
	}

}
