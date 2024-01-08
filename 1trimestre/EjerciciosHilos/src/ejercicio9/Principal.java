package ejercicio9;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int longitudCarrera = 30;
		int nHilos = 3;
		Datos datos = new Datos(longitudCarrera,nHilos);
		Thread[] hilos=new Thread[nHilos];
		for (int i=0;i<hilos.length;++i) {
			hilos[i]=new Thread(new HiloEjercicio9(datos,i));
			hilos[i].start();
		}
		
		for (int i=0;i<hilos.length;++i) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		datos.mostrarGanador();
	}

}
