package ejemplo1;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Padre {
	public static void main(String[] args) {
		/*Scanner sc=new Scanner(System.in);
		String entrada=sc.nextLine();*/
		System.out.println(entrada);
		File arxivo=new File("prueba.bin");
		try {
			arxivo.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
