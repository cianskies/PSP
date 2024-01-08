package ejercicio2;

public class Ejercicio2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (int i=0;i<4;++i) {
			Thread hilo = new Thread(new HiloEjercicio2());
			hilo.start();
		}
		
	}

}
