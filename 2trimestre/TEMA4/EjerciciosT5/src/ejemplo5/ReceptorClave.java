package ejemplo5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ejemplo1.Cifrador;

public class ReceptorClave {
	
	private static final String RUTA_ALMAC="almacen.pk12";
	public static final String ALIAS_ALMACEN="roberto";
	private static final String PASS="123qwe";
	public static void main(String[] args) {
		//recibimos un datagramPacket con la clave cifrada
		
		byte[] buffer=new byte[256];
		
		DatagramPacket paquete= new DatagramPacket(buffer,buffer.length);
		
		try {
			DatagramSocket socket=new DatagramSocket(1234);
			
			socket.receive(paquete);
			byte[] mensaje=paquete.getData();
			System.out.println("Se ha recibido "+new String(mensaje));
			System.out.println("La longitud del paquete es "+mensaje.length);
			
			//Ahora debemos descifrarlo con nuestra clave privada
			//Cargamos almacen y obtenemos clave privada
			PrivateKey clavePrivada=obtenerClavePrivada();
			
			//Crear descifrador con clave privada
			Cipher descifrador=crearDescifrador(clavePrivada);
			
			
			byte[] descifrado=descifrar(mensaje,descifrador);
			System.out.println(new String(descifrado));
			
			//Con esa secret key codificamos un mensaje
			String respuesta="Mensaje de respuesta";
			//Lo codificamos con la secretkey que hemos recibido.
			
			SecretKey claveSecreta=obtenerSecretKey(descifrado);
			
			Cipher cifrador=crearCifrador(claveSecreta);
			byte[] cifrado=cifrarTexto(respuesta,cifrador);
			
			System.out.println("El mensaje cifrado es "+new String(cifrado));
			
			//Montamos nuestro paquete con el mensaje ya cifrado y lo mandamos por udp.
			DatagramPacket paqueteRespuesta=new DatagramPacket(cifrado,cifrado.length,paquete.getAddress(),paquete.getPort());
			System.out.println("La longitud del paquete es "+paqueteRespuesta.getLength());
			socket.send(paqueteRespuesta);
			System.out.println("Se ha enviado el mensaje cifrado");
			
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static PrivateKey obtenerClavePrivada() {
		PrivateKey clave=null;
		
		
		try {
			KeyStore almacen=KeyStore.getInstance("PKCS12");
			almacen.load(new FileInputStream(RUTA_ALMAC), PASS.toCharArray());
			
			clave=(PrivateKey) almacen.getKey(ALIAS_ALMACEN, "123qwe".toCharArray());
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
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clave;
	}
	private static Cipher crearDescifrador(PrivateKey clavePrivada) {
		Cipher descifrador=null;
		try {
			descifrador=Cipher.getInstance(clavePrivada.getAlgorithm());
			descifrador.init(Cipher.DECRYPT_MODE, clavePrivada);
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
		return descifrador;
		
		
	}
	private static byte[] descifrar(byte[] contenido, Cipher descifrador) {
		byte[] contenidoDesifrado=null;
		try {
			contenidoDesifrado=descifrador.doFinal(contenido);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contenidoDesifrado;
		
	}
	private static SecretKey obtenerSecretKey(byte[] contenido) {
		SecretKeySpec clave=null;
		clave=new SecretKeySpec(contenido,"AES");
		return  clave;
		
	}
	private static Cipher crearCifrador(SecretKey clave) {
		Cipher cifrador=null;
		try {
			cifrador=Cipher.getInstance(clave.getAlgorithm());
			cifrador.init(Cipher.ENCRYPT_MODE, clave);
			
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cifrador;
	}
	private static byte[] cifrarTexto(String texto, Cipher cifrador) {
		byte[] cifrado=null;
		try {
			cifrado=cifrador.doFinal(texto.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cifrado;
	}
}
