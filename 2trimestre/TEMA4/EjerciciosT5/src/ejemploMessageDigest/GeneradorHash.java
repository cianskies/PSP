package ejemploMessageDigest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class GeneradorHash {
	public static void main(String[] args) {
		//Generar resumen a partir de cadena de caracteres con el algoritmo MD5
		String cadena="Resumen MD5";
		
		guardarEnArchivo(cadena, "cadenaResumen.txt");	
		byte[] archivoBytes=cargarArchivoComoArrayDeBytes("cadenaResumen.txt");
		byte[] resumen=generarResumen(archivoBytes);
		
		escribirBytesEnArchivo(resumen,"hash.txt");
		
		
		
	}
	private static void guardarEnArchivo(String mensaje, String ruta) {
		Path rutaPath=Paths.get(ruta);
		try {
			Files.createFile(Paths.get(ruta));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bw=null;
		try {
			bw = Files.newBufferedWriter(rutaPath);
			bw.write(mensaje);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static byte[] cargarArchivoComoArrayDeBytes(String ruta) {
		byte[] arrayDeBytesArchivo=null;
		try {
			arrayDeBytesArchivo=Files.readAllBytes(Paths.get(ruta));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayDeBytesArchivo;
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

	private static void escribirBytesEnArchivo(byte[] bytes, String ruta) {
		String bytesEnString=codificarBytesEnBase64(bytes);
		guardarEnArchivo(bytesEnString,ruta);
		
	}
	private static String codificarBytesEnBase64(byte[] bytes) {
		Encoder encoder=Base64.getEncoder();
		String codificado=encoder.encodeToString(bytes);
		return codificado;
		
	}
}
