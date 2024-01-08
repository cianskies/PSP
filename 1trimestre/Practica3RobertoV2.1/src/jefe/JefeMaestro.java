/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jefe;

import creadorTareas.Productor;
import datos.Datos;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class JefeMaestro {

    /**
     * @param args the command line arguments
     */
     private ArrayList<Thread> hilosJefe;
    private ArrayList<Socket> sockets;
    private int numeroHilos;
    
    private Process proceso;
    
    //Datos del MasterChief
    private Datos datos;
    private ServerSocket serverSocket;
    private final long TIEMPO_DE_ESPERA=10000;


    public JefeMaestro(){
        this.proceso=iniciarProcesoCreadorTareas();
        try {
            this.serverSocket=new ServerSocket(1112);
        } catch (IOException ex) {
            
        }
        hilosJefe=new ArrayList<>();
        sockets=new ArrayList<>();
        
        
        this.numeroHilos=aceptarConexiones();
        this.datos=new Datos(this.numeroHilos,this.proceso);
        
        Thread productor=new Thread(new Productor(datos));
        
        productor.start();
        iniciarHilosJefe();
        
        
        
    }
    public int aceptarConexiones(){
        int contador=0;
        try {
            //inicia el serversocket para que accedan los trabajadores
            //este se lo manda al temporizador para que lo cierre
            Thread temporizador=new Thread(new Temporizador(serverSocket,TIEMPO_DE_ESPERA));
            temporizador.start();
            while(true){
            Socket socket=serverSocket.accept();
            sockets.add(socket);
            ++contador;
            socket=null;
            }
        } catch (IOException ex) {
        }   
        return contador;
    }
    private void iniciarHilosJefe(){
        for(Socket s:sockets){
            
            hilosJefe.add(new Thread(new HiloJefe(hilosJefe.size(),datos,s,2000+hilosJefe.size())));
        }
        //Iniciamos a nuestros trabajadores, las comunicaciones a partir de ahora serán entre ellos
        for(Thread h:hilosJefe){
            //iniciamos todos los hilos con los sockets que hemos recibido
            h.start();
        }
    }
    private Process iniciarProcesoCreadorTareas(){
        Process proceso=null;
        ProcessBuilder pb=new ProcessBuilder("java","-cp","build/classes","creadorTareas.CreadorTareas");
         try {
            proceso =pb.start();
         } catch (IOException ex) {
             Logger.getLogger(JefeMaestro.class.getName()).log(Level.SEVERE, null, ex);
         }
          return proceso;
        }
       
}
