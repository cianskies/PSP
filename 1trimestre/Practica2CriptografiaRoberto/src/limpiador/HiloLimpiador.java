package limpiador;

import java.text.Normalizer;

public class HiloLimpiador implements Runnable{
		private Datos datos;
		private int id;
		private String trozo;
		public HiloLimpiador(Datos datos,int id) {
			this.datos=datos;
			this.id=id;
		}
		@Override
		public void run() {
			trozo=datos.recibirTrozoDeTexto(id);
			trozo=trozo.toUpperCase();
			trozo=Normalizer.normalize(trozo, Normalizer.Form.NFD);
			trozo=trozo.replaceAll("[^A-Z ]", " ");
			trozo=trozo.replaceAll("\\s+","");

			datos.darTrozo(trozo,id);
			
			
		}

	
}
