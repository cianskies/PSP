package DHCP;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Servidor {
	public static void main(String[] args) {
		
		//ESTA ES LA CLASE PRINCIPAL,
		//SE ENCARGA DE ESTAR SIEMPRE RECIBIENDO LOS MENSAJES
		//RECIBIDOS POR EL PUERTO 67
		DatagramSocket socketPuerto67;
		try {
			socketPuerto67 = new DatagramSocket(67);
			Datos datos=new Datos(socketPuerto67);
			
			while(true) {
				MensajeDHCP mensajeDHCP=datos.recibirNuevoMensajeDHCP();
				//AQUI COMPROBAMOS SI LA MAC LA ESTA USANDO ALGUN COMUNICADORDHCP,
				if(datos.comprobarMacLibre(mensajeDHCP.getMac())) {
					//SI ESTA LIBRE, CREAREMOS UN NUEVO COMUNICADOR QUE SE HAGA CARGO DEL MENSAJE
					Thread comunicador=new Thread(new ComunicadorDHCP(mensajeDHCP,datos));
					comunicador.start();
				}else {
					//EN CASO DE QUE ESTE SIENDO USADO, SE ALAMACENARA EN LA PILA DE MENSAJES DE DATOS
					datos.almacenarMensaje(mensajeDHCP);
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
