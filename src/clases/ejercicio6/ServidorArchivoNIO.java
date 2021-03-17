/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.ejercicio6;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 *
 * @author depot
 */
public class ServidorArchivoNIO {
    
    ServerSocketChannel servidor;
    SocketChannel cliente;
    ByteBuffer buffer;
    FileChannel file;

    public ServidorArchivoNIO() throws IOException {
        
        this.servidor = ServerSocketChannel.open();//Creando socket servidor
        SocketAddress ip = new InetSocketAddress(9001);
        
        this.servidor.socket().bind(ip);//Enlazamos el ip para el servidor
        this.servidor.configureBlocking(false);//Para que sea conexion no bloqueante
    
    }
    
    public void recibirArchivo() throws IOException, InterruptedException{
        
        while(true){//Ciclo infinito
            this.cliente = this.servidor.accept();
            if(this.cliente != null){//Si es diferente de nulo, un cliente se ha conectado
                String nombreArchivo = "";//Aqui guardaremos el nombre con el que se va a guardar el archivo
                System.out.println("Cliente: " + this.cliente.getRemoteAddress());
                this.buffer = ByteBuffer.allocate(1024);//Inicializamos el buffer
                this.buffer.clear();//Limpiamos el buffer
                
                this.cliente.read(this.buffer);//Leemos lo que manda el cliente
                this.buffer.flip();//Preparamos el buffer para lectura
                byte[] bytes = new byte[this.buffer.remaining()];//Creamos un arreglo de bytes
                this.buffer.get(bytes);//Convertimos el ByteBuffer a bytes
                nombreArchivo = new String(bytes);//Convertimos el arreglo de bytes a un String
                
                //Creamos el archivo vacío
                new File("archivos_servidor\\"+nombreArchivo.trim()).createNewFile();
                //Obtenemos el canal para escribir el archivo que envía el cliente
                this.file = new RandomAccessFile("archivos_servidor\\"+nombreArchivo.trim(),"rw").getChannel();
                
                this.buffer.clear();//Limpiamos el buffer
                while(this.cliente.read(this.buffer) > 0){//Mientras haya datos por escribir
                    this.buffer.flip();//Preparamos para la lectura
                    this.file.write(this.buffer);//Escribimos en el archivo lo que tiene el buffer
                    this.buffer.clear();//Limpiamos el buffer
                }
                this.file.close();//Cerramos el archivo
                System.out.println("Se recibió el archivo");
                this.cliente.close();//Cerramos el socket del cliente
            }else{//Si no se conectan, esperamos 2 segundos
                System.out.println("Esperando...");
                Thread.sleep(2000);
            }
        }
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        ServidorArchivoNIO serv = new ServidorArchivoNIO();
        serv.recibirArchivo();
    }
    
    
    
}
