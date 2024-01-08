package director;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Director {

	public static void main(String[] args) {
		
		try {
		
			int numeroHormigas=extraerDatosDeInstrucciones("Numero de Hormigas");
			int anchoTablero=extraerDatosDeInstrucciones("Ancho del Tablero");
			int altoTablero=extraerDatosDeInstrucciones("Alto del Tablero");
			int numeromax=extraerDatosDeInstrucciones("Numero de Movimientos");
			
			procesoHormiga[] hormigas = CrearProcesosHormiga(numeroHormigas);
			
			Posicion[][] tablero=new Posicion[anchoTablero][altoTablero];
			tablero=VaciarTablero(tablero,anchoTablero,altoTablero);
			
			DarOrdenInicialAHormigas(numeroHormigas, hormigas);
	
			BufferedWriter bwGrabador=new BufferedWriter(IniciarProcesoGrabador(altoTablero));
			
			while(numeromax>1) {
			--numeromax;
			RealizarIteracion(anchoTablero, altoTablero, hormigas, tablero, bwGrabador);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	private static void DarOrdenInicialAHormigas(int numeroHormigas, procesoHormiga[] hormigas) throws IOException {
		for(int i=0;i<numeroHormigas;++i) {
			IndicarReglaYDireccion(hormigas[i].getBwH(),hormigas[i].getRegla(),hormigas[i].getDireccion());
		}
		for(int i=0;i<numeroHormigas;++i) {
			IndicarPosicionaHormiga(hormigas[i].getPosicion(),hormigas[i].getBwH());
		}
	}

	private static void RealizarIteracion(int anchoTablero, int altoTablero, procesoHormiga[] hormigas,
			Posicion[][] tablero, BufferedWriter bwGrabador) {
		for(int i=0;i<hormigas.length;++i) {
			hormigas[i].setPosicion(recibirPosicionHormiga(hormigas[i].getBrH(),tablero));
			CambiarColorDeCasilla(tablero, hormigas[i].getPosicion());
			IndicarColorHormiga(tablero,hormigas[i].getPosicion(), hormigas[i].getBwH());
			}
		try {
			EnviarTableroAgrabador(tablero,bwGrabador,anchoTablero,altoTablero,hormigas);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static int extraerDatosDeInstrucciones(String patron) throws IOException {
		BufferedReader brInstrucciones= LeerManualDeInstrucciones();
		String linea;
		Pattern pattern = Pattern.compile("\\d+");
		int dato=0;
		Matcher matcher;
		while ((linea = brInstrucciones.readLine()) != null) {
		
        matcher= pattern.matcher(linea);
       
        while (matcher.find()) {
            int numero = Integer.parseInt(matcher.group());
            if (linea.contains(patron)) {
                dato = numero;
            }
        }
		}
        return dato;
	}
	private static String extraerTextoDeInstrucciones(String texto) throws IOException {
		BufferedReader brInstrucciones= LeerManualDeInstrucciones();
		 String linea;
		    String dato = "";
		    String patron = texto + "\\s+(\\w+)";
		    Pattern pattern = Pattern.compile(patron);

		    while ((linea = brInstrucciones.readLine()) != null) {
		        Matcher matcher = pattern.matcher(linea);
		        if (matcher.find()) {
		            dato = matcher.group(1);
		            break; 
		        }
		    }

		    return dato;
		
	}
	private static procesoHormiga[] CrearProcesosHormiga(int numeroHormigas)
			throws IOException {
		
		int ancho,alto,direccion;
		procesoHormiga pHormiga;
		String regla;
		Process proceso;
		BufferedReader brH;
		BufferedWriter bwH;
		procesoHormiga[] hormigas=new procesoHormiga[numeroHormigas];
		for(int i=0;i<numeroHormigas;++i) {
			ancho=extraerDatosDeInstrucciones("Ancho de Hormiga "+i);
			alto=extraerDatosDeInstrucciones("Alto de Hormiga "+i);
			Posicion inicial=new Posicion(ancho,alto);		
			direccion=extraerDatosDeInstrucciones("Direccion de Hormiga "+i);
			regla=extraerTextoDeInstrucciones("Regla de Hormiga "+i+":");
			proceso=IniciarProcesoHormiga();
			brH=IniciarBR(proceso);
			bwH=IniciarBW(proceso);
			pHormiga=new procesoHormiga(inicial,brH,bwH,direccion,regla);
			hormigas[i]=pHormiga;
		}
		return hormigas;
	}

	private static BufferedReader LeerManualDeInstrucciones() throws FileNotFoundException, IOException {
		
		File instrucciones=new File("instrucciones.txt");
		FileReader fr=new FileReader(instrucciones);
		BufferedReader brInstrucciones=new BufferedReader(fr);
		return brInstrucciones;
	}

	private static void CambiarColorDeCasilla(Posicion[][] tablero, Posicion posicionHormiga) {
		tablero[posicionHormiga.GetAncho()][posicionHormiga.GetAlto()].setColor(posicionHormiga.GetColor()+1);
		if(tablero[posicionHormiga.GetAncho()][posicionHormiga.GetAlto()].GetColor()>3) {
			tablero[posicionHormiga.GetAncho()][posicionHormiga.GetAlto()].setColor(0);
		}
	}
	
	private static void IndicarReglaYDireccion(BufferedWriter bwHormiga,String regla, int direccion) throws IOException {
		bwHormiga.write(regla);
		bwHormiga.newLine();
		bwHormiga.flush();
		bwHormiga.write(String.valueOf(direccion));
		bwHormiga.newLine();
		bwHormiga.flush();
	}
	
	private static BufferedWriter IniciarProcesoGrabador(int alto) throws IOException {
		ProcessBuilder pbGrabador= new ProcessBuilder("java", "-cp", "bin","grabador.Grabador");
		Process procesoGrabador=pbGrabador.start();
		BufferedWriter bwGrabador=new BufferedWriter(new OutputStreamWriter(procesoGrabador.getOutputStream()));
		bwGrabador.write(String.valueOf(alto));
		bwGrabador.newLine();
		bwGrabador.flush();
		return bwGrabador;
	}
	
	private static Process IniciarProcesoHormiga() throws IOException {
		ProcessBuilder pbHormiga=new ProcessBuilder("java","-cp","bin","hormiga.Hormiga");
		Process procesoHormiga=pbHormiga.start();
		return procesoHormiga;
	}
	private static BufferedReader IniciarBR(Process proceso) {
		BufferedReader brHormigx=new BufferedReader(new InputStreamReader(proceso.getInputStream()));
		return brHormigx;
	}
	private static BufferedWriter IniciarBW(Process proceso) {
		BufferedWriter bWHormigx=new BufferedWriter(new OutputStreamWriter(proceso.getOutputStream()));
		return bWHormigx;
	}
	
	private static void EnviarTableroAgrabador(Posicion[][] tablero, BufferedWriter bwGrabador,int anchoTablero, int altoTablero, procesoHormiga[] hormigas) throws IOException {
		String lineaTablero="";
		boolean casillaOcupada;
		for(int i=0;i<anchoTablero;++i) {
			for(int j=0;j<altoTablero;++j) {
			casillaOcupada=comprobarSiHayHormigaEnCasilla(tablero[i][j],hormigas);
			if(casillaOcupada) {
				lineaTablero+='4';
				casillaOcupada=false;
			}else {
						lineaTablero+=tablero[i][j].GetColor();
					}
			}	
				bwGrabador.write(lineaTablero);
				bwGrabador.newLine();
				bwGrabador.flush();
				lineaTablero="";
		}
		bwGrabador.newLine();
		bwGrabador.flush();
	}
	private static boolean comprobarSiHayHormigaEnCasilla(Posicion posicion, procesoHormiga[] hormigas) {
		boolean casillaOcupada=false;
		for(int i=0;i<hormigas.length;++i) {
			if(posicion.GetAncho()==hormigas[i].getPosicion().GetAncho()&&posicion.GetAlto()==hormigas[i].getPosicion().GetAlto()) {
				casillaOcupada=true;
			}
		}
		return casillaOcupada;
	}
	
	private static void IndicarPosicionaHormiga(Posicion posicion, BufferedWriter bwHormiga) {
		try {
			bwHormiga.write(String.valueOf(posicion.GetAncho()));
			bwHormiga.newLine();
			bwHormiga.flush();
			bwHormiga.write(String.valueOf(posicion.GetAlto()));
			bwHormiga.newLine();
			bwHormiga.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void IndicarColorHormiga(Posicion[][] tablero, Posicion posicion,BufferedWriter bwHormiga) {
		try {
			bwHormiga.write(String.valueOf(posicion.GetColor()));
			bwHormiga.newLine();
			bwHormiga.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static Posicion[][] VaciarTablero(Posicion[][] tablero,int ancho,int alto){
		Posicion[][] tableroVacio=tablero;
		for(int i=0;i<ancho;++i) {
			for(int j=0;j<alto;++j) {
				tableroVacio[i][j]=new Posicion(i,j,0);
			}
		}
		return tableroVacio;
	}
	private static Posicion recibirPosicionHormiga(BufferedReader brHormiga,Posicion[][] tablero) {
		int ancho=0,alto=0;
		int color=0;
		try {
			ancho=Integer.valueOf(brHormiga.readLine());
			if(ancho>tablero.length-1) {
				ancho=0;
			}
			if(ancho<0) {
				ancho=tablero.length-1;
			}
			alto=Integer.valueOf(brHormiga.readLine());
			if(alto>tablero[0].length-1) {
				alto=0;
			}
			if(alto<0) {
				alto=tablero.length-1;
			}
			
			color=tablero[ancho][alto].GetColor();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Posicion nueva=new Posicion(ancho,alto,color);
		return nueva;
	}
}