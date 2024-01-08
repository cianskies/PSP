package ejercicio13c;


public class Principal {

	public static void main(String[] args) {
		
		int nHilos=2;
		String palabra="culo";
		Datos datos = new Datos();
		Thread[] hilos = new Thread[nHilos];
		Thread productor=new Thread(new HiloProductor(datos,"libro.txt"));
		productor.start();
		for (int i=0;i<nHilos;++i) {
			hilos[i]=new Thread(new HiloConsumidor(datos,palabra));
			hilos[i].start();
		}
		
		for (int i=0;i<nHilos;++i) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		datos.mostrarResultados();
	}

}
