package sslSocket.cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class Cliente {

	private static final String PASSWORD_KEYSTORE = "123qwe";
	private static BufferedReader br;
	private static BufferedWriter bw;
	public static void main(String[] args) {
		SSLSocket socket = crearSSLSocket();
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bw.write("Tonto el que lo lea");
			bw.newLine();
			bw.flush();
			String respuesta = br.readLine();
			System.out.println(respuesta);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	private static TrustManagerFactory crearTrustManagerFactory(KeyStore almacen) {
		TrustManagerFactory tm = null;
		try {
			tm = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
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
	private static SSLSocket crearSSLSocket() {
		SSLSocket ss = null;
		try {			
			KeyStore almacen = KeyStore.getInstance("PKCS12");
			almacen.load(new FileInputStream("clavePublicaPepe.pk12"), PASSWORD_KEYSTORE.toCharArray());
			
			TrustManagerFactory tm = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tm.init(almacen);
			
			SSLContext contexto = SSLContext.getInstance("TLS");
			contexto.init(null, tm.getTrustManagers(), null);
			
			InetAddress dir = InetAddress.getByName("127.0.0.1");
			int puerto = 1234;
			ss = (SSLSocket) contexto.getSocketFactory().createSocket(dir, puerto);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss;
	}
	
}
