package ejercicio5;

public class HiloEjercicio5 implements Runnable {

	public enum Tipo {SumaPares,SumaImpares,SumaAcaban2o3}
	
	private Tipo tipo;
	private Datos datos;
	
	public HiloEjercicio5(Tipo tipo, Datos datos) {
		this.tipo=tipo;
		this.datos=datos;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		switch(tipo) {
		case SumaPares:
			calcularSumaPares();
			break;
		case SumaImpares:
			calcularSumaImpares();
			break;
		case SumaAcaban2o3:
			calcularSumaAcaban2o3();
			break;
		}
	}
	
	private void calcularSumaPares() {
		int suma=0;
		for (int i=2;i<=1000;i+=2) {
			suma+=i;
		}
		datos.setSumaPares(suma);
	}
	
	private void calcularSumaImpares() {
		int suma=0;
		for (int i=1;i<1000;i+=2) {
			suma+=i;
		}
		datos.setSumaImpares(suma);
	}
	
	private void calcularSumaAcaban2o3() {
		int suma=0;
		for (int i=2;i<1000;++i) {
			int modulo=i%10;
			if (modulo==2 || modulo==3) {
				suma+=i;
			}
		}
		datos.setSumaAcaban2o3(suma);
	}

}
