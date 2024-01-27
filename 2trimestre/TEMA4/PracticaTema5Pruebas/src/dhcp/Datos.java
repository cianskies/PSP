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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class Datos {
    public HashMap<String, Integer> codigos;
    private DatagramSocket socketPuerto67;
    //el rango de ip debe ser 172.16.1.120-172.16.1.255
    private int IDHost=120;
    HashMap<Integer,byte[]> direccionesEnUso;
    
    public Datos(DatagramSocket socketPuerto67){
        this.socketPuerto67=socketPuerto67;
        codigos=iniciarDiccionario();
        direccionesEnUso=new HashMap<Integer,byte[]>();
    }
    public void enviarMensaje(MensajeDHCP mensaje){
            try {
                DatagramPacket dp=new DatagramPacket(mensaje.getDatos(),0,mensaje.getDatos().length, InetAddress.getByName("255.255.255.255"),68);
                
                try {
                    socketPuerto67.send(dp);
                } catch (SocketException ex) {
                    Logger.getLogger(ComunicadorDHCP.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ComunicadorDHCP.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } catch (UnknownHostException ex) {
                Logger.getLogger(ComunicadorDHCP.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    public MensajeDHCP recibirNuevoMensajeDHCP(){
      byte[] bufferMensaje=new byte[1024];
            DatagramPacket dpMensaje=new DatagramPacket(bufferMensaje,bufferMensaje.length);
            try {
                socketPuerto67.receive(dpMensaje);
            } catch (IOException ex) {
                Logger.getLogger(ComunicadorDHCP.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Ok");
            bufferMensaje=dpMensaje.getData();
            
        MensajeDHCP mensajeDHCP=new MensajeDHCP(bufferMensaje);      
        return mensajeDHCP;
    }
    public MensajeDHCP generarDHCPOffer(MensajeDHCP mensajeCliente){
        byte[] cabeceraRespuesta=generarCabeceraNoRenovacion(mensajeCliente);
        byte[] opcionesRespuesta=generarOpcionesOffer();
        MensajeDHCP mensajeDHCPOffer=montarMensaje(cabeceraRespuesta,opcionesRespuesta);
        return mensajeDHCPOffer;
    }    
    public MensajeDHCP generarDHCPRequest(MensajeDHCP mensajeCliente){
        byte[] cabeceraRequest=generarCabeceraNoRenovacion(mensajeCliente);
        boolean ack=comprobarIPSolicitda(mensajeCliente);
        byte[] opcionesRequest=generarOpcionesRequest(ack,mensajeCliente);
        MensajeDHCP mensajeDHCPRequest=montarMensaje(cabeceraRequest,opcionesRequest);
        return mensajeDHCPRequest;
    
    }
    private MensajeDHCP montarMensaje(byte[] cabecera,byte[] opciones){
        ByteBuffer bbMensaje= ByteBuffer.allocate(576);
        bbMensaje.put(cabecera);
        bbMensaje.put(opciones);
        byte[] datosMensaje=bbMensaje.array();
        MensajeDHCP mensaje=new MensajeDHCP(datosMensaje);
        return mensaje;
    }
    private boolean comprobarIPSolicitda(MensajeDHCP mensaje){
        byte[] requestedIP=mensaje.extraerOpcion(codigos.get("Requested IP"));
        int host=requestedIP[3] & 0xFF;
        boolean ipCorrecta=false;

        mensaje.imprimirArrayDeBytes(requestedIP);
        for(Map.Entry<Integer,byte[]> item:direccionesEnUso.entrySet()){
            if(item.getKey()!=host&&item.getValue()!=mensaje.getMAC()){
                ipCorrecta=true;
               
            }
        }
        if(!ipCorrecta){
            
            ++IDHost;
            System.out.println("++idHost "+IDHost);
        }else{
            System.
        }
        return ipCorrecta;
    }    
     private byte[] generarCabeceraNoRenovacion(MensajeDHCP mensajeCliente){
        ByteBuffer bbCabecera=ByteBuffer.allocate(236);
            bbCabecera.put((byte)2);
            bbCabecera.put((byte)1);
            bbCabecera.put((byte)6);
            bbCabecera.put((byte)0);
            bbCabecera.put(mensajeCliente.getXID());
            bbCabecera.put((byte)0);
            bbCabecera.put((byte)0);
            bbCabecera.put((byte)0);
            bbCabecera.put((byte)32768);
            
            //ip del cliente
            bbCabecera.put((byte)0);
            bbCabecera.put((byte)0);
            bbCabecera.put((byte)0);
            bbCabecera.put((byte)0);
            //direccion que ofrece al cliente 172.16.1.120
            bbCabecera.put((byte)172);
            bbCabecera.put((byte)16);
            bbCabecera.put((byte)1);
            bbCabecera.put((byte)IDHost);

            
            //siguiente servidor dhcp (0)
            for(int i=0;i<8;++i){
                bbCabecera.put((byte)0);
            }
            bbCabecera.put(mensajeCliente.getMAC());
            if(bbCabecera.hasRemaining()){
                bbCabecera.put((byte)0);
            }
            //ya estaria la cabecera
            byte[] cabeceraRespuesta=bbCabecera.array();
            return cabeceraRespuesta;
    }
    private byte[] generarOpcionesOffer(){
        ByteBuffer bbOpciones=ByteBuffer.allocate(236);
        //magicCookie
            bbOpciones.put((byte)99);
            bbOpciones.put((byte)130);
            bbOpciones.put((byte)83);
            bbOpciones.put((byte)99);
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
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)60);
            //renovacion
            bbOpciones.put((byte)58);
            bbOpciones.put((byte)4);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)30);
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
            return opcionesRespuesta;
    }
    private byte[] generarOpcionesRequest(boolean ack, MensajeDHCP mensaje){
        ByteBuffer bbOpciones=ByteBuffer.allocate(236);
            bbOpciones.put((byte)99);
            bbOpciones.put((byte)130);
            bbOpciones.put((byte)83);
            bbOpciones.put((byte)99);
            //tipo de mensaje offer
            bbOpciones.put((byte)53);
            bbOpciones.put((byte)1);
            if(ack){
            //ack
                bbOpciones.put((byte)5);
                direccionesEnUso.put(IDHost,mensaje.getMAC());
                System.out.println("Se añade "+IDHost+" "+mensaje.getMAC());
            }
            else{
                //nak
                System.out.println("nak");
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
                        //tiempo de cesion seg
            bbOpciones.put((byte)51);
            bbOpciones.put((byte)4);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)60);
            //renovacion
            bbOpciones.put((byte)58);
            bbOpciones.put((byte)4);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)0);
            bbOpciones.put((byte)30);
            //end
            bbOpciones.put((byte)255);
            if(bbOpciones.hasRemaining()){
                bbOpciones.put((byte)0);
            }
            byte[] opcionesRespuesta=bbOpciones.array();
            return opcionesRespuesta;
    }
    public byte[] generarDHCPRenovacion(MensajeDHCP mensaje){
        return null;
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
    
}
