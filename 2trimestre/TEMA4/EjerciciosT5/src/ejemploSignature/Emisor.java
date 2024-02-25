package ejemploSignature;

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
import java.util.Base64.Encoder;

public class Emisor {
	public static void main(String[] args) {
		String cadena="Hola que tal estas";
		
		//Cargamos el almacen
		KeyStore almacen=cargarAlmacen("almacen.pk12","123qwe");
		System.out.println("almacen: "+almacen.toString());
		
		//Extraemos la clave privada del almacen
		PrivateKey clavePrivada=extraerClavePrivada(almacen,"123qwe");
		System.out.println("La clave privada es "+clavePrivada.toString());
		
		//con la clave privada obtenemos una firma de la cadena
		byte[] firma=obtenerFirma(clavePrivada,cadena);
		
		//escribimos la firma en firma.txt y la cadena en cadenaSignature.txt
		escribirEnArchivo(firma,"firma.txt");
		escribirEnArchivo(cadena.getBytes(),"cadenaSignture.txt");
	}
	
	private static KeyStore cargarAlmacen(String almacen, String contrasenha) {
		
		KeyStore ks=null;
		try {
			ks = KeyStore.getInstance("PKCS12");
			ks.load(new FileInputStream(almacen), contrasenha.toCharArray());
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
		return ks;
	}

	private static PrivateKey extraerClavePrivada(KeyStore almacen,String contrasenha) {
		PrivateKey clave=null;
		try {
			clave=(PrivateKey)almacen.getKey("roberto", contrasenha.toCharArray());
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
		return clave;
	}

	private static byte[] obtenerFirma(PrivateKey clave,String texto) {
		byte[] firma=null;
		try {
			//instancia con el que se firmo la clave privada
			Signature signature=Signature.getInstance("SHA256withRSA");
			
			//initSign de nuestra clave privada
			signature.initSign(clave);
			
			//update con LOS BYTES de lo que queremos obtener la firma
			signature.update(texto.getBytes());
			
			firma=signature.sign();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return firma;
	}

	private static void escribirEnArchivo(byte[] bytes,String ruta) {
		String contenidoCodificado=codificarBase64(bytes);
		Path path=Paths.get(ruta);
		if(!Files.exists(path)){
			try {
				Files.createFile(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			BufferedWriter bw=Files.newBufferedWriter(path);
			bw.write(contenidoCodificado);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}
	
	private static String codificarBase64(byte[] bytes) {
		Encoder e=Base64.getEncoder();
		String codificado=e.encodeToString(bytes);
		return codificado;
	}
}
