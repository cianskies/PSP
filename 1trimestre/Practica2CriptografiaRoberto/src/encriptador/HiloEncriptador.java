package encriptador;


public class HiloEncriptador implements Runnable{
	
	private boolean encriptar;
	private String palabra;
	private String clave;
	private int numeroIteracion;
	private String alfabeto;
	private char[] caracteresDePalabra;
	private DatosEncriptador d;
	private int id;
	
	public HiloEncriptador(int id, boolean encriptar, String clave,DatosEncriptador d) {
		this.id=id;
		this.encriptar=encriptar;
		this.clave=clave;
		alfabeto="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		this.d=d;
	}
	@Override
	public void run() {
		boolean salir=false;
		while(!salir){
			try {
				palabra=d.recibirPalabra(clave.length(),id);
				//numeroIteracion=d.recibirNumeroIteracion(id);

					palabra=EncriptarPalabra();
				d.anhadirPalabra(palabra,id);

			} catch (TextoVacioException e) {
				// TODO Auto-generated catch block
				salir=true;
			}
			
			
			
		}
		
	}
	private String EncriptarPalabra() {
		String encriptada="";
		caracteresDePalabra=palabra.toCharArray();
		int indiceClave=0;
		int posicionABCletra, posicionABCclave, posicionNueva=0;
		for(int i=0;i<caracteresDePalabra.length;++i) {
			posicionABCletra=caracteresDePalabra[i]-'A';
			posicionABCclave=clave.charAt(indiceClave)-'A';
			++indiceClave;
			if(indiceClave>=clave.length()) {
				indiceClave=0;
			}
			if(encriptar) {
				posicionNueva=posicionABCletra+posicionABCclave;
			}else {
				posicionNueva=posicionABCletra-posicionABCclave;
			}
			while(posicionNueva>25) {
				posicionNueva=posicionNueva-26;
			}while(posicionNueva<0) {
				posicionNueva=26+posicionNueva;
				
			}
			caracteresDePalabra[i]=alfabeto.charAt(posicionNueva);
			encriptada+=caracteresDePalabra[i];
		}	
		
		return encriptada;
	}
	

}