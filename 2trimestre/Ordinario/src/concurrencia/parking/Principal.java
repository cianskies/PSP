package concurrencia.parking;

public class Principal {
	
	private static final int NUMERO_PLAZAS=4;
	private static final int NUMERO_COCHES=12;
	private static final int ITERACIONES_MAX=10;
	private static int contadorIDCoches=1;
	private static Datos datos;
	
	public static void main(String[] args) {
		datos= new Datos(NUMERO_PLAZAS);
		
		Thread[] hilosCoche=new Thread[NUMERO_COCHES];
		for(int i=0;i<NUMERO_COCHES;++i) {
			Thread h=new Thread(new HiloCoche(contadorIDCoches, datos, ITERACIONES_MAX));
			hilosCoche[i]=h;
			++contadorIDCoches;
			}
		
		
		for(int i=0;i<NUMERO_COCHES;++i) {
			hilosCoche[i].start();
			
		}

	}
}