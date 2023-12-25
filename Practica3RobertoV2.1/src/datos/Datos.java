/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import creadorTareas.todosLosTrabajadoresTerminadosException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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


public class Datos {
    private Tarea tarea;
    private Random randomTurno;
    private int turnoDeHilo;
    private int numeroConsumidores;
    
    private ArrayList<Integer> hilosTerminados;
    private String peticion;
    
    private StringBuilder sbInforme;
    
    int contadorRechazadas;
    
    private Process proceso;
    private ArrayList<Integer> hilosRechazados;
    
    public Datos(int numeroConsumidores,Process proceso){
        this.proceso=proceso;
        tarea=null;
        peticion="TAREA";
        this.numeroConsumidores=numeroConsumidores;
        randomTurno=new Random();
        turnoDeHilo=randomTurno.nextInt(this.numeroConsumidores);
        hilosTerminados=new ArrayList<>();
        sbInforme=new StringBuilder();
    }
         
    public synchronized void anhadirTarea(Tarea tarea) throws todosLosTrabajadoresTerminadosException{
        //El productor añade una tarea para que otros trabajadores la recojan
        //se queda esperando si ya hay alguna tarea
        while(this.tarea!=null){
            try {
                
            if(peticion.equals("PARAR")){
                //sale de este bucle cuando la peticion sea PARAR
            throw new todosLosTrabajadoresTerminadosException();
            }   
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.tarea=tarea;
        contadorRechazadas=0;
        hilosRechazados=new ArrayList<Integer>();
        notifyAll();
        
    }
    public synchronized Tarea getTarea(int id){
        //Espera si no es su turno o no hay tarea
        while(turnoDeHilo!=id||tarea==null){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

         return tarea;
    }
    public synchronized void consumirTarea(){
        tarea=null;
        pasarTurno();
    }
    public synchronized void pasarTurno(){
        //cambiamos el turno del trabajador a otro al azar teniendo en cuenta que el hilo seleccionado
        //al azar no haya ya terminado
        do{turnoDeHilo=randomTurno.nextInt(this.numeroConsumidores);
        
        }while(hilosTerminados.contains(turnoDeHilo));
        
        notifyAll();
    }
    public synchronized void registrarFinDeConexion(int id, String tareasAceptadas,String idHash) {
        hilosTerminados.add(id);
        sbInforme.append("Tareas aceptadas por "+idHash+": "+tareasAceptadas+"\n");
        sbInforme.append(System.lineSeparator());
        if(hilosTerminados.size()==numeroConsumidores){
            cerrarProcesos();
        }else{
            pasarTurno();
        }
    }
    public String getPeticion(){
        return this.peticion;
    }
    public synchronized void rechazarTarea(int id){
        //un hilo rechaza la tarea pero no la deshecha
        if(!hilosRechazados.contains(id)){
            ++contadorRechazadas;
            hilosRechazados.add(id);  
        }
        //Se deshecha la tarea
        if(contadorRechazadas+hilosTerminados.size()==numeroConsumidores){
            tarea=null;
            
        } 
        pasarTurno();
    }
    private void imprimirInformeResultados(){
            File archivo=new File("ResultadosTareas.txt");
            int i=1;
            while(archivo.exists()){
                archivo=new File("ResultadosTareas"+i+".txt");
                ++i;
            }
            try {
                archivo.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                FileWriter fr=new FileWriter(archivo);
                fr.write(sbInforme.toString());
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    public synchronized String generarIdHash(String id, String algorithm) throws NoSuchAlgorithmException {
        
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] hashedBytes = md.digest((id+randomTurno.nextInt(999)).getBytes());
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte hashedByte : hashedBytes) {
            hexStringBuilder.append(String.format("%02x", hashedByte));
        }

        return hexStringBuilder.toString();
    }
    private void cerrarProcesos(){
        //cambia el estado de la tarea para que salga del bucle,
        //también le manda esa peticion al creador de tareas y este finalice
       this.peticion="PARAR";
       notifyAll();
       //imprimir el informe
       imprimirInformeResultados();
       //destruir proceso creador de tareas
       this.proceso.destroy();        
    }
}
