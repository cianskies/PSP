package ejemploPrimos;

public class Datos {

	
	private boolean esPrimo=true;
	private long numero;
	private double raiz;
	private int nHilos;
	private long limiteInferior;
	private long limiteSuperior;
	private int contadorHilos;

	
	public Datos(long numero,int nHilos) {
		this.numero=numero;
		raiz=Math.sqrt(numero);
		this.nHilos=nHilos;
		limiteInferior=2;
		limiteSuperior=(long)(raiz/nHilos);
		contadorHilos=1;
	}
	
	public synchronized long[] pedirLimites() {
		++contadorHilos;
		long[] limites=new long[] {limiteInferior,limiteSuperior};
		limiteInferior=limiteSuperior+1;
		limiteSuperior=(long)(contadorHilos*raiz/nHilos);
		System.out.println("Mandando límites: "+limites[0]+" "+limites[1]);
		return limites;		
	}
	
	public synchronized boolean getEsPrimo() {
		return esPrimo;
	}

	public synchronized void setEsPrimo(boolean esPrimo) {
		this.esPrimo = esPrimo;
	}
	
	
	
}
