package DHCP;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Servidor {
<<<<<<< HEAD
	
	public static void Main(String[] args) {
		
=======
	public static void main(String[] args) {
		
		//ESTA ES LA CLASE PRINCIPAL,
		//SE ENCARGA DE ESTAR SIEMPRE RECIBIENDO LOS MENSAJES
		//RECIBIDOS POR EL PUERTO 67
>>>>>>> 7eba26d5f16e1e3c66f0ec2968644044939fdd70
		DatagramSocket socketPuerto67;
		try {
			socketPuerto67 = new DatagramSocket(67);
			Datos datos=new Datos(socketPuerto67);
			
<<<<<<< HEAD
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
			
			
			
=======
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
>>>>>>> 7eba26d5f16e1e3c66f0ec2968644044939fdd70
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
<<<<<<< HEAD
	
=======

>>>>>>> 7eba26d5f16e1e3c66f0ec2968644044939fdd70
}
