package ejemplo1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Mayusculas {
	
	public static void main(String[] args) {
		
		ProcessBuilder padre= new ProcessBuilder("java", "-cp","bin","ejemplo1.Padre");
		try {
			Process procesoPadre=padre.start();
			/*BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(procesoPadre.getOutputStream()));
			bw.write("AAA");
			bw.newLine();
			bw.flush();
			BufferedReader br=new BufferedReader(new InputStreamReader(procesoPadre.getInputStream()));
			System.out.println(br.readLine());*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
