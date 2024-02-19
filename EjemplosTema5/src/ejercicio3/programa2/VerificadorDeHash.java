package ejercicio3.programa2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class VerificadorDeHash {

	public static void main(String[] args) {
		byte[] informacionSospechosa=cargarArchivoComoArrayDeBytes("original.txt");
		byte[] hashSospechoso = generarHash(informacionSospechosa);
		String hashSospechosoEnString = codificarEnBase64(hashSospechoso);
		
		String hashDeVerdad=cargarArchivo("hash.txt");
		
		//Tenemos que comparar el hashSospechoso con el hashDeVerdad.
		//Si son iguales, es que la información que nos ha llegado no
		//ha sido manipulada.
		if (hashDeVerdad.equals(hashSospechosoEnString)) {
			System.out.println("El archivo no ha sido manipulado");
		}
		else {
			System.out.println("¡Atención! ¡Te han jakiado!");
		}
	}

	
	private static byte[] cargarArchivoComoArrayDeBytes(String ruta) {
		byte[] resultado=null;
		try {
			resultado=Files.readAllBytes(Paths.get(ruta));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	private static String cargarArchivo(String ruta) {
		String resultado="";
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get(ruta));
			resultado=br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;		
	}
	
	private static byte[] generarHash(byte[] origen) {
		byte[] resultado=null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultado = md.digest(origen);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	private static String codificarEnBase64(byte[] contenido) {
		Encoder codificador = Base64.getEncoder();
		String contenidoCifrado = codificador.encodeToString(contenido);
		return contenidoCifrado;
	}
}
