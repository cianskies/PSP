package encriptadorDesencriptador;

public class HiloEncriptador implements Runnable {
	public enum Encriptacion{Encriptar,Desencriptar}
	private DatosEncriptar datos;
	private int idHilo;
	private String clave;
	private Encriptacion encriptacion;
	public HiloEncriptador(DatosEncriptar datos, int idHilo, Encriptacion encriptacion, String clave) {
		this.datos=datos;
		this.idHilo=idHilo;
		//El tipo de encriptación le dice al hilo como tiene que actuar
		this.encriptacion=encriptacion;
		this.clave=clave;	
	}
	@Override
	public void run() {
		//Mientras texto no esté terminado se encripta/desencripta
		try {
			while(!datos.isFinalTexto()) {
				encriptarCadena();
			}
		}
		catch(NoMasCadenasException e) {
		}
	}
	//Esta función encripta una cadena
	private void encriptarCadena() throws NoMasCadenasException {
		char caracterEncriptado;
		StringBuilder sb = new StringBuilder();
		//Se obtiene la cadena, para que el programa sea más rápido se pasan cadenas más largas
		//de esta manera se evita que se pisen los hilos constantemente
		String cadena = datos.getCadena(idHilo,clave.length()*10);		 
		int posicionCaracterPalabraClave = 0;
		//Se recorre la cadena
		for(int i=0;i<cadena.length();++i) {
			caracterEncriptado = devolverCaracterEncriptado(cadena.charAt(i),clave.charAt(posicionCaracterPalabraClave));
			//se añade el caracter
			sb.append(caracterEncriptado);
			++posicionCaracterPalabraClave;
			if(posicionCaracterPalabraClave>=clave.length()) {
				posicionCaracterPalabraClave=0;
			}
		}
		//Se añade la cadena a datos
		datos.anhadirCadenaEncriptada(sb.toString(),idHilo);
	}
	//Esta función devuleve el caracter encriptado a partir del caracterCadena y caracterPalabra
	private char devolverCaracterEncriptado(char caracterCadena, char caracterPalabra) {
		int caracterASCII;
		if(encriptacion == Encriptacion.Encriptar) {
			caracterASCII = devolverCaracterASCIIEncriptado(caracterCadena, caracterPalabra);
			//System.out.println(caracterASCII);
		}
		else {
			caracterASCII = devolverCaracterASCIIDesencriptado(caracterCadena, caracterPalabra);
			//System.out.println(caracterASCII);
		}
		return (char)caracterASCII;
	}
	//Esta función devuelve el caracter en ASCCI encriptado
	private int devolverCaracterASCIIEncriptado(char caracterCadena, char caracterPalabra) {
		int operacion = caracterCadena+caracterPalabra -'A';
		if(operacion>'Z') {
			operacion-=26;
		}
		return operacion;
	}
	//Esta función devuelve el caracter en ASCCI desencriptado
	private int devolverCaracterASCIIDesencriptado(char caracterCadena, char caracterPalabra) {
		int operacion = caracterCadena-caracterPalabra;
		if(operacion<0) {
			operacion+=('Z'+1);
		}
		else {
			operacion+='A';
		}
		return operacion;
	}
}
