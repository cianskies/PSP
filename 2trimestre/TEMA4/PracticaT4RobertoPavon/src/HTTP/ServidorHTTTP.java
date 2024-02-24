package HTTP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorHTTTP {
	public static void main(String[] args) {
		
		ServerSocket s;
		
		try {
			s=new ServerSocket(8081);
			while(true) {
				Socket socket=s.accept();
				System.out.println("Se ha recibido un nuevo mensaje");
				
				Thread hiloHTTP=new Thread(new HiloHTTP(socket));
				hiloHTTP.start();
			}
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
