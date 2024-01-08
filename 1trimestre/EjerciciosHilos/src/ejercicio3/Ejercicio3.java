package ejercicio3;

public class Ejercicio3 {

	public static void main(String[] args) {
		
		String vocales = "aeiou";
		String texto = "En un lugar de La Mancha, de cuyo nombre no quiero acordarme...";
		Thread[] hilos=new Thread[vocales.length()];
		for (int i=0;i<vocales.length();++i) {
			hilos[i] = new Thread(new HiloContadorVocales(vocales.charAt(i),texto.toLowerCase()));
			hilos[i].start();
		}
		
		for (int i=0;i<vocales.length();++i) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println(HiloContadorVocales.nVocales);
	}

}
