package ejercicio2;

public class HiloEjercicio2 implements Runnable {

	public static int contador = 0;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i=0;i<4;++i) {
			++contador;
			System.out.println(Thread.currentThread().getName()+" "+contador);
		}
	}

}
