package encriptadorDesencriptador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import encriptadorDesencriptador.HiloEncriptador.Encriptacion;

public class EncriptadorDesencriptador {
	private int nHilos;
	private String clave;
	private Encriptacion modoEncriptacion;
	public EncriptadorDesencriptador(int nHilos, String clave) {
		this.clave=clave;
		this.nHilos=nHilos;
	}
	//Esta función encripta el archivo
	public void encriptar() {
		modoEncriptacion = Encriptacion.Encriptar;
		ejecutarHilosEncriptadores();
	}
	//Esta función desencripta el archivo
	public void desencriptar() {
		modoEncriptacion = Encriptacion.Desencriptar;
		ejecutarHilosEncriptadores();
	}
	//Esta función ejecuta los hilos encriptadores
	private void ejecutarHilosEncriptadores()
	{
		try {
			DatosEncriptar datos = crearClaseDatos();
			Thread[] hilos = new Thread[nHilos];
			for(int i =0;i<hilos.length;++i) {
				hilos[i] = new Thread(new HiloEncriptador(datos,i,modoEncriptacion,clave));
				hilos[i].start();
			}
			for(int i=0;i<hilos.length;++i) {
				hilos[i].join();
			}
			escribirEnArchivo(datos.getTextoEncriptado());			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	//Esta función crea la clase Datos
	private DatosEncriptar crearClaseDatos() throws IOException {
		String ruta;
		//dependiendo de la encriptación ruta será limpio.txt o encriptado.txt
		if(modoEncriptacion==Encriptacion.Encriptar) {
			ruta = "limpio.txt";
		}
		else {
			ruta="encriptado.txt";
		}
		//Se devuelve Datos
		return new DatosEncriptar(ruta,nHilos);
	}
	//Esta función escribe en el archivo
	private void escribirEnArchivo(String texto) {
		File archivo = crearArchivo();
		try {
			FileWriter fw = new FileWriter(archivo);
			BufferedWriter bw  =new BufferedWriter(fw);
			bw.write(texto);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	//Esta función crea el archivo dónde se escribirá el resultado
	private File crearArchivo() {
		String nombreArchivo;
		//Dependiendo de la encriptación el nombre será diferente
		if(modoEncriptacion == Encriptacion.Encriptar) {
			nombreArchivo = "encriptado.txt";
		}
		else {
			nombreArchivo ="desencriptado.txt";
		}
		File archivo = new File(nombreArchivo);
		return archivo;
	}
}
