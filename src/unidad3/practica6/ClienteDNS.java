/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad3.practica6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 *
 * @author depot
 */
public class ClienteDNS {
    
    private DatagramSocket cliente;
    private DatagramPacket recibe;
    private DatagramPacket envia;
    private byte[] buffer;

    public ClienteDNS() throws IOException {
        try {
            System.out.println("Cliente conectandose al servidor...");
            this.cliente = new DatagramSocket();
            this.buffer = "http://www.virtual.zacatecas.ipn.mx/moodle/mod/assign/view.php?id=6407".getBytes();
            enviarDatos();
            recibirRespuesta();
    
            this.cliente.close();
            
        } catch (SocketException e) {
        } catch(IOException e){
        }
    }
    
    private void enviarDatos() throws IOException{
        SocketAddress ip_serv = new InetSocketAddress("127.0.0.1",3000);
        //Guardamos el URL o la IP que manda el usuario 
        this.envia = new DatagramPacket(this.buffer,0,this.buffer.length,ip_serv);
        this.cliente.send(this.envia);
        System.out.println("Mensaje enviado");      
    }
    
    private void recibirRespuesta() throws IOException{
        //Recibimos lo del servidor
        this.buffer = new byte[512];
        this.recibe = new DatagramPacket(this.buffer,0,this.buffer.length);
        this.cliente.receive(this.recibe);
        String msg = new String(this.recibe.getData());
        System.out.println("Servidor dice: " + msg);
    }
    public static void main(String[] args) throws IOException {
        ClienteDNS cliente = new ClienteDNS();
    }
    
    
}
