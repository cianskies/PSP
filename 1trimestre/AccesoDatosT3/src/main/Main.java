package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;




public class Main {

	public static void main(String[] args) {
		
		File archivo=new File("depAModificar.txt");
		FileReader lector;
		BufferedReader br=null;
		try {
			lector = new FileReader(archivo);
			br=new BufferedReader(lector);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBManager manager=new DBManager();

		String linea;
		try {
			while((linea=br.readLine())!=null) {
				String[] valores=linea.replaceAll(" ","").split(",");
				System.out.println("a");
				if(valores[1]!="") {
					manager.modificarDepartamento(Integer.valueOf(valores[0]), valores[1]);
				}
				if(valores.length>2) {
					if(valores[2]!=null) {
						manager.modificarLocalidad(Integer.valueOf(valores[0]), valores[2]);
					}
				}
				br.readLine();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		

	}
	

}
	

