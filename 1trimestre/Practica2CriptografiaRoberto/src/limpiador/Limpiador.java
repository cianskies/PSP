package limpiador;

public class Limpiador {
	
	private String rutaArchivo;
	private int numeroLimpiadores;
	private String textoLimpio;
	private Datos datos;
	
	public Limpiador(String rutaArchivo, int numeroLimpiadores) {
		this.rutaArchivo=rutaArchivo;
		this.numeroLimpiadores=numeroLimpiadores;
		datos=montarDatos();
		indicarLimpiadores();
		textoLimpio=datos.recibirTextoLimpio();
		
		
	}
	private Datos montarDatos() {
		Datos datos=new Datos(rutaArchivo, numeroLimpiadores);
		return datos;
	}
	private void indicarLimpiadores() {
		Thread[] limpiadores=new Thread[numeroLimpiadores];
		for(int i=0;i<numeroLimpiadores;++i) {
			limpiadores[i]=new Thread(new HiloLimpiador(datos,i));
			limpiadores[i].start();
		}
		
		for(int i=0;i<numeroLimpiadores;++i) {
			try {
				limpiadores[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	}
	public String getTextoLimpio(){
		return textoLimpio;
	}

}
