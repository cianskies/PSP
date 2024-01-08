package ejercicio9;

public class Datos {

	private int longitudMaxima;
	private int nHilos;
	private int[] posiciones;
	private int hilosQueHanAvanzado;
	private int ganador;
	private boolean finDeCarrera;
	
	public Datos(int longitudMaxima, int nHilos) {
		this.longitudMaxima=longitudMaxima;
		this.nHilos=nHilos;
		posiciones=new int[nHilos];
		hilosQueHanAvanzado=0;
		finDeCarrera=false;
	}
	
	public synchronized boolean determinarFinalDeCarrera(int idHilo, int posicion) {
		posiciones[idHilo]=posicion;
		++hilosQueHanAvanzado;
		if (hilosQueHanAvanzado<nHilos) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			//Buscamos el hilo que más ha avanzado
			int maximoAvance=0;
			int idMaximoAvance=0;
			for (int i=0;i<posiciones.length;++i) {
				if (posiciones[i]>maximoAvance) {
					maximoAvance=posiciones[i];
					idMaximoAvance=i;
				}
			}
			if(maximoAvance>=longitudMaxima) {
				ganador=idMaximoAvance;
				finDeCarrera=true;
			}
			hilosQueHanAvanzado=0;
			notifyAll();
		}
		return finDeCarrera;
	}
	
	public void mostrarGanador() {
		System.out.println("Y el ganador es: "+ganador);
		for (int i=0;i<posiciones.length;++i) {
			System.out.println("El animal "+i+" ha llegado a "+posiciones[i]+" metros");
		}
	}
	
}
