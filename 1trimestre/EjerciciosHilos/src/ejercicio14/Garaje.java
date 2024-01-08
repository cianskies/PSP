package ejercicio14;

public class Garaje {

	private String[] plazas;
	private static final String LIBRE = "L";
	
	public Garaje(int nPlazas) {
		plazas=new String[nPlazas];
		abrirGaraje();
	}
	
	private void abrirGaraje() {
		for (int i=0;i<plazas.length;++i) {
			plazas[i]=LIBRE;
		}
	}
	
	public synchronized int aparcar(String idCoche) {
		//Miramos a ver si hay plaza libre.
		int plaza=esperarTurnoParaAparcar();
		plazas[plaza]=idCoche;
		mostrarGaraje();
		return plaza;
	}
	
	private synchronized int esperarTurnoParaAparcar() {
		boolean aparcamientoEncontrado=false;
		int plaza=0;
		do {
			try {
				plaza=buscarPlazaLibre();
				aparcamientoEncontrado=true;
			}
			catch(NoHayPlazasLibresException e){
				try {
					wait();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}while(!aparcamientoEncontrado);
		return plaza;
	}
	
	private synchronized int buscarPlazaLibre() throws NoHayPlazasLibresException{
		boolean plazaLibreEncontrada=false;
		int plazaLibre=0;
		for (int i=0;i<plazas.length && !plazaLibreEncontrada;++i) {
			if (plazas[i].equals(LIBRE)) {
				plazaLibre=i;
				plazaLibreEncontrada=true;
			}
		}
		if (!plazaLibreEncontrada) {
			throw new NoHayPlazasLibresException();
		}
		return plazaLibre;
	}
	
	public synchronized void salirDelParking(String idCoche, int plaza) {
		plazas[plaza]=LIBRE;
		mostrarGaraje();
		notify();		
	}
	
	private synchronized void mostrarGaraje() {
		for (int i=0;i<plazas.length;++i) {
			System.out.println("Plaza "+i+": "+plazas[i]);
		}
		System.out.println("************************************");
	}
	
	
	
}
