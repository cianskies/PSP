package concurrencia.e13textoPartido;

public class HiloAnalizadorTexto implements Runnable{
	private int id;
	private Datos d;
	private String trozo;
	public HiloAnalizadorTexto(int id, Datos d) {
		this.id=id;
		this.d=d;
	}
	
	@Override
	public void run() {
		trozo=d.recogerTrozo(id);
		trozo=trozo.toUpperCase();
		d.appendTrozo(id,trozo);
		
	}
}
