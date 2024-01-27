/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrador
 */
public class HiloHttp implements Runnable{
    
    private Socket socket;
    private int id;
    private BufferedReader lector;
    private OutputStream salida;
    
    public HiloHttp(Socket socket, int contador){
        this.socket=socket;
        try {
            this.lector=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.salida=socket.getOutputStream();
            this.id=id;
            System.out.println("soy "+id);
        } catch (IOException ex) {
            Logger.getLogger(HiloHttp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Override
    public void run(){
        System.out.println("Se ha recibido la peticion de "+socket.getInetAddress());
            String linea;
            String respuesta="";
                try {
                    String peticion=lector.readLine();
                    System.out.println(peticion);
                    String archivo=extraerPeticion(peticion);
                    System.out.println(archivo);
                    if(!archivo.contains(".html")){
                        Path path= Paths.get("site/"+archivo);
                        byte[] bytesIcono=Files.readAllBytes(path);
                        salida.write(bytesIcono);
                    }else{
                        Path path= Paths.get("site/"+archivo);
                        List<String> index=Files.readAllLines(path);
                        StringBuilder sb=new StringBuilder();
                        for(String l: index){
                            sb.append(l);
                        }
                        
                      respuesta = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: " +sb.toString().length()+"\r\n"+
                        "Connection: close\r\n\r\n" +
                        sb.toString();
                        salida.write(respuesta.getBytes());
                    }

                    
                    salida.flush();
                    socket.close();
                    
                } catch (IOException ex) {
                    Logger.getLogger(HiloHttp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        
    }
    private String extraerPeticion(String linea) {
        
        Pattern patron = Pattern.compile("GET /([^\\s]+) HTTP/1\\.1");
        Matcher matcher = patron.matcher(linea);   
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "index.html";
        }
    }

    
}
