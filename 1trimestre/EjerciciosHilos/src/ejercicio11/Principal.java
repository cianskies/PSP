package ejercicio11;

public class Principal {

	public static void main(String[] args) {
		int nHilos=6;
		long totalTiradas = 1000000000;
		Datos datos = new Datos(totalTiradas,nHilos);
		Thread[] hilos = new Thread[nHilos];
		for (int i=0;i<hilos.length;++i) {
			hilos[i]=new Thread(new Hilo(datos));
			hilos[i].start();
		}
		long antes = System.currentTimeMillis();
		for (int i=0;i<hilos.length;++i) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long despues = System.currentTimeMillis();
		System.out.println("PI vale: "+datos.getEstimacionDePi());
		System.out.println("Tiempo consumido: "+(float)(despues-antes)/1000+" segundos");
	}

}
