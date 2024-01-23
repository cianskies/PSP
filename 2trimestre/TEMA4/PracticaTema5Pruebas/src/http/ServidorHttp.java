/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class ServidorHttp {
    
    public static void main(String[] args){
        
        try {
            int contador=0;
            ServerSocket sSocket=new ServerSocket(8081);
            while(true){
                Socket socket=sSocket.accept();
                System.out.println("Ok");
                Thread hilo=new Thread(new HiloHttp(socket,contador));
                ++contador;
                hilo.start();
                
            }

            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorHttp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
