package sslSocket.Servidor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class Servidor {
	private static final String PASSWORD_KEYSTORE = "123qwe";
	public static void main(String[] args) {
		KeyStore almacen = cargarAlmacen("clavePepe.pk12", PASSWORD_KEYSTORE);		
		KeyManagerFactory km = crearKeyManagerFactory(almacen, PASSWORD_KEYSTORE);
		SSLContext contexto = crearSSLContext(km);
		SSLServerSocket sss = crearSSLServerSocket(contexto);
		SSLSocket ss;
		Thread hilo;
		while(true) {
			try {
				ss = (SSLSocket) sss.accept();
				hilo = new Thread(new HiloServidor(ss));
				hilo.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
	private static KeyManagerFactory crearKeyManagerFactory(KeyStore almacen, String contrasenha) {
		KeyManagerFactory km = null;
		try {
			km = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			km.init(almacen, contrasenha.toCharArray());
		} catch (NoSuchAlgorithmException e) {
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
	private static SSLContext crearSSLContext(KeyManagerFactory km) {
		SSLContext contexto = null;
		try {
			contexto = SSLContext.getInstance("TLS");
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
		SSLServerSocket sss = null;
		try {
			sss =  (SSLServerSocket) contexto.getServerSocketFactory().createServerSocket(1234);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sss;
	}
}

