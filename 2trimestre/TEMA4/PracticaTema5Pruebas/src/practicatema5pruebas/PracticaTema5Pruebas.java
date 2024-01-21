/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicatema5pruebas;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class PracticaTema5Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //Lo primero seria detectar el mensaje que llega al puerto 68/67
            DatagramSocket dSocket=new DatagramSocket(67);
        
            //Almacenar el mensaje
            byte[] bufferMensaje=new byte[1024];
            DatagramPacket dpMensaje=new DatagramPacket(bufferMensaje,bufferMensaje.length);
            dSocket.receive(dpMensaje);
            bufferMensaje=dpMensaje.getData();
            boolean magicCookieDetectada=false;
            
            int[] bytesLeidos=new int[4];
            int contador=0;

            int codigo=0;
            ArrayList<Integer> codigos=new ArrayList<>();
            ArrayList<Integer> mensajes=new ArrayList<>();
            int longitudMensaje=0;
            for(byte b:bufferMensaje){
                if(!magicCookieDetectada){
                    int byteDecimal=b & 0xFF;
                    bytesLeidos[contador]=byteDecimal;
                    ++contador;
                    if(contador==4){
                        System.out.println("Se han leido 4 bytes: ");
                        for(int i=0;i<bytesLeidos.length;++i){
                            System.out.println(bytesLeidos[i]); 
                        }
                        if(bytesLeidos[0]==99
                                &&bytesLeidos[1]==130
                                    &&bytesLeidos[2]==83
                                        &&bytesLeidos[3]==99){
                            magicCookieDetectada=true;
                            
                        }
                        contador=0;
                    }
                }else{
                    ///
                    
                    int byteDecimal=b & 0xFF;
                    if(codigo==0){
                        codigo=byteDecimal;
                        System.out.println("Codigo:" +codigo);
                    }else if(longitudMensaje==0){
                        longitudMensaje=byteDecimal;
                        System.out.println("longitud: "+longitudMensaje);
                    }
                    else if(longitudMensaje>contador){
                       System.out.print(byteDecimal);
                        ++contador;
                    }else{
                        System.out.println();
                        System.out.println("escritos "+contador+"caracteres");
                        System.out.println("//////////");
                        longitudMensaje=0;
                        codigo=0;
                    }
                    
                    
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(PracticaTema5Pruebas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PracticaTema5Pruebas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*                if(contador==4){
                    System.out.println(new String(stringBuilder));
                    contador=0;
                    stringBuilder=new StringBuilder();
                }
                int decimal=b & 0xFF;
                stringBuilder.append(decimal);
                ++contador;*/
    /*                }else{
                   int codigo=b & 0xFF;
                   System.out.println("Codigo del mensaje: "+codigo);
                   int longitud= b & 0xFF;
                   System.out.println("Longitud del mensaje: "+longitud);
                   for(int i=0;i<longitud;++i){
                       int byteMensaje=b & 0xFF;
                       System.out.print(byteMensaje);
                   }
                   System.out.println();
                   
                }
*/
    
}
