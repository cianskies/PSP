package DHCP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;




public class Datos {
	
	//LA IP DEL SERVIDOR ES 172.16.1.2
	
	private DatagramSocket _socketPuerto67;
	private ArrayList<byte[]> _macOcupadas;
	
	private int IDHost;
	
	HashMap<Integer,byte[]> _IpYMacAsociadas;
	private ArrayList<MensajeDHCP> _pilaMensajes;
	
	public Datos(DatagramSocket socket) {
		this._socketPuerto67=socket;
		_macOcupadas=new ArrayList<>();
		_IpYMacAsociadas=new HashMap<>();
		_pilaMensajes=new ArrayList<>();
	}
	
	//FUNCIONES DEL SOCKET (ENVIAR Y RECIBIR MENSAJES) Y RELACIONADO MENSAJES EN GENERAL
	
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
		byte[] buffer=new byte[1024];
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
	public synchronized void almacenarMensaje(MensajeDHCP mensaje) {
		_pilaMensajes.add(mensaje);
		notifyAll();
	}
	public synchronized MensajeDHCP recogerMEnsaje(byte[] mac) {
		MensajeDHCP recogido=null;
		boolean mensajeRecogido=false;
		Iterator<MensajeDHCP> it=_pilaMensajes.iterator();
		while(it.hasNext()&&!mensajeRecogido) {
			MensajeDHCP elemento=it.next();
			if(new String(elemento.getMac()).equals(new String(mac))) {
				recogido=elemento;
				it.remove();
				mensajeRecogido=true;
			}
		}
		return recogido;
	}
	public synchronized void zzz() {
		try {
			wait();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	//**
	//*
	
	//FUNCIONES PARA GENERAR LOS MENSAJES DHCP NECESARIOS
	//(ESTAS FUNCIONES SERAN UTILIZADAS POR EL COMUNICADOR)
	
	public MensajeDHCP generarDHCPOffer(MensajeDHCP mensaje) {
		byte[] cabeceraOffer=generarCabeceraOffer(mensaje);
		byte[] opcionesOffer=generarOpcionesOffer();
		
		MensajeDHCP mensajeDHCPOffer=montarMensaje(cabeceraOffer,opcionesOffer);
		return mensajeDHCPOffer;
	}
	
	public MensajeDHCP generarDHCPRequest(MensajeDHCP mensaje) {
		byte[] cabeceraRequest=generarCabeceraRequest(mensaje);
		boolean ack=comprobarIpSolicitada(mensaje.getRequestedIp(),mensaje.getMac());
		byte[] opcionesRequest=generarOpcionesRequest(ack);
		
		MensajeDHCP mensajeDHCPRequest=montarMensaje(cabeceraRequest,opcionesRequest);
		return mensajeDHCPRequest;
	}
	
	public MensajeDHCP generarDHCPRenovacion(MensajeDHCP mensaje) {
		byte[] ip=mensaje.getIPCabecera();
		byte[] mac=mensaje.getMac();
		boolean ack=comprobarIpSolicitada(ip,mac);
		byte[] cabeceraRenovacion=generarCabeceraRequest(mensaje);
		byte[] opcionesRenovacion=generarOpcionesRequest(ack);
		
		MensajeDHCP mensajeDHCPRenovacion=montarMensaje(cabeceraRenovacion,opcionesRenovacion);
		return mensajeDHCPRenovacion;
		
	}
	
	private MensajeDHCP montarMensaje(byte[] cabecera, byte[] opciones) {
		ByteBuffer bb= ByteBuffer.allocate(576);
		bb.put(cabecera);
		bb.put(opciones);
		byte[] datosMensaje=bb.array();
		MensajeDHCP mensaje= new MensajeDHCP(datosMensaje);
		return mensaje;
	}
	
	private boolean comprobarIpSolicitada(byte[] ip, byte[] mac) {
		int host=ip[3] & 0xFF;
		boolean ipCorrecta=true;
		
		for(Map.Entry<Integer, byte[]> item: _IpYMacAsociadas.entrySet() ) {
			String mac1=new String(item.getValue());
			String mac2=new String(mac);
			if(item.getKey()==host&&!mac1.equals(mac2)) {
				ipCorrecta=false;
				//LA IP ESTA EN USO POR OTRA DIRECCION MAC
			}
		}
		return ipCorrecta;
	}
	
	//FUNCIONES PARA GENERAR CABECERAS
	//*
	private byte[] generarCabeceraOffer(MensajeDHCP mensaje) {
		ByteBuffer bb=ByteBuffer.allocate(236);
		
		bb=insertarValoresIniciales(bb,mensaje,false);
		//YIADDR
		++IDHost;
		bb.put((byte)172);
		bb.put((byte)16);
		bb.put((byte)1);
		bb.put((byte)IDHost);
		//SIADDR,GIADDR,MAC,SNAME,FILE...
		if(bb.hasRemaining()) {
			bb.put((byte)0);
		}
	
		byte[] cabecera=bb.array();
		return cabecera;
	}
	private byte[] generarCabeceraRequest(MensajeDHCP mensaje) {
		ByteBuffer bb=ByteBuffer.allocate(236);
		
		bb=insertarValoresIniciales(bb,mensaje,true);
		//YIADDR
		bb.put(mensaje.getRequestedIp());
		//SIADDR,GIADDR,MAC,SNAME,FILE...
		if(bb.hasRemaining()) {
			bb.put((byte)0);
		}
	
		byte[] cabecera=bb.array();
		return cabecera;
	}
	
	private byte[] generarOpcionesOffer() {
		ByteBuffer bb=ByteBuffer.allocate(236);
		//MAGIC COOKIE
		bb=insertarMagicCookie(bb);
		//OPCION
		bb.put((byte)53);//TIPO DE MENSAJE
		bb.put((byte)1);//

		bb=insertarOpcionesFijas(bb);
		
		byte[] opcionesOffer=bb.array();
		return opcionesOffer;		


		
	}
	
	private byte[] generarOpcionesRequest(boolean ack) {
		ByteBuffer bb=ByteBuffer.allocate(236);
		//MAGIC COOKIE
		bb=insertarMagicCookie(bb);
		//OPCION
		bb.put((byte)53);//TIPO DE MENSAJE
		bb.put((byte)1);//
		if(ack) {
			bb.put((byte)5);//ACK
		}else {
			bb.put((byte)6);//NAK
		}
		bb=insertarOpcionesFijas(bb);
		
		byte[] opcionesOffer=bb.array();
		return opcionesOffer;		

	}
	
	//FUNCIONES PARA EL MANAGEMENT DE LAS MACS, COMPROBAR SI ESTAN EN USO,
	//AÑADIRLAS O ELIMINARLAS DE LA LISTA DE OCUPADAS
	public boolean comprobarMacLibre(byte[] mac) {
		boolean macLibre=true;
		for(byte[] b:_macOcupadas) {
			if(new String(b).equals(new String(mac))) {
				macLibre=false;
			}
		}
		return macLibre;
	}
	
	public synchronized void anhadirMac(byte[] mac) {
		_macOcupadas.add(mac);
	}
	public synchronized void eliminarMac(byte[] mac) {
		boolean macEliminada=false;
		Iterator<byte[]> it=_macOcupadas.iterator();
		while(it.hasNext()&&!macEliminada) {
			byte[] elemento=it.next();
			if(new String(elemento).equals(new String(mac))) {
				it.remove();
				macEliminada=true;
			}
		}
	}
	
	private ByteBuffer insertarValoresIniciales(ByteBuffer bb,MensajeDHCP mensaje, boolean broadcast) {
		//CAMPOS OP, HTYPE, HLEN, HOPS
		bb.put((byte)2);
		bb.put((byte)1);
		bb.put((byte)6);
		bb.put((byte)0);
		//CAMPO XID
		bb.put(mensaje.getXID());
		//SECS
		bb.put((byte)0);
		bb.put((byte)0);
		//FLAGS
		if(broadcast) {
			bb.put((byte)0);
			bb.put((byte)0);
		}else {
			bb.put((byte)0);
			bb.put((byte)32678);
		}
		//CIADDR (ip cliente)
		bb.put((byte)0);
		bb.put((byte)0);
		bb.put((byte)0);
		bb.put((byte)0);
		return bb;
	}
	
	private ByteBuffer insertarMagicCookie(ByteBuffer bb) {
		bb.put((byte)99);
		bb.put((byte)130);
		bb.put((byte)83);
		bb.put((byte)99);
		return bb;
	}
	
	private ByteBuffer insertarOpcionesFijas(ByteBuffer bb) {
		//OPCION
		bb.put((byte)1);//MASCARA
		bb.put((byte)4);//
		bb.put((byte)255);//255.255.255.0
		bb.put((byte)255);
		bb.put((byte)255);
		bb.put((byte)0);
		//OPCION
		bb.put((byte)6);//IP DNS
		bb.put((byte)4);//
		bb.put((byte)8);//8.8.8.8
		bb.put((byte)8);
		bb.put((byte)8);
		bb.put((byte)8);
		//OPCION
		bb.put((byte)3);//IP ROUTER
		bb.put((byte)4);//
		bb.put((byte)172);//255.255.255.0
		bb.put((byte)16);
		bb.put((byte)1);
		bb.put((byte)1);
		//OPCION
		bb.put((byte)3);//IP SERVIDOR (ESTE EQUIPO)
		bb.put((byte)4);//
		bb.put((byte)172);//255.255.255.0
		bb.put((byte)16);
		bb.put((byte)1);
		bb.put((byte)2);
		//OPCION
		bb.put((byte)51);//TIEMPO DE CESION (SEG)
		bb.put((byte)4);//
		bb.putInt(60);//60 SEGS
		//OPCION
		bb.put((byte)58);//TIEMPO DE RENOVACION (SEG)
		bb.put((byte)4);//
		bb.putInt(30);//30 SEGS
		//OPCION
		bb.put((byte)255);//TIEMPO DE CESION (SEG)
		//LLENAR EL RESTO DEL BUFFER DE 0
		if(bb.hasRemaining()) {
			bb.put((byte)0);
		}
		return bb;		
	}
	
	
	
}
