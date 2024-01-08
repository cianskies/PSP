package ejemplo1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Hilo implements Runnable{
	
	private InetAddress dirCliente;
	private int puertoCliente;
	private byte[] datos;
	private DatagramSocket socket;
	private byte[] datosTotales;
	
	public Hilo(DatagramPacket paquete) {
		dirCliente=paquete.getAddress();
		puertoCliente=paquete.getPort();
		datos=paquete.getData();
		try {
			socket=new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override 
	
	public void run() {
		
		//Gestionamos la comunicacion con el cliente
		
		//Primero el cliente envia el numerp de mensajes que va a enviar a continuacion
		ByteBuffer bb= ByteBuffer.wrap(datos);
		int nMensajes=bb.getInt();
		int tamanhoMensaje=bb.getInt();
		byte[] respuesta="OK".getBytes();
		//el hilo le envia un ok al cliente para que el cliente sepa a que puerto enviar los menmsajes
		DatagramPacket paqueteTespuresta=new DatagramPacket(respuesta,respuesta.length,dirCliente,puertoCliente);
		
		System.out.println("El cliente me va a enviar"+ nMensajes +"mensajes.");
		datosTotales=new byte[tamanhoMensaje*nMensajes];
		byte[] buffer;
		DatagramPacket paquete;
		for(int i=0;i<nMensajes;++i) {
			//Recibimos el resto de mesnajes
			buffer=new byte[tamanhoMensaje];
			paquete=new DatagramPacket(buffer,buffer.length);
			try {
				socket.receive(paquete);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Guardamos los bytes del buffer en datosTotales
			acumularBufferEnDatos(buffer,i*tamanhoMensaje);
			//Guardamos en un archivo los datos recibidos
			File archivo=new File("recibido.jpg");
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(archivo);
				fos.write(datosTotales);
				fos.flush();
				fos.close();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	private void acumularBufferEnDatos(byte[] buffer,int posicion) {
		for (int i=0;i<buffer.length && (posicion+i<datosTotales.length);++i) {
			
			datosTotales[posicion+i]=buffer[i];
		}
	}
	
}
