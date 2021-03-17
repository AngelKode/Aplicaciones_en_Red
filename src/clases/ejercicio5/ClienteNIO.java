/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 *
 * @author depot
 */
public class ClienteNIO {
    
    private SocketChannel cliente;
    private ByteBuffer buffer;

    public ClienteNIO() {
        try {
            //Creamos y abriendo el socket
            this.cliente = SocketChannel.open();
            //Enlace de conexion al servidor
            SocketAddress ip = new InetSocketAddress("127.0.0.1",2019);
            //Nos conectamos
            this.cliente.connect(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void leer(){
        this.buffer = ByteBuffer.allocate(1024);
        this.buffer.clear();//Limpiamos el buffer
        
        try {
            this.cliente.read(this.buffer);//Leemos lo que manda el servidor
            //Preparamos el buffer para lectura
            this.buffer.flip();
            while(this.buffer.hasRemaining()){
                System.out.print((char)this.buffer.get());
            }
            this.buffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClienteNIO ciente = new ClienteNIO();
        ciente.leer();
    }
    
}
