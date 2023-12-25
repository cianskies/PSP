/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */public class Tarea implements Serializable{
   private int duracion;
    private int identificador;
    
    public Tarea(int duracion, int identificador){
        this.duracion=duracion;
        this.identificador=identificador;
    }
    
    public int getDuracion(){
        return this.duracion;
    }
    
    public int getIdentificador(){
        return this.identificador;
    }
    
    
    @Override
    public String toString(){
        return "[Tarea "+identificador+"] Duración "+duracion;
    }
    
}