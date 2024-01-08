package hormiga;
import java.io.*;
import java.util.*;

import director.Posicion;

public class Hormiga {
	
	public static void main(String[] args) {
		HashMap<Integer,Character> colores=new HashMap<Integer,Character>();
		Scanner sc=new Scanner(System.in);
		String regla=sc.nextLine();
		int color=0;
		char charRegla[]=regla.toCharArray();
		int direccion=Integer.valueOf(sc.nextLine());
		colores.put(0,charRegla[0]);
		colores.put(1,charRegla[1]);
		colores.put(2,charRegla[2]);
		colores.put(3,charRegla[3]);
		Posicion actual=cogerPosicionInicial(sc);
		EnviarPosicionADirector(actual);
		while(true) {
				color=Integer.parseInt(sc.nextLine());
			
				direccion=cambiarDireccion(color,direccion,colores);
				
				actual=Moverse(actual,direccion);
				
				EnviarPosicionADirector(actual);
			}
		}
	
	private static Posicion Moverse(Posicion posicion,int direccion) {
		
			Posicion nueva=new Posicion(posicion.GetAncho(),posicion.GetAlto());

				switch(direccion) {
				case 0://up
					nueva.setAlto(posicion.GetAlto()-1);
					break;
				case 1://der
					nueva.setAncho(posicion.GetAncho()+1);
					break;
				case 2://abajo
					nueva.setAlto(posicion.GetAlto()+1);
					break;
				case 3://izqd
					nueva.setAncho(posicion.GetAncho()-1);
					break;
				}
			return nueva;
	}
	private static Posicion cogerPosicionInicial(Scanner sc) {
		int ancho,alto;
		ancho=Integer.parseInt(sc.nextLine());
		alto=Integer.parseInt(sc.nextLine());
		Posicion inicial=new Posicion(ancho,alto);
		return inicial;
	}
	private static int cambiarDireccion(int color, int direccion,HashMap<Integer,Character> colores) {

		if(colores.get(color).equals('l')) {
			++direccion;
			if(direccion>3) {
				direccion=0;	
			}	
			}else if(colores.get(color).equals('r'))
				--direccion;
				if(direccion<0) {
			direccion=3;
			}		
			return direccion;
	}
		

	private static void EnviarPosicionADirector(Posicion posicion) {
		System.out.println(String.valueOf(posicion.GetAncho()));
		System.out.println(String.valueOf(posicion.GetAlto()));
	}
}
