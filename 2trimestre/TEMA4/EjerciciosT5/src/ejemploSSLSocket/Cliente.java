package ejemploSSLSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class Cliente {
	private static final String CONSTRASENHA_ALMACEN="123qwe";
	private static BufferedReader br;
	private static BufferedWriter bw;
	public static void main(String[] args) {
		
		//primero cargamos el almacen
		KeyStore almacen=cargarAlmacen("almacen.pk12");
		//creamos trustmanagerfactory
		TrustManagerFactory tmf=crearTMF(almacen);
		
		//Creamos contexto
		SSLContext contexto=crearSSLContext(tmf);
		//Creamos socket a partir de contexto
		SSLSocket socket=crearSSLSocket(contexto);
		try {
			bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String mensaje="Me oye camarada?";
			bw.write(mensaje);
			bw.newLine();
			bw.flush();
			
			String respuesta=br.readLine();
			System.out.println(respuesta);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	private static KeyStore cargarAlmacen(String rutaAlmacen) {
		KeyStore almacen=null;
		try {
			almacen=KeyStore.getInstance("PKCS12");
			almacen.load(new FileInputStream(rutaAlmacen),CONSTRASENHA_ALMACEN.toCharArray());
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

	private static TrustManagerFactory crearTMF(KeyStore almacen) {
		TrustManagerFactory tmf=null;
		try {
			tmf=TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(almacen);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmf;
	}
	
	private static SSLContext crearSSLContext(TrustManagerFactory tmf) {
		SSLContext contexto=null;
		try {
			contexto=SSLContext.getInstance("TLS");
			try {
				contexto.init(null, tmf.getTrustManagers(), null);
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return contexto;
	}
	private static SSLSocket crearSSLSocket(SSLContext contexto) {
		SSLSocket socket=null;
		try {
			socket=(SSLSocket)contexto.getSocketFactory().createSocket("192.168.5.50",1234);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return socket;
	}

}
