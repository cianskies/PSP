package procesos.invertirCadena;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class InvertirCadenas {
	private static Scanner sc=new Scanner(System.in);
	private static boolean seguir=true;
	private static BufferedReader br;
	private static BufferedWriter bw;
	
	public static void main(String[] args) {
	

		
		while(seguir) {
			System.out.println("Introduce una cadena");
			String cadena=pedirCadenaTexto();
			iniciarLectores();
			try {
				bw.write(cadena);
				bw.newLine();
				bw.flush();
				System.out.println("La cadena invertida es: "+br.readLine());
				bw.close();
				br.close();
				if(cadena.equals("fin")) {
					System.out.println("bye");
					seguir=false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	private static void iniciarLectores() {
		ProcessBuilder pb=new ProcessBuilder("java", "-cp","bin","procesos.invertirCadena.InvertirCadena");
		Process proceso;
		try {
			proceso = pb.start();
			br=new BufferedReader(new InputStreamReader(proceso.getInputStream()));
			bw=new BufferedWriter(new OutputStreamWriter(proceso.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static String pedirCadenaTexto() {
		System.out.println("Introduce texto");
		String cadena=sc.nextLine();
		return cadena;
	}
}
