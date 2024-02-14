package DHCP;

<<<<<<< HEAD
public class ComunicadorDHCP implements Runnable {

	
	@Override
	public void run() {
		
	}
=======
import DHCP.MensajeDHCP.TipoMensaje;

public class ComunicadorDHCP implements Runnable{
	
	private MensajeDHCP _mensajeCliente;
	MensajeDHCP _mensajeRecogido;
	MensajeDHCP _mensajeRespuesta;
	private Datos _datos;
	private boolean _acabar;
	
	public ComunicadorDHCP(MensajeDHCP mensajeCliente, Datos datos) {
		this._mensajeCliente=mensajeCliente;
		this._datos=datos;
		this._acabar=false;
		
		datos.anhadirMac(_mensajeCliente.getMac());
	}
	
	@Override
	
	public void run() {
		
		interpretarMensaje(this._mensajeCliente);
		
		if(!_acabar) {
			_mensajeRecogido=_datos.recogerMEnsaje(_mensajeCliente.getMac());
			if(_mensajeRecogido!=null) {
				interpretarMensaje(_mensajeRecogido);
			}
			
		}
		_datos.eliminarMac(_mensajeCliente.getMac());
	}
	
	private void interpretarMensaje(MensajeDHCP mensaje) {
		
		switch(mensaje.getTipoDeMensaje()){
		
		case Discover:
			_mensajeRespuesta=_datos.generarDHCPOffer(mensaje);
			break;
		case RequestRenovacion:
			_mensajeRespuesta=_datos.generarDHCPRenovacion(mensaje);
			break;
		case Request:
			_mensajeRespuesta=_datos.generarDHCPRequest(mensaje);
			break;
		default:
			System.err.println("Seguramente se haya detectado un mensaje DHCP Inform. Ignorado con éxito.");
			_mensajeRespuesta=null;
			break;
		}
		
		if(_mensajeRespuesta!=null) {
			_datos.enviarMensaje(_mensajeRespuesta);
		}
		if(_mensajeCliente.getTipoDeMensaje()==TipoMensaje.Discover) {
			this._datos.zzz();
		}
	}

>>>>>>> 7eba26d5f16e1e3c66f0ec2968644044939fdd70
}
