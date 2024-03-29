package DHCP;
import java.nio.ByteBuffer;

public class MensajeDHCP {
	
	public enum TipoMensaje {
		Discover,
		Offer,
		Request,
		RequestRenovacion,
		Otro
	}
	
	private byte[] _datosMensaje;
	
	private byte[] _mac;
	private byte[] _IpCabecera;
	private TipoMensaje _tipoDeMensaje;
	private byte[] _requestedIp;
	private byte[] _xid;

	
	public MensajeDHCP(byte[] datosMensaje) {
		this._datosMensaje=datosMensaje;
		this._mac=identificarMac(this._datosMensaje);
		this._IpCabecera=identificarIpCabecera(this._datosMensaje);
		this._tipoDeMensaje=identificarTipoDeMensaje(this._datosMensaje);
		this._xid=identificarXid(this._datosMensaje);
	}
	
	public byte[] getDatos() {
		return this._datosMensaje;
	}
	public byte[] getMac() {
		return this._mac;
	}
	
	public byte[] getIPCabecera() {
		return this._IpCabecera;
	}
	
	public TipoMensaje getTipoDeMensaje() {
		return _tipoDeMensaje;
	}
	
	public byte[] getRequestedIp() {
		return this._requestedIp;
	}
	
	public byte[] getXid() {
		return _xid;
	}
	
	//ESTO SEGURO K LO PUEDO HACER MAS LIMPIO CON UN DICCIONARIO PERO VAMOS SIN PROBLEMA
	private byte[] identificarMac(byte[] datos) {
		byte[] mac=extraerDeByteArray(datos,28,16);
		return mac;
	}
	
	private byte[] identificarIpCabecera(byte[] datos) {
		byte[] ip=extraerDeByteArray(datos,12,4);
		return ip;
	}
	
	private byte[] identificarRequestedIp(byte[] datos) {
		byte[] requestedIp=extraerOpcion(50);
		return requestedIp;
	}
	
	private TipoMensaje identificarTipoDeMensaje(byte[] datos) {
		byte[] opcionTipoMensaje=extraerOpcion(53);
		int tipoDeMensaje=opcionTipoMensaje[0] & 0xFF;
		TipoMensaje tipo;
		if(tipoDeMensaje==1) {
			tipo=TipoMensaje.Discover;
		}else if(tipoDeMensaje==3 &&_IpCabecera[0]!=0) {
			tipo=TipoMensaje.RequestRenovacion;
			this._requestedIp=new byte[3];
			
		}else if(tipoDeMensaje==3) {
			tipo=TipoMensaje.Request;
			this._requestedIp=identificarRequestedIp(this._datosMensaje);
		}else {
			tipo=TipoMensaje.Otro;
		}
		return tipo;
	}
	
	private byte[] identificarXid(byte[] datos) {
		//copmrobar
		byte[] xid=extraerDeByteArray(datos,4,4);
		return xid;
		
	}
	private byte[] extraerDeByteArray(byte[] array,int offset,int longitud) {
		ByteBuffer bb=ByteBuffer.allocate(longitud);
		bb.put(array,offset,longitud);
		byte[] resultado=bb.array();
		return resultado;
	}
	
	private byte[] extraerOpcion(int codigo) {
		byte[] opciones=extraerDeByteArray(this._datosMensaje,236,this._datosMensaje.length-237);
		int longitud=getLongitudDeOpcion(codigo);
		int offset=getOffsetDeOpcion(codigo,opciones);
		byte[] valorOpcion=extraerDeByteArray(opciones,offset+2,longitud);
		return valorOpcion;
	}
	
	private int getLongitudDeOpcion(int codigo) {
		int longitudDeOpcion=4;
		if(codigo==53) {
			longitudDeOpcion=1;
		}
		return longitudDeOpcion;
	}
	
	private int getOffsetDeOpcion(int codigo,byte[] opciones) {
		int offset=0;
		for(int i=0;i<opciones.length && (opciones[i] & 0xFF) !=codigo;++i) {
			++offset;
		}
		return offset;
	}
	
}
