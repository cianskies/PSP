package encriptadorDesencriptador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatosEncriptar {
	private String texto;
	private StringBuilder constructorTextoEncriptado;
	private int posicionCaracterTexto;
	private int idMaximo;
	private int contadorHilosCadenaObtenida;
	private int contadorHilosCadenaAnhadida;
	public DatosEncriptar(String rutaArchivo,int nHilos) throws IOException {
		leerArchivo(rutaArchivo);
		this.idMaximo = nHilos-1;
		posicionCaracterTexto = 0;
		contadorHilosCadenaObtenida=0;
		contadorHilosCadenaAnhadida=0;
		constructorTextoEncriptado = new StringBuilder();
	}
	//Esta funci�n lee el archivo y guarda el texto
	private void leerArchivo(String rutaArchivo) throws IOException {
		texto = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
	}
	//Esta funci�n sincronizada permite a los hilos recoger una cadena de determinada longitud ordenadamente
	public synchronized String getCadena(int idHilo,int longitud) throws NoMasCadenasException {
		//!isFinalTexto() en el bucle esta justificado, para varios hilos con texto peque�o
		//y claves largas, en algunas ejecuciones el �ltimo hilo en iniciarse
		//cuando es ejecutado el isFinalTexto ya es true en el run porque los otros hilos
		//ya cogieron todo el texto posible, por lo tanto nunca entra y no cambia el contador
		//quedandose los otros hilos en wait eterno, esto evita que ocurra
		while(contadorHilosCadenaObtenida!=idHilo && !isFinalTexto()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		comprobarSiNoHayMasCadenas();
		String cadena = devolverCadenaCortada(longitud);
		cambiarContadorHilosCadenaObtenida();
		aumentarPosicionCaracterTexto(longitud);		
		//Una vez obtenida la cadena, aumentado el contador y el cursor de la posici�n en el texto
		//todos los hilos son notificados
		notifyAll();
		return cadena;
	}
	//Esta funci�n comprueba si no hay m�s cadenas
	private void comprobarSiNoHayMasCadenas() throws NoMasCadenasException {
		//Si el texto termina salta excepci�n
		if(isFinalTexto()) {
			notifyAll();
			throw new NoMasCadenasException();
		}
	}
	//Esta funci�n devuelve una cadena cortada del texto
	private String devolverCadenaCortada(int longitud) {
		int posicionFinal = posicionCaracterTexto + longitud;
		//Si posicionFinal llega al l�mite se corrige
		if( posicionFinal >= texto.length()) {
			posicionFinal = texto.length();
		}
		return texto.substring(posicionCaracterTexto,posicionFinal);
	}
	//Esta funci�n aumenta la posicion del caracter del texto
	private void aumentarPosicionCaracterTexto(int longitud) {
		//Se aumenta la posicion en el texto
		posicionCaracterTexto+=longitud;
		if(posicionCaracterTexto>=texto.length())
		{
			posicionCaracterTexto = texto.length();
		}
	}
	//Esta funci�n cambia la variable que indica el orden de los hilos al obtener una cadena
	private void cambiarContadorHilosCadenaObtenida() {
		if(contadorHilosCadenaObtenida==idMaximo) {
			contadorHilosCadenaObtenida = 0;
		}
		else {
			++contadorHilosCadenaObtenida;
		}
	}
	//Esta funci�n sincronizada a�ade una cadena encriptada al String Builder ordenadamente por id
	public synchronized void anhadirCadenaEncriptada(String cadena,int idHilo) {
		while(contadorHilosCadenaAnhadida!=idHilo) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		constructorTextoEncriptado.append(cadena);
		cambiarContadorHilosCadenaAnhadida();		
		notifyAll();
	}
	//Esta funci�n cambia la variable que indica el orden de los hilos al a�adir una cadena
	private void cambiarContadorHilosCadenaAnhadida() {
		if(contadorHilosCadenaAnhadida==idMaximo) {
			contadorHilosCadenaAnhadida=0;
		}
		else {
			++contadorHilosCadenaAnhadida;
		}
	}
	public boolean isFinalTexto() {
		return posicionCaracterTexto >= texto.length();
	}
	public String getTextoEncriptado() {
		return constructorTextoEncriptado.toString();
	}
}
