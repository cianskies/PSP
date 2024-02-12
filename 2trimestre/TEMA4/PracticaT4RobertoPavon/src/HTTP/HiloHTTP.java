package HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HiloHTTP implements Runnable{
	private Socket _s;
	private int _id;
	private BufferedReader _lector;
	private OutputStream _salida;
	
	public HiloHTTP(Socket socket) {
		this._s=socket;
		try {
			this._lector=new BufferedReader(new InputStreamReader(this._s.getInputStream()));
			this._salida=_s.getOutputStream();
			System.out.println("Se ha creado un hilo para atender un mensaje");
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		System.out.println("Comienzo a atender la peticion de "+_s.getInetAddress());
		String linea;
		String respuesta="";
		
		try {
			String peticion=_lector.readLine();
			System.out.println(peticion);
			String archivo=extraerPeticion(peticion);
			System.out.println(archivo);
			
			Path path=Paths.get("site/"+archivo);
			
			if(!archivo.contains(".html")) {
				
				byte[] bytesIcono=Files.readAllBytes(path);
				_salida.write(bytesIcono);
			}else {
				//Se trata de texto plano y lo trataremos como tal
				List<String> index=Files.readAllLines(path);
				StringBuilder sb=new StringBuilder();
				for(String s:index) {
					sb.append(s);
				}
				
				respuesta= "HTTP/1.1 200 OK\r\n"+
						"Content-Type: text/html\r\n"+
						"Content-Length: "+sb.toString().length()+"\r\n"+
						"Connection:close\r\n\r\n"+
						sb.toString();
				
				_salida.write(respuesta.getBytes());
				
				
			}
			
			_salida.flush();
			_s.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String extraerPeticion(String linea) {
		Pattern pattern=Pattern.compile("GE /([T^\\s])+) HTTP/1\\.1");
		Matcher matcher=pattern.matcher(linea);
		if(matcher.find()) {
			return matcher.group(1);
		}else {
			return "index.html";
		}
	}
	
	
	
}
