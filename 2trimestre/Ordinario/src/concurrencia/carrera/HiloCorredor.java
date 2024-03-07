package concurrencia.carrera;

public class HiloCorredor implements Runnable {
	
	private int id;
	private Datos d;
	
	private int posicionActual;
	private boolean seguir;
	
	public HiloCorredor(Datos d, int id) {
		this.d=d;
		this.id=id;
		seguir=true;
	}
	@Override
	public void run() {
		while(seguir) {
			//conseguir posicion a avanzar random
			int recorrido=d.obtenerDistanciaRandom();
			System.out.println("Soy el hilo "+id+" y recorro "+recorrido+" metros");
			//registra su posicion en datos
			d.registrarPosicionActual(id,recorrido);
			//comprueba si puede seguir 
			seguir=d.puedeSeguirCorredor(id);
		}

	}

}
