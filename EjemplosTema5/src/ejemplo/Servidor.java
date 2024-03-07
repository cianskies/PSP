package ejemplo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class Servidor {
	
	private static final String PASSWORD_KEYSTORE="123qwe";
	
	public static void main(String[] args) {
		KeyStore almacen=cargarAlmacen("clavePepe.pk12");
		KeyManagerFactory km=crearManagerKeyFactory(almacen);
		SSLContext contexto=crearSSLContext(km);
		SSLServerSocket sss= crearSSLServerSocket(contexto);
		SSLSocket ss;
		while(true) {
			try {
				ss=(SSLSocket)sss.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
	
	private static KeyManagerFactory crearManagerKeyFactory(KeyStore almacen) {
		KeyManagerFactory km=null;
		try {
			km=KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			km.init(almacen,PASSWORD_KEYSTORE.toCharArray());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return km;
		
		
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


	private static SSLContext crearSSLContext(KeyManagerFactory km) {
		SSLContext contexto=null;
		try {
			contexto=SSLContext.getInstance("TLS");
			contexto.init(km.getKeyManagers(), null, null);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		
		}
		return contexto;
	}
	private static SSLServerSocket crearSSLServerSocket(SSLContext contexto) {
		SSLServerSocket sss=null;
		try {
			sss=(SSLServerSocket)contexto.getServerSocketFactory().createServerSocket(1234);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sss;
	}
}
