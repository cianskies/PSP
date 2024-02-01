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
    private boolean acabar;
    public ComunicadorDHCP (MensajeDHCP mensajeCliente,Datos datos) {
        this.mensajeCliente=mensajeCliente;
        this.tipoDeMensaje=new byte[3];
        this.datos=datos;
        this.acabar=false;
    }
    @Override
    public void run(){
        System.out.println("un nuevo hilo atiende un mensaje");
        datos.anhadirXID(mensajeCliente.getXID());
        interpretarMensaje(this.mensajeCliente);
        if((tipoDeMensaje[0] & 0xFF)==1){
            datos.zzz();
        }
        while(!acabar){
            interpretarMensaje(this.mensajeCliente);
            
                MensajeDHCP mensajeRecogido=null;
                while(mensajeRecogido==null&&!acabar){
                    System.out.println("Se pide un msj");
                    mensajeRecogido=datos.recogerMensaje(mensajeCliente.getXID());

                }
                mensajeCliente=mensajeRecogido;
                    if((tipoDeMensaje[0] & 0xFF)==1){
                        datos.zzz();
                    }
           }
        
        System.out.println("termina un hilo");
        

        
       
    }
    public void interpretarMensaje(MensajeDHCP mensaje){
        //Lo primero para interpretar un mensaje es saber 
        //que tipo de mensaje se trata (opcion 53)
        
        this.tipoDeMensaje=mensaje.extraerOpcion(datos.codigos.get("Tipo de Mensaje"));
        
        if(mensaje.getIPCabecera()[0]!=0){
            System.out.println("Se esta solicitando renovacion");
            this.tipoDeMensaje[0]=(byte)04;
        }
        MensajeDHCP mensajeRespuesta=null;
        switch(this.tipoDeMensaje[0] & 0xFF){
            case 1:
                //Discover
                System.out.println("Discover");
                mensajeRespuesta=datos.generarDHCPOffer(mensajeCliente);
                break;
            case 3: 
                //request
                
                System.out.println("Request");
                datos.eliminarXID(mensajeCliente.getXID());
                 acabar=true;
                mensajeRespuesta=datos.generarDHCPRequest(mensajeCliente);
                
               
                break;
            case 4:
                System.out.println("Renovacion");
                datos.eliminarXID(mensajeCliente.getXID());
                acabar=true;
                mensajeRespuesta=datos.generarDHCPRenovacion(mensajeCliente);
                
                break;
        }       
            datos.enviarMensaje(mensajeRespuesta);
        
    }  


   
}
