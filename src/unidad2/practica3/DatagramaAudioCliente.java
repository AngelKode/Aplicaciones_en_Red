/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad2.practica3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import javax.swing.JFileChooser;

/**
 *
 * @author depot
 */
public class DatagramaAudioCliente {
    
    private DatagramSocket cliente;
    private DatagramPacket recibe;
    private DatagramPacket envia;
    private byte[] buffer;
    private FileInputStream in;
    
     public DatagramaAudioCliente() throws InterruptedException{
        
        try {
            System.out.println("Cliente");
            System.out.println("Estamos esperando a que mandes un audio...");
            this.cliente = new DatagramSocket();//Creamos un nuevo socket
            SocketAddress ip_serv = new InetSocketAddress("127.0.0.1",2000);//Direccion del cliente y puerto
            //Obtenemos el audio a mandar
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            //Creamos un nuevo stream para el archivo
            this.in = new FileInputStream(chooser.getSelectedFile());
            //Una vez obtenido el audio, empezamos a mandar al servidor
            this.buffer = new byte[1024];
            while(this.in.read(this.buffer) != -1){//Mientras aun haya datos por leer
                //Creamos un nuevo paquete
                this.envia = new DatagramPacket(this.buffer,0,this.buffer.length,ip_serv);
                //Enviamos al servidor
                this.cliente.send(this.envia);
            }
            //Al mandarse el audio, mandamos un mensaje al cliente
            System.out.println("Audio enviado");
            this.in.close();
            this.cliente.close();
        } catch (SocketException e) {
        } catch(IOException e){
        
        }
    }
     
    public static void main(String[] args) throws InterruptedException{
        DatagramaAudioCliente cliente = new DatagramaAudioCliente();
    }
    
}
