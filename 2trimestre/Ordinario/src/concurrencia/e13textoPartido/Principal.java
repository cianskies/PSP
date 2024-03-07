package concurrencia.e13textoPartido;


public class Principal {
	private static final String RUTA="quijote.txt";
	private static final int NUM_HILOS=5;
	public static void main(String[] args) {
		
		Datos d=new Datos(RUTA,NUM_HILOS);
		int idContador=1;

		
		Thread[] corredores=new Thread[NUM_HILOS];
		for(int i=0;i<NUM_HILOS;++i) {
			corredores[i]=new Thread(new HiloAnalizadorTexto(idContador,d));
			++idContador;
			corredores[i].start();

		}
		for(int i=0;i<NUM_HILOS;++i) {
			try {
				corredores[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		d.imprimirTXT();
	}

}
