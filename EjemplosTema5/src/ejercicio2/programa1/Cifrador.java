package ejercicio2.programa1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Cifrador {

	public static void main(String[] args) {
		String mensajeACifrar = pedirCadenaAlUsuario();
		SecretKey clave = crearClaveSecreta();
		Cipher cifrador = crearCifrador(clave);
		byte[] mensajeCifrado = cifrarMensaje(mensajeACifrar, cifrador);
		//byte[] claveEnBytes = cifrarMensaje(clave.getEncoded(),cifrador);
		escribirEnArchivo(mensajeCifrado, "mensaje.txt");
		escribirEnArchivo(clave.getEncoded(), "clave.txt");
	}

	private static String pedirCadenaAlUsuario() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Escribe una frase:");
		return sc.nextLine();
	}

	private static SecretKey crearClaveSecreta() {
		SecretKey key = null;
		try {
			KeyGenerator kg = KeyGenerator.getInstance("AES");
			kg.init(128);
			key = kg.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}

	private static Cipher crearCifrador(SecretKey clave) {
		Cipher cifrador = null;
		try {
			cifrador = Cipher.getInstance(clave.getAlgorithm());
			cifrador.init(Cipher.ENCRYPT_MODE,clave);
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

	private static void escribirEnArchivo(byte[] contenido, String ruta) {
		String contenidoEnString = codificarEnBase64(contenido);
		Path rutaEnPath = Paths.get(ruta); 
		try {
			if (!Files.exists(rutaEnPath)) {
				Files.createFile(Paths.get(ruta));
			}
			BufferedWriter bw = Files.newBufferedWriter(rutaEnPath);
			bw.write(contenidoEnString);
			bw.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String codificarEnBase64(byte[] contenido) {
		Encoder codificador = Base64.getEncoder();
		String contenidoCifrado = codificador.encodeToString(contenido);
		return contenidoCifrado;
	}
	
	private static byte[] cifrarMensaje(String mensaje, Cipher cifrador) {
		byte[] resultado=null;
		try {
			resultado=cifrador.doFinal(mensaje.getBytes());
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

}
