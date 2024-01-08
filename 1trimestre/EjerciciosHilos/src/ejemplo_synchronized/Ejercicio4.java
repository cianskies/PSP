package ejemplo_synchronized;

public class Ejercicio4 {

	public static void main(String[] args) {
		Thread[] hilos =new Thread[5];
		for (int i=0;i<5;++i) {
			hilos[i] = new Thread(new HiloEjercicio4());
			hilos[i].start();			
		}

		for (int i=0;i<5;++i) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("suma: "+Datos.getInstance().getSuma());
	}

}
