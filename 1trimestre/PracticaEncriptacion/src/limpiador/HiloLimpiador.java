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
	//Esta función crea el HashMap de caracteres especiales
	private void declararHashMapCaracteresEspeciales() {
		caracteresEspeciales = new HashMap<Character,Character>();
		caracteresEspeciales.put('Á', 'A');
		caracteresEspeciales.put('É', 'E');
		caracteresEspeciales.put('Í', 'I');
		caracteresEspeciales.put('Ó', 'O');
		caracteresEspeciales.put('Ú', 'U');
		caracteresEspeciales.put('Ñ', 'N');
		caracteresEspeciales.put('Ü', 'U');
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
				//Si letra no es nula se añade 
				sb.append(letra);
			}
		}
		//Añadimos el trozo  de texto limpio
		datos.anhadirTrozoLimpio(sb.toString(),idHilo);
	}
	//Esta función devuelve una letra válida a partir de un caracter
	private char devolverLetraValida(char caracter) {
		caracter = Character.toUpperCase(caracter);
		if(caracter>='A'&& caracter<='Z') {
			//caracter se mantiene constante porque está en el intervalo
		}
		else {
			//Devuelve una letra válida o el caracter '\0'
			caracter = tranformarLetraEspecialOConTilde(caracter);
		}
		return caracter;
	}
	//Esta función transforma letras especiales en normales
	private char tranformarLetraEspecialOConTilde(char caracter) {
		if(caracteresEspeciales.containsKey(caracter)) {
			caracter = caracteresEspeciales.get(caracter);
		}
		else {
			//si no está en el diccionario caracter es nulo
			caracter='\0';
		}
		return caracter;
	}
}
