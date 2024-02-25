package ejemplo1;

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
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Descifrador {
	public static final String ALIAS_ALMACEN="roberto";
	public static final String CONTRASENHA="123qwe";
	public static void main(String[] args) {
		
		KeyStore almacen=crearAlmacen();
		System.out.println("Se crea el almacen "+almacen.toString());
		
		PrivateKey clavePrivada= extraerClavePrivada(almacen);
		System.out.println("Se ha extraido la clave privada: "+clavePrivada.toString());
		
		Cipher descifrador=crearDesCifrador(clavePrivada);
		System.out.println("Se crea el cifrador "+descifrador.toString());
		
		String mensaje=cargarTextoDeArchivo("mensajeCifrado.txt");
		System.out.println("El mensaje cifrado es "+mensaje);
		
		byte[] mensajeDecodificadoBase64=decodificarBase64(mensaje);
		
		String mensajeDescifrado=descifrarMensaje(descifrador, mensajeDecodificadoBase64);
		System.out.println("El mensaje descifrado es "+mensajeDescifrado);
		
	}
	private static KeyStore crearAlmacen() {
		KeyStore almacen=null;
		try {
			almacen=KeyStore.getInstance("PKCS12");
			almacen.load(new FileInputStream("almacen.pk12"),"123qwe".toCharArray());
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
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
		return almacen;
	}
	
	private static PrivateKey extraerClavePrivada(KeyStore almacen) {
		PrivateKey clavePrivada=null;
		try {
			clavePrivada= (PrivateKey)almacen.getKey(ALIAS_ALMACEN, CONTRASENHA.toCharArray());
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
	
	private static Cipher crearDesCifrador(PrivateKey clave) {
		Cipher descifrador=null;
		try {
			descifrador=Cipher.getInstance(clave.getAlgorithm());
			descifrador.init(Cipher.DECRYPT_MODE, clave);
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
		return descifrador;
	}
	
	private static String cargarTextoDeArchivo(String ruta) {
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
	
	private static String descifrarMensaje(Cipher descifrador, byte[] mensaje) {
		byte[] mensajeDescifrado=null;
		try {
			mensajeDescifrado=descifrador.doFinal(mensaje);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return new String(mensajeDescifrado);	
	}
	
	private static byte[] decodificarBase64(String mensaje) {
		return Base64.getDecoder().decode(mensaje);
	}
}
