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
    private byte[] MAC;
    private Datos datos;
    private boolean acabar;
    private int id;
    public ComunicadorDHCP (MensajeDHCP mensajeCliente,Datos datos,int id) {
        this.mensajeCliente=mensajeCliente;
        this.tipoDeMensaje=new byte[3];
        this.datos=datos;
        this.acabar=false;
        this.id=id;
    }
    @Override
    public void run(){
        MAC=mensajeCliente.getMAC();
        System.out.println("un nuevo hilo atiende un mensaje "+new String(mensajeCliente.getMAC()));
        datos.anhadirMAC(MAC);
       
        interpretarMensaje(this.mensajeCliente);
        if((tipoDeMensaje[0] & 0xFF)==1){
            System.out.println("Me duermo "+id);
            datos.zzz();
        }
        System.out.println(id+" Se despierta");
        
        if(!acabar){  
            
            MensajeDHCP mensajeRecogido=null;
            while(mensajeCliente==null||(tipoDeMensaje[0] & 0xFF)==1){
                 System.out.println(id+ " Se pide un msj  "+new String(MAC));
                  mensajeRecogido=datos.recogerMensaje(MAC);
         
                
                 mensajeCliente=mensajeRecogido;
                 if(mensajeCliente!=null){
                    System.out.println(id+ " recoge un mensaje con la mac "+new String(mensajeCliente.getMAC()));
                    interpretarMensaje(this.mensajeCliente);
                    
                 }

                 else{
                     System.out.println(id+" No ha encontrado mensaje");
                     datos.zzz();
                     
                 }
            }
        }
                 
        
        datos.eliminarMAC(MAC);
        System.out.println("termina un hilo "+id);
         

        
       
    }
    public void interpretarMensaje(MensajeDHCP mensaje){
        //Lo primero para interpretar un mensaje es saber 
        //que tipo de mensaje se trata (opcion 53)
        
        this.tipoDeMensaje=mensaje.extraerOpcion(datos.codigos.get("Tipo de Mensaje"));
        
        if(mensaje.getIPCabecera()[0]!=0){
            System.out.println(id+" esta solicitando renovacion "+new String(mensajeCliente.getMAC()));
            this.tipoDeMensaje[0]=(byte)04;
        }
        MensajeDHCP mensajeRespuesta=null;
        switch(this.tipoDeMensaje[0] & 0xFF){
            case 1:
                //Discover
                System.out.println(id+"Discover "+new String(mensajeCliente.getMAC()));
                mensajeRespuesta=datos.generarDHCPOffer(mensajeCliente);
                break;
            case 3: 
                //request
                
                System.out.println(id+"Request "+new String(mensajeCliente.getMAC()));
               
                 acabar=true;
                mensajeRespuesta=datos.generarDHCPRequest(mensajeCliente);
                
               
                break;
            case 4:
                System.out.println("Renovacion "+new String(mensajeCliente.getMAC()));
                
                acabar=true;
                mensajeRespuesta=datos.generarDHCPRenovacion(mensajeCliente);
                
                break;
        }       
            datos.enviarMensaje(mensajeRespuesta);
        
    }  


   
}