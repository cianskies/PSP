package signature.emisor;

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
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Scanner;
import java.util.Base64.Encoder;

public class SignatureEmisor {

	public static void main(String[] args) {
		String cadena = pedirCadenaAlUsuario();
		KeyStore almacen = cargarAlmacen("clavePepe.pk12","123qwe");
		PrivateKey clave = extraerClavePrivada(almacen);
		byte[] firma = obtenerFirmaDelDocumento(cadena.getBytes(), clave);
		escribirEnArchivo(cadena.getBytes(), "original.txt");
		escribirEnArchivo(firma, "firma.txt");
	}
	private static PrivateKey extraerClavePrivada(KeyStore almacen) {
		PrivateKey clavePrivada = null;
		try {
			clavePrivada = (PrivateKey) almacen.getKey("pepe", "123qwe".toCharArray());
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return clavePrivada;
	}
	private static KeyStore cargarAlmacen(String ruta, String password) {
		KeyStore almacen = null;
		try {
			almacen = KeyStore.getInstance("PKCS12");
			almacen.load(new FileInputStream(ruta), password.toCharArray());
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return almacen;
	}
	private static String pedirCadenaAlUsuario() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Escribe una frase:");
		return sc.nextLine();
	}
	private static void escribirEnArchivoDeTexto(String cadena, String ruta) {
		Path rutaEnPath = Paths.get(ruta);
		try {
			if(!Files.exists(rutaEnPath)) {
				Files.createFile(Paths.get(ruta));
			}
			BufferedWriter bw = Files.newBufferedWriter(rutaEnPath);
			bw.write(cadena);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void escribirEnArchivo(byte[] contenido, String ruta) {
		String contenidoEnString = codificarEnBase64(contenido);
		escribirEnArchivoDeTexto(contenidoEnString, ruta);
	}
	private static String codificarEnBase64(byte[] contenido) {
		Encoder codificador = Base64.getEncoder();
		String contenidoCifrado = codificador.encodeToString(contenido);
		return contenidoCifrado;
	}
	private static byte[] obtenerFirmaDelDocumento(byte[] original, PrivateKey clave) {
		byte[] firma = null;
		Signature signature;
		try {
			signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(clave);
			signature.update(original);
			firma = signature.sign();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return firma;
	}
}
