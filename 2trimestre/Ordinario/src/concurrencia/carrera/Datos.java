package concurrencia.carrera;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Datos {
	private int longitudMax;
	private int numCorredores;
	private int hilosQueHanAvanzado;
	private int idGanador;
	private boolean seguir;
	private Random random;
	
	private HashMap<Integer,Integer> posicionCorredores;
	
	public Datos(int longitud, int numCorredores) {
		this.longitudMax=longitud;
		this.numCorredores=numCorredores;
		posicionCorredores=new HashMap();
		random=new Random();
		seguir=true;
	}
	
	public synchronized void registrarPosicionActual(int id, int recorrido) {
		if(posicionCorredores.containsKey(id)) {
			int posicion=posicionCorredores.get(id);
			System.out.println(id+ "esta en la posicion "+posicion);
			posicionCorredores.put(id, posicion+recorrido);
		}else {
			posicionCorredores.put(id, recorrido);
		}

		++hilosQueHanAvanzado;
		
		notifyAll();
		
		
	}
	public synchronized boolean puedeSeguirCorredor(int id) {
		
		if(hilosQueHanAvanzado<numCorredores){
			try {
				wait();
				System.out.println("me toca esperar "+id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}	
		hilosQueHanAvanzado=0;
		for(Map.Entry<Integer, Integer> entry:posicionCorredores.entrySet()){
		{
			if(entry.getValue()>=longitudMax) {
				idGanador=entry.getKey();
				System.out.println("Ha ganado "+idGanador);
				seguir=false;
			}
		}
			
		}
		notifyAll();
		return seguir;
	}
	public int obtenerDistanciaRandom() {
		return random.nextInt(2, 4);
	}

}
