package principal;
//Esta excepción salta cuando el "config.txt" no tiene un formato correcto
public class ArchivoConfiguracionIncorrectoException extends Exception {
	private String mensaje;
	public ArchivoConfiguracionIncorrectoException(String mensaje) {
		this.mensaje=mensaje;
	}
	@Override
	public void printStackTrace() {
		System.out.println(mensaje);
	}
	
	
}
