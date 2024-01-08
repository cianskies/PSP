package ejemplo_synchronized;

public class Datos {
	//Esta clase queremos que sea un Singleton.
	//En la ejecución del programa solamente puede haber una instancia
	//de esta clase, que además ha de ser accesible desde cualquier
	//sitio del programa.
	private static Datos instance;
	
	private int dato;
	
	private Datos() {
		dato=0;
	}
	
	private int suma=0;
	
	public synchronized void funcion(){
		
		System.out.println("Entra el hilo "+Thread.currentThread().getName());
		for (int i = 0; i < 100000; ++i){
			suma += 1;
		}
		System.out.println("Sale el hilo "+Thread.currentThread().getName());
		
	}

	public int getSuma() {
		return suma;
	}
	
	public void aumentarDato(int aumento) {
		dato+=aumento;
	}
	
	public synchronized static Datos getInstance() {
		if (instance == null) {
			instance = new Datos();
		}
		return instance;
	}
	
	
}
