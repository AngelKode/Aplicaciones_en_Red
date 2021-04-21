/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad2.ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author depot
 */
public class ServidorDatagrama {
    
    private DatagramSocket servidor;
    private DatagramPacket recibe;
    private DatagramPacket envia;
    private byte[] buffer;

    public ServidorDatagrama() {
        
        try {
            System.out.println("Servidor");
            this.servidor = new DatagramSocket(2000);
            System.out.println(servidor.getPort());
            this.buffer = new byte[512];
            //creamos paquetes de datagrama par recibir
            this.recibe = new DatagramPacket(this.buffer,0,this.buffer.length);
            this.servidor.receive(this.recibe);
            String msg = new String(this.recibe.getData());
            System.out.println("Recibido: " + msg);
            //Ahora mandamos mensaje al cliente
            this.buffer = "Recibí tu mensaje cliente!".getBytes();
            this.envia = new DatagramPacket(this.buffer,0,this.buffer.length,this.recibe.getAddress(),this.recibe.getPort());
            this.servidor.send(this.envia);
            System.out.println("Mensaje enviado al cliente");
            this.servidor.close();
        } catch (SocketException e) {
        }catch(IOException e){
        }
    
    }
    
    public static void main(String[] args) {
        ServidorDatagrama servidor = new ServidorDatagrama();
    }
    
    
}
