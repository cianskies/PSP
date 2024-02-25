package signature.receptor;

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
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.Base64;

public class SignatureReceptor {

	public static void main(String[] args) {
		byte[] firma = decodicarCadenaEnBase64(cargarArchivo("firma.txt"));
		byte[] original = decodicarCadenaEnBase64(cargarArchivo("original.txt"));
		PublicKey clave = extraerClavePublica(cargarAlmacen("clavePublicaPepe.pk12","123qwe"));
		boolean esCorrecto = comprobarFirma(original, firma, clave);
		if(esCorrecto) {
			System.out.println("Correcto");
		}
		else {
			System.out.println("No es Correcto");
		}

	}
	private static boolean comprobarFirma(byte[] original, byte[] firma, PublicKey clave) {
		boolean esCorrecto = false;
		try {
			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initVerify(clave);
			signature.update(original);
			esCorrecto = signature.verify(firma);
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
		return esCorrecto;
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
	private static byte[] decodicarCadenaEnBase64(String cadena) {
		return Base64.getDecoder().decode(cadena);
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
	private static PublicKey extraerClavePublica(KeyStore almacen) {
		PublicKey clavePublica = null;
		try {
			clavePublica = (PublicKey) almacen.getCertificate("pepe").getPublicKey();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return clavePublica;
	}
}
