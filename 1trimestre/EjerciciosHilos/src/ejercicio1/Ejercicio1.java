package ejercicio1;

public class Ejercicio1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread hilo = new Thread(new HiloEjercicio1());
		hilo.start();
	}

}
