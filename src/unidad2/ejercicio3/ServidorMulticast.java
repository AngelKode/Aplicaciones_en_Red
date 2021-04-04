/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad2.ejercicio3;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javax.swing.JFileChooser;

/**
 *
 * @author depot
 */
public class ServidorMulticast {
    
    private static final int port = 3000;
    private static final String IP = "225.10.10.25";
    private final MulticastSocket servidor;
    private DatagramPacket paquete;
    private final InetAddress ip_grupo;
    private final ByteBuffer buffer;
    private final FileChannel canal;

    public ServidorMulticast() throws IOException, InterruptedException {
        //Inicializamos el servidor
        this.servidor = new MulticastSocket(port);
        //Establecemos direccion IP multicast
        this.ip_grupo = InetAddress.getByName(IP);
        //Unirse al grupo
        this.servidor.joinGroup(this.ip_grupo);
        
        //Obteniendo archivo
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        RandomAccessFile archivo = new RandomAccessFile(chooser.getSelectedFile(), "rw");
        this.canal = archivo.getChannel();
        
        this.buffer = ByteBuffer.allocate(32768);
        
        while(this.canal.read(buffer) > 0){
            this.buffer.flip();//Prepara para lectura
            this.paquete = new DatagramPacket(this.buffer.array(),0,this.buffer.remaining(),this.ip_grupo,port);
            this.servidor.send(this.paquete);
            this.buffer.clear();
            Thread.sleep(100);
        }
        
        System.out.println("Se mando el audio");
        this.canal.close();
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        ServidorMulticast servidor = new ServidorMulticast();
    }
     
}
