package ejemplo1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Cifrador {
	public static final String ALIAS_ALMACEN="roberto";
	public static final String CONTRASENHA="123qwe";
	public static void main(String[] args) {
		KeyStore almacen=crearAlmacen();
		System.out.println("Se crea el almacen "+almacen.toString());
		
		/*
		 * En el ejemplo del profe se usa la clave publica para cifrar y 
		 * la privada para descifrar
		PrivateKey clavePrivada= extraerClavePrivada(almacen);
		System.out.println("Se ha extraido la clave privada: "+clavePrivada.toString());
		*/
		
		PublicKey clavePublica= extraerClavePublica(almacen);
		System.out.println("Se ha extraido la clave publica: "+clavePublica.toString());
		
		Cipher cifrador=crearCifrador(clavePublica);
		System.out.println("Se crea el cifrador "+cifrador.toString());
		
		String mensaje="Hola";
		System.out.println("El mensaje es "+mensaje);
		
		byte[] mensajeCifrado=cifrarMensaje(cifrador, mensaje);
		System.out.println("El mensaje cifrado es: ");
		for(int i=0;i<mensajeCifrado.length;++i) {
			System.out.print(mensajeCifrado[i]);
		}
		System.out.println();
		
		String mensajeEnBase64=codificarEnBase64(mensajeCifrado);
		System.out.println("El mensaje en base 64 es: "+mensajeEnBase64);
		
		escribirEnArchivoTXT(mensajeEnBase64);
		
		
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
	
	private static PublicKey extraerClavePublica(KeyStore almacen) {
		PublicKey clavePublica=null;
		try {
			clavePublica= almacen.getCertificate(ALIAS_ALMACEN).getPublicKey();
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
	
	private static byte[] cifrarMensaje(Cipher cifrador, String mensaje) {
		byte[] mensajeCifrado=null;
		try {
			mensajeCifrado = cifrador.doFinal(mensaje.getBytes());
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mensajeCifrado;
	}
	
	private static String codificarEnBase64(byte[] mensaje) {
		Encoder codificador=Base64.getEncoder();
		String mensajeCodificado=codificador.encodeToString(mensaje);
		return mensajeCodificado;
	}
	
	private static void escribirEnArchivoTXT(String mensaje) {
		File archivo=new File("mensajeCifrado.txt");
		try {
			archivo.createNewFile();
			FileWriter writer=new FileWriter(archivo);
			writer.write(mensaje);
			writer.flush();
			writer.close();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
