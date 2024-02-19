package clavePublicaPrivada.partePublica;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Scanner;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Encriptador {

	public static void main(String[] args) {
		//Va a traer la clave pública del archivo,
		//encriptar con ella un mensaje y
		//guardarlo en un archivo
		KeyStore almacen = cargarAlmacen("clavePublicaPepe.pk12");
		PublicKey clavePublica = extraerClavePublica(almacen);
		Cipher cifrador = crearCifrador(clavePublica);
		String mensaje=pedirCadenaAlUsuario();
		byte[] mensajeCifrado=cifrarMensaje(cifrador,mensaje);
		escribirEnArchivo(mensajeCifrado,"mensajeCifrado.txt");

	}
	private static byte[] cifrarMensaje(Cipher cifrador, String mensaje) {
		byte[] msjCifrado=null;
		try {
			msjCifrado=cifrador.doFinal(mensaje.getBytes());
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msjCifrado;
	}
	
	private static KeyStore cargarAlmacen(String ruta) {
		KeyStore almacen=null;
		try {
			almacen = KeyStore.getInstance("PKCS12");
			almacen.load(new FileInputStream(ruta), "123qwe".toCharArray());
		}
		catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return almacen;
	}
	
	private static PublicKey extraerClavePublica(KeyStore almacen) {
		PublicKey clavePublica=null;

			try {
				clavePublica = (PublicKey) almacen.getCertificate("pepe").getPublicKey();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return clavePublica;
	}
	
	private static Cipher crearCifrador(PublicKey clave) {
		Cipher cifrador=null;
		try {
			cifrador=Cipher.getInstance(clave.getAlgorithm());
			cifrador.init(Cipher.ENCRYPT_MODE, clave);
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
	
	private static String codificarEnBase64(byte[] contenido) {
		Encoder codificador = Base64.getEncoder();
		String contenidoCifrado = codificador.encodeToString(contenido);
		return contenidoCifrado;
	}
	
	private static String pedirCadenaAlUsuario() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Escribe una frase:");
		return sc.nextLine();
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

}
