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
    
    private HashMap<String, Integer> codigos;
        
    
    
    private MensajeDHCP mensajeCliente;
    private byte[] tipoDeMensaje;
    private byte[] requestedIP;
    private Datos datos;
    
    public ComunicadorDHCP (MensajeDHCP mensajeCliente,Datos datos) {
        this.mensajeCliente=mensajeCliente;
        codigos=iniciarDiccionario();
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
        this.tipoDeMensaje=mensaje.extraerOpcion(codigos.get("Tipo de Mensaje"));
        MensajeDHCP mensajeRespuesta;
        switch(this.tipoDeMensaje[0] & 0xFF){
            case 1:
                //Discover
                mensajeRespuesta=datos.generarDHCPOffer(mensajeCliente);
                break;
            case 3: 
                //request
                boolean ipEstaLibre=comprobarIPSolicitda();
                GenerarDHCPRequest(ipEstaLibre);
            default:
                //Request
                System.out.println("bye");
                break;
        }       
        
        //Guardamos el id de transaccion (xid) para poder usarlo mas adelante
        
    }  
    private HashMap<String,Integer> iniciarDiccionario(){
        codigos=new HashMap<>();
        codigos.put("Tipo de Mensaje",53);
        codigos.put("Mascara",1);
        codigos.put("Servidor DNS",6);
        codigos.put("Router",3);
        codigos.put("Requested IP",50);
        codigos.put("Tiempo de cesion",51);
        codigos.put("Tiempo de renovacion",58);
        codigos.put("Identificador del servidor",54);
        return codigos;
    }
    private boolean comprobarIPSolicitda(){
        byte[] requestedIP=mensajeCliente.extraerOpcion(codigos.get("Requested IP"));
        boolean ipCorrecta=false;

        mensajeCliente.imprimirArrayDeBytes(requestedIP);
        if(requestedIP==requestedIP){
            ipCorrecta=true;
            
            
        }
        return ipCorrecta;
    }
    
    private void GenerarDHCPOffer(){
            
            //ahora deberiamos imprimir las opciones
            ByteBuffer bbOpciones=ByteBuffer.allocate(236);

            //tipo de mensaje offer
            bbOpciones.put((byte)53);
            bbOpciones.put((byte)1);
            bbOpciones.put((byte)2);
            //mascara 255.255.255.0
            bbOpciones.put((byte)1);
            bbOpciones.put((byte)4);
            bbOpciones.put((byte)255);
            bbOpciones.put((byte)255);
            bbOpciones.put((byte)255);
            bbOpciones.put((byte)0);
            //ip dns
            bbOpciones.put((byte)6);
            bbOpciones.put((byte)4);
            bbOpciones.put((byte)8);
            bbOpciones.put((byte)8);
            bbOpciones.put((byte)8);
            bbOpciones.put((byte)8);
            //ip Router
            bbOpciones.put((byte)3);
            bbOpciones.put((byte)4);
            bbOpciones.put((byte)172);
            bbOpciones.put((byte)16);
            bbOpciones.put((byte)1);
            bbOpciones.put((byte)1);
            //tiempo de cesion seg
            bbOpciones.put((byte)51);
            bbOpciones.put((byte)4);
            bbOpciones.put((byte)60);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)0);
            //renovacion
            bbOpciones.put((byte)58);
            bbOpciones.put((byte)4);
            bbOpciones.put((byte)30);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)0);
            //ip servidor
            bbOpciones.put((byte)54);
            bbOpciones.put((byte)4);
            bbOpciones.put((byte)192);
            bbOpciones.put((byte)168);
            bbOpciones.put((byte)5);
            bbOpciones.put((byte)50);
            bbOpciones.put((byte)255);
            
            
            bbOpciones.put((byte)255);
            if(bbOpciones.hasRemaining()){
                bbOpciones.put((byte)0);
            }
            byte[] opcionesRespuesta=bbOpciones.array();
            ByteBuffer bbMensajeRespuesta= ByteBuffer.allocate(576);
            bbMensajeRespuesta.put(cabeceraRespuesta);
            bbMensajeRespuesta.put(opcionesRespuesta);
            byte[] mensajeRespuesta=bbMensajeRespuesta.array();
            mensajeCliente.imprimirArrayDeBytes(mensajeRespuesta);
            
            datos.enviarMensaje(mensajeRespuesta);
    }
    public void GenerarDHCPRequest(boolean ack){
        byte[] cabeceraRespuesta=generarCabeceraNoRenovacion();
        ByteBuffer bbOpciones=ByteBuffer.allocate(236);
            bbOpciones.put((byte)99);
            bbOpciones.put((byte)130);
            bbOpciones.put((byte)83);
            bbOpciones.put((byte)99);
            //tipo de mensaje offer
            bbOpciones.put((byte)53);
            bbOpciones.put((byte)1);
            if(ack){
                bbOpciones.put((byte)5);
            
            }else{
                //nak
                bbOpciones.put((byte)6);
            }
            //mascara 255.255.255.0
            bbOpciones.put((byte)1);
            bbOpciones.put((byte)4);
            bbOpciones.put((byte)255);
            bbOpciones.put((byte)255);
            bbOpciones.put((byte)255);
            bbOpciones.put((byte)0);
            //ip dns
            bbOpciones.put((byte)6);
            bbOpciones.put((byte)4);
            bbOpciones.put((byte)8);
            bbOpciones.put((byte)8);
            bbOpciones.put((byte)8);
            bbOpciones.put((byte)8);
            //ip Router
            bbOpciones.put((byte)3);
            bbOpciones.put((byte)4);
            bbOpciones.put((byte)172);
            bbOpciones.put((byte)16);
            bbOpciones.put((byte)1);
            bbOpciones.put((byte)1);
                
            bbOpciones.put((byte)255);
            if(bbOpciones.hasRemaining()){
                bbOpciones.put((byte)0);
            }
            byte[] opcionesRespuesta=bbOpciones.array();
            ByteBuffer bbMensajeRespuesta= ByteBuffer.allocate(576);
            bbMensajeRespuesta.put(cabeceraRespuesta);
            bbMensajeRespuesta.put(opcionesRespuesta);
            byte[] mensajeRespuesta=bbMensajeRespuesta.array();
            mensajeCliente.imprimirArrayDeBytes(mensajeRespuesta);
            
            datos.enviarMensaje(mensajeRespuesta);
        
    }
    
   
}
