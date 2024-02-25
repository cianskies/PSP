package ejemploCriotigrafiaSimetrica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Descifrador {
	
	public static void main(String[] args) {
		String claveBase64=extraerTXTDeArchivo("claveSecreta.txt");
		String mensajeBase64=extraerTXTDeArchivo("cifradoSecretKey.txt");
		
		byte[] claveDecodificada=Base64.getDecoder().decode(claveBase64);
		byte[] mensajeDecodificado=Base64.getDecoder().decode(mensajeBase64);
		System.out.println("El mensaje codificado es "+mensajeBase64+ " y la clave "+claveBase64);
		
		//A PARTIR DE UN ARRAY DE BYTES, SE PUEDE RECUPERAR LA CLAVE CON LA CLASE SECRETKEYSPEC
		SecretKeySpec claveReconstruida=reconstruirClave(claveDecodificada);
		
		Cipher descifrador=generarDescifrador(claveReconstruida);
		String mensajeDescifrado=descifrarMensaje(descifrador, mensajeDecodificado);
		System.out.println(mensajeDescifrado);
		
	}
	
	private static String extraerTXTDeArchivo(String ruta) {
		String textoArchivo="";
		try {
			BufferedReader br=Files.newBufferedReader(Paths.get(ruta));
			textoArchivo=br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return textoArchivo;	
	}

	private static Cipher generarDescifrador(SecretKey clave) {
		 Cipher descifrador=null;
		 try {
			descifrador=Cipher.getInstance(clave.getAlgorithm());
			 try {
				descifrador.init(Cipher.DECRYPT_MODE, clave);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return descifrador;	 
	}
	
	private static SecretKeySpec reconstruirClave(byte[] clave) {
		SecretKeySpec claveReconstruida=null;
		claveReconstruida=new SecretKeySpec(clave,"AES");
		return claveReconstruida;				
		}

	private static String descifrarMensaje(Cipher descifrador, byte[] mensaje) {
		String mensajeDescifrado="";
		try {
			byte[] mensajeDescifradoEnBytes=descifrador.doFinal(mensaje);
			mensajeDescifrado=new String(mensajeDescifradoEnBytes);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mensajeDescifrado;
		
	}
}

