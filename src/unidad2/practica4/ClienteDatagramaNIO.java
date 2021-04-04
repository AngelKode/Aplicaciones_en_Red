/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad2.practica4;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import javax.swing.JFileChooser;

/**
 *
 * @author depot
 */
public class ClienteDatagramaNIO {
    
    private final static InetSocketAddress IP_SERVIDOR = new InetSocketAddress("127.0.0.1",3000);
    private final DatagramChannel cliente;
    private ByteBuffer buffer;
    private FileChannel audio_in;

    public ClienteDatagramaNIO() throws IOException {
        
        this.cliente = DatagramChannel.open();//Creamos  el canal
        this.cliente.configureBlocking(false);//Modo no bloqueante
        System.out.println("Cliente activo");//Mandamos un mensaje
    }
    
    public void enviarAudio(File audio) throws IOException, InterruptedException{
        RandomAccessFile randomAccess = new RandomAccessFile(audio, "rw");//Creamos la conexion con el archivo
        this.audio_in = randomAccess.getChannel();//Obtenemos el canal
        this.buffer = ByteBuffer.allocate(32768);//Instanciamos el buffer con un tamaño de 2^15 bits
        while(this.audio_in.read(this.buffer) > 0){
            //Preparamos el buffer para la lectura
            this.buffer.flip();
            this.cliente.send(this.buffer, ClienteDatagramaNIO.IP_SERVIDOR);//Mandamos al servidor
            this.buffer.clear();//Limpiamos el buffer
            Thread.sleep(100);
        }
        
        System.out.println("Se ha enviado el audio correctamente");
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        ClienteDatagramaNIO cliente = new ClienteDatagramaNIO();
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        cliente.enviarAudio(chooser.getSelectedFile());
    }
}
