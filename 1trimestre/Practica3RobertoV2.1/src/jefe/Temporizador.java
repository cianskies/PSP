/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jefe;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class Temporizador implements Runnable{
    private ServerSocket s;
    long tiempo;
    
    public Temporizador(ServerSocket s,long tiempo){
        this.s=s;
        this.tiempo=tiempo;
    }
    
    @Override
    public void run(){
        try {
            //cierra el socket en el tiempo indicado
            Thread.sleep(tiempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Temporizador.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(Temporizador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
