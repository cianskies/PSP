package ejercicio13c;

import java.util.ArrayList;

public class Datos {

	private ArrayList<String> coincidencias;
	private String siguientePalabra;
	private boolean hayMasPalabras;
	
	public Datos() {
		hayMasPalabras=true;
		coincidencias=new ArrayList<String>();
	}
	
	public synchronized void finalizarConsumidores() {
		hayMasPalabras=false;
		notifyAll();
	}
	
	public synchronized void producir(String palabra) {
		while(siguientePalabra!=null) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		siguientePalabra=palabra;
		notifyAll();		
	}
	
	public synchronized String consumir() throws NoMasPalabrasException {
		while(siguientePalabra==null) {
			if (!hayMasPalabras) {
				throw new NoMasPalabrasException();
			}
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String auxPalabra=siguientePalabra;
		siguientePalabra=null;
		notifyAll();
		return auxPalabra;		
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
