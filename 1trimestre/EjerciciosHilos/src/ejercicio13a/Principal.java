package ejercicio13a;

public class Principal {

	public static void main(String[] args) {
		int nHilos=2;
		String palabra="culo";
		Datos datos = new Datos("libro.txt",nHilos);
		Thread[] hilos = new Thread[nHilos];
		for (int i=0;i<nHilos;++i) {
			hilos[i]=new Thread(new HiloEjercicio13a(datos,palabra));
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
