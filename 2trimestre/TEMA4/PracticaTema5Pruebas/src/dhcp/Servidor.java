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
    public static DatagramSocket socketPuerto67;
    public static void main(String[] args){
         try {
            //Lo primero seria detectar el mensaje que llega al puerto 68/67
            socketPuerto67=new DatagramSocket(67);
            Datos datos=new Datos(socketPuerto67);

            //Almacenar el mensaje
            while(true){
                MensajeDHCP mensajeDHCP=recibirNuevoMensajeDHCP();
                //aqui debe comprobar si la mac o el xid lo esta utilizando algun hilo
                //si no lo esta usando ninguno, crea un nuevo comunicador dhcp.
                //Si hay uno usandolo, enviarslo a datos.
                if(datos.comprobarTransaccion(mensajeDHCP.getXID())){
                    Thread comunicador =new Thread(new ComunicadorDHCP(mensajeDHCP,datos));
                    
                    comunicador.start();
                }else{
                    datos.almacenarMensajeDHCP(mensajeDHCP);
                }
            


            }
            
         }catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static synchronized MensajeDHCP recibirNuevoMensajeDHCP() {
        byte[] bufferMensaje=new byte[1024];
        DatagramPacket dpMensaje=new DatagramPacket(bufferMensaje,bufferMensaje.length);
        try {
            socketPuerto67.receive(dpMensaje);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("Ok");
            bufferMensaje=dpMensaje.getData();
            MensajeDHCP mensajeDHCP=new MensajeDHCP(bufferMensaje);
            return mensajeDHCP;
                        
        }
    }
