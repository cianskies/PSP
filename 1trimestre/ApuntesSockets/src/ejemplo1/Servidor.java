package ejemplo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Servidor {
	
	public static void main(String[] args) {
		
		
		byte[] msj= new byte[140];
		try {
			DatagramSocket socket= new DatagramSocket(1234);
			while(true) {
				msj=new byte[140];
				DatagramPacket paquete=new DatagramPacket(msj,msj.length);
				socket.receive(paquete);
				Thread hilo=new Thread(new Hilo(paquete));
				hilo.start();
			}
		
		
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
