/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad2.ejercicio3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Random;

/**
 *
 * @author depot
 */
public class ClienteMulticast {
    private final MulticastSocket cliente;
    private DatagramPacket paquete;
    private final InetAddress ip_grupo;
    private byte[] buffer;
    private FileOutputStream in;

    public ClienteMulticast() throws IOException {
        
        this.cliente = new MulticastSocket(3000);
        //Generamos la IP del grupo
        this.ip_grupo = InetAddress.getByName("225.10.10.25");
        //Nos unimos al grupo
        this.cliente.joinGroup(this.ip_grupo);
        this.buffer = new byte[16];//Inicializamos el buffer
        this.in = null;
        this.paquete = null;
        
        System.out.println("Cliente iniciado...");
    }
    
    public void recibirArchivo() throws IOException{
        this.paquete = new DatagramPacket(this.buffer, this.buffer.length);//Cremos un nuevo paquete
        this.cliente.receive(this.paquete);//Recibimos el mensaje que se envía
        this.in.write(buffer);
    }
    
    public void recibirExtension() throws IOException{
        //Obtenemos la extensión y creamos el archivo vacio
        this.paquete = new DatagramPacket(this.buffer, this.buffer.length);//Cremos un nuevo paquete
        this.cliente.receive(this.paquete);//Recibimos el mensaje que se envía
        
        //Creamos un nuevo archivo vacío
        Random random = new Random();
        int numero = random.nextInt(2000);
        String extension = new String(this.paquete.getData());
        String nombreArchivo = "audios-servidor\\" + numero + "." + extension;
        File file = new File(nombreArchivo.trim());
        file.createNewFile();
       
        //Inicializamos el flujo de salida
        this.in = new FileOutputStream(file);
        this.buffer = new byte[32768];//Inicializamos el buffer
    }
    
    public static void main(String[] args) throws IOException {
        ClienteMulticast cliente = new ClienteMulticast();
        ClienteMulticast cliente2 = new ClienteMulticast();
        ClienteMulticast cliente3 = new ClienteMulticast();
        ClienteMulticast cliente4 = new ClienteMulticast();
        ClienteMulticast cliente5 = new ClienteMulticast();
        
        //Obtenemos la extension del archivo que se va a mandar
        cliente.recibirExtension();
        cliente2.recibirExtension();
        cliente3.recibirExtension();
        cliente4.recibirExtension();
        cliente5.recibirExtension();
        System.out.println("Se recibió la extension...");
        
        //Recibimos el archivo
        while(true){
             cliente.recibirArchivo();
             cliente2.recibirArchivo();
             cliente3.recibirArchivo();
             cliente4.recibirArchivo();
             cliente5.recibirArchivo();
        }
        
    }
}
