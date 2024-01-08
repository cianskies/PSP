package ejemploPrimos;

public class HiloEjemploPrimos implements Runnable {

	private long numero;
	private Datos datos;
	
	public HiloEjemploPrimos(Datos datos,long numero) {
		this.datos=datos;
		this.numero=numero;
	}
	
	@Override
	public void run() {
		long[] limites=datos.pedirLimites();
		boolean esPrimo=true;
		for (long i=limites[0];i<=limites[1] && esPrimo;++i) {
			if (numero%i==0) {
				esPrimo=false;
				//Si no es primo, lo guardamos en datos
				datos.setEsPrimo(esPrimo);
			}
		}
	}

}
