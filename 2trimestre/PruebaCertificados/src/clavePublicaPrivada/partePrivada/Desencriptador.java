package clavePublicaPrivada.partePrivada;

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
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Desencriptador {
	public static void main(String[] args) {
		KeyStore almacen=cargarAlmacen("clavePepe.pk12");
		PrivateKey clavePrivada=extraerClavePrivada(almacen);
		Cipher descifrador=generarDesCifrador(clavePrivada);
		String mensaje=cargarArchivo("mensajeCifrado.txt");
		String mensajeDescifrado=descifrarMensaje(mensaje,clavePrivada,descifrador);
		System.out.println(mensaje);
	}
	private static PrivateKey extraerClavePrivada(KeyStore almacen) {
		PrivateKey clavePrivada=null;

			try {
				clavePrivada=(PrivateKey)almacen.getKey("pepe", "123qwe".toCharArray());
			} catch (UnrecoverableKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return clavePrivada;
	}
	private static Cipher generarDesCifrador(PrivateKey clave) {
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
	
	private static String descifrarMensaje(String mensajeCifradoEnBase64,String claveCifradaEnBase64, Cipher descifrador) {
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
}
