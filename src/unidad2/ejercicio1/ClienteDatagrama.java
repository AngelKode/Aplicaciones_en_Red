/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad2.ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

/**
 *
 * @author depot
 */
public class ClienteDatagrama {
    
    private DatagramSocket cliente;
    private DatagramPacket recibe;
    private DatagramPacket envia;
    private byte[] buffer;

    public ClienteDatagrama() {
        
        try {
            System.out.println("Cliente");
            this.cliente = new DatagramSocket();
            this.buffer = "Estamos mandando mensaje al servidor".getBytes();
            SocketAddress ip_serv = new InetSocketAddress("127.0.0.1",2000);
            this.envia = new DatagramPacket(this.buffer,0,this.buffer.length,ip_serv);
            this.cliente.send(this.envia);
            System.out.println("Mensaje enviado");
            //Recibimos lo del servidor
            this.buffer = new byte[512];
            this.recibe = new DatagramPacket(this.buffer,0,this.buffer.length);
            this.cliente.receive(this.recibe);
            String msg = new String(this.recibe.getData());
            System.out.println("Servidor dice: " + msg);
            this.cliente.close();
        } catch (SocketException e) {
        } catch(IOException e){
        
        }
    }
    
    public static void main(String[] args) {
        ClienteDatagrama cliente = new ClienteDatagrama();
    }
    
    
}
