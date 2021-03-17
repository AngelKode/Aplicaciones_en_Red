/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import javax.swing.JFileChooser;

/**
 *
 * @author depot
 */
public class ClienteArchivoNIO {
    
    SocketChannel cliente;
    ByteBuffer buffer;
    
    //Para poder leer/escribir el archivo
    FileChannel file;

    public ClienteArchivoNIO() throws IOException{
        this.cliente = SocketChannel.open();//Creando socket y estableciendo canal de comunicacion
        SocketAddress ip_servidor = new InetSocketAddress("127.0.0.1",9001);//Establecemos la IP y puerto del servidor
        this.cliente.connect(ip_servidor);//Conectamos con el servidor
    }
    
    public void enviarArchivo(File archivo) throws FileNotFoundException, IOException, InterruptedException{
        RandomAccessFile random = new RandomAccessFile(archivo, "rw");
        this.file = random.getChannel();//Obtenemos el canal del archivo
        this.buffer = ByteBuffer.wrap(new byte[(int) archivo.length()]);//Inicializamos el buffer del tamaño del archivo
        
        //Primero enviamos el nombre del archivo
        this.buffer.put(archivo.getName().getBytes())//Ponemos los bytes del nombre en el buffer
                   .rewind();//Preparamos el buffer
        this.cliente.write(this.buffer);//Mandamos al servidor
        
        this.buffer.clear();//Limpiamos el buffer
  
        //Ciclo para lectura del archivo y enviando el archivo al servidor
        while(this.file.read(this.buffer) > 0){
            this.buffer.flip();//Prepara para lectura
            this.cliente.write(this.buffer);//Envia al servidor el buffer con el contenido del archivo
            this.buffer.clear();//Limpiamos el buffer
        }
        //Cerramos el canal
        this.file.close();
        System.out.println("El archivo se ha enviado");
        this.cliente.close();//Cerramos el socket del cliente
    } 
    public static void main(String[] args) throws IOException, FileNotFoundException, InterruptedException {
        ClienteArchivoNIO cliente = new ClienteArchivoNIO();
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        cliente.enviarArchivo(chooser.getSelectedFile());
    }
    
    
    
}
