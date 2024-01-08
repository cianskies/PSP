package ejercicio1;

public class HiloEjercicio1 implements Runnable {

	//Una variable estática se puede utilizar para coordinar
	//hilos. No obstante, puede provocar fallos (no es muy
	//probable, pero tampoco es raro)
	private static int profundidad = 0;
	
	
	/*public HiloEjercicio1(int profundidad) {
		this.profundidad=profundidad;
	}*/
	
	@Override
	public void run() {
		if (profundidad<5) {
			++profundidad;
			Thread hilo = new Thread(new HiloEjercicio1());
			hilo.start();
		}
		
		for (int i=0;i<10;++i) {
			System.out.println("Soy "+Thread.currentThread().getName());
		}

	}

}
