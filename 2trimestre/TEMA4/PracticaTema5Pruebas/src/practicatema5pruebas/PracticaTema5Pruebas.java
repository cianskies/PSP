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
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> 2ed0fdeb942a71e0cf4f192a6caa22223480a706
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class PracticaTema5Pruebas {

        static List<byte[]> opciones=new ArrayList<>();
        static byte[] tipoMensaje;
        static byte[] mascara;
        static byte[] dns;
        static byte[] router;
        static byte[] requestedIp;
        static byte[] tiempoCesion;
        static byte[] tiempoRenovacion;
        static byte[] identificadorServidor;
    public static void main(String[] args) {
        
        //Colecciones de datos

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
<<<<<<< HEAD
=======
            
            boolean codigo=false;
            boolean longitud= false;
            int longitudMensaje=-1;
            int longitudLeida=0;
            int codigoActual=0;
            byte[] mensaje=new byte[1024];
            
            
            
            for(byte b:bufferMensaje){
                if(contador==4&&stringBuilder.toString().equals("991308399")&&!mCookie){
                    mCookie=true;
                    System.out.println("Se ha detectado la Magic cookie: "+stringBuilder.toString());
                }else if(contador==4){
                    contador=0;
                    stringBuilder=new StringBuilder();
                }
                if(!mCookie){
                 int decimal=b & 0xFF;
                    stringBuilder.append(decimal);
                    ++contador;
                }else{
                   int byteLeido=b & 0xFF;
                   if(byteLeido==255){
                       System.out.println("End");
                   }
                   if(!codigo){
                        System.out.println("Codigo del mensaje: "+byteLeido);
                        codigo=true;
                        codigoActual=byteLeido;
                   }else if(!longitud){
                       System.out.println("longitud del mensaje: "+byteLeido);
                        longitudMensaje=byteLeido;
                        longitud=true;
                        mensaje=new byte[longitudMensaje];
                   }else if(longitudLeida<longitudMensaje){
                       System.out.print(byteLeido);
                       //lo guardo mal en la variable demensaje, solo guarda el ultimo byte
                       mensaje[longitudLeida]=b;
                       ++longitudLeida;
                   }if(longitudLeida==longitudMensaje){
                       System.out.println();
                       for(byte by:mensaje){
                           System.out.print(b & 0xFF);
                       }
                       System.out.println();
                       System.out.println("siguiente");
                       codigo=false;
                       longitud=false;
                       longitudMensaje=-1;
                       longitudLeida=0;
                   }
                   
                }
>>>>>>> 2ed0fdeb942a71e0cf4f192a6caa22223480a706

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
<<<<<<< HEAD
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
    
=======
    private static void comprobarCodigo(byte[] mensaje,int codigo, int longitud){
        switch(codigo){
            case 53:
                tipoMensaje=mensaje;
                opciones.add(tipoMensaje);
                break;
            case 1:
                mascara=mensaje;
                opciones.add(mascara);
                break;
            case 6:
                dns=mensaje;
                opciones.add(dns);
                break;
            case 3:
                router=mensaje;
                opciones.add(router);
                break;
            case 50:
                requestedIp=mensaje;
                opciones.add(requestedIp);
                break;
            case 51:
                tiempoCesion=mensaje;
                opciones.add(tiempoCesion);
                break;
            case 58:
                tiempoRenovacion=mensaje;
                opciones.add(tiempoRenovacion);
                break;
            case 54:
                identificadorServidor=mensaje;
                opciones.add(identificadorServidor);
                break;
        }
    }
>>>>>>> 2ed0fdeb942a71e0cf4f192a6caa22223480a706
}
