package grabador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import director.Posicion;
public class Grabador {
	public static void main(String[] args) {
		HashMap<Character,Character> colores=new HashMap<Character,Character>();
		colores.put('0',' ');
		colores.put('1','#');
		colores.put('2','/');
		colores.put('3','&');
		colores.put('4', '¥');
		File archivo=new File("hormiga.txt");
		int contador=1;
		int buffer;
		try {
			FileWriter writer=new FileWriter(archivo);
			Scanner sc=new Scanner(System.in);
			int alturaTablero=Integer.parseInt(sc.nextLine());
			buffer=alturaTablero;
			String lineaTablero;
			while(true) {
				if(buffer==alturaTablero) {
					writer.write(String.valueOf(contador));
					writer.write("\n");
					buffer=0;
					++contador;
				}else {
					lineaTablero=sc.nextLine();
					lineaTablero=ConvertirLinea(lineaTablero,colores);
					writer.write(lineaTablero);
					writer.write("\n");
					++buffer;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static String ConvertirLinea(String linea,HashMap<Character,Character> colores) {
		String nuevaLinea="";
		char[] buf=linea.toCharArray();
		for(int i=0;i<linea.length();++i) {

				nuevaLinea+=colores.get(buf[i]);
			}
		
		return nuevaLinea;
	}


}
