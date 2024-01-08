package ejercicio4;

public class Ejercicio4 {

	public static void main(String[] args) {
		Datos datos = new Datos();
		
		Thread hilo1 = new Thread(new HiloEjercicio4(datos));
		Thread hilo2 = new Thread(new HiloEjercicio4(datos));
		
		hilo1.start();
		hilo2.start();
		
		try {
			hilo1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			hilo2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("FIN");
	}

}
