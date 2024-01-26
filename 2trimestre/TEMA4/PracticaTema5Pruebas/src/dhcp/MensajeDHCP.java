/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhcp;

import java.nio.ByteBuffer;

/**
 *
 * @author Administrador
 */
public class MensajeDHCP {
    
    private byte[] cabecera;
    private byte[] opciones;
    private byte[] datos;
    
    private byte[] XID;
    private byte[] MAC;
    
    
    
    public MensajeDHCP(byte[] datos){
        this.datos=datos;
        this.cabecera=getCabeceraMsj(datos);
        this.opciones=getOpcionesMsj(datos);
        this.XID=identificarXID(cabecera);
        this.MAC=identificarMAC(cabecera);
        
    }
    public byte[] getDatos(){
        return datos;
    }
    public byte[] getXID(){
        return XID;
    }
    public byte[] getMAC(){
        return MAC;
    }
    public byte[] getCabeceraMsj(byte[] datos){
        byte[] cabecera=extraerDeByteArray(datos,0,236);
        imprimirArrayDeBytes(cabecera);
        return cabecera;
    }
    public byte[] getOpcionesMsj(byte[] datos){
        byte[] opciones=extraerDeByteArray(datos,236,datos.length-237);
        return opciones;
    }
    public byte[] extraerOpcion(int codigo){
        int longitud=getLongitudDeOpcion(codigo);
        int offset=getOffsetDeOpcion(codigo);
        byte[] valor=extraerDeByteArray(opciones,offset+2,longitud);      
        return valor;
    }
    private int getLongitudDeOpcion(int codigo){
        int longitud=4;
        if(codigo==53){
            longitud=1;
        }
        return longitud;
    }
    private int getOffsetDeOpcion(int codigo){
        int offset=0;
        for(int i=0;i<opciones.length&&((opciones[i] & 0xFF)!=codigo);++i){
            ++offset;
        }
        System.out.println(opciones[offset] & 0xFF);
        
        return offset;
    }
    private byte[] extraerDeByteArray(byte[] array,int offset, int longitud){
        ByteBuffer bb=ByteBuffer.allocate(longitud);
        bb.put(array,offset,longitud);
        byte[] resultado=bb.array();
        return resultado;
    }
    private byte[] identificarXID(byte[] cabecera){
        byte[] xid=extraerDeByteArray(cabecera,4,4);
        System.out.println("xid:");
        return xid;
        
    }
    private byte[] identificarMAC(byte[] cabecera){
        byte[] mac=extraerDeByteArray(cabecera,28,16);
        return mac;
    }
    public void imprimirArrayDeBytes(byte[] array){
        for(byte b:array){
            System.out.print(b & 0xFF);
            System.out.print(" ");
        }
        System.out.println();
        System.out.println(array.length);
    }
}
