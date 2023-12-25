/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creadorTareas;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class CreadorTareas {
    private static DatagramSocket dSocket;
    private static DatagramPacket dPacketPeticion;
    private static DatagramPacket dPacketTarea;
    private static boolean seguir;
    
    private static byte[] bufferPeticion;
    private static byte [] bufferTarea;
    private static ByteBuffer bb;
    
    private static String peticion;
    private static InetAddress dir;
    private static int puerto;
    
    private static int idTarea;
    private static Random randomDuracion;
    
    
    public static void main(String[] args){

        try {
            dSocket=new DatagramSocket(1111);
        } catch (SocketException ex) {
        }
        idTarea=0;
        randomDuracion=new Random();
        seguir=true;
        while(seguir){
            generarTareaPorPeticion();
        }
    }
    public static void generarTareaPorPeticion(){
        try {
            //Si le pide TAREA generara una tarea
            bufferPeticion=new byte[5];
            dPacketPeticion=new DatagramPacket(bufferPeticion,bufferPeticion.length);
            dSocket.receive(dPacketPeticion);
            peticion=new String(dPacketPeticion.getData());
            dir=dPacketPeticion.getAddress();
            puerto=dPacketPeticion.getPort();
            System.out.println("La peticion es "+peticion);

        } catch (IOException ex) {
            Logger.getLogger(CreadorTareas.class.getName()).log(Level.SEVERE, null, ex);
        }
        //parara si le piden parar
        if(peticion.equals("PARAR")){
            seguir=false;
         
        }else{
        //Genera tarea
        bb=ByteBuffer.allocate(8);
        
        bb.putInt(randomDuracion.nextInt(3)+1);
        bb.putInt(idTarea);
        //enviar a productor
        dPacketTarea=new DatagramPacket(bb.array(),bb.capacity(),dPacketPeticion.getAddress(),dPacketPeticion.getPort());
        
        try {
            dSocket.send(dPacketTarea);
        } catch (IOException ex) {
            Logger.getLogger(CreadorTareas.class.getName()).log(Level.SEVERE, null, ex);
        }
        ++idTarea;
        }
    }
}
