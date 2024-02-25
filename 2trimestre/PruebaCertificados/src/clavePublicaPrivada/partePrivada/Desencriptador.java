package clavePublicaPrivada.PartePrivada;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Desencriptador {

	public static void main(String[] args) {
		KeyStore almacen = cargarAlmacen("clavePepe.pk12", "123qwe");
		PrivateKey clave = extraerClavePrivada(almacen);
		Cipher descifrador = crearDescifrador(clave);
		String mensaje = cargarArchivo("mensajeCifrado.txt");
		String mensajeDescifrado = descifrarMensaje(mensaje, descifrador);
		System.out.println(mensajeDescifrado);
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
	private static byte[] decodicarCadenaEnBase64(String cadena) {
		return Base64.getDecoder().decode(cadena);
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
	private static Cipher crearDescifrador(PrivateKey clave) {
		Cipher cifrador = null;
		try {
			cifrador = Cipher.getInstance(clave.getAlgorithm());
			cifrador.init(Cipher.DECRYPT_MODE, clave);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return cifrador;
	}
	private static String descifrarMensaje(String mensajeCifradaEnBase64, Cipher cifrador) {
		byte[] mensajeCifrado = decodicarCadenaEnBase64(mensajeCifradaEnBase64);
		byte[] mensajeDescifrado = aplicarCifradorAMensaje(cifrador, mensajeCifrado);
		return new String(mensajeDescifrado);
	}
	private static byte[] aplicarCifradorAMensaje(Cipher desCifrador, byte[] mensaje) {
		byte[] mensajeDescifrado = null;
		try {
			mensajeDescifrado = desCifrador.doFinal(mensaje);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return mensajeDescifrado;
	}
	private static String cargarArchivo(String ruta) {
		String cadena = null;
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get(ruta));
			cadena = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cadena;
	}
}
