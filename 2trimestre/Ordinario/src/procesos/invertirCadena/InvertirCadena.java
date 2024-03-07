package procesos.invertirCadena;

import java.util.Scanner;

public class InvertirCadena {
	private static Scanner sc=new Scanner(System.in);
	public static void main(String[] args) {
		String cadena=pedirCadenaTexto();
		String invertido=invertirString(cadena);
	}
	private static String pedirCadenaTexto() {
		String cadena=sc.nextLine();
		return cadena;
	}
	private static String invertirString(String cadena) {
		char[] caracteres=cadena.toCharArray();
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<caracteres.length;++i) {
			sb.append(caracteres[caracteres.length-i-1]);
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	
}
