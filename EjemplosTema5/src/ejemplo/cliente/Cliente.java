package ejemplo.cliente;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class Cliente {
	private static String PASSWORD_KEYSTORE="123qwe";
	public static void main(String[] args) {
		
	}
	private static KeyStore cargarAlmacen(String ruta) {
		KeyStore almacen=null;
		try {
			almacen = KeyStore.getInstance("PKCS12");
			almacen.load(new FileInputStream(ruta), PASSWORD_KEYSTORE.toCharArray());
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
	private static TrustManagerFactory crearTrustManagerFactory(KeyStore almacen) {
		TrustManagerFactory tm=null;
		try {
			tm=TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tm.init(almacen);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tm;
	}
	private static SSLSocket crearSSLSocket() {
		KeyStore almacen=KeyStore.getInstance("PCKS12");
		almacen.load((new FileInputStream("clavePublicaPepe.pk12")),PASSWORD_KEYSTORE.toCharArray()
				);
		
		TrustManagerFactory tm=null;
		try {
			tm=TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tm.init(almacen);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
	}
	
}
