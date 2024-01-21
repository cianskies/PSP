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
            //Vamos a pasar el mensaje a Decimal para poder leerlo en condiciones
            boolean mCookie=false;
            StringBuilder stringBuilder=new StringBuilder();
            int contador=0;
            int contador2=0;
            for(byte b:bufferMensaje){
                if(contador==4&&stringBuilder.toString().equals("991308399")){
                    mCookie=true;
                }else if(contador==4){
                    contador=0;
                    stringBuilder=new StringBuilder();
                }
                if(!mCookie){
                 int decimal=b & 0xFF;
                    stringBuilder.append(decimal);
                    ++contador;
                }else{
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
    
}
