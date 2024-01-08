package limpiador;

import java.util.HashMap;

public class HiloLimpiador implements Runnable {
	private Datos datos;
	private HashMap<Character,Character> caracteresEspeciales;
	private int idHilo;
	public HiloLimpiador(Datos datos, int idHilo) {
		this.datos=datos;
		this.idHilo=idHilo;
		declararHashMapCaracteresEspeciales();
	}
	//Esta funci�n crea el HashMap de caracteres especiales
	private void declararHashMapCaracteresEspeciales() {
		caracteresEspeciales = new HashMap<Character,Character>();
		caracteresEspeciales.put('�', 'A');
		caracteresEspeciales.put('�', 'E');
		caracteresEspeciales.put('�', 'I');
		caracteresEspeciales.put('�', 'O');
		caracteresEspeciales.put('�', 'U');
		caracteresEspeciales.put('�', 'N');
		caracteresEspeciales.put('�', 'U');
	}
	
	@Override
	public void run() {
		//Se pide un trozo del texto a datos
		String texto = datos.getTrozoTexto(idHilo);
		StringBuilder sb = new StringBuilder();
		char letra;
		for(int i=0;i<texto.length();++i) {
			//Se asigna valor a letra
			letra = devolverLetraValida(texto.charAt(i));
			if(Character.compare(letra, '\0') != 0) {
				//Si letra no es nula se a�ade 
				sb.append(letra);
			}
		}
		//A�adimos el trozo  de texto limpio
		datos.anhadirTrozoLimpio(sb.toString(),idHilo);
	}
	//Esta funci�n devuelve una letra v�lida a partir de un caracter
	private char devolverLetraValida(char caracter) {
		caracter = Character.toUpperCase(caracter);
		if(caracter>='A'&& caracter<='Z') {
			//caracter se mantiene constante porque est� en el intervalo
		}
		else {
			//Devuelve una letra v�lida o el caracter '\0'
			caracter = tranformarLetraEspecialOConTilde(caracter);
		}
		return caracter;
	}
	//Esta funci�n transforma letras especiales en normales
	private char tranformarLetraEspecialOConTilde(char caracter) {
		if(caracteresEspeciales.containsKey(caracter)) {
			caracter = caracteresEspeciales.get(caracter);
		}
		else {
			//si no est� en el diccionario caracter es nulo
			caracter='\0';
		}
		return caracter;
	}
}
