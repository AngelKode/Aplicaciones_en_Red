/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 *
 * @author depot
 */
public class ServidorNIO {
    
    private final ServerSocketChannel servidor;
    private SocketChannel cliente;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;

    public ServidorNIO() throws IOException, InterruptedException {
        this.servidor = ServerSocketChannel.open();
        InetSocketAddress ip = new InetSocketAddress(2000);
        this.servidor.socket().bind(ip);
        this.servidor.configureBlocking(false);
        
        while(true){
            System.out.println("Servidor activo...");
            this.cliente = this.servidor.accept();
            
            if(cliente != null){
                this.salida = new ObjectOutputStream(Channels.newOutputStream(this.cliente));
                Empleado empleado = new Empleado("Pedro", "juan@live.com.mx");
                this.salida.writeObject(empleado);
                this.cliente.close();
            }else{
                Thread.sleep(2000);
            }
        }
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        ServidorNIO serv = new ServidorNIO();
    }
    
}
