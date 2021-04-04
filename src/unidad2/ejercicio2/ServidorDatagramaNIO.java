/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad2.ejercicio2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 *
 * @author depot
 */
public class ServidorDatagramaNIO {
    
    private final static InetSocketAddress IP_SERVIDOR = new InetSocketAddress(3000);//Puerto de escucha del servidor
    private final DatagramChannel servidor;
    private ByteBuffer buffer;

    public ServidorDatagramaNIO() throws IOException {
        this.servidor = DatagramChannel.open();//Iniciamos el servidor
        this.servidor.bind(IP_SERVIDOR);//Conectamos el servidor al puerto
        this.servidor.configureBlocking(false);//Modo no bloqueante
        System.out.println("Servidor activo");
    }
    
    public void recibirMensaje() throws IOException, InterruptedException{
        this.buffer = ByteBuffer.allocate(1024);
        SocketAddress remitente = null;
        
        while(true){
            if((remitente = this.servidor.receive(this.buffer)) != null){
                this.buffer.flip();//Preparamos para la lectura
                System.out.println(new String(this.buffer.array()));
                this.buffer.clear();
                remitente = null;
            }else{
                System.out.println("Esperando cliente...");
                Thread.sleep(1000);
            }
        }  
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        ServidorDatagramaNIO servidor = new ServidorDatagramaNIO();
        servidor.recibirMensaje();
    }
}
