package ejercicio4;

public class HiloEjercicio4 implements Runnable {

	private Datos datos;
	
	public HiloEjercicio4(Datos datos) {
		this.datos=datos;
	}
	
	@Override
	public void run() {
		for (int i=0;i<5;++i) {
			System.out.println("Soy "+Thread.currentThread().getName()+" iteración "+i);
			datos.pasarTurno(i!=4);
		}

	}

}
