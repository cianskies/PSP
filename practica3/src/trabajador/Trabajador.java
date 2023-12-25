/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajador;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import tarea.Tarea;

/**
 *
 * @author Administrador
 */
public class Trabajador implements Runnable{
    
    //Atributos del trabajador
    private int id;//implementar un generador de ID al azar
    private String idHash;
    private final int HORAS_MAXIMAS=40;
    private int horasOcupadas;

    
    private boolean horarioCompleto;
    
    private ArrayList<Integer> tareasAceptadas;
    
    
    
    //Comunicarse con el hiloJefe
    private ObjectInputStream ois;
    private DataOutputStream dos;
    private DataInputStream dis;
    
    
    private final String DIR="192.168.5.51";
    private final int PUERTO=1112;
    
    private Random r;
    
    
    public Trabajador(int id){
        this.id=id;
        this.horasOcupadas=0;
        horarioCompleto=false;
        this.tareasAceptadas=new ArrayList<Integer>();
        this.r=new Random();

        
        
    }
    
    @Override
    public void run(){
        iniciarComunicacionConJefe();
        while(!horarioCompleto){
            
        try {
            //recibe una tarea
            Tarea tareaAsignada=(Tarea)ois.readObject();
            String respuesta=gestionarTarea(tareaAsignada);
            //Envia respuesta al jefe sobre si acepta o no la tarea
            dos.writeUTF(respuesta);
        } catch (IOException ex) {
            Logger.getLogger(Trabajador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trabajador.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
        String informe=generarInformeTrabajador();
        enviarInforme(informe);
        System.out.println("he terminado: "+id);
        
    }
    private String generarInformeTrabajador(){
        String informe="";
        for(int i=0;i<tareasAceptadas.size();++i){
            informe+=tareasAceptadas.get(i);
            if(i+1<tareasAceptadas.size()){
              informe+=", ";  
            }
            
        }
        System.out.println(informe);
        return informe;
    }
    private void iniciarComunicacionConJefe(){
                try {
            Socket socket=new Socket(DIR,PUERTO);
            ois=new ObjectInputStream(socket.getInputStream());
            dos=new DataOutputStream(socket.getOutputStream());
            this.dis=new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("no se ha Iniciado el trabajador "+id);
            Logger.getLogger(Trabajador.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    private String gestionarTarea(Tarea tarea){            
        String respuestaAJefe;    
        if(tarea.getDuracion()+horasOcupadas>40){
                //rechaza la tarea porque no le cabe en cuestion pero no esta completo
                respuestaAJefe="NU";
                
                if(horasOcupadas==HORAS_MAXIMAS){
                horarioCompleto=true;
                //Indica al jefe que esta completo
                respuestaAJefe="NO";
                
                }
            }else{
                respuestaAJefe="SI";
                //añade las horas de la tarea y el id de la tarea a su lista de tareas
                horasOcupadas+=tarea.getDuracion();
                tareasAceptadas.add(tarea.getIdentificador());
           
            }
        return respuestaAJefe;
    }
    private void enviarInforme(String informe){
            try {
            dos.writeUTF(informe);
            dis.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Trabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}