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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class Datos {
    
    private DatagramSocket socketPuerto67;
    
    public Datos(DatagramSocket socketPuerto67){
        this.socketPuerto67=socketPuerto67;
    }
    public void enviarMensaje(byte[] mensaje){
            try {
                DatagramPacket dp=new DatagramPacket(mensaje,0,mensaje.length, InetAddress.getByName("255.255.255.255"),68);
                
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
            /*Obtener la cabecera y las opciones de la oferta de ip
            le debe ofrecer la ip  172.16.1.120, con máscara de red 255.255.255.0, router 172.16.1.1 y servidor DNS
            8.8.8.8. El tiempo de cesión será de 60 segundos, y el tiempo de renovación será la mitad. El
            servidor debe tener en su configuración la dirección IP de la interfaz a la que debe
            conectarse el ServerSocket.*/

            byte[] cabeceraRespuesta=generarCabeceraNoRenovacion(mensajeCliente);
            return mensajeDHCPOffer;
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
            bbCabecera.put((byte)120);
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
            bbOpciones.put((byte)99);
            bbOpciones.put((byte)130);
            bbOpciones.put((byte)83);
            bbOpciones.put((byte)99);
    }
}
