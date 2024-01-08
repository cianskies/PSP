package principal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import encriptador.Encriptador;
import lectorConfiguracion.LectorConfiguracion;
import limpiador.Limpiador;

public class Principal {
	private static int numeroLimpiadores, numeroEncriptadores;
	private static String rutaArchivo,clave;
	
	
	public static void main(String[] args) {
		
		
		
		boolean encriptar=true;
		extraerDatosDeFicheroConfig();
		String textoLimpio = limpiarTexto();

		Encriptador e = instanciarEncriptador(encriptar, textoLimpio);
		e.EncriptarODesencriptarTexto(encriptar);
		encriptar=false;
		e.setTextoDeFicheroTxT("encriptado.txt");
		e.EncriptarODesencriptarTexto(encriptar);
		if(textoLimpio.equals(e.getTexto())) {
			System.out.println("ok");
		}
	}

	private static Encriptador instanciarEncriptador(boolean encriptar, String textoLimpio) {
		Encriptador e=new Encriptador(textoLimpio, numeroEncriptadores, clave);
		
		return e;
	}

	private static String limpiarTexto() {
		Limpiador l=new Limpiador(rutaArchivo,numeroLimpiadores);
		String textoLimpio=l.getTextoLimpio();
		File limpio=new File("limpio.txt");
		escribirLimpioTxT(textoLimpio, limpio);
		return textoLimpio;
	}

	private static void extraerDatosDeFicheroConfig() {
		LectorConfiguracion lectorConfiguracion=new LectorConfiguracion("config.txt");
		rutaArchivo=lectorConfiguracion.extraerTexto("Ruta del archivo:");
		clave=lectorConfiguracion.extraerTexto("Clave:").toUpperCase();
		numeroLimpiadores=lectorConfiguracion.extraerInt("NUMERO DE HILOS LIMPIADORES:");
		numeroEncriptadores=lectorConfiguracion.extraerInt("NUMERO DE HILOS ENCRIPTADORES:");
	}

	private static void escribirLimpioTxT(String textoLimpio, File limpio) {
		try {
			limpio.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileWriter fr;
		try {
			fr = new FileWriter(limpio);
			fr.write(textoLimpio);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
