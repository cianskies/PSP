package concurrencia.parking;

public class Datos {
	
	private int numeroPlazas;
	private final int LIBRE=999;
	private final int NO_HAY_PLAZA=888;
	private int[] plazas;
	
	public Datos(int numeroPlazas) {
		this.numeroPlazas=numeroPlazas;
		plazas=new int[numeroPlazas];
		abrirPlazas();
	}
	
	public synchronized int repartirPlazaLibre(int idCoche) {
		int plazaLibre=NO_HAY_PLAZA;
		boolean plazaLibreEcontrada=false;
		while(!plazaLibreEcontrada) {
			for(int i=0;i<plazas.length&&!plazaLibreEcontrada;++i) {
				if(plazas[i]==LIBRE) {
					plazaLibre=i;
					plazaLibreEcontrada=true;
				}
			}
			if(!plazaLibreEcontrada) {
				try {
					System.out.println("("+idCoche+")no encontre plaza");
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return plazaLibre;
	}
	
	public synchronized void ocuparPlazaLibre(int numeroPlaza, int idCoche) {
		plazas[numeroPlaza]=idCoche;
		imprimirIteracion();
		
	}
	public synchronized void desOcuparPlaza(int numeroPlaza) {
		plazas[numeroPlaza]=LIBRE;
		notifyAll();
		imprimirIteracion();
		
	}
	
	public void imprimirIteracion() {
		for(int i=0;i<plazas.length;++i) {
			System.out.print('#');
			System.out.print(i);
		}
		System.out.println("\n");
		for(int i=0;i<plazas.length;++i) {
			System.out.print('#');
			System.out.print(' ');
		}
		System.out.println("\n");
		for(int i=0;i<plazas.length;++i) {
			System.out.print('#');
			if(plazas[i]==LIBRE) {
				System.out.print('L');
			}else {
				System.out.print(plazas[i]);
			}
		}
		System.out.println("\n");
	}
	private void abrirPlazas() {
		for(int i=0;i<plazas.length;++i) {
			plazas[i]=LIBRE;
			}
	}
}
