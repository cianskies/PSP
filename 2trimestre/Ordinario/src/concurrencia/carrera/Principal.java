package concurrencia.carrera;

public class Principal {
	
	private static final int DISTANCIA_MAX=100;
	private static final int NUM_CORREDORES=4;
	public static void main(String[] args) {
		int idContador=1;
		Datos d=new Datos(DISTANCIA_MAX, NUM_CORREDORES);
		
		Thread[] corredores=new Thread[NUM_CORREDORES];
		for(int i=0;i<NUM_CORREDORES;++i) {
			corredores[i]=new Thread(new HiloCorredor(d,idContador));
			corredores[i].start();
			++idContador;
		}
		for(int i=0;i<NUM_CORREDORES;++i) {
			try {
				corredores[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
