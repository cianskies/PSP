package ejercicio4;

public class Datos {

	
	
	public synchronized void pasarTurno(boolean dormir) {
		//Primero despertamos al otro hilo
		notify();
		if (dormir) {
			try {
				//A continuación, nos dormimos nosotros
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
