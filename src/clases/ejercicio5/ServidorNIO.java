/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 *
 * @author depot
 */
public class ServidorNIO {
    
    private ServerSocketChannel servidor;
    private SocketChannel cliente;
    private ByteBuffer buffer;

    public ServidorNIO() {
        try{
            //Creando el socket servidor
            servidor = ServerSocketChannel.open();
            //Indicamos el puerto de escucha del servidor
            InetSocketAddress ip = new InetSocketAddress(2019);
            //Asignamos al servidor el puerto definido en el socketaddress
            servidor.socket().bind(ip);
            //Establecemos el modo no bloqueante
            servidor.configureBlocking(false);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void escucha(String msg){
        //Creamos el buffer del tamaño del mensaje
        this.buffer = ByteBuffer.wrap(msg.getBytes());
        
        while(true){
            try {
                System.out.println("El servidor esta esperando");
                this.cliente = servidor.accept();
                
                if(this.cliente == null){
                    Thread.sleep(2000);
                }else{
                    System.out.println("Se conecto: " + cliente.getRemoteAddress().toString());
                    this.buffer.rewind();//Preparamos el buffer para escritura
                    this.cliente.write(this.buffer);
                    this.cliente.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        ServidorNIO serv = new ServidorNIO();
        serv.escucha("Hola cliente, bienvenido al servidor patito");
    }
    
}
