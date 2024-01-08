package ejemplo_synchronized;

public class HiloEjercicio4 implements Runnable {

	
	@Override
	public void run() {
		Datos.getInstance().funcion();
		
		//datos.aumentarDato(10);

	}

}
