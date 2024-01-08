package ejercicio9;

import java.util.Random;

public class HiloEjercicio9 implements Runnable {

	private Datos datos;
	private int posicion;
	private int id;
	
	public HiloEjercicio9(Datos datos, int id) {
		this.datos=datos;
		this.id=id;
		posicion=0;
	}
	
	@Override
	public void run() {
		boolean salir;
		Random generador = new Random();
		do {
			posicion += generador.nextInt(6)+1;
			//Mandamos a datos nuestra nueva posición,
			//y datos nos devuelve si tenemos que terminar o no.
			salir=datos.determinarFinalDeCarrera(id, posicion);
		}while(!salir);
		

	}

}
