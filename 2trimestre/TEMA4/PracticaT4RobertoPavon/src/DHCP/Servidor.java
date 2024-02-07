package DHCP;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Servidor {
	
	public static void Main(String[] args) {
		
		DatagramSocket socketPuerto67;
		try {
			socketPuerto67 = new DatagramSocket(67);
			Datos datos=new Datos(socketPuerto67);
			
			//ALMACENAR MENSAJE
			while(true) {
				MensajeDHCP mensajeDHCP=datos.recibirNuevoMensajeDHCP();
				
				//AQUI COMPROBARA SI LA MAC ESTA SIENDO UTILIZADA POR ALGUN
				//COMUNICADOR DHCP, SI ESTA LIBRE, CREARA UN COMUNICADOR NUEVO
				//ASIGNANDOLE DICHA MAC, EN CASO DE LO CONTRARIO LO ALMACENA
				//EN LA PILA DE MENSAJE DE DATOS PARA QUE SU COMUNICADOR 
				//CORRESPONDIENTE LO RECOJA
				
				if(datos.comprobarTransaccion(mensajeDHCP.getMac())) {
					Thread comunicador=new Thread(new ComunicadorDHCP(mensajeDHCP, datos));
					comunicador.start();
				}else {
					datos.almacenarMensaje(mensajeDHCP);
				}
			}
			
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
