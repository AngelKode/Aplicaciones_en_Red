/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad2.ejercicio3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.swing.JFileChooser;

/**
 *
 * @author depot
 */
public class ServidorMulticast {
    
    private static final int port = 3000;
    private static final String IP = "225.10.10.25";
    private final MulticastSocket servidor;
    private DatagramPacket paquete;
    private final InetAddress ip_grupo;
    private byte[] buffer;
    private final File archivo;
    private final FileInputStream out;

    public ServidorMulticast() throws IOException{
        //Inicializamos el servidor
        this.servidor = new MulticastSocket(port);
        //Establecemos direccion IP multicast
        this.ip_grupo = InetAddress.getByName(IP);
        //Unirse al grupo
        this.servidor.joinGroup(this.ip_grupo);
        
        //Obteniendo archivo
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        this.archivo = chooser.getSelectedFile();
        this.out = new FileInputStream(this.archivo);
        
        this.buffer = new byte[16];
        
        System.out.println("Servidor inicializado...");
    }
    
    public void mandarArchivo() throws IOException, InterruptedException{
        while(this.out.read(this.buffer) > 0){
            this.paquete = new DatagramPacket(this.buffer,this.buffer.length,this.ip_grupo,this.port);
            this.servidor.send(this.paquete);
            Thread.sleep(100);
        }
        
        System.out.println("Se mando el archivo");
        this.out.close();
    }
    public void mandarExtensionArchivo() throws IOException{
        String nombre_archivo = this.archivo.getName();
        int punto_final = nombre_archivo.lastIndexOf(".");
        String extension = nombre_archivo.substring(punto_final + 1, nombre_archivo.length());
        this.buffer = extension.getBytes();//Mandamos la extension
        this.paquete = new DatagramPacket(this.buffer,extension.length() ,this.ip_grupo,this.port);//Creamos el paquete
        this.servidor.send(this.paquete);//Enviamos al grupo
        this.buffer = new byte[32768];
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        ServidorMulticast servidor = new ServidorMulticast();
        servidor.mandarExtensionArchivo();
        Thread.sleep(500);
        System.out.println("Mandando archivo...");
        servidor.mandarArchivo();
    }
     
}
