/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad2.ejercicio3;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.channels.FileChannel;
import java.util.Random;

/**
 *
 * @author depot
 */
public class ClienteMulticast {
    private final MulticastSocket cliente;
    private DatagramPacket paquete;
    private final InetAddress ip_grupo;
    private final byte[] buffer;
    private final FileChannel canal;
    private final RandomAccessFile archivo;

    public ClienteMulticast() throws IOException {
        
        this.cliente = new MulticastSocket(3000);
        //Generamos la IP del grupo
        this.ip_grupo = InetAddress.getByName("225.10.10.25");
        //Nos unimos al grupo
        this.cliente.joinGroup(this.ip_grupo);
        this.buffer = new byte[32768];//Inicializamos el buffer
        Random random = new Random();
        int numero = random.nextInt(2000);
        File file = new File("prueba"+ numero +".wav");
        file.createNewFile();
       
        this.archivo = new RandomAccessFile(file, "rw");
        this.canal = archivo.getChannel();
    }
    
    public void recibirArchivo() throws IOException{
        this.paquete = new DatagramPacket(this.buffer, this.buffer.length);//Cremos un nuevo paquete
        this.cliente.receive(this.paquete);//Recibimos el mensaje que se envía
        this.archivo.write(buffer);
    }
    
    public static void main(String[] args) throws IOException {
        ClienteMulticast cliente = new ClienteMulticast();
        ClienteMulticast cliente2 = new ClienteMulticast();
        ClienteMulticast cliente3 = new ClienteMulticast();
        ClienteMulticast cliente4 = new ClienteMulticast();
        ClienteMulticast cliente5 = new ClienteMulticast();
        
        while(true){
             cliente.recibirArchivo();
             cliente2.recibirArchivo();
             cliente3.recibirArchivo();
             cliente4.recibirArchivo();
             cliente5.recibirArchivo();
        }
        
    }
}
