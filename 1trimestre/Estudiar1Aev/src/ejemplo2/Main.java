package ejemplo2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		ProcessBuilder pb=new ProcessBuilder("java", "-cp","bin","ejemplo2.Datos");
		try {
			Process proceso=pb.start();
			BufferedReader br=new BufferedReader(new InputStreamReader(proceso.getInputStream()));
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(proceso.getOutputStream()));
			Scanner sc=new Scanner(System.in);
			boolean asterisco=false;
			String nombre,edad;
			boolean fin=false;
			while(!fin) {
				System.out.println("introduce el nombre");
				nombre=sc.nextLine();
				if(nombre.equals("*")) {
					fin=true;
					bw.write(nombre);
					bw.newLine();
					bw.flush();
				}else {
					System.out.println("introduce edad");
					edad=sc.nextLine();
					
					bw.write(nombre);
					bw.newLine();
					bw.flush();
						
					bw.write(edad);
					bw.newLine();
					bw.flush();
					String resultado=br.readLine();
					System.out.println(resultado);
				}

				}
			String resultado=br.readLine();
			System.out.println(resultado);
			
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}

}
