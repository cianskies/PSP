package principal;

import java.io.IOException;

import encriptadorDesencriptador.EncriptadorDesencriptador;
import limpiador.Limpiador;

public class Principal {
	private static int nHilos;
	private static String rutaTexto;
	private static String clave;
	public static void main(String[] args) {
		try {
			extraerValoresDeConfiguracion();
			//Limpiar Archivo
			Limpiador.limpiarArchivo(rutaTexto, nHilos);
			EncriptadorDesencriptador encriptadorDesencriptador = new EncriptadorDesencriptador(nHilos,clave);
			//Encriptar
			encriptadorDesencriptador.encriptar();
			//Desencriptar
			encriptadorDesencriptador.desencriptar();
		} catch (NumberFormatException e) {
			System.out.println("ERROR: El n�mero de hilos tiene que ser un valor entero positivo.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArchivoConfiguracionIncorrectoException e) {
			e.printStackTrace();
		}
	}
	//Esta funci�n asigna los valores del archivo configuraci�n
	private static void extraerValoresDeConfiguracion() throws NumberFormatException, IOException, ArchivoConfiguracionIncorrectoException {
		LectorConfiguracion lector = new LectorConfiguracion();
		nHilos = lector.getNHilos();
		rutaTexto = lector.getRutaTexto();
		clave = lector.getClave();
	}

}
