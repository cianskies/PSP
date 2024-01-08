package ejemplo1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class Cliente {
	
	public static void main(String[] args) {
		
		//Manda el primer mensaje al servidor (por el puerto 1234)
		DatagramSocket socket=null;
		try {
			socket=new DatagramSocket(234);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Cargamos la imagen,
		File archivo=new File("imagen.jpg");

		byte[] imagen=null;
		FileInputStream fis;
		try {
			fis = new FileInputStream(archivo);
			imagen=new byte[(int)archivo.length()];
			fis.read(imagen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//Mandamos el nº de mensajes, el tamaño por mensaje y por último el tamaño total
		
		int nMensajes=5;
		int tamanhoTotal=imagen.length;
		int tamanhoMensaje=(int) Math.ceil(imagen.length/nMensajes);
		ByteBuffer bb=ByteBuffer.allocate(12);
		bb.putInt(nMensajes);
		bb.putInt(tamanhoMensaje);
		bb.putInt(tamanhoTotal);
		
		try {
			InetAddress dir=InetAddress.getByName("10.0.2.4");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatagramPacket paquete=new DatagramPacket(bb.array(),bb.capacity());
		int puerto;
		try {
			socket.send(paquete);
			puerto=paquete.getPort();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Recibde la respuesta del hilo, y se queda con el puerto
		
		
		
		
		
		// rompemos la imagen en trozos y la enviamos por 
		//socket al hilo
		

		
		//Enviamos los datos correspondientes
	}

}
