package DHCP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class Datos {
	
	private DatagramSocket _socketPuerto67;
	private ArrayList<byte[]> _macOcupadasActuales;
	
	private int IDHost;
	
	HashMap<Integer,byte[]> _IpYMacAsociadas;
	private ArrayList<MensajeDHCP> _pilaMensajes;
	
	public Datos(DatagramSocket socket) {
		this._socketPuerto67=socket;
		_macOcupadasActuales=new ArrayList<>();
		_IpYMacAsociadas=new HashMap<>();
		_pilaMensajes=new ArrayList<>();
	}
	
	
	//FUNCIONES DEL SOCKET (ENVIAR Y RECIBIR MENSAJES)
	
	public synchronized void enviarMensaje(MensajeDHCP mensaje) {
		try {
			DatagramPacket dp=new DatagramPacket(mensaje.getDatos(),0,mensaje.getDatos().length,
					InetAddress.getByName("255.255.255.255"),68);
			_socketPuerto67.send(dp);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public MensajeDHCP recibirNuevoMensajeDHCP() {
		byte[] buffer=new Byte[1024];
		DatagramPacket dp=new DatagramPacket(buffer,buffer.length);
		try {
			_socketPuerto67.receive(dp);
		}catch(IOException e) {
			e.printStackTrace();
		}
		buffer=dp.getData();
		MensajeDHCP mensajeRecibido=new MensajeDHCP(buffer);
		return mensajeRecibido;
	}

	//**
	//*
	
	//FUNCIONES PARA GENERAR LOS MENSAJES DHCP NECESARIOS
	//(ESTAS FUNCIONES SERAN UTILIZADAS POR EL COMUNICADOR)
	
	public MensajeDHCP generarDHCPOffer(MensajeDHCP mensaje) {
		
	}
	
	public MensajeDHCP generarDHCPRequest(MensajeDHCP mensaje) {
		
	
	
	}
	
	public MensajeDHCP generarDHCPRenovacion(MensajeDHCP mensaje) {
		
	}
	
	
}
