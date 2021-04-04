/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad2.ejercicio2;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 *
 * @author depot
 */
public class ClienteDatagramaNIO {
    
    private final static InetSocketAddress IP_SERVIDOR = new InetSocketAddress("127.0.0.1",3000);
    private final DatagramChannel cliente;
    private ByteBuffer buffer;
    private FileInputStream audio_in;

    public ClienteDatagramaNIO() throws IOException {
        
        this.cliente = DatagramChannel.open();//Creamos  el canal
        this.cliente.configureBlocking(false);//Modo no bloqueante
        System.out.println("Cliente activo");//Mandamos un mensaje
    }
    
    public void enviarMensaje(String mensaje) throws IOException{
        this.buffer = ByteBuffer.wrap(mensaje.getBytes());//Creamos el nuevo buffer con el mensaje que se quiere mandar
        this.cliente.send(this.buffer, ClienteDatagramaNIO.IP_SERVIDOR);//Mandamos al servidor
        this.buffer.clear();//Limpiamos el buffer
        System.out.println("Se ha enviado el mensaje correctamente");
    }
    
    public static void main(String[] args) throws IOException {
        ClienteDatagramaNIO cliente = new ClienteDatagramaNIO();
        cliente.enviarMensaje("HOLA SERVIDOR");
    }
}
