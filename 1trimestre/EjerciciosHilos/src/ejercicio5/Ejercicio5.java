package ejercicio5;

public class Ejercicio5 {

	public static void main(String[] args) {
		Datos datos=new Datos();
		Thread[] hilos=new Thread[3];
		hilos[0]= new Thread(new HiloEjercicio5(HiloEjercicio5.Tipo.SumaPares,datos));
		hilos[1]= new Thread(new HiloEjercicio5(HiloEjercicio5.Tipo.SumaImpares,datos));
		hilos[2]= new Thread(new HiloEjercicio5(HiloEjercicio5.Tipo.SumaAcaban2o3,datos));
		
		for (int i=0;i<hilos.length;++i) {
			hilos[i].start();
		}
		
		for (int i=0;i<hilos.length;++i) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		datos.mostrarResultados();
	}

}
