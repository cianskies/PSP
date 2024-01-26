/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhcp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class ComunicadorDHCP implements Runnable{
    
    
        
    
    
    private MensajeDHCP mensajeCliente;
    private byte[] tipoDeMensaje;
    private byte[] requestedIP;
    private Datos datos;
    
    public ComunicadorDHCP (MensajeDHCP mensajeCliente,Datos datos) {
        this.mensajeCliente=mensajeCliente;
        
        this.datos=datos;
        
    }
    @Override
    public void run(){
        while(true){
        interpretarMensaje(this.mensajeCliente);
        this.mensajeCliente=datos.recibirNuevoMensajeDHCP();
        }
    }
    public void interpretarMensaje(MensajeDHCP mensaje){
        //Lo primero para interpretar un mensaje es saber 
        //que tipo de mensaje se trata (opcion 53)
        this.tipoDeMensaje=mensaje.extraerOpcion(datos.codigos.get("Tipo de Mensaje"));
        MensajeDHCP mensajeRespuesta=null;
        switch(this.tipoDeMensaje[0] & 0xFF){
            case 1:
                //Discover
                mensajeRespuesta=datos.generarDHCPOffer(mensajeCliente);
                break;
            case 3: 
                //request
                mensajeRespuesta=datos.generarDHCPRequest(mensajeCliente);
            default:
                //Request
                System.out.println("bye");
                break;
        }       
        if(mensajeRespuesta!=null){
            datos.enviarMensaje(mensajeRespuesta);
        }else{
            System.out.println("fok");
        }
        
    }  


   
}
