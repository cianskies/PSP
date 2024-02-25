package ejemploCriotigrafiaSimetrica;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Cifrador {
	public static final String ALGORITMO="AES";
	public static void main(String[] args) {
		String prueba="Este es el mensaje descifrado";
		
		//Se obtiene un keygenerator con el algoritmo que queremos
		KeyGenerator keygen=obtenerKeyGenerator();
		
		//el keygen genera una secretkey del tamaño que le hemos dicho
		SecretKey claveSecreta=obtenerClaveSecreta(keygen);
		System.out.println("La SecretKey es "+claveSecreta.toString());
		
		//a partir de la secretKey creamos el cifrador
		Cipher cifrador=obtenerCifradorAPartirDeClave(claveSecreta);
		System.out.println("El cifrador es "+cifrador.toString());
		byte[] mensajeCifrado=null;
		try {
			mensajeCifrado=cifrador.doFinal(prueba.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		escribirEnArchivoTXT(mensajeCifrado,"cifradoSecretKey.txt");
		escribirEnArchivoTXT(claveSecreta.getEncoded(),"claveSecreta.txt");
		
		
		
	}
	private static KeyGenerator obtenerKeyGenerator() {
		KeyGenerator keygen=null;
		try {
			keygen=KeyGenerator.getInstance(ALGORITMO);
			keygen.init(128);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keygen;
		
		
	}
	
	private static SecretKey obtenerClaveSecreta(KeyGenerator keygen) {
		SecretKey clave=null;
		clave=keygen.generateKey();
		return clave;
	}
	
	private static Cipher obtenerCifradorAPartirDeClave(SecretKey clave) {
		Cipher cifrador=null;
		try {
			cifrador=Cipher.getInstance(clave.getAlgorithm());
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
	
	private static void escribirEnArchivoTXT(byte[] mensaje, String ruta) {
		String mensajeEnString=codificarEnBase64(mensaje);
		File archivo=new File(ruta);
		try {
			archivo.createNewFile();
			FileWriter writer=new FileWriter(archivo);
			writer.write(mensajeEnString);
			writer.flush();
			writer.close();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String codificarEnBase64(byte[] mensaje) {
		Encoder codficador=Base64.getEncoder();
		String mensajeCodificado=codficador.encodeToString(mensaje);
		return mensajeCodificado;
	}
}
