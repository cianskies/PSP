package ejercicio3.programa1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.util.Base64.Encoder;

public class GeneradorDeHash {

	public static void main(String[] args) {
		String cadena=pedirCadenaAlUsuario();
		escribirEnArchivoDeTexto(cadena, "original.txt");
		byte[] hash = generarHash(cargarArchivoComoArrayDeBytes("original.txt"));
		escribirEnArchivo(hash, "hash.txt");
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

	
	private static String pedirCadenaAlUsuario() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Escribe una frase:");
		return sc.nextLine();
	}
	
	private static void escribirEnArchivo(byte[] contenido, String ruta) {
		String contenidoEnString = codificarEnBase64(contenido);
		escribirEnArchivoDeTexto(contenidoEnString,ruta);
	}
	
	private static void escribirEnArchivoDeTexto(String cadena,String ruta) {
		Path rutaEnPath = Paths.get(ruta); 
		try {
			if (!Files.exists(rutaEnPath)) {
				Files.createFile(Paths.get(ruta));
			}
			BufferedWriter bw = Files.newBufferedWriter(rutaEnPath);
			bw.write(cadena);
			bw.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	private static String codificarEnBase64(byte[] contenido) {
		Encoder codificador = Base64.getEncoder();
		String contenidoCifrado = codificador.encodeToString(contenido);
		return contenidoCifrado;
	}
}
