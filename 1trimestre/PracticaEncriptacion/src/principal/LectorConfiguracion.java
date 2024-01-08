package principal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LectorConfiguracion {
	private String rutaTexto;
	private int nHilos;
	private String clave;
	//Esta clase lee el archivo de Configuración "config.txt" y extrae los valores
	public LectorConfiguracion() throws IOException, NumberFormatException, ArchivoConfiguracionIncorrectoException {
		leerConfiguracion();
	}
	//Esta función lee configuración y asigna los valores
	private void leerConfiguracion() throws IOException, ArchivoConfiguracionIncorrectoException,NumberFormatException {
		File archivo = new File("config.txt");
		BufferedReader lector = new BufferedReader(new FileReader(archivo));
		//Valor ruta
		String ruta = lector.readLine();
		asignarValorRuta(ruta.split("=")[1].trim());
		//Valor número de hilos
		String numeroHilos = lector.readLine();
		asignarValorNHilos(numeroHilos .split("=")[1].trim());
		//Valor clave
		String clave =  lector.readLine();
		int posicion = clave.indexOf("=");
		String extraido = clave.substring(posicion+1);
		asignarValorClave(extraido.trim());
		lector.close();
	}
	//Esta función comprueba si el archivo existe y es un .txt, después asigna valor a ruta 
	private void asignarValorRuta(String ruta) throws ArchivoConfiguracionIncorrectoException {
		File archivo = new File(ruta);
		//Si no existe salta la excepción
		if(!archivo.exists()) {
			throw new ArchivoConfiguracionIncorrectoException(String.format("ERROR: El archivo %s no existe",ruta));
		}
		String extension = ruta.substring(ruta.length()-4,ruta.length());
		//Si no es .txt salta excepción
		if(!extension.equals(".txt")) {
			throw new ArchivoConfiguracionIncorrectoException(String.format("ERROR: El archivo %s no es .txt",ruta));
		}
		rutaTexto = ruta;
	}
	//Esta función comprueba si nHilos es entero positivo y asigna valor
	private void asignarValorNHilos(String hilos) throws NumberFormatException{
		int numeroHilos = Integer.parseInt(hilos);
		if(numeroHilos <= 0) {
			throw new NumberFormatException();
		}
		nHilos = numeroHilos;
	}
	//Esta función asigna valor a clave, primero comprueba si clave esta entre A-Z y no es nula
	private void asignarValorClave(String claveEncriptacion) throws ArchivoConfiguracionIncorrectoException {		
		if(claveEncriptacion.isEmpty()) {
			throw new ArchivoConfiguracionIncorrectoException("ERROR: La clave de Encriptación no puede "
					+ "ser nula");
		}
		claveEncriptacion = claveEncriptacion.toUpperCase();
		for(int i = 0;i<claveEncriptacion.length();++i) {
			if(claveEncriptacion.charAt(i)>='A' && claveEncriptacion.charAt(i)<='Z') {
				//Si esta en el intervalo continua el bucle, si no está salta execpción
			}
			else {
				throw new ArchivoConfiguracionIncorrectoException("ERROR: La clave de Encriptación contiene "
																+ "caracteres fuera del intervalo A-Z");
			}
		}
		clave = claveEncriptacion;
	}
	public String getRutaTexto() {
		return rutaTexto;
	}
	public int getNHilos() {
		return nHilos;
	}
	public String getClave() {
		return clave;
	}
}

