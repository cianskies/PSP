/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creadorTareas;

import datos.Datos;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import tarea.Tarea;
import tarea.Tarea;

/**
 *
 * @author Administrador
 */
public class Productor implements Runnable{
    
    private Tarea tarea;
    private Datos datos;
    private boolean seguir;
    
    private DatagramSocket dSocket;
    private DatagramPacket dPacketPeticion;
    private DatagramPacket dPacketTarea;
    private ByteBuffer bbDatosTarea;
    private byte[] bufferPeticion;
    private String peticion;
    
    private final String DIR_CREADOR_TAREAS="192.168.5.51";
    private final int PUERTO_CREADOR_TAREAS=1111;
    
    
    public Productor(Datos datos){
        this.datos=datos;
        seguir=true;
        try {
            dSocket=new DatagramSocket(1113);
        } catch (SocketException ex) {
        }
    }
    
    @Override
    public void run (){
        while(seguir){
          //produce tarea a no ser que le especifiquen lo contrario

          peticion=datos.getPeticion();

          if(peticion.equals("PARAR")){
              seguir=false;
          }else{
          bufferPeticion=peticion.getBytes();
            try {
                dPacketPeticion=new DatagramPacket(bufferPeticion,bufferPeticion.length,InetAddress.getByName(DIR_CREADOR_TAREAS),PUERTO_CREADOR_TAREAS);
            } catch (UnknownHostException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                dSocket.send(dPacketPeticion);
                
            } catch (IOException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //recibe datos de tarea y serializa
                byte[] bufferTarea=new byte[1024];
                dPacketTarea=new DatagramPacket(bufferTarea,bufferTarea.length);
                
                
                dSocket.receive(dPacketTarea);
                bbDatosTarea=ByteBuffer.wrap(dPacketTarea.getData());
                tarea=new Tarea(bbDatosTarea.getInt(),bbDatosTarea.getInt());
                
              try {
                  //la añade a datos cuando esta es nula

                  datos.anhadirTarea(tarea);
              } catch (todosLosTrabajadoresTerminadosException ex) {
              }

                
            } catch (IOException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
       
    }
    }
}
