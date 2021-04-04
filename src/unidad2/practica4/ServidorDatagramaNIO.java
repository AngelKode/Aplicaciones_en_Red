/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad2.practica4;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;

/**
 *
 * @author depot
 */
public class ServidorDatagramaNIO {
    
    private final static InetSocketAddress IP_SERVIDOR = new InetSocketAddress(3000);//Puerto de escucha del servidor
    private final DatagramChannel servidor;
    private ByteBuffer buffer;
    private FileChannel out;

    public ServidorDatagramaNIO() throws IOException {
        this.servidor = DatagramChannel.open();//Iniciamos el servidor
        this.servidor.bind(IP_SERVIDOR);//Conectamos el servidor al puerto
        this.servidor.configureBlocking(false);//Modo no bloqueante
        System.out.println("Servidor activo");
    }
    
    public void recibirAudio() throws IOException, InterruptedException{
        this.buffer = ByteBuffer.allocate(32768);
        SocketAddress remitente = null;
        //Creamos el archivo
        File file = new File("audios-servidor\\nuevo.wav");
        file.createNewFile();
        //Obtenemos el canal donde escribiremos el nuevo archivo
        RandomAccessFile random = new RandomAccessFile(file, "rw");
                
        while(true){
            this.out = random.getChannel();
            if((remitente = this.servidor.receive(this.buffer)) != null){
                this.buffer.rewind();//Preparamos para la lectura
                this.out.write(this.buffer);
                this.buffer.clear();
                remitente = null;
            }else{
                System.out.println("Esperando cliente...");
                Thread.sleep(100);
            }
        }  
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        ServidorDatagramaNIO servidor = new ServidorDatagramaNIO();
        servidor.recibirAudio();
    }
}
