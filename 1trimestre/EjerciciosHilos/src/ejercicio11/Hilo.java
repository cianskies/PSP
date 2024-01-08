package ejercicio11;

import java.util.Random;

public class Hilo implements Runnable {

	private Datos datos;
	private Random generador;
	
	public Hilo(Datos datos) {
		this.datos=datos;
		generador=new Random();
	}
	
	@Override
	public void run() {
		long totalTiradas = datos.getTiradasPorHilo();
		long aciertos=0;
		System.out.println("Tengo que hacer "+totalTiradas+" tiradas");

		double[] tirada;
		for (int i=0;i<totalTiradas;++i) {
			//Elegimos un par de coordenadas al azar entre 0 y 1
			tirada = generarTirada();
			//Verificamos si está dentro del círculo
			if (estaDentroDelCirculo(tirada)) {
				//Si está, lo acumulamos como acierto
				++aciertos;
			}
		}
		//Guardamos todos los aciertos en datos
		datos.guardarResultados(aciertos);
	}
	
	private double[] generarTirada() {
		double[] coordenadas = new double[2];
		coordenadas[0]=generador.nextDouble()-0.5;
		coordenadas[1]=generador.nextDouble()-0.5;
		return coordenadas;
	}
	
	private boolean estaDentroDelCirculo(double[] tirada) {
		return Math.sqrt(Math.pow(tirada[0], 2)+Math.pow(tirada[1], 2))<=0.5;
		
	}
	
	

}
