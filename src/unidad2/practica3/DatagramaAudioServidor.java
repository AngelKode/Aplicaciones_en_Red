/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad2.practica3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author depot
 */
public class DatagramaAudioServidor {
    
    private DatagramSocket servidor;
    private DatagramPacket recibe;
    private DatagramPacket envia;
    private byte[] buffer;
    private FileOutputStream out;
    
    public DatagramaAudioServidor(){
        
        try{ 
            System.out.println("Servidor");
            System.out.println("Esperando que el cliente mande un clip");
            this.servidor = new DatagramSocket(2000);
            this.out = new FileOutputStream(new File("audios-servidor\\nuevo.wav"));
            this.buffer = new byte[1024];
            while(true){
                this.recibe = new DatagramPacket(this.buffer,0,this.buffer.length);
                this.servidor.receive(this.recibe);
                this.buffer = this.recibe.getData();
                this.out.write(this.buffer);
                this.buffer = new byte[1024];
            }
            
        } catch (SocketException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    
    }
    
    public static void main(String[] args){
        DatagramaAudioServidor serv = new DatagramaAudioServidor();
    }

    
}
