package ejercicio13b;

import java.util.ArrayList;


public class HiloEjercicio13b implements Runnable {
	private Datos datos;
	private String palabraABuscar;
	
	
	public HiloEjercicio13b(Datos datos,String palabraABuscar) {
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
				palabra = datos.getPalabra();
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
