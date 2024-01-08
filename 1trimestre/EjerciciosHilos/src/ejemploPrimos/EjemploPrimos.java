package ejemploPrimos;

import java.sql.Time;

public class EjemploPrimos {

	public static void main(String[] args) {
		long numero = 95457911l*95457912l;
		int nHilos=6;
		Thread[] hilos=new Thread[nHilos];
		long antes = System.currentTimeMillis();
		Datos datos=new Datos(numero,nHilos);
		for (int i=0;i<nHilos;++i) {
			hilos[i]=new Thread(new HiloEjemploPrimos(datos,numero));
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
		
		long despues=System.currentTimeMillis();
		
		if (datos.getEsPrimo()) {
			System.out.println("El número "+numero+" es primo");
		}
		else {
			System.out.println("El número "+numero+" no es primo");
		}
		
		System.out.println("He tardado "+(despues-antes)/1000f);
		
	}

}
