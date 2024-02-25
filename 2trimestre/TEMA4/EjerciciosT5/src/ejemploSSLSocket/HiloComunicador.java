package ejemploSSLSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLSocket;

public class HiloComunicador implements Runnable{
	
	private SSLSocket socket;
	private BufferedReader br;
	private BufferedWriter bw;
	
	public HiloComunicador(SSLSocket socket) {
		this.socket=socket;
		try {
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Override
	public void run() {
		String mensaje1=recibirMensaje();
		System.out.println(mensaje1);
		
		String respuesta="Alto y claro mi capitan";
		enviarMensaje(respuesta);
		System.out.println("He enviado la respuesta: "+respuesta);
		
	}
	private String recibirMensaje() {
		String mensaje=null;
		try {
			mensaje=br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mensaje;
		
	}
	
	private void enviarMensaje(String mensaje) {
		try {
			bw.write(mensaje);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
