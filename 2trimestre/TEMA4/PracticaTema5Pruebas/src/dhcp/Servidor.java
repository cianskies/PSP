/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhcp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class Servidor {
    public static void main(String[] args){
         try {
            //Lo primero seria detectar el mensaje que llega al puerto 68/67
            DatagramSocket socketPuerto67=new DatagramSocket(67);
            Datos datos=new Datos(socketPuerto67);

            //Almacenar el mensaje
            byte[] bufferMensaje=new byte[1024];
            DatagramPacket dpMensaje=new DatagramPacket(bufferMensaje,bufferMensaje.length);
            socketPuerto67.receive(dpMensaje);
            System.out.println("Ok");
            bufferMensaje=dpMensaje.getData();
            
            MensajeDHCP mensajeDHCP=new MensajeDHCP(bufferMensaje);
            Thread comunicador =new Thread(new ComunicadorDHCP(mensajeDHCP,datos));
            comunicador.run();
            
            
         }catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
