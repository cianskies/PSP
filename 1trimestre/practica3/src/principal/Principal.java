/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.util.logging.Level;
import java.util.logging.Logger;
import trabajador.Trabajador;

/**
 *
 * @author Administrador
 */
public class Principal {
    public static void main(String[] args){
        
        //Parametros que utilizo en mi configuracion, cambiar el puerto en caso de que sea necesario
        int numeroTrabajadores=3;
        int puerto=3000;
        long tiempoEspera=1000;
        
        
        Thread[] trabajadores=new Thread[numeroTrabajadores];
        //iniciar trabajadores segun el numero especificado e iniciarlos
        for(int i=0;i<numeroTrabajadores;++i){
            trabajadores[i]=new Thread(new Trabajador(i));
            trabajadores[i].start();
            try {
                //una espera para que se inicie el siguiente
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}