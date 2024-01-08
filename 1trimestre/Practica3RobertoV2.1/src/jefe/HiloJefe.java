/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jefe;

import tarea.Tarea;
import datos.Datos;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class HiloJefe implements Runnable{
    
    
    //constructor
    private int id;
    private Datos datos;
    private int puertoJefe;
    private Socket socketTrabajador;
    private String idHash;
    
    boolean trabajadorCompleto;
    
    ByteBuffer byteBufferDatosTarea;
    private Tarea tarea;
    
    //transferencia de datos
    ObjectOutputStream oos;
    DataInputStream dis;
    private DataOutputStream dos;
    
    public HiloJefe(int id, Datos datos,Socket socket,int puerto){
        this.id=id;
        this.datos=datos;
        this.socketTrabajador=socket;
        this.puertoJefe=puerto;
        try {
            this.oos=new ObjectOutputStream(this.socketTrabajador.getOutputStream());
            this.dis=new DataInputStream(this.socketTrabajador.getInputStream());
            this.dos=new DataOutputStream(socketTrabajador.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(HiloJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.trabajadorCompleto=false;
    }
    
    @Override
    public void run(){
        try {
            //este es el nombre que recibirá cada uno de los trabajadores
            this.idHash=datos.generarIdHash(String.valueOf(this.id),"SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HiloJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("iniciado trabajador "+ idHash);
        while(!trabajadorCompleto){
            //recibe tarea de datos, espera si no es su turno o no hay tareas disponibles
            tarea=datos.getTarea(id);
            
            //envia la tarea al trabajador
            try {
                oos.writeObject(tarea);
            } catch (IOException ex) {
                Logger.getLogger(HiloJefe.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //Recibe la respuesta del trabajador, tanto si acepta la tarea o no
                
                String respuesta=new String(dis.readUTF());
                gestionarRespuesta(respuesta);
                
            }catch (IOException ex) {
                Logger.getLogger(HiloJefe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            String tareasAceptadas=dis.readUTF();
            dos.writeUTF("OK");
            
            datos.registrarFinDeConexion(id,tareasAceptadas,idHash);
        } catch (IOException ex) {
            Logger.getLogger(HiloJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    private void gestionarRespuesta(String respuesta){
        //indica que el trabajador ha terminado
        if(respuesta.equals("NO")){
           trabajadorCompleto=true;
        }else if(respuesta.equals("NU")){
        //rechaza la tarea
            datos.rechazarTarea(id);     
        }else{
            //marca la tarea como nula porque el trabajasdor la ha aceptaedo
            datos.consumirTarea(); 
        }
    }
}
