package ejemploMessageDigest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class VerificadorClaveHash {
	
	public static void main(String[] args) {
		byte[] archivoAVerificar=cargarArchivoComoArrayDeBytes("cadenaResumen.txt");
		byte[] resumenArchivoAVerificar=generarResumen(archivoAVerificar);
		
		String resumen1codificado=codificarBase64(resumenArchivoAVerificar);
		
		String resumenOriginal=cargarArchivoTXT("hash.txt");
		
				
		System.out.println("El hash de la cadena a verificar es "+resumen1codificado);
		System.out.println("El hash de hash .txt es "+resumenOriginal);
		
		
	}
	
	private static byte[] cargarArchivoComoArrayDeBytes(String ruta) {
		byte[] bytesDeArchivo=null;
		Path path=Paths.get(ruta);
		try {
			bytesDeArchivo=Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bytesDeArchivo;
		
	}
	
	private static byte[] generarResumen(byte[] bytes) {
		byte[] resumen=null;
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			resumen=md.digest(bytes);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resumen;
	}
	
	private static String codificarBase64(byte[] bytes) {
		Encoder e=Base64.getEncoder();
		String mensajeCodificado=e.encodeToString(bytes);
		return mensajeCodificado;
	}
	
	private static String cargarArchivoTXT(String ruta) {
		String resultado="";
		Path path=Paths.get(ruta);
		try {
			BufferedReader br=Files.newBufferedReader(path);
			resultado=br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
		
	}
}
