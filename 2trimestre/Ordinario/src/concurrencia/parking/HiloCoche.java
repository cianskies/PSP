package concurrencia.parking;

import java.util.Random;

public class HiloCoche implements Runnable{
	
	private boolean seguir;
	private final int NO_HAY_PLAZA=888;
	private Random random;
	private int contadorIteraciones;
	
	private int id;
	private Datos datos;
	private final int ITERACIONES_MAX;
	public HiloCoche(int id, Datos datos, int ITERACIONES_MAX) {
		this.id=id;
		this.datos=datos;
		this.ITERACIONES_MAX=ITERACIONES_MAX;
		this.seguir=true;
		this.random=new Random();
		contadorIteraciones=0;
	}
	@Override
	public void run() {
		while(seguir) {
			esperarTiempoRandom();
			int plazaAOcupar=datos.repartirPlazaLibre(id);
			datos.ocuparPlazaLibre(plazaAOcupar, id);
			System.out.println("("+id+") ha ocuapdo la plaza "+plazaAOcupar);
			esperarTiempoRandom();
			datos.desOcuparPlaza(plazaAOcupar);
			System.out.println("("+id+") ha abandonado la plaza "+plazaAOcupar);
			++contadorIteraciones;
			if(contadorIteraciones>=ITERACIONES_MAX) {
				seguir=false;
			}
		}
		System.out.println("("+id+") ha terminado");
	}
	private void esperarTiempoRandom() {
		int segundos=random.nextInt(5, 6);
		//System.out.println("Procedo a esperar "+segundos+ "segundos ("+id+")");
		try {
			Thread.sleep((long)segundos*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Me despierto!! ("+id+")");
	}

}
