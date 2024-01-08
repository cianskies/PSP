package ejercicio11;

public class Datos {
	private long totalAciertos;
	private long totalTiradas;
	private int totalHilos;
	private int contadorHilos;
	
	public Datos(long totalTiradas, int totalHilos) {
		this.totalTiradas=totalTiradas;
		this.totalHilos = totalHilos;
		totalAciertos=0;
		contadorHilos=0;
	}
	
	public synchronized long getTiradasPorHilo() {
		long tiradasPorHilo=totalTiradas/totalHilos;
		++contadorHilos;
		if (contadorHilos==totalHilos) {
			tiradasPorHilo+=totalTiradas-tiradasPorHilo*totalHilos;
		}
		return tiradasPorHilo;
	}
	
	public synchronized void guardarResultados(long aciertos) {
		totalAciertos+=aciertos;
	}
	
	public double getEstimacionDePi() {
		return 4*(double)totalAciertos/totalTiradas;
	}
	
	
}
