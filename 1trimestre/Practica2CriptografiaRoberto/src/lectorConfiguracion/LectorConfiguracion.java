package lectorConfiguracion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LectorConfiguracion {
	
	private String rutaArchivo;
	private File instrucciones;
	private BufferedReader br;
	
	public LectorConfiguracion(String rutaArchivo) {
		this.rutaArchivo=rutaArchivo;
		instrucciones=instanciarArchivo();
		
	}
	private File instanciarArchivo() {
		File instrucciones=new File(rutaArchivo);

		
		return instrucciones;
	}
	private BufferedReader instanciarManual() {
		BufferedReader brInstrucciones=null;
		try {
			FileReader fr = new FileReader(instrucciones);
			brInstrucciones=new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
		
		return brInstrucciones;
		
	}
    public String extraerTexto(String patron) {
    	br=instanciarManual();
    	Pattern pattern=Pattern.compile(patron);
    	String linea,resultado=null;
    	try {
			while((linea=br.readLine())!=null) {
				Matcher matcher=pattern.matcher(linea);
				if(matcher.find()) {
					 resultado = linea.substring(matcher.end()).trim();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        return resultado;
    }
    
    public int extraerInt(String patron) {
    	br=instanciarManual();
    	Pattern pattern=Pattern.compile("\\d+");
    	String linea;
    	int resultado=0;
    	try {
			while((linea=br.readLine())!=null) {
				Matcher matcher=pattern.matcher(linea);
				if(matcher.find()) {
					 int num=Integer.parseInt(matcher.group());
					 if(linea.contains(patron)){
						 resultado=num;
					 }
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        return resultado;
    	
    }
	

}
