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
import java.nio.channels.SocketChannel;

/**
 * @author depot
 */
public class ClienteNIO {
    private final SocketChannel cliente;
    private final ObjectInputStream entrada;
    private ObjectOutputStream salida;

    public ClienteNIO() throws IOException, ClassNotFoundException {
        
        this.cliente = SocketChannel.open();
        InetSocketAddress ip = new InetSocketAddress("127.0.0.1",2000);
        this.cliente.connect(ip);
        this.entrada = new ObjectInputStream(Channels.newInputStream(this.cliente));
        Empleado empleado = (Empleado)this.entrada.readObject();
        System.out.println(empleado.getNombre() + "\n" + empleado.getEmail());
        this.cliente.close();
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClienteNIO cliente = new ClienteNIO();
    }
    
    
}
