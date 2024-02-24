package ejercicio2.programa2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Descifrador {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String claveEnBase64 = cargarArchivo("clave.txt");
		String mensajeCodificadoBase64 = cargarArchivo("mensaje.txt");
		String mensajeDescifrado = descifrarMensaje(mensajeCodificadoBase64,claveEnBase64);
		System.out.println(mensajeDescifrado);
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
	
	private static byte[] decodificarBase64(String cadena) {
		return Base64.getDecoder().decode(cadena);
	}
	
	private static SecretKeySpec reconstruirClave(String claveCodificada) {
		byte[] claveEnBytes=decodificarBase64(claveCodificada);
		SecretKeySpec clave = new SecretKeySpec(claveEnBytes, "AES");
		return clave;
	}
	
	private static String descifrarMensaje(String mensajeCifradoEnBase64,String claveCifradaEnBase64) {
		SecretKeySpec clave=reconstruirClave(claveCifradaEnBase64);
		Cipher descifrador = generarCifrador(clave);
		byte[] mensajeCifrado = decodificarBase64(mensajeCifradoEnBase64);
		byte[] mensajeDescifrado = aplicarDescifradorAMensaje(descifrador,mensajeCifrado);
		return new String(mensajeDescifrado);		
	}
	
	private static byte[] aplicarDescifradorAMensaje(Cipher descifrador,byte[] mensaje) {
		byte[] mensajeDescifrado=null;
		try {
			mensajeDescifrado = descifrador.doFinal(mensaje);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mensajeDescifrado;
	}
	
	private static Cipher generarCifrador(SecretKey clave) {
		Cipher cifrador=null;
		try {
			cifrador = Cipher.getInstance(clave.getAlgorithm());
			cifrador.init(Cipher.DECRYPT_MODE,clave);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cifrador;
	}
	
	

}
