package ejercicio3;

public class HiloContadorVocales implements Runnable {

	public static int nVocales=0;
	private char vocal;
	private String texto;
	
	public HiloContadorVocales(char vocal, String texto) {
		this.vocal=vocal;
		this.texto=texto;
	}
	
	@Override
	public void run() {
		int contador=0;
		for (int i=0;i<texto.length();++i) {
			if (texto.charAt(i)==vocal) {
				++contador;
			}
		}
		nVocales+=contador;
	}
}
