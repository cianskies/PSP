package ejemploSSLSocket;

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
	private static final String CONTRASENHA_ALMACEN="123qwe";
	public static void main(String[] args) {
		//Primero cargar almacen
		KeyStore almacen=cargarAlmacen("almacen.pk12");
		
		//Crear KeyManagerFactory a partir del almacen
		KeyManagerFactory kmf=crearKeyManagerFactory(almacen);
		
		//Creamos contexto SSL
		SSLContext contexto=crearContexto(kmf);
		
		//Crear un SSLServerSocket
		
		SSLServerSocket sss=null;
		try {
			sss = (SSLServerSocket)contexto.getServerSocketFactory().createServerSocket(1234);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Iniciamos el servidor
		
		while(true) {
			//Recordar hay que hacer casting para que sea sslsocket
		
			SSLSocket sslsocket;
			try {
				sslsocket = (SSLSocket)sss.accept();
				Thread hiloComunicador=new Thread(new HiloComunicador(sslsocket));
				hiloComunicador.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
		
			
		
	}
	
	private static KeyStore cargarAlmacen(String ruta) {
		KeyStore almacen=null;
		try {
			almacen=KeyStore.getInstance("PKCS12");
			
			almacen.load(new FileInputStream(ruta), CONTRASENHA_ALMACEN.toCharArray());
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
	
	
	private static KeyManagerFactory crearKeyManagerFactory(KeyStore almacen) {
		KeyManagerFactory kmf=null;
		try {
			kmf=KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(almacen, CONTRASENHA_ALMACEN.toCharArray());
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
		
		return kmf;
	}
	
	private static SSLContext crearContexto(KeyManagerFactory kmf) {
		SSLContext contexto=null;
		try {
			contexto=SSLContext.getInstance("TLS");
			contexto.init(kmf.getKeyManagers(),null,null);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return contexto;
	}
}
