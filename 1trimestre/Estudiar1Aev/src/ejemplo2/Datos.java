package ejemplo2;

import java.util.ArrayList;
import java.util.Scanner;

public class Datos {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		ArrayList<String> nombres=new ArrayList<String>();
		ArrayList<Integer> edades=new ArrayList<Integer>();
		boolean fin=false;
		while(!fin) {
			
			String nombre=sc.nextLine();

			
			if(nombre.equals("*")) {
				fin=true;
			}else {
				int edad=Integer.parseInt(sc.nextLine());
				System.out.println("El nombre es "+nombre+" y tiene "+edad+" años.");
				nombres.add(nombre);
				edades.add(edad);
			}

		}
		System.out.println("Ok");
		
	}
}
